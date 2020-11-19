import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RolMessageService } from '@services/messages/rol.message.service';
import { observable, Observable, of as observableOf } from 'rxjs';
import { RolAccount } from '@models/rol/rol-account';
import { environment } from '@env/environment';
import { Response } from '@models/response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { ErrorResponse } from '@models/error-response';

@Injectable({
  providedIn: 'root'
})
export class RolService {

  public apiScheduleEndPoint: string = environment.apis['schedule-api']['end.point'];

  constructor(public httpClient: HttpClient, private rolMessageService: RolMessageService) { }

  public getRoles(): Observable<RolAccount[]> {
    return this.httpClient.get<Response<RolAccount[]>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_ROL}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.rolMessageService.generateDefaultErrorRol(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.rolMessageService.generateDefaultErrorRol(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.rolMessageService.generateDefaultErrorRol(
                errorResponse.result.message
              );
              break;
            default:
              this.rolMessageService.generateDefaultErrorRol(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

}
