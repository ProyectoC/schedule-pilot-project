import { ErrorHandler, Injectable, Injector } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { RoutingConstants } from '@constants/routing-constants';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private injector: Injector) {
  }

  handleError(error: any) {
    const router = this.injector.get(Router);
    console.log(`Request URL: ${router.url}`);

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

  static handleErrorRequest(error: HttpErrorResponse) {
    // if (error.error instanceof ErrorEvent) {
    //   // console.error('An error occurred client side:', error.error.message);
    //   const errorJsonStr = JSON.stringify(error);
    //   errorResponse = JSON.parse(errorJsonStr);
    // } else {
    //
    // }
    if (error instanceof HttpErrorResponse) {
      // Server or connection error happened
      if (!navigator.onLine) {
        // Handle offline error
        console.log('No Internet Connection');
      } else {
        // Handle Http Error (error.status === 403, 404...)
        console.log(`${error.status} - ${error.message}`);
      }
    }
    // Log the error anyway
    console.error('It happens: ', error);
    if (error && error.status === 401) {
      // 401 errors are most likely going to be because we have an expired token that we need to refresh.
    }
    const errorJsonStr = JSON.stringify(error);
    const errorJson = JSON.parse(errorJsonStr);
    return throwError(errorJson);
  }
}
