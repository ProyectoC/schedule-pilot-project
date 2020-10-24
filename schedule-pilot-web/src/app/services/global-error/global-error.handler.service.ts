import { ErrorHandler, Injectable, Injector } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { RoutingConstants } from '@constants/routing-constants';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private injector: Injector) {
  }

  handleError(error: any) {
    const router = this.injector.get(Router);
    console.log(`Request URL: ${router.url}`);
    console.log(error);
    if (error instanceof HttpErrorResponse) {
      console.error('Backend returned status code:', error.status);
      console.error('Response body: ', error.message);
      if (error && error.status === 401) {
        router.navigate([RoutingConstants.URL_AUTHENTICATION]).then(() => {
        });
      }
    } else {
      console.error('And error ocurred:', error);
    }
    router.navigate([RoutingConstants.URL_ERROR]).then(() => {
    });
  }
}
