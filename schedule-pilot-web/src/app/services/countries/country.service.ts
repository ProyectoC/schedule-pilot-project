import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { ResponsePage } from '@models/response-page';
import { CountryMessageService } from '@services/messages/country.message.service';
import { Observable } from 'rxjs';
import { CountryResponse } from '@models/country/country-response';
import { Response } from '@models/response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { ErrorResponse } from '@models/error-response';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  public apiScheduleEndPoint: string =
    environment.apis['schedule-api']['end.point'];

  constructor(
    public httpClient: HttpClient,
    private countryMessageService: CountryMessageService
  ) {}
  
  public getCountries(): Observable<CountryResponse[]> {
    return this.httpClient.get<Response<ResponsePage<CountryResponse>>>(
        `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_GET_COUNTRIES}`, { params: {} }
      )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result.content;
          } else {
            this.countryMessageService.generateCountryDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result.content;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.countryMessageService.generateCountryDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.countryMessageService.generateCountryDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.countryMessageService.generateCountryDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }
}
