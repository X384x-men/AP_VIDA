import { Component, Inject, ViewChild } from '@angular/core';
import moment from 'moment';
import {  ChartComponent,  ApexChart,  ApexXAxis,  ApexTitleSubtitle, ApexNonAxisChartSeries, ApexResponsive, ApexStroke, ApexFill, ApexLegend, ApexPlotOptions, ApexAnnotations, ApexDataLabels, ApexGrid, ApexYAxis} from "ng-apexcharts";
import { map, timeout } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { SolicitudVariable } from 'src/app/core/static/variables/url/URLImages';
import swal from 'sweetalert2';

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
  public chartOptions3: Partial<ChartOptions>;
  solicitudes : any;
  solicitudesData : any;
  loading : boolean = false;
  nueva : number = 0;
  terminada : number = 0;
  rechazada : number = 0;
  proceso : number = 0;
  solicitudesFecha : any;
  sdComplementaria : number = 0;
  sdParcial: number = 0;
  sdTotal: number = 0;
  userApp  : any;
  status : boolean = false;
  tramite : boolean = false;
  fecha : boolean = false;
  seleccion : boolean = true;
  analista : boolean = false;
  mes : boolean = false;
  fecha1 : string = "";
  fecha2 : string = "";
  empleado1 : any[] = [];
  empleado2 : any[] = [];
  empleado3 : any[] = [];
  empleado4 : any[] = [];
  dataFecha : any;

  constructor( @Inject('ServiceResource') private subResourceService: SubResourceService<any>, private authencationService: AuthenticationService ){}

  ngOnInit(): void {
    this.authencationService.validacionAdmin();
  }

  functionChart = () =>{

    this.chartOptions = {
      series: [
        {
          name: "Data",
          data: [this.sdParcial, this.sdTotal, this.sdComplementaria]
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
          "Retiro Parcial",
          "Retiro Total",
          "Retiro Complementario"

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

    this.chartOptions2 = {
       series: [
         {
           name: "Data",
           data: [this.solicitudesFecha, this.nueva, this.proceso, this.terminada, this.rechazada]
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
           "Nueva",
           "En proceso",
           "Terminadas",
           "Rechazadas",

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

    this.chartOptions3 = {
      series: [
        {
          name: "Data",
          data: [this.empleado1.length, this.empleado2.length, this.empleado3.length, this.empleado4.length]
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
          "Analista01",
          "Analista02",
          "Analista03",
          "Analista04"

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

   solicitudProceso = async (proceso : string) => {
    let tipoTramite = []
    await this.dataFecha.forEach( element => {
      if (element.tipoTramite === proceso) {
        tipoTramite.push( element.tipoTramite )
      };
    });
    return tipoTramite
   }

   solicitudStatus = async (tipo : string) => {
    let status = []
    await this.dataFecha.forEach( element => {
      if (element.statusSolicitud === tipo) {
        status.push( element.statusSolicitud )
      };
    });
    return status
   }

   solicituFechaInicial = ( fechaInicial : Date ) => {
    this.fecha1 = moment(fechaInicial).format('YYYY-MM-DD');
   }

   solicituFechafinal = ( fechaFinal : Date ) => {
      this.fecha2 = moment(fechaFinal).format('YYYY-MM-DD');
   }

   reiniciarFechas = () => {
    this.mes = false;
    this.loading = false;
    this.fecha1 = "";
    this.fecha2 = "";
   }

   almacenarFecha = () => {
    this.getSolicitudes( '', '', '', '', this.fecha1, this.fecha2 ).subscribe( data => {
      this.loading = true;
      this.solicitudesFecha = data.length;
      this.dataFecha = data;

      data.forEach(element => {
        if (element.empleadoAsignacion === 'XAXX010101001') {
          this.empleado1.push(element.empleadoAsignacion)
        }else if (element.empleadoAsignacion === 'XAXX010101002') {
          this.empleado2.push(element.empleadoAsignacion)
        }else if (element.empleadoAsignacion === 'XAXX010101003') {
          this.empleado3.push(element.empleadoAsignacion)
        }else if (element.empleadoAsignacion === 'XAXX010101004') {
          this.empleado4.push(element.empleadoAsignacion)
        }
      });
    }, error => {
      this.loading = true;
      this.solicitudesFecha = 0;
      this.dataFecha = [];
      this.solicitudPorFecha();
      console.log({error});
    })

    setTimeout(() => {
      if (this.solicitudesFecha !== undefined && this.solicitudesFecha !== 0) {
        this.solicitudPorFecha();
      }
    }, 3000);
   }

   procesando = async ( tipo : string ) => {
    let procesos1 = [];
    await this.dataFecha.forEach( procesos =>{
      if (procesos.statusSolicitud === tipo) {
        procesos1.push( procesos.statusSolicitud )
      }});
    return procesos1;

   }

   solicitudPorFecha = async () => {
    this.mes = false;
    if (this.solicitudesFecha > 1) {
      const nueva = await this.solicitudStatus('Nueva')
      this.nueva = nueva.length;
      const proceso1 = await this.solicitudStatus('En proceso')
      this.proceso = proceso1.length;
      const terminada = await this.solicitudStatus('Terminada')
      this.terminada = terminada.length;
      const rechazada = await this.solicitudStatus('Rechazada')
      this.rechazada = rechazada.length;
      const parcial = await this.solicitudProceso('Retiro Parcial')
      this.sdParcial = parcial.length;
      const complementario = await this.solicitudProceso('Retiro Complementario')
      this.sdComplementaria = complementario.length;
      const total = await this.solicitudProceso('Retiro Total')
      this.sdTotal = total.length;
        this.functionChart();
        this.mes = true;
        this.loading = false;
    }else{
      swal('Alerta', 'No se consiguieron solicitudes en esta fecha', 'warning')
      this.fecha1 = '';
      //this.functionChart();
      //this.mes = true;
      this.loading = false
    }

   }

   getSolicitudes = ( nombre, rfc, tramite, status, fecheIni, fechaFin) => {
     return this.subResourceService.list(SolicitudVariable.GET_SOLIITUDES_ANALISTAS,'' ,{nombre: nombre, RFC: rfc, tramite: tramite, status: status, fechaIni: fecheIni, fechaFin: fechaFin, categoriaSolicitud: ''}).pipe(map( (data : any ) => {
        if (fechaFin === '') {
          setTimeout(() => {
            this.functionChart()
            this.loading = false
          }, 4000);
        }
       return data
     }, error => {
      console.log({error});
      return 0
    } ));
   }

   condicionalFront = ( condicion : string ) => {
    if (condicion === "status") {
      this.status = true;
      this.fecha = false;
      this.tramite = false;
      this.seleccion = false;
      this.analista = false;
    }else if( condicion === "tramite" ){
      this.tramite = true;
      this.status = false;
      this.fecha = false;
      this.seleccion = false;
      this.analista = false;
    }else if( condicion === "fecha"){
      this.fecha = true
      this.status = false;
      this.tramite = false;
      this.seleccion = false;
      this.analista = false;
    }else if( condicion === "analista"){
      this.fecha = false;
      this.status = false;
      this.tramite = false;
      this.seleccion = false;
      this.analista = true;
    }
   }

}
