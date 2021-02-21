import { Component, OnInit, ViewChild } from '@angular/core';
import { DashboardService } from '@services/dashboard/dashboard.service';
import { GeneralChart } from '@models/dashboard/general-chart';
import { ReportChartsComponent } from '../report-charts/report-charts.component';
import { ReportService } from '@services/reports/report.service';
import { ReportLoanProductResponse } from '@models/reports/report-loan-product-response';
import { ReportPenaltySummaryResponse } from '@models/reports/report-penalty-summary-response';

@Component({
  selector: 'app-report-container',
  templateUrl: './report-container.component.html',
  styleUrls: ['./report-container.component.scss']
})
export class ReportContainerComponent implements OnInit {

  @ViewChild(ReportChartsComponent, { static: true })
  reportChartsComponent: ReportChartsComponent;

  public currentFilter = null;

  constructor(public dashboardService: DashboardService, public reportService: ReportService) { }

  ngOnInit(): void {
  }

  public filterReports($event: any): void {
    this.currentFilter = $event;
    this.dashboardService.getDashboardLoanProducts($event).subscribe(
      (bodyResponse: GeneralChart) => {
        this.reportChartsComponent.loadDashBoardLoanProducts(bodyResponse);
      }, (error) => {
        this.reportChartsComponent.loadDashBoardLoanProducts(null);
      });

    this.dashboardService.getDashboardRequestVsLoans($event).subscribe(
      (bodyResponse: GeneralChart) => {
        this.reportChartsComponent.loadDashBoardRequestVsLoans(bodyResponse);
      }, (error) => {
        this.reportChartsComponent.loadDashBoardRequestVsLoans(null);
      });

    this.dashboardService.getDashboardPenaltySummary($event).subscribe(
      (bodyResponse: GeneralChart) => {
        this.reportChartsComponent.loadDashBoardPenaltySummary(bodyResponse);
      }, (error) => {
        this.reportChartsComponent.loadDashBoardPenaltySummary(null);
      });

    // Add another charts
  }

  public generateReportLoanProducts(): void {
    this.reportService.getReportLoanProducts(this.currentFilter).subscribe(
      (bodyResponse: ReportLoanProductResponse[]) => {
        this.reportService.exportAsExcelFile(bodyResponse, 'report-loan-products');
      }, (error) => {

      });
  }

  public generateReportPenaltySummary(): void {
    this.reportService.getReportPenaltySummary(this.currentFilter).subscribe(
      (bodyResponse: ReportPenaltySummaryResponse[]) => {
        this.reportService.exportAsExcelFile(bodyResponse, 'report-penalty-summary');
      }, (error) => {

      });
  }

}
