import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ParametersQuery } from '@models/parameters-query';
import { TicketCheckLogRequest } from '@models/ticket-check-log/request/ticket-check-log-request';
import { RequestTicketCheckOutParameters } from '@models/ticket-check-out/request/request-ticket-check-out-parameters';
import { TicketCheckOutResponse } from '@models/ticket-check-out/response/ticket-check-out-response';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { LoansService } from '@services/loans/loans.service';
import { MessagesService } from '@services/messages/message.service';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { TicketCheckInPaginatorConfiguration } from 'app/config/pagination/ticket-check-in-pagination-configuration';
import { merge, of } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-return-ticket-check-out-container',
  templateUrl: './return-ticket-check-out-container.component.html',
  styleUrls: ['./return-ticket-check-out-container.component.scss'],
  providers: [
    {
      provide: MatPaginatorIntl,
      useValue: new TicketCheckInPaginatorConfiguration().getPaginatorIntl(),
    },
  ],
})
export class ReturnTicketCheckOutContainerComponent implements OnInit, AfterViewInit {

  public displayedColumns: string[] = [
    'number',
    'trackIdTicketOut',
    'trackIdTicketIn',
    'deliveryDate',
    'returnDate',
    'item',
    'status',
    'validate'
  ];

  public data: TicketCheckOutResponse[] = [];
  public resultsLength = 0;
  public isLoadingResults = true;
  public isRateLimitReached = false;
  public requestTicketCheckOutParameters: RequestTicketCheckOutParameters;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private scrollTop: ScrollTopService, public loanService: LoansService,
    public authService: AuthenticationService, public messageService: MessagesService) { }

  ngOnInit(): void {
    this.requestTicketCheckOutParameters = new RequestTicketCheckOutParameters();
    this.scrollTop.setScrollTop();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    this.refreshTicketCheckOut(); 
  }

  refreshTicketCheckOut() {
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
          return this.loanService!.getTicketCheckOut(parametersQuery, this.authService.userSession.user.id,
            this.requestTicketCheckOutParameters);
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

  public filterRequestTicketCheckOut(requestTicketCheckOutParameters: RequestTicketCheckOutParameters): void {
    this.requestTicketCheckOutParameters = requestTicketCheckOutParameters;
    this.refreshTicketCheckOut();
  }

  public validateTicketCheckOut(ticketCheckOut: TicketCheckOutResponse) {
    let requestTicketCheckLog: TicketCheckLogRequest = new TicketCheckLogRequest();
    requestTicketCheckLog.userAccountId = this.authService.userSession.user.id;
    requestTicketCheckLog.trackIdentificationCheckOut = ticketCheckOut.trackIdTicketOut;

    this.messageService.generateConfirmMessage(null, 'Se redimira el ticketCheckOut.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.loanService.createTicketCheckLog(requestTicketCheckLog).subscribe((bodyResponse: any) => {
            this.messageService.generateSuccessMessage('Validar TicketCheckOut', 'El ticket ha sido validado correctamente.');
            this.refreshTicketCheckOut();
          });
        }
      });
  }

}
