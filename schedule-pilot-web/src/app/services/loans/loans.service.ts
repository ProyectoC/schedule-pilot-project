import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommonConstants } from '@constants/common-constants';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { environment } from '@env/environment';
import { ErrorResponse } from '@models/error-response';
import { ParametersQuery } from '@models/parameters-query';
import { RequestCheckIn } from '@models/request-check-in/request-check-in';
import { RequestCheckInResponse } from '@models/request-check-in/response/request-check-in-response';
import { Response } from '@models/response';
import { ResponsePage } from '@models/response-page';
import { ServiceUtils } from '@utils/service-utils';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { LoanMessageService } from '../messages/loan.message.service';
import { TicketCheckInResponse } from '@models/ticket-check-in/response/ticket-check-in-response';
import { TicketCheckOutRequest } from '@models/ticket-check-out/request/ticket-check-out-request';
import { TicketCheckOutResponse } from '@models/ticket-check-out/response/ticket-check-out-response';
import { TicketCheckLogRequest } from '@models/ticket-check-log/request/ticket-check-log-request';
import { RequestCheckInParameters } from '@models/request-check-in/request/request-check-in-search-parameters';
import { RequestTicketCheckInParameters } from '@models/ticket-check-in/request/request-ticket-check-in-parameters';
import { RequestTicketCheckOutParameters } from '@models/ticket-check-out/request/request-ticket-check-out-parameters';

@Injectable({
  providedIn: 'root'
})
export class LoansService {

  public apiScheduleEndPoint: string = environment.apis['schedule-api']['end.point'];

  constructor(
    public httpClient: HttpClient,
    private loanMessageService: LoanMessageService
  ) {
  }

