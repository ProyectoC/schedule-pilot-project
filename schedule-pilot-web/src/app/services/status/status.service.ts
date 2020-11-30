import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { observable, Observable, of as observableOf } from 'rxjs';
import { environment } from '@env/environment';
import { Response } from '@models/response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { ErrorResponse } from '@models/error-response';
import { StatusMessageService } from '../messages/status.message.service';
import { StatusRequestCheckIn } from '../../models/status/status-request-check-in';
import { StatusTicketCheck } from '../../models/status/status-ticket-check';

@Injectable({
  providedIn: 'root'
})
export class StatusService {

  public apiScheduleEndPoint: string = environment.apis['schedule-api']['end.point'];

  constructor(public httpClient: HttpClient, private statusMessageService: StatusMessageService) { }

  public getStatusRequestCheckIn(): Observable<StatusRequestCheckIn[]> {
    return this.httpClient.get<Response<StatusRequestCheckIn[]>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_REQUEST_CHECK_IN_STATUS}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.statusMessageService.generateDefaultErrorStatus(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.statusMessageService.generateDefaultErrorStatus(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.statusMessageService.generateDefaultErrorStatus(
                errorResponse.result.message
              );
              break;
            default:
              this.statusMessageService.generateDefaultErrorStatus(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public getStatusTicketCheck(): Observable<StatusTicketCheck[]> {
    return this.httpClient.get<Response<StatusTicketCheck[]>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_TICKET_CHECK_STATUS}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.statusMessageService.generateDefaultErrorStatus(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.statusMessageService.generateDefaultErrorStatus(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.statusMessageService.generateDefaultErrorStatus(
                errorResponse.result.message
              );
              break;
            default:
              this.statusMessageService.generateDefaultErrorStatus(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

}
