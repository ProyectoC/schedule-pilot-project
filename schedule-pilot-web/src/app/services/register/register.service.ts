import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { User } from '@models/user';
import { Observable } from 'rxjs';
import { Response } from '@models/response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { RegisterMessageService } from '@services/messages/register.message.service';
import { ErrorResponse } from '@models/error-response';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  public isLoading: boolean;
  public apiScheduleEndPoint: string =
    environment.apis['schedule-api']['end.point'];
    
  constructor(public httpClient: HttpClient, private registerMessageService: RegisterMessageService) { 
    this.isLoading = false;
  }

  public registerUser(data: User): Observable<Response<any>> {
    this.isLoading = true;
    return this.httpClient
      .post<any>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_REGISTER}`,
        data
      )
      .pipe(
        map((bodyResponse) => {
          this.isLoading = false;
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.registerMessageService.generalErrorRegister(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          this.isLoading = false;
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.registerMessageService.generalErrorRegister(
                null
              );
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.registerMessageService.generalErrorRegister(
                errorResponse.result.message
              );
              break;
            default:
              this.registerMessageService.generalErrorRegister(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }
}
