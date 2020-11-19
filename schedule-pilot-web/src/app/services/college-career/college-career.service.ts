import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { CollegeCareerMessageService } from '@services/messages/college-career.message.service';
import { CollegeCareer } from '@models/college-career/college-career';
import { Observable } from 'rxjs';
import { Response } from '@models/response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { catchError, map } from 'rxjs/operators';
import { CommonConstants } from '@constants/common-constants';
import { ErrorResponse } from '@models/error-response';

@Injectable({
  providedIn: 'root'
})
export class CollegeCareerService {

  public apiScheduleEndPoint: string = environment.apis['schedule-api']['end.point'];

  constructor(public httpClient: HttpClient, private collegeCareerMessageService: CollegeCareerMessageService) { }

  public getCollegeCareers(): Observable<CollegeCareer[]> {
    return this.httpClient.get<Response<CollegeCareer[]>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_COLLEGE_CAREER}`, { params: {} }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.collegeCareerMessageService.generateCollegeCareerDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.collegeCareerMessageService.generateCollegeCareerDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.collegeCareerMessageService.generateCollegeCareerDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.collegeCareerMessageService.generateCollegeCareerDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }
}
