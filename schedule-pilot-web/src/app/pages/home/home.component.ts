import { AfterViewInit, Component, OnInit } from '@angular/core';
import * as Chart from 'chart.js';
import { DashboardService } from '@services/dashboard/dashboard.service';
import { GeneralChart } from '@models/dashboard/general-chart';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, AfterViewInit {

  public canvas: any;
  public ctx: any;
  public dashboardStatusOperations: GeneralChart;

  constructor(public dashboardService: DashboardService) { }

  public ngOnInit(): void { }

  public ngAfterViewInit(): void {
    this.dashboardService.getDashboardStatusOperations().subscribe(
      (bodyResponse: GeneralChart) => {
        this.dashboardStatusOperations = bodyResponse;
        // this.reportNotifications = this.dataCharts.generalNotificationsReport;
        // this.createLineChart(this.dataCharts.generalProjectsChart, 'chart-projects-status');
        // this.createPieChart(this.dataCharts.generalActivitiesStatusChart, 'chart-activities-status');
        this.createBarChart(this.dashboardStatusOperations, 'chart-status-operations');
        // this.createGanttChart(this.dataCharts.generalActivitiesReport, 'chart_div');
        // this.listRecentActivities = this.dataCharts.generalActivityRecent;
      }, (error) => {
        this.dashboardStatusOperations = null;
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
}
