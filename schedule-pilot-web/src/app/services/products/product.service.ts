import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ParametersQuery } from '@models/parameters-query';
import { ResponsePage } from '@models/response-page';
import { ProductResponse } from '@models/product/response/product-response';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';
import { Response } from '@models/response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { ProductMessageService } from '@services/messages/product.message.service';
import { ErrorResponse } from '@models/error-response';
import { ProductRequest } from '@models/product/request/product-request';
import { ServiceUtils } from '@utils/service-utils'

@Injectable({
  providedIn: 'root',
})
export class ProductService {

  public apiScheduleEndPoint: string =
    environment.apis['schedule-api']['end.point'];

  constructor(
    public httpClient: HttpClient,
    private productMessageService: ProductMessageService
  ) {
  }

  public getProducts(
    parametersQuery: ParametersQuery
  ): Observable<Response<ResponsePage<ProductResponse>>> {
    return this.httpClient
      .get<Response<ResponsePage<ProductResponse>>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_PRODUCTS}`, { params: ServiceUtils.getHttpParameters(parametersQuery) }
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.productMessageService.generateMessageErrorGetProducts(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.productMessageService.generateMessageProductsDefault(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.productMessageService.generateMessageProductsDefault(
                errorResponse.result.message
              );
              break;
            default:
              this.productMessageService.generateMessageProductsDefault(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public getProductsList(): Observable<ProductResponse[]> {
    return this.httpClient.get<Response<ResponsePage<ProductResponse>>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_PRODUCTS}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result.content;
          } else {
            this.productMessageService.generateMessageErrorGetProducts(
              bodyResponse.description
            );
          }
          return bodyResponse.result.content;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.productMessageService.generateMessageProductsDefault(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.productMessageService.generateMessageProductsDefault(
                errorResponse.result.message
              );
              break;
            default:
              this.productMessageService.generateMessageProductsDefault(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public createProduct(
    productRequest: ProductRequest
  ): Observable<Response<ProductResponse>> {
    return this.httpClient
      .post<Response<ProductResponse>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_CREATE_PRODUCTS}`, productRequest
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.productMessageService.generateMessageErrorCreateProducts(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.productMessageService.generateMessageProductsDefault(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.productMessageService.generateMessageProductsDefault(
                errorResponse.result.message
              );
              break;
            default:
              this.productMessageService.generateMessageProductsDefault(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public updateProduct(
    productRequest: ProductRequest
  ): Observable<Response<ProductResponse>> {
    return this.httpClient
      .put<Response<ProductResponse>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_UPDATE_PRODUCTS}`, productRequest
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.productMessageService.generateMessageErrorUpdateProducts(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.productMessageService.generateMessageProductsDefault(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.productMessageService.generateMessageProductsDefault(
                errorResponse.result.message
              );
              break;
            default:
              this.productMessageService.generateMessageProductsDefault(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public deleteProduct(
    productRequest: ProductRequest
  ): Observable<Response<string>> {

    const httpOptions = {
      headers: {},
      body: productRequest
    };

    return this.httpClient
      .delete<Response<string>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_DELETE_PRODUCTS}`, httpOptions
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.productMessageService.generateMessageErrorDeleteProducts(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.productMessageService.generateMessageProductsDefault(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.productMessageService.generateMessageProductsDefault(
                errorResponse.result.message
              );
              break;
            default:
              this.productMessageService.generateMessageProductsDefault(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }
}
