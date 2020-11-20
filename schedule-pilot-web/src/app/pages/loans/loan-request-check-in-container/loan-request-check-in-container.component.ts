import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ParametersQuery } from '@models/parameters-query';
import { RequestCheckInResponse } from '@models/request-check-in/response/request-check-in-response';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { LoansService } from '@services/loans/loans.service';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { RequestCheckInPaginatorConfiguration } from 'app/config/pagination/request-check-in-paginator-configuration';
import { merge, of } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-loan-request-check-in-container',
  templateUrl: './loan-request-check-in-container.component.html',
  styleUrls: ['./loan-request-check-in-container.component.scss'],
  providers: [
    {
      provide: MatPaginatorIntl,
      useValue: new RequestCheckInPaginatorConfiguration().getPaginatorIntl(),
    },
  ],
})
export class LoanRequestCheckInContainerComponent implements OnInit, AfterViewInit {

  public displayedColumns: string[] = [
    'number',
    'name',
    'trackId',
    'loanDate',
    'createdDate',
    'status'
  ];

  public data: RequestCheckInResponse[] = [];
  public resultsLength = 0;
  public isLoadingResults = true;
  public isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private scrollTop: ScrollTopService, public loanService: LoansService,
    public authService: AuthenticationService) { }

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    this.refreshRequestCheckIn();
  }

  refreshRequestCheckIn() {
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
          return this.loanService!.getRequestCheckIn(parametersQuery, this.authService.userSession.user.id);
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


}
