import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import moment from 'moment';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import { UserAp } from 'src/app/core/interface/apUser/apUser';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { ExcelService } from 'src/app/core/services/excel-service/excel-service.service';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { GlobalVariable, SolicitudVariable } from 'src/app/core/static/variables/url/URLImages';
import { ModalService } from 'src/app/shared/services/modal.service';
import swal from 'sweetalert2';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-dashboard-analista-solicitud',
  templateUrl: './dashboard-analista-solicitud.component.html',
  styleUrls: ['./dashboard-analista-solicitud.component.css']
})
export class DashboardAnalistaSolicitudComponent implements OnInit {

  apvidaBackground  = GlobalVariable.BACKGROUND_IMG_APVIDA;
  userApp: any;
  list: number = 1;
  solicitudes = [];
  isComercial = false;
  isSiniestros = false;
  isContabilidad = false;
  isExterno = false;
  optionsTipoTramite: SelectMenu[];
  currentTipoTramite: SelectMenu;
  optionsEstatus: SelectMenu[];
  currentEstatus: SelectMenu;
  tipoTramite = 'Todos';
  estatus = 'Todos';
  rfc = '';
  folio = '';
  solicitudesAux = [];
  allLayout: boolean = false;
  allReporteActuaria: boolean = false;
  fileNameExcel = 'Cargar Archivo';
  fileNameExcel2 = '';
  jsonExcel = '';
  listCalc = [];
  showList = 1;
  loading = true;
  selectedEstatus = '';
  selectedTramite='';
  today = new Date();
  public dataSource = new MatTableDataSource<UserAp>();
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  dataBancos = [
    { id: 1	, data:'HSBC'},
    { id: 2	, data:'BANAMEX'},
    { id: 3	, data:'SCOTIABANK INVERLAT'},
    { id: 4	, data:'BANCOMER'},
    { id: 5	, data:'Banco Mercantil Del Norte S.A.'},
    { id: 6	, data:'IXE'},
    { id: 7	, data:'MIFEL'},
    { id: 8	, data:'BANCO MULTIVA SA'},
    { id: 9	, data:'BANCO AUTOFIN MEXICO'},
    { id: 10, data:'	ACTINVER'},
    { id: 11, data:'	BANCO DEL BAJIO'},
    { id: 12, data:'	BANCO NACIONAL DEL EJERCITO'},
    { id: 13, data:'	BANCO COPPEL'},
    { id: 14, data:'	Banco Santander (México) S.A.'},
    { id: 15, data:'	AMEX'},
    { id: 16, data:'	BANREGIO'},
    { id: 17, data:'	INBURSA'},
    { id: 18, data:'	BANK OF AMERICA MEXICO, S.A.'},
    { id: 19, data:'	BANCO AZTECA'},
    { id: 20, data:'	BANSEFI'},
    { id: 21, data:'	CIBANCO'},
    { id: 22, data:'	BANCO FAMSA'},
    { id: 23, data:'	LIBERTAD SERVICIOS FINANCIEROS, S.A. DE C.V., SFP'},
    { id: 24, data:'	BANCO COMPARTAMOS, S.A.'},
    { id: 25, data:'	BANCO VE POR MAS'},
    { id: 26, data:'	BANCA AFIRME'},
    { id: 27, data:'	BANCO MONEX, S.A.'},
    { id: 58, data:'	GLOBAL BANK CORPORATION'}
  ]

  solicitud = {
    nombre : "",
    RFC: "",
    tramite: "",
    status: ""
  };

  constructor(
    private router: Router,
    @Inject('ServiceResource') private subResourceService: SubResourceService<any>,
    private excelService:ExcelService,
    private modal: ModalService,
    private announcer: LiveAnnouncer,
  ) {}

  ngOnInit() {
    let userExterno = JSON.parse(localStorage.getItem('currentUser'));
    let com = JSON.parse(localStorage.getItem('currentUserComercial'));
    let sin = JSON.parse(localStorage.getItem('currentUserSiniestros'));
    let cont = JSON.parse(localStorage.getItem('currentUserContabilidad'));
    if(com !== null){
      this.userApp = com;
      this.isComercial = true;
    }else
    if(sin !== null){
      this.userApp = sin;
      this.isSiniestros = true;
    }else
    if(cont !== null){
      this.userApp = cont;
      this.isContabilidad = true;
    }else
    if(userExterno !== null){
      this.userApp = userExterno;
      this.isExterno = true;
    }

    this.refreshList();

  }

