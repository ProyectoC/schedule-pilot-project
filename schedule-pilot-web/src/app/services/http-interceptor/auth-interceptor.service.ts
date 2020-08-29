import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import * as UrlTokenServicesCompanyConst from '../../url-services/url-services-token-company';
import { map, catchError, retry } from 'rxjs/operators';
import { GlobalErrorHandler } from '../global-error/global-error.handler.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { environment } from '@env/environment';
import { LocalStorageConstants } from '@constants/local-storage-constants';
import { RoutingConstants } from '@constants/routing-constants';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  public urlTokensCompany: string[] = UrlTokenServicesCompanyConst.URL_SERVICES_TOKEN_COMPANY;

  constructor(private router: Router, public authenticationService: AuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let retries = 0;
    if (this.validateUrlWithTokenCompany(req.url)) {
      // Token Company
      const tokenCompany = environment.services['token.company'];
      req = req.clone({
        setHeaders: {
          'Authorization': 'Bearer ' + tokenCompany,
          'Content.Type': 'application/json',
          'Accept': 'application/json'
        }
      });
      retries = 0;
    } else {
      // Token User
      const authUSer = JSON.parse(localStorage.getItem(LocalStorageConstants.USER_SESSION));
      if (authUSer) {
        req = req.clone({
          setHeaders: {
            'Authorization': 'Bearer ' + authUSer.token,
            'Content.Type': 'application/json',
            'Accept': 'application/json'
          }
        });
        retries = 2;
      } else {
        this.router.navigate([RoutingConstants.URL_AUTHENTICATION]);
      }
    }
    return next.handle(req).pipe(retry(retries),
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          console.log('event--->>>', event);
        }
        if (event instanceof HttpResponse && event.status === 401) {
          this.logout();
        }
        return event;
      }), catchError(GlobalErrorHandler.handleErrorRequest));
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate([RoutingConstants.URL_AUTHENTICATION]).then(() => {
    });
  }

  validateUrlWithTokenCompany(url: string): boolean {
    const endPoint = environment.services['end.point'];
    for (let i = 0; i < this.urlTokensCompany.length; i++) {
      const serviceTemp = endPoint + this.urlTokensCompany[i];
      if (url === serviceTemp) {
        return true;
      }
    }
    return false;
  }
}
