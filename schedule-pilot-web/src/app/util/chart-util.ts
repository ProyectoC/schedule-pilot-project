import { CommonUtils } from './common-utils';

export class ChartUtil {
  static getCurrentDate() {
    var d = new Date(),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
  }

  static getLineChart(chartData: any): any {
    let listDataSets: Array<any> = [];
    //Get position current date
    let todayDate = this.getCurrentDate();
    let positionToday = 0;
    for (var i = 0; i < chartData.labels.length; i++) {
      if (chartData.labels[i] === todayDate) {
        positionToday = i;
        break;
      }
    }

    for (var i = 0; i < chartData.listItems.length; i++) {
      let item = chartData.listItems[i];
      let sizePoints: Array<any> = [];
      for (var j = 0; j < item.itemValues.length; j++) {
        if (j === positionToday) {
          sizePoints.push(7);
        } else {
          sizePoints.push(3);
        }
      }
      const dataset = {
        label: item.itemName + ' Horas',
        data: item.itemValues,
        fill: false,
        borderColor: CommonUtils.getRandomColorHex(),
        backgroundColor: CommonUtils.getRandomColorHex(),
        pointRadius: sizePoints,
      };
      listDataSets.push(dataset);
    }
    const chart = {
      type: chartData.type,
      data: {
        labels: chartData.labels,
        datasets: listDataSets,
      },
      options: {
        title: {
          display: true,
          text: [chartData.title, chartData.subTitle],
          fontSize: 16,
        },
        legend: {
          display: true,
          position: 'top',
        },
        scales: {
          yAxes: [
            {
              display: true,
              ticks: {
                stepSize: 2,
              },
              scaleLabel: {
                display: true,
                labelString: chartData.yName,
              },
            },
          ],
          xAxes: [
            {
              scaleLabel: {
                display: true,
                labelString: chartData.xName,
              },
            },
          ],
        },
      },
    };
    return chart;
  }

  static getBarChart(chartData: any): any {
    let listDataSets: Array<any> = [];
    for (var i = 0; i < chartData.listItems.length; i++) {
      let item = chartData.listItems[i];
      const dataset = {
        label: item.itemName,
        backgroundColor: [
          '#3e95cd',
          '#8e5ea2',
          '#3cba9f',
          '#e8c3b9',
          '#c45850',
        ],
        data: item.itemValues,
      };
      listDataSets.push(dataset);
    }
    const chart = {
      type: chartData.type,
      data: {
        labels: chartData.labels,
        datasets: listDataSets,
      },
      options: {
        title: {
          display: true,
          text: [chartData.title, chartData.subTitle],
          fontSize: 16,
        },
        legend: {
          display: true,
          position: 'top',
        },
        scales: {
          yAxes: [
            {
              scaleLabel: {
                display: true,
                labelString: chartData.yName,
              },
            },
          ],
          xAxes: [
            {
              scaleLabel: {
                display: true,
                labelString: chartData.xName,
              },
            },
          ],
        },
      },
    };
    return chart;
  }

  static getPieChart(chartData: any): any {
    let listDataSets: Array<any> = [];
    for (var i = 0; i < chartData.listItems.length; i++) {
      let item = chartData.listItems[i];
      const dataset = {
        label: item.itemName,
        backgroundColor: [
          '#3e95cd',
          '#8e5ea2',
          '#3cba9f',
          '#e8c3b9',
          '#c45850',
        ],
        data: item.itemValues,
      };
      listDataSets.push(dataset);
    }
    const chart = {
      type: chartData.type,
      data: {
        labels: chartData.labels,
        datasets: listDataSets,
      },
      options: {
        title: {
          display: true,
          text: [chartData.title, chartData.subTitle],
          fontSize: 16,
        },
      },
    };
    return chart;
  }
}