  ngAfterViewInit():void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  refreshList(){
    this.getSolicitudes();
    this.initOptionsTipoTramite();
    this.initOptionsEstatus();
    if(this.isContabilidad){
      this.getListCalculo();
    }
  }

  addSolicitud(){
    this.router.navigate(['/angular/form-solicitudes'], {queryParams: {opt: 1}});
  }

  verDetalleSolicitud(item){
    this.router.navigate(['/angular/form-edit-solicitudes'], {queryParams: {solicitud: item.idSolicitud, opt: 1}});
  }

  cancelSolicitud(item){
    item.statusSolicitud = 'Cancelada';
    this.subResourceService.create(item, SolicitudVariable.UPDATE_STATUS_SOLICITUD)
    .subscribe(data=>{
      this.modal.success('Éxito', data.message)
    }, error=>{
      console.log(error);
    });
  }

  getSolicitudes(){
    this.subResourceService.list(SolicitudVariable.GET_SOLIITUDES_ANALISTAS,'' ,{nombre: '', RFC: '', tramite: '', status: ''})
      .subscribe( data=> {
        data.forEach(item => {
          item.isLayout = false;
          item.isReporteContable = false;
        });
       let dataFiltrada = data.filter((data) => data.empleadoAsignacion === this.userApp.username  );
       this.solicitudes = dataFiltrada;
       this.solicitudesAux = Object.assign([],data);
       this.dataSource.data = this.solicitudes
      }, error=>{
        console.log(error);
      });
      setTimeout(() => {
        this.loading = false;
      }, 500);
  }

  getListCalculo(){
    this.subResourceService.list(SolicitudVariable.GET_LIST_CALCULO,'' ,{})
      .subscribe(data=>{
        this.listCalc = data;
      }, error=>{
        console.log(error);
      });
  }

  showLists(opt){
    this.showList = opt;
  }

  initOptionsTipoTramite(){
    let data = [
      {data: 'Todos', id: 0},
      {data: 'Retiro Total', id: 0},
      {data: 'Retiro Parcial', id: 0},
      {data: 'Retiro Complementario' ,id: 0}
    ]

    this.optionsTipoTramite = Smartwfm.createSelectOptions(data, 'data');

  }

  initOptionsEstatus(){
    let data = [
      {data: 'Todos', id: 0},
      {data: 'Nueva', id: 0},
      {data: 'En proceso', id: 0},
      {data: 'PENDIENTE DE DOCS' ,id: 0},
      {data: 'Proceso de revision de pago' ,id: 0},
      {data: 'Importes validados' ,id: 0},
      {data: 'Actualizada' ,id: 0},
      {data: 'Falta de información' ,id: 0},
      {data: 'Terminada' ,id: 0},
      {data: 'Rechazada' ,id: 0}
    ]

    this.optionsEstatus = Smartwfm.createSelectOptions(data, 'data');
  }

  getTipoTramite(event:  string ){
    this.tipoTramite = event;
    this.filterRFC();
  }
  getEstatus(event: string){
    this.estatus = event;
    this.filterRFC();
  }

  filterRFC(){
    if ((this.rfc === '' || this.rfc === null) && (this.folio === '' || this.folio === null)) {
      this.solicitudes = Object.assign([], this.filterCombos());
    } else if((this.rfc !== '' && this.rfc !== null) && (this.folio === '' || this.folio === null)) {
      this.solicitudes = Object.assign([], this.filterCombos());
      this.solicitudes = this.solicitudes.filter((item) => {
        return ( item.rfcAsegurado.toLowerCase().indexOf(this.rfc.toLowerCase()) ) !== -1;
      })
    }else if((this.rfc === '' || this.rfc === null) && (this.folio !== '' && this.folio !== null)) {
      this.solicitudes = Object.assign([], this.filterCombos());
      this.solicitudes = this.solicitudes.filter((item) => {
        return item.numeroRegistro === Number(this.folio) ? item : null;
      })
    }else if((this.rfc !== '' && this.rfc !== null) && (this.folio !== '' && this.folio !== null)) {
      this.solicitudes = Object.assign([], this.filterCombos());
      this.solicitudes = this.solicitudes.filter((item) => {
        return (item.numeroRegistro === Number(this.folio) ? item : null) &&
        (( item.rfcAsegurado.toLowerCase().indexOf(this.rfc.toLowerCase()) ) !== -1);
      })
    }
  }

