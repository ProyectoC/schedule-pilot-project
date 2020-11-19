import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { ParametersQuery } from '@models/parameters-query';
import { Response } from '@models/response';
import { ResponsePage } from '@models/response-page';
import { ItemMessageService } from '@services/messages/item.message.service';
import { Observable } from 'rxjs';
import { ItemResponse } from '@models/item/response/item-response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { ServiceUtils } from '@utils/service-utils';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { ErrorResponse } from '@models/error-response';
import { ItemStatus } from '@models/item/item-status';
import { ItemRequest } from '@models/item/request/item-request';

@Injectable({
  providedIn: 'root'
})
export class ItemsService {

  public apiScheduleEndPoint: string =
    environment.apis['schedule-api']['end.point'];

  constructor(
    public httpClient: HttpClient,
    private itemMessageService: ItemMessageService
  ) {
  }
  
  public getItems(parametersQuery: ParametersQuery, productId: number): Observable<Response<ResponsePage<ItemResponse>>> {
    let parameters = ServiceUtils.getHttpParameters(parametersQuery);
    if (productId) {
      parameters = parameters.append('productId', productId.toString());
    }
    return this.httpClient
      .get<Response<ResponsePage<ItemResponse>>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_ITEMS}`, { params: parameters }
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.itemMessageService.generateItemDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.itemMessageService.generateItemDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.itemMessageService.generateItemDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.itemMessageService.generateItemDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public createItem(itemRequest: ItemRequest): Observable<Response<ItemResponse>> {
    return this.httpClient
      .post<Response<ItemResponse>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_CREATE_ITEMS}`, itemRequest
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.itemMessageService.generateMessageErrorCreateItem(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.itemMessageService.generateMessageErrorCreateItem(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.itemMessageService.generateMessageErrorCreateItem(
                errorResponse.result.message
              );
              break;
            default:
              this.itemMessageService.generateMessageErrorCreateItem(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public updateItem(itemRequest: ItemRequest): Observable<Response<ItemResponse>> {
    return this.httpClient
      .put<Response<ItemResponse>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_UPDATE_ITEMS}`, itemRequest
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse;
          } else {
            this.itemMessageService.generateMessageErrorUpdateItem(
              bodyResponse.description
            );
          }
          return bodyResponse;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.itemMessageService.generateMessageErrorUpdateItem(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.itemMessageService.generateMessageErrorUpdateItem(
                errorResponse.result.message
              );
              break;
            default:
              this.itemMessageService.generateMessageErrorUpdateItem(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }
}
