import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import * as LoginConst from '../../constants/login';
import * as CommonConst from '../../constants/common';
import { Response } from '../../models/response';
import { Login } from '../../models/login';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
import * as UrlServicesConst from '../../url-services/url-services';
import { catchError, map } from 'rxjs/operators';
import { GlobalErrorHandler } from '@services/global-error/global-error.handler.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
    false
  );

  public URL: string = environment.services['end.point'];

  constructor(
    private router: Router, public http: HttpClient
  ) {
  }

  public loginUser(data: Login): Observable<Response> {
    return this.http.post<any>(`${this.URL}${UrlServicesConst.SERVICE_LOGIN}`, data)
      .pipe(map(bodyResponse => {
        if (bodyResponse.code === CommonConst.SUCCESS_CODE) {
          localStorage.setItem(LoginConst.USER_SESSION, JSON.stringify(bodyResponse.result));
          // localStorage.setItem(CommonConst.CLIENTS_SESSION, JSON.stringify(bodyResponse.result.clientsList));
          // localStorage.setItem(CommonConst.USERS_SESSION, JSON.stringify(bodyResponse.result.usersList));
          // localStorage.setItem(CommonConst.STATUS_SESSION, JSON.stringify(bodyResponse.result.statusList));
          // localStorage.setItem(CommonConst.NOTIFICATIONS_SESSION, JSON.stringify(bodyResponse.result.notificationsDontReceived));
          // localStorage.setItem(CommonConst.SKILLS_SESSION, JSON.stringify(bodyResponse.result.skillList));
          // localStorage.setItem(CommonConst.SPRINTS_SESSION, JSON.stringify(bodyResponse.result.sprintsList));
          return bodyResponse;
        }
        return bodyResponse;
      }),
        catchError(GlobalErrorHandler.handleErrorRequest));
  }

  public get isLoggedIn() {
    const userAuth = JSON.parse(localStorage.getItem(LoginConst.USER_SESSION));
    if (userAuth === null) {
      this.loggedIn.next(false);
      return this.loggedIn.asObservable();
    } else {
      this.loggedIn.next(true);
      return this.loggedIn.asObservable();
    }
  }

  logout() {
    localStorage.removeItem(LoginConst.USER_SESSION);
    this.loggedIn.next(false);
  }
}
