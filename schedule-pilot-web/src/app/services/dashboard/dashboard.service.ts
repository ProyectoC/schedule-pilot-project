import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { Observable } from 'rxjs';
import { DashboardMessageService } from '../../services/messages/dashboard.message.service';
import { GeneralChart } from '../../models/dashboard/general-chart';
import { Response } from '@models/response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { ErrorResponse } from '@models/error-response';
import { RequestReport } from '@models/reports/request-report';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  public apiScheduleEndPoint: string =
    environment.apis['schedule-api']['end.point'];

  constructor(public httpClient: HttpClient, private dashboardMessageService: DashboardMessageService) {

  }

  public getDashboardStatusOperations(): Observable<GeneralChart> {
    return this.httpClient.get<Response<GeneralChart>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_DASHBOARD_GET_STATUS_OPERATIONS}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.dashboardMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.dashboardMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.dashboardMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.dashboardMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public getDashboardStatusLoanMade(): Observable<GeneralChart> {
    return this.httpClient.get<Response<GeneralChart>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_DASHBOARD_GET_STATUS_LOAN_MADE}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.dashboardMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.dashboardMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.dashboardMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.dashboardMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public getDashboardStatusReturnMade(): Observable<GeneralChart> {
    return this.httpClient.get<Response<GeneralChart>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_DASHBOARD_GET_STATUS_RETURN_MADE}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.dashboardMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.dashboardMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.dashboardMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.dashboardMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public getDashboardPrincipal(): Observable<GeneralChart> {
    return this.httpClient.get<Response<GeneralChart>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_DASHBOARD_GET_PRINCIPAL}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.dashboardMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.dashboardMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.dashboardMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.dashboardMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  // Reports
  public getDashboardLoanProducts(requestReport: RequestReport): Observable<GeneralChart> {

    let parameters = new HttpParams();

    if(requestReport.startDate != null && requestReport.startDate.trim() != "") {
      parameters = parameters.append('date_start', requestReport.startDate);
    }
    if(requestReport.endDate != null && requestReport.endDate.trim() != "") {
      parameters = parameters.append('date_end', requestReport.endDate);
    }

    return this.httpClient.get<Response<GeneralChart>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_DASHBOARD_GET_LOAN_PRODUCTS}`, { params: parameters }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.dashboardMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.dashboardMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.dashboardMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.dashboardMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }


  public getDashboardRequestVsLoans(requestReport: RequestReport): Observable<GeneralChart> {

    let parameters = new HttpParams();

    if(requestReport.startDate != null && requestReport.startDate.trim() != "") {
      parameters = parameters.append('date_start', requestReport.startDate);
    }
    if(requestReport.endDate != null && requestReport.endDate.trim() != "") {
      parameters = parameters.append('date_end', requestReport.endDate);
    }

    return this.httpClient.get<Response<GeneralChart>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_DASHBOARD_GET_REQUEST_VS_LOANS}`, { params: parameters }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.dashboardMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.dashboardMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.dashboardMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.dashboardMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public getDashboardPenaltySummary(requestReport: RequestReport): Observable<GeneralChart> {

    let parameters = new HttpParams();

    if(requestReport.startDate != null && requestReport.startDate.trim() != "") {
      parameters = parameters.append('date_start', requestReport.startDate);
    }
    if(requestReport.endDate != null && requestReport.endDate.trim() != "") {
      parameters = parameters.append('date_end', requestReport.endDate);
    }

    return this.httpClient.get<Response<GeneralChart>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_DASHBOARD_GET_PENALTY_SUMMARY}`, { params: parameters }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.dashboardMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.dashboardMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.dashboardMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.dashboardMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }



}
