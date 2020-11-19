import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommonConstants } from '@constants/common-constants';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { environment } from '@env/environment';
import { ErrorResponse } from '@models/error-response';
import { RequestCheckIn } from '@models/request-check-in/request-check-in';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { LoanMessageService } from '../messages/loan.message.service';

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
              bodyResponse.description
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
            case 401:
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
}
