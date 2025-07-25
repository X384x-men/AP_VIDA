import { Component, OnInit, Inject } from '@angular/core';
import { CargaBatchVariable, GlobalVariable, UsuarioAcceso } from 'src/app/core/static/variables/url/URLImages';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { ActivatedRoute } from '@angular/router';
import moment from 'moment';
import swal from 'sweetalert2';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import { PdfVariable } from 'src/app/core/static/variables/url/URLImages';
import { Vector } from 'src/app/core/interface/Vector';
import { ResumenMovimiento } from 'src/app/core/interface/resumenMovimiento';
import { DetalleMovimiento } from 'src/app/core/interface/DetalleMovimiento';
import { PeriodosConsulta } from 'src/app/core/interface/PeriodosConsulta';
import { ReporteService } from 'src/app/core/services/service-reporte/reporte.service';
import { ModalService } from 'src/app/shared/services/modal.service';


const ARRAY_MESES = { '1':'Enero','2':'Febrero','3':'Marzo',
    '4':'Abril','5':'Mayo','6':'Junio',
    '7':'Julio','8':'Agosto','9':'Septiembre',
    '10':'Octubre','11':'Noviembre','12':'Diciembre', '01':'Enero','02':'Febrero','03':'Marzo',
    '04':'Abril','05':'Mayo','06':'Junio',
    '07':'Julio','08':'Agosto','09':'Septiembre'
    };

@Component({
  selector: 'app-reporte-movimientos',
  templateUrl: './reporte-movimientos.component.html',
  styleUrls: ['./reporte-movimientos.component.css']
})
export class ReporteMovimientosComponent implements OnInit {
  integraIcon = GlobalVariable.MAIN_LOGO_INTEGRA;

  dataReport: any = null;
  Resumen: any;
  vector:Vector={
    numCuenta:'',
    periodoConsulta: '',
    anioConsulta: '',
    mesConsulta: '',
    codigoRFC: '',
    numPoliza:'',
    retenedor: '',
    dependencia: '',
    tasaPeriodo:'',
    totalDetalleMovimiento: '',
    listResumenMovimiento : new Array<ResumenMovimiento>(),
    listDetalleMovimiento :new Array<DetalleMovimiento>(),
	  listPeriodosConsulta :new Array<PeriodosConsulta>()
  };