  public createRequestCheckIn(requestCheckIn: RequestCheckIn): Observable<any> {
    return this.httpClient.post<any>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_CREATE_REQUEST_CHECK_IN}`, requestCheckIn
    ).pipe(
      map((bodyResponse) => {
        if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
          return bodyResponse;
        } else {
          this.loanMessageService.generateCreateRequestCheckInError(
            bodyResponse.result.message
          );
        }
        return bodyResponse;
      }),
      catchError((err) => {
        const httpErrorResponse: HttpErrorResponse = err;
        switch (httpErrorResponse.status) {
          case 0:
            this.loanMessageService.generateCreateRequestCheckInError(null);
            break;
          case 400:
            const errorResponse: ErrorResponse = err.error;
            this.loanMessageService.generateCreateRequestCheckInError(
              errorResponse.result.message
            );
            break;
          default:
            this.loanMessageService.generateCreateRequestCheckInError(
              err.message
            );
            break;
        }
        throw Error(err);
      })
    );
  }

  public getRequestCheckIn(parametersQuery: ParametersQuery, userAccountId: number,
    requestCheckInParameters: RequestCheckInParameters): Observable<Response<ResponsePage<RequestCheckInResponse>>> {
    let parameters = ServiceUtils.getHttpParameters(parametersQuery);

    if(requestCheckInParameters.productName != null && requestCheckInParameters.productName.trim() != "") {
      parameters = parameters.append('product_name', requestCheckInParameters.productName);
    }
    if(requestCheckInParameters.trackId != null && requestCheckInParameters.trackId.trim() != "") {
      parameters = parameters.append('track_id', requestCheckInParameters.trackId);
    }
    if(requestCheckInParameters.status != null && requestCheckInParameters.status.trim() != "") {
      parameters = parameters.append('status', requestCheckInParameters.status);
    }
    if(requestCheckInParameters.loanDateStart != null && requestCheckInParameters.loanDateStart.trim() != "") {
      parameters = parameters.append('loan_date_start', requestCheckInParameters.loanDateStart);
    }
    if(requestCheckInParameters.loanDateEnd != null && requestCheckInParameters.loanDateEnd.trim() != "") {
      parameters = parameters.append('loan_date_end', requestCheckInParameters.loanDateEnd);
    }
    if(requestCheckInParameters.requestDateStart != null && requestCheckInParameters.requestDateStart.trim() != "") {
      parameters = parameters.append('request_date_start', requestCheckInParameters.requestDateStart);
    }
    if(requestCheckInParameters.requestDateEnd != null && requestCheckInParameters.requestDateEnd.trim() != "") {
      parameters = parameters.append('request_date_end', requestCheckInParameters.requestDateEnd);
    }

    return this.httpClient.get<Response<ResponsePage<RequestCheckInResponse>>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_REQUEST_CHECK_IN}/${userAccountId}/request-check-in`, { params: parameters }
    ).pipe(
      map((bodyResponse) => {
        if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
          return bodyResponse;
        } else {
          this.loanMessageService.generateLoanDefaultError(
            bodyResponse.description
          );
        }
        return bodyResponse;
      }),
      catchError((err) => {
        const httpErrorResponse: HttpErrorResponse = err;
        switch (httpErrorResponse.status) {
          case 0:
            this.loanMessageService.generateLoanDefaultError(null);
            break;
          case 401:
            const errorResponse: ErrorResponse = err.error;
            this.loanMessageService.generateLoanDefaultError(
              errorResponse.result.message
            );
            break;
          default:
            this.loanMessageService.generateLoanDefaultError(
              err.message
            );
            break;
        }
        throw Error(err);
      })
    );
  }

  public getTicketCheckIn(parametersQuery: ParametersQuery, userAccountId: number,
    requestTicketCheckInParameters: RequestTicketCheckInParameters): Observable<Response<ResponsePage<TicketCheckInResponse>>> {
    let parameters = ServiceUtils.getHttpParameters(parametersQuery);

    if(requestTicketCheckInParameters.trackIdTicket != null && requestTicketCheckInParameters.trackIdTicket.trim() != "") {
      parameters = parameters.append('track_id_ticket', requestTicketCheckInParameters.trackIdTicket);
    }
    if(requestTicketCheckInParameters.trackIdRequest != null && requestTicketCheckInParameters.trackIdRequest.trim() != "") {
      parameters = parameters.append('track_id_request', requestTicketCheckInParameters.trackIdRequest);
    }
    if(requestTicketCheckInParameters.itemName != null && requestTicketCheckInParameters.itemName.trim() != "") {
      parameters = parameters.append('item_name', requestTicketCheckInParameters.itemName);
    }
    if(requestTicketCheckInParameters.status != null && requestTicketCheckInParameters.status.trim() != "") {
      parameters = parameters.append('status', requestTicketCheckInParameters.status);
    }
    if(requestTicketCheckInParameters.deliveryDateStart != null && requestTicketCheckInParameters.deliveryDateStart.trim() != "") {
      parameters = parameters.append('delivery_date_start', requestTicketCheckInParameters.deliveryDateStart);
    }
    if(requestTicketCheckInParameters.deliveryDateEnd != null && requestTicketCheckInParameters.deliveryDateEnd.trim() != "") {
      parameters = parameters.append('delivery_date_end', requestTicketCheckInParameters.deliveryDateEnd);
    }
    if(requestTicketCheckInParameters.returnDateStart != null && requestTicketCheckInParameters.returnDateStart.trim() != "") {
      parameters = parameters.append('return_date_start', requestTicketCheckInParameters.returnDateStart);
    }
    if(requestTicketCheckInParameters.returnDateEnd != null && requestTicketCheckInParameters.returnDateEnd.trim() != "") {
      parameters = parameters.append('return_date_end', requestTicketCheckInParameters.returnDateEnd);
    }

    return this.httpClient.get<Response<ResponsePage<TicketCheckInResponse>>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_TICKET_CHECK_IN}/${userAccountId}/ticket-check-in`, { params: parameters }
    ).pipe(
      map((bodyResponse) => {
        if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
          return bodyResponse;
        } else {
          this.loanMessageService.generateLoanDefaultError(
            bodyResponse.description
          );
        }
        return bodyResponse;
      }),
      catchError((err) => {
        const httpErrorResponse: HttpErrorResponse = err;
        switch (httpErrorResponse.status) {
          case 0:
            this.loanMessageService.generateLoanDefaultError(null);
            break;
          case 401:
            const errorResponse: ErrorResponse = err.error;
            this.loanMessageService.generateLoanDefaultError(
              errorResponse.result.message
            );
            break;
          default:
            this.loanMessageService.generateLoanDefaultError(
              err.message
            );
            break;
        }
        throw Error(err);
      })
    );
  }

  public createTicketCheckOut(ticketCheckOutRequest: TicketCheckOutRequest): Observable<any> {
    return this.httpClient.post<any>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_CREATE_TICKET_CHECK_OUT}`, ticketCheckOutRequest
    ).pipe(
      map((bodyResponse) => {
        if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
          return bodyResponse;
        } else {
          this.loanMessageService.generateCreateTicketCheckOutError(
            bodyResponse.description
          );
        }
        return bodyResponse;
      }),
      catchError((err) => {
        const httpErrorResponse: HttpErrorResponse = err;
        switch (httpErrorResponse.status) {
          case 0:
            this.loanMessageService.generateCreateTicketCheckOutError(null);
            break;
          case 401:
            const errorResponse: ErrorResponse = err.error;
            this.loanMessageService.generateCreateTicketCheckOutError(
              errorResponse.result.message
            );
            break;
          default:
            this.loanMessageService.generateCreateTicketCheckOutError(
              err.message
            );
            break;
        }
        throw Error(err);
      })
    );
  }

  public getTicketCheckOut(parametersQuery: ParametersQuery, userAccountId: number,
    requestTicketCheckOutParameters: RequestTicketCheckOutParameters): Observable<Response<ResponsePage<TicketCheckOutResponse>>> {
    let parameters = ServiceUtils.getHttpParameters(parametersQuery);

    if(requestTicketCheckOutParameters.trackIdOut != null && requestTicketCheckOutParameters.trackIdOut.trim() != "") {
      parameters = parameters.append('track_id_out', requestTicketCheckOutParameters.trackIdOut);
    }
    if(requestTicketCheckOutParameters.trackIdIn != null && requestTicketCheckOutParameters.trackIdIn.trim() != "") {
      parameters = parameters.append('track_id_in', requestTicketCheckOutParameters.trackIdIn);
    }
    if(requestTicketCheckOutParameters.itemName != null && requestTicketCheckOutParameters.itemName.trim() != "") {
      parameters = parameters.append('item_name', requestTicketCheckOutParameters.itemName);
    }
    if(requestTicketCheckOutParameters.status != null && requestTicketCheckOutParameters.status.trim() != "") {
      parameters = parameters.append('status', requestTicketCheckOutParameters.status);
    }
    if(requestTicketCheckOutParameters.deliveryDateStart != null && requestTicketCheckOutParameters.deliveryDateStart.trim() != "") {
      parameters = parameters.append('delivery_date_start', requestTicketCheckOutParameters.deliveryDateStart);
    }
    if(requestTicketCheckOutParameters.deliveryDateEnd != null && requestTicketCheckOutParameters.deliveryDateEnd.trim() != "") {
      parameters = parameters.append('delivery_date_end', requestTicketCheckOutParameters.deliveryDateEnd);
    }
    if(requestTicketCheckOutParameters.returnDateStart != null && requestTicketCheckOutParameters.returnDateStart.trim() != "") {
      parameters = parameters.append('return_date_start', requestTicketCheckOutParameters.returnDateStart);
    }
    if(requestTicketCheckOutParameters.returnDateEnd != null && requestTicketCheckOutParameters.returnDateEnd.trim() != "") {
      parameters = parameters.append('return_date_end', requestTicketCheckOutParameters.returnDateEnd);
    }

    return this.httpClient.get<Response<ResponsePage<TicketCheckOutResponse>>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_TICKET_CHECK_OUT}/${userAccountId}/ticket-check-out`, { params: parameters }
    ).pipe(
      map((bodyResponse) => {
        if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
          return bodyResponse;
        } else {
          this.loanMessageService.generateLoanDefaultError(
            bodyResponse.description
          );
        }
        return bodyResponse;
      }),
      catchError((err) => {
        const httpErrorResponse: HttpErrorResponse = err;
        switch (httpErrorResponse.status) {
          case 0:
            this.loanMessageService.generateLoanDefaultError(null);
            break;
          case 401:
            const errorResponse: ErrorResponse = err.error;
            this.loanMessageService.generateLoanDefaultError(
              errorResponse.result.message
            );
            break;
          default:
            this.loanMessageService.generateLoanDefaultError(
              err.message
            );
            break;
        }
        throw Error(err);
      })
    );
  }

  public createTicketCheckLog(ticketCheckLogRequest: TicketCheckLogRequest): Observable<any> {
    return this.httpClient.post<any>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_CREATE_REQUEST_CHECK_LOG}`, ticketCheckLogRequest
    ).pipe(
      map((bodyResponse) => {
        if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
          return bodyResponse;
        } else {
          this.loanMessageService.generateCreateTicketCheckLogError(
            bodyResponse.description
          );
        }
        return bodyResponse;
      }),
      catchError((err) => {
        const httpErrorResponse: HttpErrorResponse = err;
        switch (httpErrorResponse.status) {
          case 0:
            this.loanMessageService.generateCreateTicketCheckLogError(null);
            break;
          case 401:
            const errorResponse: ErrorResponse = err.error;
            this.loanMessageService.generateCreateTicketCheckLogError(
              errorResponse.result.message
            );
            break;
          default:
            this.loanMessageService.generateCreateTicketCheckLogError(
              err.message
            );
            break;
        }
        throw Error(err);
      })
    );
  }
}