  filterCombos(){
    let arrayAux = [];
    if(this.estatus !== 'Todos'){
      if(this.tipoTramite == 'Todos'){
        this.solicitudesAux.forEach(item => {
          if(item.statusSolicitud === this.estatus){
            arrayAux.push(item);
          }
        });
        return arrayAux;
      }else{
        this.solicitudesAux.forEach(item => {
          if(item.statusSolicitud === this.estatus && item.tipoTramite === this.tipoTramite){
            arrayAux.push(item);
          }
        });
        return arrayAux;
      }
    }else if(this.estatus == 'Todos'){
      if(this.tipoTramite == 'Todos'){
        return this.solicitudesAux;
      }else{
        this.solicitudesAux.forEach(item => {
          if(item.tipoTramite === this.tipoTramite){
            arrayAux.push(item);
          }
        });
        return arrayAux;
      }
    }
  }

  exportAsXLSX() {
    let arrayAux = [];
    this.solicitudes.forEach(item => {
      if(item.statusSolicitud === 'Importes validados' && item.isLayout && item.fechaOrdenPago == null){
        arrayAux.push(item);
      }
    });
    if(arrayAux.length > 0){
      this.question('¿Estas seguro que quieres generar el archivo de ordenes de pago?').then(data=>{
        if(data){
          arrayAux.forEach(item => {
            item.rfcEmpleadoGeneraOrden = this.userApp.username;
            item.fechaOrdenPago = moment().format('YYYY-MM-DD HH:mm:ss');
          });
          this.saveLayoutFecha(arrayAux);
        }
      });

    }else{
      this.modal.info('Información', 'Se debe de seleccionar por lo menos una solicitud para generar el layout de ordenes de pago.');
    }
  }

  saveLayoutFecha(arrayAux){
    this.subResourceService.create( arrayAux,SolicitudVariable.UPDATE_FECHA_ORDEN_PAGO)
      .subscribe(data=>{
        this.buildLayout(data.idOrdenPago);
      }, error=>{
        console.log(error);
      });
  }


  buildLayout(idOrdenPago){
    this.subResourceService.read(SolicitudVariable.GET_ORDEN_PAGO_LAYOUT, {idOrdenPago: idOrdenPago})
    .subscribe(data=>{
      let dataLayout = [];
      let optionsBanco = Smartwfm.createSelectOptions(this.dataBancos, 'data');
      data.forEach((item, index) => {
        let prueba = optionsBanco.filter(banco => banco.extras.id === item.idBanco ).map( info => {
          return info.extras.data;
        })
        console.log({item});
        let data1 = {
          Consecutivo: (index+1),
          Ap_paterno: item.apellidoPaterno,
          ap_materno: item.apellidoMaterno,
          Nombre: item.nombre,
          RFC: item.rfcGEM,
          Fnac: item.fechaNac ? moment(item.fechaNac).format('DD/MM/YYYY') : 'No Existe',
          fecha_Alta: moment(item.fechaSolicitud).format('DD/MM/YYYY'),
          sexo: item.sexo,
          Notas: item.tipoTramite,
          Sn_transferencia: item.tipoPago,
          Banco: item.idBanco !== 0 ? prueba[0] : '',
          CLABE: item.clabe,
          BANCO_INT: 'NINGUNO',
          IMPORTE: (Math.round(item.importeApagar * 100) / 100).toFixed(2)
        }
        dataLayout.push(data1);
      });
      this.excelService.exportAsExcelFile(dataLayout, data.length > 0 ? 'Orden_Pago_' +data[0].folioOrdenPago + '_' + data[0].fechaOrdenPago : 'Reporte');
      this.modal.success('Éxito', 'Se generó correctamente el reporte de ordenes de pago');
      this.refreshList();
    }, error=>{
      console.log({error});
    });

  }

