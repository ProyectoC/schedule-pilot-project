import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import * as Chart from 'chart.js';
import { GeneralChart } from '@models/dashboard/general-chart';

@Component({
  selector: 'app-report-charts',
  templateUrl: './report-charts.component.html',
  styleUrls: ['./report-charts.component.scss']
})
export class ReportChartsComponent implements OnInit {

  public canvas: any;
  public ctx: any;
  public dashboardLoanProducts = null;
  public dashboardRequestLoans = null;
  public dashboardPenaltySummary = null;

  @Output() onGenerateReportLoanProducts = new EventEmitter<void>();
  @Output() onGenerateReportPenaltySummary = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  public loadDashBoardLoanProducts(result: any): void {
    if( result != null) {
      this.dashboardLoanProducts = result;
      this.createBarChart(this.dashboardLoanProducts, 'chart-loan-products');
    } else {
      this.dashboardLoanProducts = null;
    }
  }

  public loadDashBoardRequestVsLoans(result: any): void {
    if( result != null) {
      this.dashboardRequestLoans = result;
      this.createBarChart(this.dashboardRequestLoans, 'chart-request-loans');
    } else {
      this.dashboardRequestLoans = null;
    }
  }

  public loadDashBoardPenaltySummary(result: any): void {
    if( result != null) {
      this.dashboardPenaltySummary = result;
      this.createBarChart(this.dashboardPenaltySummary, 'chart-penalty-summary');
    } else {
      this.dashboardPenaltySummary = null;
    }
  }

  public exportReportLoanProducts(): void {
    this.onGenerateReportLoanProducts.emit();
  }
  
  public exportReportPenaltySummary(): void {
    this.onGenerateReportPenaltySummary.emit();
  }

  public createBarChart(dataChart: GeneralChart, id: string): void {
    Chart.defaults.global.defaultFontFamily = "'Didact Gothic', sans-serif";
    let listDataSets: Array<any> = [];
    for (var i = 0; i < dataChart.listItems.length; i++) {
      let item = dataChart.listItems[i];
      const dataset = {
        label: item.itemName,
        backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
        data: item.itemValues,
      }
      listDataSets.push(dataset);
    }
    this.canvas = document.getElementById(id);
    this.ctx = this.canvas.getContext('2d');
    let myChart = new Chart(this.ctx, {
      type: dataChart.type,
      data: {
        labels: dataChart.labels,
        datasets: listDataSets
      },
      options: {
        title: {
          display: true,
          text: [dataChart.title, dataChart.subTitle],
          fontSize: 16
        },
        legend: {
          display: true,
          position: 'top'
        },
        scales: {
          yAxes: [{
            scaleLabel: {
              display: true,
              labelString: dataChart.yname
            }
          }],
          xAxes: [{
            scaleLabel: {
              display: true,
              labelString: dataChart.xname
            }
          }]
        }
      }
    });
  }

}
