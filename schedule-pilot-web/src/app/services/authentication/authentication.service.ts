import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Response } from '@models/response';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '@env/environment';
import { catchError, map } from 'rxjs/operators';
import { GlobalErrorHandler } from '@services/global-error/global-error.handler.service';
import { CommonConstants } from '@constants/common-constants';
import { LocalStorageConstants } from '@constants/local-storage-constants';
import { ErrorResponse } from '@models/error-response';
import { AuthenticationMessageService } from '@services/messages/authentication.message.service';
import { AuthUser } from '@models/auth-user';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  public isLoading: boolean;
  private loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
    false
  );
  public apiScheduleEndPoint: string =
    environment.apis['schedule-api']['end.point'];

  constructor(
    public httpClient: HttpClient,
    private authenticationMessageService: AuthenticationMessageService
  ) {
    this.isLoading = false;
  }

  public authenticateUser(data: AuthUser): Observable<Response> {
    this.isLoading = true;
    return this.httpClient
      .post<any>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_AUTHENTICATION}`,
        data
      )
      .pipe(
        map((bodyResponse) => {
          this.isLoading = false;
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            localStorage.setItem(
              LocalStorageConstants.USER_SESSION,
              JSON.stringify(bodyResponse.result)
            );
            return bodyResponse;
          } else {
            this.authenticationMessageService.generalErrorAuthentication(
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
              this.authenticationMessageService.generalErrorAuthentication(
                null
              );
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.authenticationMessageService.generalErrorAuthentication(
                errorResponse.result.message
              );
              break;
            default:
              this.authenticationMessageService.generalErrorAuthentication(
                err.message
              );
              break;
          }
          return GlobalErrorHandler.handleErrorRequest;
        })
      );
  }

  public get isLoggedIn() {
    const userAuth = JSON.parse(
      localStorage.getItem(LocalStorageConstants.USER_SESSION)
    );
    if (userAuth === null) {
      this.loggedIn.next(false);
      return this.loggedIn.asObservable();
    } else {
      this.loggedIn.next(true);
      return this.loggedIn.asObservable();
    }
  }

  logout() {
    localStorage.removeItem(LocalStorageConstants.USER_SESSION);
    this.loggedIn.next(false);
  }
}