  selectAll(){
    if(this.allLayout){
      this.solicitudes.forEach(item => {
        if(item.statusSolicitud === 'Importes validados' && item.fechaOrdenPago == null){
          item.isLayout = true;
        }
      });
    }else{
      this.solicitudes.forEach(item => {
        if(item.statusSolicitud === 'Importes validados' && item.fechaOrdenPago == null){
          item.isLayout = false;
        }
      });
    }
  }

  exportAsXLSXActuaria() {
    let arrayAux = [];
    this.solicitudes.forEach(item => {
      if(item.statusSolicitud === 'Proceso de revision de pago' && item.isReporteContable && item.idCalculoActuaria == 0){
        arrayAux.push(item);
      }
    });
    if(arrayAux.length > 0){
      this.question('¿Estas seguro que quieres generar el archivo para cálculo actuaria?').then(data=>{
        if(data){
          arrayAux.forEach(item => {
            item.rfcEmpleadoGeneraOrden = this.userApp.username;
            item.fechaOrdenPago = moment().format('YYYY-MM-DD HH:mm:ss');
          });
          this.saveFechaReporteActuaria(arrayAux);
        }
      });

    }else{
      this.modal.success('Información', 'Se debe de seleccionar por lo menos una solicitud para generar el archivo para cálculo actuaria.');
    }
  }

  saveFechaReporteActuaria(arrayAux){
    this.subResourceService.create( arrayAux,SolicitudVariable.CREAR_LAYOUT_CALCULO_ACTUARIA)
      .subscribe(data=>{
        this.buildLayoutActuaria(data.idCalculo);
      }, error=>{
        console.log(error);
      });
  }

  buildLayoutActuaria(idCalculo){
    this.subResourceService.read(SolicitudVariable.GET_DATA_CALCULO_ACTUARIA, {idCalculo: idCalculo})
    .subscribe(data=>{
      let dataLayout = [];
      console.log({data});
      data.forEach((item, index) => {
        let data1 = {
          NumProceso: item.numProcesoCalculo,
          FechaProceso: item.fechaCreacionCalculo,
          NoSolicitud: item.numeroRegistro,
          Fecha_de_Solicitud: item.fechaSolicitud,
          Tipo_de_tramite: item.tipoTramite,
          RFC1: item.rfcGEM,
          RFC: item.rfcAsegurado,
          Apaterno: item.apellidoPaterno,
          Amaterno: item.apellidoMaterno,
          NOMBRE: item.nombre,
          FECHA_DE_FINLABORAL: item.fechaFinLaboral,
          DEPENDENCIA: item.dependencia,
          UsuarioAlta: item.analistaComercialValida,
          APORTACION_INICIAL: '' ,
          QUIN_74_M: '',
          QUINCENAS_AGO_FEB: '',
          INTERESES: '',
          TOTAL: '',
          MONTO_CALCULADO_POS_PAGADO: '',
          FALTANTE_A_PAGAR: '',
          VALOR_QUINCENA_VALIDAR: '',
          Importe_Solicitado: item.importeSolicitado
        };
        let data2 = {
          numero: (index+1),
          Folio_Mascara: item.numeroRegistro,
          FECHA_SOLICITUD: moment(item.fechaSolicitud).format('DD/MM/YYYY'),
          DEPENDENCIA: item.dependencia,
          MODULO_DE_ATENCION: item.analistaComercialValida,
          APELLIDO_PATERNO: item.apellidoPaterno,
          APELLIDO_MATERNO: item.apellidoMaterno,
          NOMBRE: item.nombre,
          RFC_MODULO: item.rfcAsegurado,
          RFC_GEM: item.rfcGEM,
          TIPO_DE_TRAMITE: item.tipoTramite,
          SUELDO_BASE: item.sueldo,
          FECHA_DE_FINALIZACION_LABORAL: item.fechaFinLaboral ? moment(item.fechaFinLaboral).format('DD/MM/YYYY') : '' ,
          Pago_Anterior: item.pagoAnterior,
          Total: '',
          FECHA_PAGADO: item.fechaPago ? moment(item.fechaPago).format('DD/MM/YYYY') : '' ,
          PENDIENTE: '',
          OBSERVACION: '',
          NumProceso: item.numProcesoCalculo,
          FechaProceso: moment(item.fechaCreacionCalculo).format('DD/MM/YYYY'),
          Importe_Solicitado: item.importeSolicitado,
          SALDO_FINAL: '',
          QUINCENAS: '',
          INTERES: '',
          RETIRO: '',
          SALDO: '',
          VAL_RETENCION: '',
          NETO_A_PAG: '',
          FECHA_CALCULO: ''
        }
        dataLayout.push(data2);
      });
      this.excelService.exportAsExcelFile(dataLayout, data.length > 0 ? 'Calculo_Actuaria_' +data[0].numProcesoCalculo + '_' + data[0].fechaCreacionCalculo : 'Reporte');
      this.modal.success('Éxito', 'Se generó correctamente el reporte de ordenes de pago');
      this.refreshList();
    }, error=>{
      console.log(error);
    });

  }


