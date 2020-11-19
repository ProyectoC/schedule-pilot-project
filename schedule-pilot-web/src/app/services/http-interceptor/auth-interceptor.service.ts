import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import * as UrlTokenServicesCompanyConst from '../../url-services/url-services-token-company';
import { map, catchError, retry } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { environment } from '@env/environment';
import { LocalStorageConstants } from '@constants/local-storage-constants';
import { RoutingConstants } from '@constants/routing-constants';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  public urlTokensCompany: string[] = UrlTokenServicesCompanyConst.URL_SERVICES_TOKEN_COMPANY;

  constructor(private router: Router, public authenticationService: AuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let retries = 0;
    if (this.isEndPointServicePublic(req.url)) {
      // const tokenCompany = environment.services['token.company'];
      req = req.clone({
        setHeaders: {
          // 'Authorization': 'Bearer ' + tokenCompany,
          'Content.Type': 'application/json',
          'Accept': 'application/json'
        }
      });
      retries = 0;
    } else {
      const authUser = JSON.parse(localStorage.getItem(LocalStorageConstants.USER_SESSION));
      if (authUser) {
        req = req.clone({
          setHeaders: {
            'Authorization': 'Bearer ' + authUser.token,
            'Content.Type': 'application/json',
            'Accept': 'application/json'
          }
        });
        retries = 0;
      } else {
        this.router.navigate([RoutingConstants.URL_AUTHENTICATION]);
      }
    }

    return next.handle(req).pipe(retry(retries),
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          console.log('event--->>>', event);
        }
        if (event instanceof HttpErrorResponse && event.status === 401) {
          this.logout();
        }
        return event;
      }), catchError((error: HttpErrorResponse) => {
        this.handleErrorRequest(error);
        return throwError(error);
      }));
  }

  isEndPointServicePublic(url: string): boolean {
    const scheduleApiEndPoint = environment.apis["schedule-api"]["end.point"];
    for(let i = 0; i < EndPointsHttpConstants.PUBLIC_SERVICES.length; i ++ ) {
      const serviceTemp = scheduleApiEndPoint + EndPointsHttpConstants.PUBLIC_SERVICES[i];
      if (url === serviceTemp) {
        return true;
      }
    }
    return false;
  }

  handleErrorRequest(error: HttpErrorResponse): void {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred client side:', error.error.message);
    } 
    if (error instanceof HttpErrorResponse) {
      if (!navigator.onLine) {
        console.log('No Internet Connection');
      } else {
        console.log(`${error.status} - ${error.message}`);
      }
    }
    console.error('It happens: ', error);
    if (error && error.status === 401) {
       this.logout();
    }
    const errorJsonStr = JSON.stringify(error);
    const errorJson = JSON.parse(errorJsonStr);
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate([RoutingConstants.URL_AUTHENTICATION]).then(() => {
    });
  }
}
