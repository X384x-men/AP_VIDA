import { Component, Inject, ViewChild } from '@angular/core';
import {  ChartComponent,  ApexChart,  ApexXAxis,  ApexTitleSubtitle, ApexNonAxisChartSeries, ApexResponsive, ApexStroke, ApexFill, ApexLegend, ApexPlotOptions, ApexAnnotations, ApexDataLabels, ApexGrid, ApexYAxis} from "ng-apexcharts";
import { map } from 'rxjs/operators';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { SolicitudVariable } from 'src/app/core/static/variables/url/URLImages';

// parte de la libreria
export type ChartOptions = {
  series: any | ApexNonAxisChartSeries;
  chart: any | ApexChart;
  labels: any;
  colors: any;
  legend: any | ApexLegend;
  plotOptions: any | ApexPlotOptions;
  responsive: any | ApexResponsive | ApexResponsive[];
  stroke: any | ApexStroke;
  fill: any | ApexFill;
  annotations: any | ApexAnnotations;
  dataLabels: any | ApexDataLabels;
  grid: any | ApexGrid;
  xaxis: any; //ApexXAxis;
  yaxis: any | ApexYAxis;
};

@Component({
  selector: 'app-main-chart',
  templateUrl: './main-chart.component.html',
  styleUrls: ['./main-chart.component.css']
})
export class MainChartComponent{

  @ViewChild("chart") chart?: ChartComponent;
  public chartOptions: Partial<ChartOptions>;
  public chartOptions1: Partial<ChartOptions>;
  public chartOptions2: Partial<ChartOptions>;
  solicitudes : any;
  loading : boolean = true;
  nueva : number = 0
  terminada : number = 0
  rechazada : number = 0
  enProceso : number = 0


  constructor( @Inject('ServiceResource') private subResourceService: SubResourceService<any> ){
    this.refresh()
  }

  refresh = () => {
    this.loading = true;
    this.allSolicitudes()
    this.solicitudNueva()
    this.solicitudRechazada()
    this.solicitudTerminada()

  }

  functionChart = () =>{

      this.chartOptions = {
      series: [this.solicitudes, this.terminada, this.rechazada],
      chart: {
        height: 390,
        type: "radialBar"
      },
      plotOptions: {
        radialBar: {
          offsetY: 0,
          startAngle: 0,
          endAngle: 270,
          hollow: {
            margin: 5,
            size: "30%",
            background: "transparent",
            image: undefined
          },
          dataLabels: {
            name: {
              show: false
            },
            value: {
              show: false
            }
          }
        }
      },
      colors: ["#1ab7ea", "#0084ff", "#39539E"],
      labels: ["Solicitudes", "Terminada", "Rechazada"],
      legend: {
        show: true,
        floating: true,
        fontSize: "16px",
        position: "left",
        offsetX: 50,
        offsetY: 10,
        labels: {
          useSeriesColors: true
        },
        formatter: function(seriesName : any, opts: any) {
          return seriesName + ":  " + opts.w.globals.series[opts.seriesIndex];
        },
        itemMargin: {
          horizontal: 3
        }
      },
      responsive: [
        {
          breakpoint: 480,
          options: {
            legend: {
              show: false
            }
          }
        }
      ]
      };


      this.chartOptions1 = {
       series: [this.solicitudes, this.terminada, this.rechazada], // data de la grafica
      chart: {
        type: "polarArea"
      },
      labels: ["Solicitudes", "Terminada", "Rechazada"], // nombres de los labels
      stroke: {
        colors: ["#fff"] // color de letra
      },
      colors:['#F44336', '#E91E63', '#000', '#E91E99'], // colores de la grafica
       fill: {
         opacity: 0.8
       },
       responsive: [
         {
           breakpoint: 480,
           options: {
             chart: {
               width: 200
             },
             legend: {
               position: "bottom"
             }
           }
         }
       ]
      };


     this.chartOptions2 = {
      series: [
        {
          name: "Data",
          data: [this.solicitudes, this.terminada, this.rechazada]
        }
      ],
      annotations: {
        points: [
          {
            x: "Solicitudes",
            seriesIndex: 0,
            label: {
              borderColor: "#775DD0",
              offsetY: 0,
              style: {
                color: "#fff",
                background: "#775DD0"
              },
              text: "Punto mayor"
            }
          }
        ]
      },
      chart: {
        height: 350,
        type: "bar"
      },
      plotOptions: {
        bar: {
          columnWidth: "50%",
          endingShape: "rounded"
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        width: 2
      },

      grid: {
        row: {
          colors: ["#fff", "#f2f2f2"]
        }
      },
      xaxis: {
        labels: {
          rotate: -45
        },
        categories: [
          "Solicitudes",
          "Termimadas",
          "Rechazadas"
        ],
        tickPlacement: "on"
      },
      yaxis: {
        title: {
          text: "Data"
        }
      },
      fill: {
        type: "gradient",
        gradient: {
          shade: "light",
          type: "horizontal",
          shadeIntensity: 0.25,
          gradientToColors: undefined,
          inverseColors: true,
          opacityFrom: 0.85,
          opacityTo: 0.85,
          stops: [50, 0, 100]
        }
      }
    };
  }

  allSolicitudes(){
    this.getSolicitudes( '', '', '', '' ).subscribe( data => {
      this.solicitudes = data.length;
    } )
  }

  solicitudTerminada(){
    this.getSolicitudes( '', '', '', 'Terminada' ).subscribe( data => {
      this.terminada = data.length;
    } )
  }

  solicitudRechazada(){
    this.getSolicitudes( '', '', '', 'Rechazada' ).subscribe( data => {
      this.rechazada = data.length;;
    } )
  }

  solicitudNueva(){
    this.getSolicitudes( '', '', '', 'Nueva' ).subscribe( data => {
      this.nueva = data.length;;
    }, error => {
      console.log({error});
    } )
  }

  getSolicitudes = ( nombre, rfc, tramite, status ) => {
    return this.subResourceService.list(SolicitudVariable.GET_SOLIITUDES_ANALISTAS,'' ,{nombre: nombre, RFC: rfc, tramite: tramite, status: status}).pipe(map( (data : any ) => {
      setTimeout(() => {
        console.log('hola');
        this.functionChart()
        this.loading = false
      }, 2000);
      return data
    }));
  }

}