  selectAllActuaria(){
    if(this.allReporteActuaria){
      this.solicitudes.forEach(item => {
        if(item.statusSolicitud === 'Proceso de revision de pago' && item.idCalculoActuaria == 0 ){
          item.isReporteContable = true;
        }
      });
    }else{
      this.solicitudes.forEach(item => {
        if(item.statusSolicitud === 'Proceso de revision de pago' && item.idCalculoActuaria == 0 ){
          item.isReporteContable = false;
        }
      });
    }
  }

  question(message){
    return new Promise((resolve, reject)=>{
      swal({
        title: 'Atención',
        text: message,
        type: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Aceptar'
      }).then((result) => {
        if(result.value){
          resolve(true)
        }else{
          resolve(false);
        }
      });
    });
  }

  back( ruta ){
    if (ruta === 'mySolicitud') {
      this.router.navigate(['/angular/dashboard-analista-solicitud']);
    }
    else if  (ruta === 'solicitudes') {
      this.router.navigate(['/angular/dashboard-solicitudes']);
    }
    else if (ruta === 'asegurado') {
      this.router.navigate(['/angular/dashboard-analista']);
    }else if( ruta === 'analista' ){
      this.router.navigate(['/angular/dashboard-all-analista']);
    }
  }

  donwloadReport(idOrdenPago){
    this.buildLayout(idOrdenPago);
  }


  subirExcel(ev) {
    let name = 'Reporte';
    this.fileNameExcel2 = ev.target.files[0].name;
    if(ev.target.files[0].type == 'application/vnd.ms-excel'){
      let workBook = null;
      let jsonData = null;
      const reader = new FileReader();
      const file = ev.target.files[0];
      reader.onload = (event) => {
        const data = reader.result;
        workBook = XLSX.read(data, { type: 'binary',cellDates: true });
        jsonData = workBook.SheetNames.reduce((initial, name) => {
          const sheet = workBook.Sheets[name];
          initial[name] = XLSX.utils.sheet_to_json(sheet);
          return initial;
        }, {});
        const dataString = JSON.stringify(jsonData);
        this.jsonExcel = dataString;
        this.buildCalculoActuaria(JSON.parse(this.jsonExcel).data);
      }
      reader.readAsBinaryString(file);
    }
  }

