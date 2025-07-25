import { Component, OnInit, Inject } from '@angular/core';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioAcceso, GlobalVariable, PdfVariable, CargaBatchVariable } from 'src/app/core/static/variables/url/URLImages';
import { Vector } from 'src/app/core/interface/Vector';
import { ResumenMovimiento } from 'src/app/core/interface/resumenMovimiento';
import { DetalleMovimiento } from 'src/app/core/interface/DetalleMovimiento';
import { PeriodosConsulta } from 'src/app/core/interface/PeriodosConsulta';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import swal from 'sweetalert2';
import { ReporteService } from 'src/app/core/services/service-reporte/reporte.service';
import { Location } from '@angular/common';

const ARRAY_MESES = { '1':'Enero','2':'Febrero','3':'Marzo',
    '4':'Abril','5':'Mayo','6':'Junio',
    '7':'Julio','8':'Agosto','9':'Septiembre',
    '10':'Octubre','11':'Noviembre','12':'Diciembre', '01':'Enero','02':'Febrero','03':'Marzo',
    '04':'Abril','05':'Mayo','06':'Junio',
    '07':'Julio','08':'Agosto','09':'Septiembre'
    };

@Component({
  selector: 'app-reporte-admin',
  templateUrl: './reporte-admin.component.html',
  styleUrls: ['./reporte-admin.component.css']
})
export class ReporteAdminComponent implements OnInit {
  integraIcon = GlobalVariable.MAIN_LOGO_INTEGRA;
  nameUser: any = '';
  userAdmin: any;
  userApp: any;
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



  interes:number;
  rfc = '';
  anio = '';
  mes = '';
  monthSelected: any;
  anioSelected: any;

  optionsAnio: SelectMenu[];
  currentAnio: SelectMenu;

  optionsMes: SelectMenu[];
  currentMes: SelectMenu;

  arrayMesAnio = [];

  fechaPeriodo: any = '';

  constructor(private router: Router,private activatedRoute: ActivatedRoute, private reporteService: ReporteService,@Inject('ServiceResource') private subResourceService: SubResourceService<any>, private location: Location) { }

  ngOnInit() {
    this.nameUser = JSON.parse(localStorage.getItem('nameUserAP'));
    this.userApp = JSON.parse(localStorage.getItem('currentUser'));
    this.userAdmin = JSON.parse(localStorage.getItem('currentUserAdmin'));

    //this.initAnio();
    //this.initMes();
  }


  search(){
    console.log();
    this.getReporte(this.rfc, this.anioSelected, this.monthSelected);
  }


  getReporte(rfc, anio, mes){
    this.subResourceService.read(UsuarioAcceso.GET_REPORTE_AP, {rfc: rfc, anio: anio, mes: mes})
      .subscribe(data=>{
        console.log(data);
        this.dataReport = data;

        if(data.dsResumen !== null){
          this.Resumen=data.dsResumen
        }else{
          swal('Notificación', 'No se encontro registro para búsqueda deseada', 'info');
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

  initAnio(){
    let data = [
      {year: 2021},
      {year: 2020},
      {year: 2019},
      {year: 2018},
      {year: 2017}
    ]
    this.optionsAnio = Smartwfm.createSelectOptions(data, 'year');

  }

  initMes(){
    let data = [
      {mes: 'Enero', numeroMes: 1},
      {mes: 'Febrero', numeroMes: 2},
      {mes: 'Marzo', numeroMes: 3},
      {mes: 'Abril', numeroMes: 4},
      {mes: 'Mayo', numeroMes: 5},
      {mes: 'Junio', numeroMes: 6},
      {mes: 'Julio', numeroMes: 7},
      {mes: 'Agosto', numeroMes: 8},
      {mes: 'Septiembre', numeroMes: 9},
      {mes: 'Octubre', numeroMes: 10},
      {mes: 'Noviembre', numeroMes: 11},
      {mes: 'Diciembre', numeroMes: 12},
    ]
    this.optionsMes = Smartwfm.createSelectOptions(data, 'mes');

  }

  getAnio($event){
    console.log($event);
    //this.anioSelected = $event.extras.year;
    this.fechaPeriodo = this.arrayMesAnio[$event.index];
  }

  getMes($event){
    console.log($event);
    this.monthSelected = $event.extras.numeroMes;
  }

  end(){
    this.location.back();
    // this.router.navigate(['/angular/dashboard-analista'])
  }

  generatePDF(){
    console.log('Resumen: ',this.dataReport);

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
/* if(this.Resumen.PeriodosConsulta.length>0){
  for (let i = 0; i < this.Resumen.PeriodosConsulta.length; i++) {
    let periodCon = new PeriodosConsulta();
    periodCon.anio=this.Resumen.PeriodosConsulta[i].Anio;
    periodCon.mes=this.Resumen.PeriodosConsulta[i].Mes;
    periodCon.periodo=this.Resumen.PeriodosConsulta[i].Periodo;
    this.vector.listPeriodosConsulta.push(periodCon);
  }

} */
    console.log('Reporte: ',this.vector);
    this.subResourceService.create( this.vector,  PdfVariable.GENERATE_PDF)
    .subscribe(data=>{
      console.log(data);
      if(data!=null){
        this.download(data.message);
      }

    }, error=>{
      console.log(error);
      swal('Alerta', error, 'info');
    });

  }


  download(name ){

    console.log('archivo nombre: ',name);
    this.subResourceService.downloadFile(PdfVariable.DOWNLOAD_PDF, {filename: name})
    .subscribe((data) => {
      console.log(data);
      let element = document.createElement('a');
      element.setAttribute('href', "data:application/pdf;base64," +data.responseMsg);
      element.setAttribute('download', name);
      element.setAttribute('target', '_blank');
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
    });
  }


  mayus(attr) {
    this.rfc = this.rfc.replace(/[^0-9a-zA-ZñÑáéíóúÁÉÍÓÚ]/g,'');

    this.rfc = this.rfc.toUpperCase()
  }



  getComboMesAnio(){
    this.subResourceService.read(CargaBatchVariable.GET_COMBO_MES_ANIO, {rfc: this.rfc})
      .subscribe(data=>{
        console.log(data);
        this.arrayMesAnio = data;
        this.optionsAnio = Smartwfm.createSelectOptions(this.changeFormatDate(data), '');
        console.log(this.optionsAnio);
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
    if(this.rfc !== '' && this.fechaPeriodo !== ''){
      this.dataReport = null;
      this.subResourceService.read(CargaBatchVariable.GET_MOVIMIENTOS, {rfc: this.rfc, fecha: this.fechaPeriodo})
        .subscribe(data=>{
          console.log(data);
          swal('Éxito', data.mensaje, 'success');
          this.dataReport = data;
        }, error=>{
        });
    }else{
      swal('Alerta', 'Se debe seleccionar RFC y Periodo', 'info');
    }
  }
}
