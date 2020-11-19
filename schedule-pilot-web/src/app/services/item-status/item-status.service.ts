import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommonConstants } from '@constants/common-constants';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { environment } from '@env/environment';
import { ErrorResponse } from '@models/error-response';
import { ItemStatus } from '@models/item/item-status';
import { Response } from '@models/response';
import { ItemStatusMessageService } from '@services/messages/item-status.message.service';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ItemStatusService {

  public apiScheduleEndPoint: string = environment.apis['schedule-api']['end.point'];

  constructor(
    public httpClient: HttpClient,
    private itemStatusMessageService: ItemStatusMessageService
  ) {
  }

  public getItemsStatus(): Observable<ItemStatus[]> {
    return this.httpClient
      .get<Response<ItemStatus[]>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_ITEM_STATUS}`, { params: {} }
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.itemStatusMessageService.generateItemStatusDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.itemStatusMessageService.generateItemStatusDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.itemStatusMessageService.generateItemStatusDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.itemStatusMessageService.generateItemStatusDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }
}