  buildCalculoActuaria(data){
    let dataUpload = [];
    data.forEach((item, index) => {
      if((item.INTERES && item.INTERES !== '' && item.INTERES !== 0)
      && (item.NETO_A_PAG && item.NETO_A_PAG !== '' && item.NETO_A_PAG !== 0)){
        let solic = {
          numProcesoCalculo: item.NumProceso ,
          fechaCreacionCalculo: item.FechaProceso,
          numeroRegistro: item.NoSolicitud,
          fechaSolicitud: item.Fecha_de_Solicitud,
          tipoTramite: item.Tipo_de_tramite,
          rfcGEM: item.RFC1,
          rfcAsegurado: item.RFC,
          apellidoPaterno: item.Apaterno,
          apellidoMaterno: item.Amaterno,
          nombre: item.NOMBRE,
          fechaFinLaboral: item.FECHA_DE_FINLABORAL,
          dependencia: item.DEPENDENCIA,
          nombreAnalistaComercialValida: item.UsuarioAlta,
          aportacionTotal: item.APORTACION_INICIAL ? item.APORTACION_INICIAL : 0,
          quinM: item.QUIN_74_M ? item.QUIN_74_M : 0,
          quincAgoFeb: item.QUINCENAS_AGO_FEB ? item.QUINCENAS_AGO_FEB : 0,
          intereses: item.INTERESES,
          importeApagar: item.TOTAL,
          montoCalculado: item.MONTO_CALCULADO_POS_PAGADO ? item.MONTO_CALCULADO_POS_PAGADO : 0,
          faltanteAPagar: item.FALTANTE_A_PAGAR ? item.FALTANTE_A_PAGAR : 0,
          valorQuincValidar: item.VALOR_QUINCENA_VALIDAR ? item.VALOR_QUINCENA_VALIDAR : 0,
          importeSolicitado: item.Importe_Solicitado,
          fechaCarga: moment().format('DD/MM/YYYY')
        }
        let solic2 = {
          numeroRegistro: item.Folio_Mascara ,
          fechaSolicitud: item.FECHA_SOLICITUD,
          dependencia: item.DEPENDENCIA,
          analistaComercialValida: item.MODULO_DE_ATENCION,
          apellidoPaterno: item.APELLIDO_PATERNO,
          apellidoMaterno: item.APELLIDO_MATERNO,
          nombre: item.NOMBRE,
          rfcAsegurado: item.RFC_MODULO,
          rfcGEM: item.RFC_GEM,
          tipoTramite: item.TIPO_DE_TRAMITE,
          sueldo: item.SUELDO_BASE ? item.SUELDO_BASE : 0,
          fechaFinLaboral: item.FECHA_DE_FINALIZACION_LABORAL ? item.FECHA_DE_FINALIZACION_LABORAL : '',
          pagoAnterior: item.Pago_Anterior ? item.Pago_Anterior : 0,
          totalPagado: item.Total ? item.Total : 0,
          fechaPago: item.FECHA_PAGADO ? moment(item.FECHA_PAGADO).format('DD/MM/YYYY') : 0,
          estatus: item.PENDIENTE ? item.PENDIENTE : '',
          observacionesContable: item.OBSERVACION ? item.OBSERVACION : '',
          numProcesoCalculo: item.NumProceso,
          fechaCreacionCalculo: item.FechaProceso,
          importeSolicitado: item.Importe_Solicitado,
          saldoFinal: item.SALDO_FINAL ? item.SALDO_FINAL : 0,
          quincAgoFeb: item.QUINCENAS ? item.QUINCENAS : 0,
          intereses: item.INTERES,
          retiroMaximo: item.RETIRO ? item.RETIRO : 0,
          importeContable: item.SALDO ? item.SALDO : 0,
          valRetencion: item.VAL_RETENCION ? item.VAL_RETENCION : 0,
          importeApagar: item.NETO_A_PAG,
          fechaCalculo: item.FECHA_CALCULO ? moment(item.FECHA_CALCULO).format('DD/MM/YYYY') : 0,
          montoCalculado: item.MONTO_CALCULADO_POS_PAGADO ? item.MONTO_CALCULADO_POS_PAGADO : 0,
          faltanteAPagar: item.FALTANTE_A_PAGAR ? item.FALTANTE_A_PAGAR : 0,
          valorQuincValidar: item.VALOR_QUINCENA_VALIDAR ? item.VALOR_QUINCENA_VALIDAR : 0,
          aportacionTotal: item.APORTACION_INICIAL ? item.APORTACION_INICIAL : 0,
          quinM: item.QUIN_74_M ? item.QUIN_74_M : 0,
          fechaCarga: item.fechaCarga ? moment(item.fechaCarga).format('DD/MM/YYYY') : ''
        }
        dataUpload.push(solic2);
      }
    });
    this.saveArchivoCalculoActuaria(dataUpload);
  }


