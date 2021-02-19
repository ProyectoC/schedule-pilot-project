import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Response } from '@models/response';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '@env/environment';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { LocalStorageConstants } from '@constants/local-storage-constants';
import { ErrorResponse } from '@models/error-response';
import { AuthenticationMessageService } from '@services/messages/authentication.message.service';
import { AuthUser } from '@models/auth-user';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { AuthResponse } from '@models/authentication/response/auth-response';
import { ForgotPassword } from '@models/forgot-password';
import { ChangePasswordRequest } from '@models/authentication/request/change-password-request';

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

  public authenticateUser(data: AuthUser): Observable<Response<AuthResponse>> {
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
          throw Error(err);
        })
      );
  }

  public restartPasswordUser(
    forgotPasswordRequest: ForgotPassword
  ): Observable<Response<any>> {
    return this.httpClient
      .post<Response<any>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_FORGOT_PASSWORD}`, forgotPasswordRequest
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.authenticationMessageService.generalErrorForgotPassword(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.authenticationMessageService.generalErrorForgotPassword(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.authenticationMessageService.generalErrorForgotPassword(
                errorResponse.result.message
              );
              break;
            default:
              this.authenticationMessageService.generalErrorForgotPassword(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public changePasswordUser(changePasswordRequest: ChangePasswordRequest): Observable<Response<any>> {
    return this.httpClient.post<Response<any>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_CHANGE_PASSWORD}`, changePasswordRequest
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.authenticationMessageService.generalErrorChangePassword(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.authenticationMessageService.generalErrorChangePassword(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.authenticationMessageService.generalErrorChangePassword(
                errorResponse.result.message
              );
              break;
            default:
              this.authenticationMessageService.generalErrorChangePassword(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public get userSession() {
    return JSON.parse(localStorage.getItem(LocalStorageConstants.USER_SESSION));
  }

  public isAdminUser(): boolean {
    if (this.userSession.user.rolAccount.id === 1 || this.userSession.user.rolAccount.id === 2) {
      return true;
    } else {
      return false;
    }
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
