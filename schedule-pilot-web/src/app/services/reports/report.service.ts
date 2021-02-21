import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { ReportMessageService } from '@services/messages/report.message.service';
import { RequestReport } from '@models/reports/request-report';
import { Observable } from 'rxjs';
import { ReportLoanProductResponse } from '@models/reports/report-loan-product-response';
import { EndPointsHttpConstants } from '@constants/end-points-http-constants';
import { CommonConstants } from '@constants/common-constants';
import { map, catchError } from 'rxjs/operators';
import { ErrorResponse } from '@models/error-response';
import { Response } from '@models/response';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import { ReportPenaltySummaryResponse } from '@models/reports/report-penalty-summary-response';

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  public apiScheduleEndPoint: string =
    environment.apis['schedule-api']['end.point'];

  constructor(public httpClient: HttpClient, private reportMessageService: ReportMessageService) {

  }

  public exportAsExcelFile(json: any[], excelFileName: string): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, excelFileName);
  }

  private saveAsExcelFile(buffer: any, fileName: string): void {
     const data: Blob = new Blob([buffer], {type: EXCEL_TYPE});
     FileSaver.saveAs(data, fileName + '_export_' + new  Date().getTime() + EXCEL_EXTENSION);
  }

  public getReportLoanProducts(requestReport: RequestReport): Observable<ReportLoanProductResponse[]> {

    let parameters = new HttpParams();

    if (requestReport.startDate != null && requestReport.startDate.trim() != "") {
      parameters = parameters.append('date_start', requestReport.startDate);
    }
    if (requestReport.endDate != null && requestReport.endDate.trim() != "") {
      parameters = parameters.append('date_end', requestReport.endDate);
    }

    return this.httpClient.get<Response<ReportLoanProductResponse[]>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_REPORTS_GET_LOAN_PRODUCTS}`, { params: parameters }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.reportMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.reportMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.reportMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.reportMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }

  public getReportPenaltySummary(requestReport: RequestReport): Observable<ReportPenaltySummaryResponse[]> {

    let parameters = new HttpParams();

    if (requestReport.startDate != null && requestReport.startDate.trim() != "") {
      parameters = parameters.append('date_start', requestReport.startDate);
    }
    if (requestReport.endDate != null && requestReport.endDate.trim() != "") {
      parameters = parameters.append('date_end', requestReport.endDate);
    }

    return this.httpClient.get<Response<ReportPenaltySummaryResponse[]>>(
      `${this.apiScheduleEndPoint}${EndPointsHttpConstants.SERVICE_REPORTS_GET_PENALTY_SUMMARY}`, { params: parameters }
    )
      .pipe(
        map((bodyResponse) => {
          if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
            return bodyResponse.result;
          } else {
            this.reportMessageService.generateDefaultError(
              bodyResponse.description
            );
          }
          return bodyResponse.result;
        }),
        catchError((err) => {
          const httpErrorResponse: HttpErrorResponse = err;
          switch (httpErrorResponse.status) {
            case 0:
              this.reportMessageService.generateDefaultError(null);
              break;
            case 401:
              const errorResponse: ErrorResponse = err.error;
              this.reportMessageService.generateDefaultError(
                errorResponse.result.message
              );
              break;
            default:
              this.reportMessageService.generateDefaultError(
                err.message
              );
              break;
          }
          throw Error(err);
        })
      );
  }
}
