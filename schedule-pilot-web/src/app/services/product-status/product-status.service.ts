import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { Response } from '@models/response';
import { ProductStatusMessageService } from '@services/messages/product-status.message.service'
import { Observable } from 'rxjs';
import { ProductStatus } from '@models/product/product-status';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { CommonConstants } from '@constants/common-constants';
import { catchError, map } from 'rxjs/operators';
import { ErrorResponse } from '@models/error-response';

@Injectable({
  providedIn: 'root'
})
export class ProductStatusService {

  public apiScheduleEndPoint: string = environment.apis['schedule-api']['end.point'];

  constructor(
    public httpClient: HttpClient,
    private productStatusMessageService: ProductStatusMessageService
  ) {
  }

  public getProductStatus(): Observable<ProductStatus[]> {
    return this.httpClient.get<Response<ProductStatus[]>>(`${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_PRODUCT_STATUS}`, { params: {} })
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.productStatusMessageService.generateProductStatusDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.productStatusMessageService.generateProductStatusDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.productStatusMessageService.generateProductStatusDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.productStatusMessageService.generateProductStatusDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }
}