  saveArchivoCalculoActuaria(upload){
    if(upload.length > 0){
      this.question('¿Estas seguro que quieres subir los importes del archivo ' + this.fileNameExcel2 + ' ?').then(data=>{
      this.subResourceService.create( upload,SolicitudVariable.UPDATE_IMPORTES_DATA_LAYOUT)
      .subscribe(data=>{
        this.modal.success('Éxito', 'Se han cargado correctamente los importes')
        this.refreshList();
      }, error=>{
        console.log(error);
      });
    });
    }else{
      this.modal.info('Información', 'El archivo cargado debe tener por lo menos un registro con todos los campos completos');
    }
  }

  downloadSolicitudBatch = () => {
    let dataLayout = [];
    let optionsBanco = Smartwfm.createSelectOptions(this.dataBancos, 'data');

    this.solicitudes.forEach((item, index) => {
      let prueba = optionsBanco.filter(banco => banco.extras.id === item.idBanco ).map( info => {
        return info.extras.data;
      })
      let data = {
        id: item.numeroRegistro,
        FechaDeSolicitud: moment(item.fechaSolicitud).format('DD/MM/YYYY'),
        Dependencia: item.dependencia,
        Ap_Paterno: item.apellidoPaterno,
        Ap_Materno:  item.apellidoMaterno,
        Nombre: item.nombre,
        rfcAsegurado: item.rfcAsegurado,
        TipoDeTramite: item.tipoTramite,
        ImporteSolicitado: item.importeSolicitado,
        telefono: item.telefono,
        eMail: item.email,
        FechaFinLaboral: item.fechaFinLaboral ? moment(item.fechaFinLaboral).format('DD/MM/YYYY') : '',
        banco: item.idBanco !== 0 ? prueba[0] : '',
        CLABE: item.clabe,
        TipoDePago: item.tipoPago,
        Observaciones: item.observaciones,
        sueldo: item.sueldo
      }
      dataLayout.push(data);
    })
    this.excelService.exportAsExcelFile(dataLayout, 'SolicitudesTotales' + this.today);
    this.modal.success('Éxito', 'Se generó correctamente la descarga de solicitudes');
  }


  // Filtros nuevos de tabla nueva

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onSelectedoptionsEstatus(value:string): void {
    if (value === 'Todos' || value === '- Seleccionar un opción  -') {
      this.selectedEstatus = ''
    }else{
      this.selectedEstatus = value;
    }
  }

  onSelectedTipoTramite(value:string): void {
    if (value === 'Todos' || value === '- Seleccionar un opción  -') {
      this.selectedTramite = ''
    }else{
      this.selectedTramite = value;
    }
  }

  filter() {
    this.solicitudes = [];
    this.dataSource.data = [];
    const batchSize = 100;
    this.solicitud.status= this.selectedEstatus;
    this.solicitud.tramite=this.selectedTramite;
    this.subResourceService.read(SolicitudVariable.GET_SOLIITUDES_ANALISTAS, {
        nombre: this.solicitud.nombre,
        RFC: this.solicitud.RFC,
        tramite: this.solicitud.tramite,
        status: this.solicitud.status,
      }
    ).subscribe( (data) => {
      this.solicitudes = data;
      let currentIndex = 0;
      const addEmployees = setInterval(() => {
        const batch = this.solicitudes.slice(currentIndex, currentIndex + batchSize);
        this.dataSource.data.push(...batch);
        currentIndex += batchSize;
        this.dataSource._updateChangeSubscription();
        if (currentIndex >= this.solicitudes.length) {
          clearInterval(addEmployees)
          this.sort.sortChange.subscribe(event => {
            if (event.direction) {
              this.announcer.announce(`Sorted ${event.direction}ending`);
            } else {

              this.announcer.announce('Sorting cleared');
            }
          })
        }
      }, 500);

    }, error=>{
      swal(error, '', 'error')
      console.log({error});
    });
  }


}
