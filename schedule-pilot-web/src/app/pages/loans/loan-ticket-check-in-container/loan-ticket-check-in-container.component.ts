import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ParametersQuery } from '@models/parameters-query';
import { TicketCheckInResponse } from '@models/ticket-check-in/response/ticket-check-in-response';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { LoansService } from '@services/loans/loans.service';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { merge, of } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { TicketCheckInPaginatorConfiguration } from '../../../config/pagination/ticket-check-in-pagination-configuration';
import { TicketCheckOutRequest } from '@models/ticket-check-out/request/ticket-check-out-request';
import { MessagesService } from '@services/messages/message.service';

@Component({
  selector: 'app-loan-ticket-check-in-container',
  templateUrl: './loan-ticket-check-in-container.component.html',
  styleUrls: ['./loan-ticket-check-in-container.component.scss'],
  providers: [
    {
      provide: MatPaginatorIntl,
      useValue: new TicketCheckInPaginatorConfiguration().getPaginatorIntl(),
    },
  ],
})
export class LoanTicketCheckInContainerComponent implements OnInit, AfterViewInit {

  public displayedColumns: string[] = [
    'number',
    'trackIdTicket',
    'trackIdRequest',
    'deliveryDate',
    'returnDate',
    'item',
    'status',
    'validate'
  ];

  public data: TicketCheckInResponse[] = [];
  public resultsLength = 0;
  public isLoadingResults = true;
  public isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private scrollTop: ScrollTopService, public loanService: LoansService,
    public authService: AuthenticationService, public messageService: MessagesService) { }

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    this.refreshTicketCheckIn(); 
  }

  refreshTicketCheckIn() {
    this.paginator.pageIndex = 0;
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          let parametersQuery = new ParametersQuery();
          parametersQuery.page = this.paginator.pageIndex;
          parametersQuery.per_page = this.paginator.pageSize;
          parametersQuery.order_by = `${this.sort.direction}:${this.sort.active}:1`;
          return this.loanService!.getTicketCheckIn(parametersQuery, this.authService.userSession.user.id);
        }),
        map((data) => {
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.result.totalRows;
          return data.result.content;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return of([]);
        })
      )
      .subscribe((data) => (this.data = data));
  }

  public validateTicketCheckIn(ticketCheckIn: TicketCheckInResponse) {
    let requestTicketCheckOut: TicketCheckOutRequest = new TicketCheckOutRequest();
    requestTicketCheckOut.userAccountId = this.authService.userSession.user.id;
    requestTicketCheckOut.trackIdentificationCheckIn = ticketCheckIn.trackIdTicket;

    this.messageService.generateConfirmMessage(null, 'Se redimira el ticketCheckIn.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.loanService.createTicketCheckOut(requestTicketCheckOut).subscribe((bodyResponse: any) => {
            this.messageService.generateSuccessMessage('Validar TicketCheckIn', 'El ticket ha sido validado correctamente.');
            this.refreshTicketCheckIn();
          });
        }
      });
  }

}
