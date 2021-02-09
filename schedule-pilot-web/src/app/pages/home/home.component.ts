import { AfterViewInit, Component, OnInit } from '@angular/core';
import * as Chart from 'chart.js';
import { DashboardService } from '@services/dashboard/dashboard.service';
import { GeneralChart } from '@models/dashboard/general-chart';
import { CommonUtils } from '@utils/common-utils';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, AfterViewInit {

  public canvas: any;
  public ctx: any;
  public dashboardStatusOperations: GeneralChart;
  public dashboardStatusLoanMade: GeneralChart;
  public dashboardStatusReturnMade: GeneralChart;
  public dashboardPrincipal: GeneralChart;

  constructor(public dashboardService: DashboardService) { }

  public ngOnInit(): void { }

  public ngAfterViewInit(): void {
    this.dashboardService.getDashboardStatusOperations().subscribe(
      (bodyResponse: GeneralChart) => {
        this.dashboardStatusOperations = bodyResponse;
        this.createBarChart(this.dashboardStatusOperations, 'chart-status-operations');
      }, (error) => {
        this.dashboardStatusOperations = null;
      });

    this.dashboardService.getDashboardStatusLoanMade().subscribe(
      (bodyResponse: GeneralChart) => {
        this.dashboardStatusLoanMade = bodyResponse;
        this.createPieChart(this.dashboardStatusLoanMade, 'chart-status-loan-made');
      }, (error) => {
        this.dashboardStatusLoanMade = null;
      });

    this.dashboardService.getDashboardStatusReturnMade().subscribe(
      (bodyResponse: GeneralChart) => {
        this.dashboardStatusReturnMade = bodyResponse;
        this.createPieChart(this.dashboardStatusReturnMade, 'chart-status-return-made');
      }, (error) => {
        this.dashboardStatusReturnMade = null;
      });

    this.dashboardService.getDashboardPrincipal().subscribe(
      (bodyResponse: GeneralChart) => {
        this.dashboardPrincipal = bodyResponse;
        this.createLineChart(this.dashboardPrincipal, 'chart-principal');
      }, (error) => {
        this.dashboardPrincipal = null;
      });


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

  public createPieChart(dataChart: GeneralChart, id: string): void {
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
        }
      }
    });
  }

  public createLineChart(dataChart: GeneralChart, id: string): void {
    let listDataSets: Array<any> = [];
    for (var i = 0; i < dataChart.listItems.length; i++) {
      let item = dataChart.listItems[i];
      const dataset = {
        label: item.itemName,
        data: item.itemValues,
        fill: false,
        borderColor: CommonUtils.getRandomColorHex()
      }
      listDataSets.push(dataset);
    }
    this.canvas = document.getElementById(id);
    this.ctx = this.canvas.getContext('2d');
    let myChart = new Chart(this.ctx, {
      responsive: true,
      maintainAspectRatio: false,
      type: dataChart.type,
      data: {
        labels: dataChart.labels,
        datasets: listDataSets
      },
      options: {
        title: {
          display: true,
          text: [dataChart.title + ' - ' + dataChart.subTitle],
          fontSize: 16,
          fontFamily: "'Didact Gothic', sans-serif"
        },
        legend: {
          display: true,
          position: 'top',
          labels: {
            fontColor: 'black',
            fontFamily: "'Didact Gothic', sans-serif"
          }
        },
        scales: {
          yAxes: [{
            scaleLabel: {
              display: true,
              labelString: dataChart.yname,
              fontFamily: "'Didact Gothic', sans-serif"
            },
            ticks: {
              beginAtZero: true
            }
          }],
          xAxes: [{
            type: 'time',
            scaleLabel: {
              display: true,
              labelString: dataChart.xname,
              fontFamily: "'Didact Gothic', sans-serif"
            }
          }]
        }
      }
    });
  }

}