  value=false;
  interes:number;
  rfc = '';
  anio = '';
  mes = '';
  userApp: any;
  monthCurrent: any;
  anioCurrent: any;
  optionsPeriodo: SelectMenu[];
  currentPeriodo: SelectMenu;
  nameUser: any = '';
  userAdmin: any;
  arrayMesAnio = [];
  fechaPeriodo: any = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private reporteService: ReporteService,
    private modal: ModalService,
    @Inject('ServiceResource')
    private subResourceService: SubResourceService<any>
  ) {}

  ngOnInit() {
    this.nameUser = JSON.parse(localStorage.getItem('nameUserAP'));
    this.userApp = JSON.parse(localStorage.getItem('currentUser'));
    this.userAdmin = JSON.parse(localStorage.getItem('currentUserAdmin'));
    let date = moment();

    this.monthCurrent = Number(date.format('M'))- 1;
    this.anioCurrent =  date.format('YYYY');
    this.getComboMesAnio();
    this.getValueAdmin();
  }

  getValueAdmin(){
  if(!(this.userAdmin==null))
    this.value=true;
  }

  getReporte(rfc, anio, mes){
    this.subResourceService.read(UsuarioAcceso.GET_REPORTE_AP, {rfc: rfc, anio: anio, mes: mes})
      .subscribe(data=>{
        this.dataReport = data;
        if(data.dsResumen !== null){
          this.Resumen=data.dsResumen
        }
      }, error=>{
        console.log(error);
      });
  }

  getTotal(array, attr){
    let total = 0;
    array.forEach(item => {
      total += item[attr];
    });
    return total;
  }

  initPeriodo(data){
    this.optionsPeriodo = Smartwfm.createSelectOptions(data, 'Periodo');
    this.setOptionPeriodo();
  }

  setOptionPeriodo(){
    this.optionsPeriodo.forEach(item => {
      if(Number(item.extras.Mes) == this.monthCurrent ){
        this.currentPeriodo = item;
      }
    });
  }

  getPeriodo($event){
    this.fechaPeriodo = this.arrayMesAnio[$event.index];
    this.getMovimientos();
  }

  generatePDF(){
   this.vector.numCuenta = '';
   this.vector.codigoRFC=this.dataReport.criterio.asegurado.rfc;
   this.vector.periodoConsulta=this.changeFormatDateUniq(this.dataReport.criterio.mes + '-' + this.dataReport.criterio.anio);
   this.vector.retenedor=this.dataReport.retenedor;
   this.vector.numPoliza=this.dataReport.poliza;
   this.vector.dependencia=this.dataReport.criterio.catalogoDependencias.descripcionCatalogo;
   this.vector.listResumenMovimiento = [];
   this.vector.listDetalleMovimiento = [];
   this.vector.listPeriodosConsulta = [];


  if(this.dataReport.resumenLista.length>0){
    for (let i = 0; i < this.dataReport.resumenLista.length; i++) {
      let resumenMovimiento = new ResumenMovimiento();
      resumenMovimiento.descripcion=this.dataReport.resumenLista[i].criterios.concepto.descripcion;
      resumenMovimiento.interesGanado=this.dataReport.resumenLista[i].interesesGanados;
      resumenMovimiento.primasAportadas=this.dataReport.resumenLista[i].primasAportadas;
      resumenMovimiento.retiros=this.dataReport.resumenLista[i].retiros;
      resumenMovimiento.saldoFinal=this.dataReport.resumenLista[i].saldoFinal;
      resumenMovimiento.saldoInicial=this.dataReport.resumenLista[i].saldoInicial;
      this.vector.listResumenMovimiento.push(resumenMovimiento);
    }
  }

  if(this.dataReport.detalles.length>0){
    for (let i = 0; i < this.dataReport.detalles.length; i++) {
      let detalleMov = new DetalleMovimiento();
      detalleMov.concepto=this.dataReport.detalles[i].criterio.concepto.descripcion;
      detalleMov.fechaMov=this.changeFormatDateComillas(this.dataReport.detalles[i].criterio.fecha);
      detalleMov.impDeposito=this.dataReport.detalles[i].deposito;
      detalleMov.impIntereses=this.dataReport.detalles[i].intereses;
      detalleMov.impRetencion=this.dataReport.detalles[i].retiros;
      detalleMov.impSaldo=this.dataReport.detalles[i].saldo;
      this.vector.listDetalleMovimiento.push(detalleMov);
    }
  }
    this.subResourceService.create( this.vector,  PdfVariable.GENERATE_PDF)
    .subscribe(data=>{
      console.log({data});
      if(data!=null){
        this.download(data.message);
      }
    }, error=>{
      console.log(error);
      this.modal.danger('Alerta', error);
    });
  }

  download(name){
    this.subResourceService.downloadFile(PdfVariable.DOWNLOAD_PDF, {filename: name})
    .subscribe((data) => {
      let element = document.createElement('a');
      element.setAttribute('href', "data:application/pdf;base64," + data.responseMsg);
      element.setAttribute('download', name);
      element.setAttribute('target', '_blank');
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
    });
  }

    getComboMesAnio(){
      this.subResourceService.read(CargaBatchVariable.GET_COMBO_MES_ANIO, {rfc: this.userApp.username})
        .subscribe(data=>{
          this.arrayMesAnio = data;
          this.optionsPeriodo  = Smartwfm.createSelectOptions(this.changeFormatDate(data), '');
        }, error=>{
        });
    }

    changeFormatDate(data){
      let arrayAux = [];
      data.forEach(item => {
        let fecha = item.split('-');
        arrayAux.push(ARRAY_MESES[fecha[0]] + ' - ' + fecha[1]);
      });
      return arrayAux;
    }

    changeFormatDateUniq(data){
      let fecha = data.split('-');
      let format = ARRAY_MESES[fecha[0]] + ' - ' + fecha[1];
      return format;
    }

    changeFormatDateComillas(data){
      let fecha = data.slice(1, data.length - 1);
      let arrayFecha = fecha.split('-');
      let format = arrayFecha[0] + '-' + ARRAY_MESES[arrayFecha[1]] + '-' + arrayFecha[2];
      return format;
    }


    getMovimientos(){
      if( this.userApp.username !== '' && this.fechaPeriodo !== ''){
        this.dataReport = null;
        this.subResourceService.read(CargaBatchVariable.GET_MOVIMIENTOS, {rfc:  this.userApp.username, fecha: this.fechaPeriodo})
          .subscribe(data=>{
            this.modal.success('Éxito', data.mensaje);
            this.dataReport = data;
          }, error=>{
          });
      }else{
        swal('Alerta', 'Se debe seleccionar RFC y Periodo', 'info');
      }
    }
}
