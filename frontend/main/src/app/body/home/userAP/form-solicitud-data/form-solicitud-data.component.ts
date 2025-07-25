import { Component, Inject, Input, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, UntypedFormControl, Validators, NgForm } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { Router } from '@angular/router';
import moment from 'moment';
import { solicitudAP } from 'src/app/core/interface/apUser/apUser';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { SolicitudVariable } from 'src/app/core/static/variables/url/URLImages';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import { DependenciesService } from 'src/app/shared/services/dependencies.service';
import { SolicitudesServices } from 'src/app/shared/services/solicitudes.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-form-solicitud-data',
  templateUrl: './form-solicitud-data.component.html',
  styleUrls: ['./form-solicitud-data.component.css']
})
export class FormSolicitudDataComponent {

  today: Date = new Date();
  maxDate: Date = new Date();
  loading = true;
  llenarForm = false;

  myDatepickerFechaFinLab: Date = new Date();
  myDatepickerFechaSolic: Date = new Date();
  myDatepickerFechaPago: Date = new Date();

  date1 = new UntypedFormControl();
  date2 = new UntypedFormControl();
  date3 = new UntypedFormControl();

  optionsDep: SelectMenu[];
  currentDep: SelectMenu;

  optionsTipoTramite: SelectMenu[];
  currentTipoTramite: SelectMenu;

  optionsTipoTramiteDoc: SelectMenu[];
  currentTipoTramiteDoc: SelectMenu;

  optionsBanco: SelectMenu[];
  currentBanco: SelectMenu;

  optionsRFC: SelectMenu[] = null;
  currentRFC: SelectMenu;

  optionsTipoPago: SelectMenu[];
  currentTipoPago: SelectMenu;

  optionsMonto: SelectMenu[];
  currentMonto: SelectMenu;

  dataTramite = [
    {data: 'Retiro Total', id: 0},
    {data: 'Retiro Parcial', id: 0},
    {data: 'Retiro Complementario' ,id: 0}
  ]

  // probando
  mostrar = false;
  dataTramite2 : any;

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

  dataPago = [
    { id: 1	, data:'Cheque'},
    { id: 2	, data:'Transferencia bancaria'},
  ]

  dataPorcentaje = [
    { id: 1	, data:'10%'},
    { id: 2	, data:'20%'},
    { id: 2	, data:'30%'},
    { id: 2	, data:'40%'},
    { id: 2	, data:'50%'},
  ]

   @Input() solicitud: solicitudAP = {
    idSolicitud: 0,
    tipoTramite: '',
    rfcAsegurado: '',
    rfcConfirmar: '',
    nombre: '',
    apellidoPaterno: '',
    apellidoMaterno: '',
    dependencia: '',
    telefono: '',
    email: '',
    importeSolicitado: '',
    diasTranscurridos: '',
    fechaFinLaboral: '',
    fechaSolicitudAPV: '',
    nombreBanco: '',
    clabe: '',
    idBanco: 0,
    observaciones: '',
    fechaSolicitud: '',
    rfcEmpleado: '',
    listObs: [],
    tipoPago: '',
    isValid: false,
    messageError: '',
    rfcGEM: '',
    rfcEmpleadoGeneraOrden: '',
    sexo: '',
    fechaNac: '',
    fechaOrdenPago: '',
    idEmpleadoGeneraOrden: 0,
    nombreEmpleadoGeneraOrden: '',
    fechadeTransferencia: moment().format('YYYY-MM-DD HH:mm:ss'),
    fechaImporteContable: moment().format('YYYY-MM-DD HH:mm:ss'),
    estPagRechPen: '',
    numChequeTransf: 0,
    obsSiniestros: '',
    statusSolicitud: 'Nueva',
    faltanteAPagar: '',
    valorQuincValidar: '',
    quincAgoFeb: '',
    montoCalculado: '',
    analistaComercialValida: '',
    pagoAnterior: '',
    fechaPago: '',
    empleadoAsignacion: ''
  }

  @Input() arrayFiles = [
    {id: 1, label:'Seleccionar archivo', file: null },
    {id: 2, label:'Seleccionar archivo', file: null },
    {id: 3, label:'Seleccionar archivo', file: null },
    {id: 4, label:'Seleccionar archivo', file: null },
    {id: 5, label:'Seleccionar archivo', file: null },
    {id: 6, label:'Seleccionar archivo', file: null },
    {id: 7, label:'Seleccionar archivo', file: null },
    {id: 8, label:'Seleccionar archivo', file: null },
    {id: 9, label:'Seleccionar archivo', file: null },
    {id: 10, label:'Seleccionar archivo', file: null },
    {id: 11, label:'Seleccionar archivo', file: null }
  ]

  @Input() arrayPdf = [
    {id: 0, tipoDocumento: 1, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 2, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 3, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 4, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 5, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 6, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 7, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 8, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 9, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 10, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 11, tipoArchivo: 0 }
  ];

  @Input() isAnalista: boolean = false;

  @Input() prueba : any;

  ultRecibo = 'Seleccionar archivo';
  fileUltimoRecibo: File = null;

  fumBaja = 'Seleccionar archivo';
  fileFumBaja: File = null;

  identificacion = 'Seleccionar archivo';
  fileIdentificacion: File = null;

  solicAhorro = 'Seleccionar archivo';
  fileSolicAhorro: File = null;

  compDomicilio = 'Seleccionar archivo';
  fileCompDomicilio: File = null;

  estadoCuenta = 'Seleccionar archivo';
  fileEstadoCuenta: File = null;

  formFiniquito = 'Seleccionar archivo';
  fileFormFiniquito: File = null;


  observacionesAPVida = ''; // Se cambiara a arreglo de observaciones AP vida

  userApp: any;

  fecha = '';

  isContable = false;
  isSiniestros = false;
  isComercial = false;
  isExterno = false;
  observacionDoc: any;

  constructor(@Inject('ServiceResource') private subResourceService: SubResourceService<any>, private dependencies_: DependenciesService, private fb:FormBuilder, private _router:Router, private getSolicitudes: SolicitudesServices ) {


    this.fecha = moment().format('YYYY-MM-DD');
    this.date1 = new UntypedFormControl({ value: '', disabled: true });
    this.date2 = new UntypedFormControl({ value: this.today, disabled: true });
    this.date3 = new UntypedFormControl({ value: this.today, disabled: true });

    this.initOptionsTipoTramite();
    this.initOptionsMonto();
    this.roles();
    this.initOptionsBanco();
    this.initOptionsTipoPago();
    this.initOptionDependencias();

    setTimeout(() => {
    this.getDiasTranscurridos();

    if (this.prueba === true) {
      return
    }

    //Validacion para aclaración
     if (this.isContable === false && this.isSiniestros === false && this.isComercial === false && this.isExterno){
       this.subResourceService.list(SolicitudVariable.GET_SOLICITUDES_BY_EMPLEADO,'' , {rfc: this.userApp.username})
       .subscribe(data => {
         if(data[0].statusSolicitud !== 'Terminada'){
           swal('Información', 'Actualmente tienes una solicitud en proceso, solo podrás hacer una solicitud de tipo aclaración', 'warning');
           this.mostrar = true;
           this.dataTramite2 = [
             {data: 'Aclaración', id: 0},
           ]
         }
       }, error=>{
         console.log({error});
       });
     }
    }, 500);
  }

  roles(){
    let userExterno = JSON.parse(localStorage.getItem('currentUser'));
    let com = JSON.parse(localStorage.getItem('currentUserComercial'));
    let sin = JSON.parse(localStorage.getItem('currentUserSiniestros'));
    let cont = JSON.parse(localStorage.getItem('currentUserContabilidad'));
    if(com !== null){
      this.userApp = com;
      this.isComercial = true;
      if(this.optionsRFC == null){
        this.getRfcExternos();
      }
    }else
    if(sin !== null){
      this.userApp = sin;
      this.isSiniestros = true;
    }else
    if(cont !== null){
      this.userApp = cont;
      this.isContable = true;
    }else
    if(userExterno !== null){
      this.userApp = userExterno;
      this.isExterno = true;
    }
  }

  ngOnChanges(changes: SimpleChanges){
    if(changes.solicitud){
      this.initDate();
      if(this.optionsRFC === null){
        this.getRfcExternos();
      }
    }
  }

  getDiasTranscurridos(){
    if(this.today !== null){
      let today = moment();
      let fechaCreacion = moment(this.solicitud.fechaSolicitudAPV);
      let days = today.diff(fechaCreacion, 'days');
      this.solicitud.diasTranscurridos = days.toString();
    }
  }

  getRfcExternos(){
    if((this.isComercial && (this.solicitud.statusSolicitud == 'PENDIENTE DE DOCS' || this.solicitud.statusSolicitud == 'Nueva' )) || (this.isSiniestros && (this.solicitud.statusSolicitud == 'En proceso'))){
      this.subResourceService.read(SolicitudVariable.GET_CAT_ASEGURADOS, {})
      .subscribe(data=>{
        data.forEach(item => {
          item.nameCombo = item.rfc + ', ' + item.nombre + ' ' + item.apellidoPaterno + ' ' + item.apellidoMaterno;
        });
        this.optionsRFC = Smartwfm.createSelectOptions(data, 'nameCombo');
      });
    }
  }

  setOptionRFC(){
    this.optionsDep.forEach(item => {
      if(item.extras.rfc == this.solicitud.rfcAsegurado){
        this.currentRFC = item;
      }
    });
  }

  getRFC($event){
    this.solicitud.rfcConfirmar = $event.extras.rfc;
    this.solicitud.rfcGEM = $event.extras.rfc;
    this.solicitud.nombre = $event.extras.nombre.toLowerCase();
    this.solicitud.apellidoPaterno = $event.extras.apellidoPaterno.toLowerCase();
    this.solicitud.apellidoMaterno = $event.extras.apellidoMaterno.toLowerCase();
  }

  initDate(){
    if(this.solicitud.fechaSolicitudAPV !== ''){
      this.date2 = new UntypedFormControl({ value: moment(this.solicitud.fechaSolicitudAPV).toDate(), disabled: true });
      this.myDatepickerFechaSolic = moment(this.solicitud.fechaSolicitudAPV).toDate();
      this.solicitud.fechaSolicitudAPV = moment(this.solicitud.fechaSolicitudAPV).format('YYYY-MM-DD HH:mm:ss');
    }

    if(this.solicitud.fechaFinLaboral !== ''){
      this.date1 = new UntypedFormControl({ value: moment(this.solicitud.fechaFinLaboral).toDate(), disabled: true });
      this.myDatepickerFechaFinLab = moment(this.solicitud.fechaFinLaboral).toDate();
    }

    if(this.solicitud.fechaPago !== ''){
      this.date3 = new UntypedFormControl({ value: moment(this.solicitud.fechaPago).toDate(), disabled: true });
      this.myDatepickerFechaPago = moment(this.solicitud.fechaPago).toDate();
    }
  }

  initOptionDependencias(){
    this.dependencies_.getDependencies().subscribe( data => {
      this.optionsDep = Smartwfm.createSelectOptions(data, 'data');
      setTimeout(() => {
        this.loading = false;
      }, 1000);
      if(this.solicitud.dependencia != ''){
        this.setOptionDependencia();
      }
    })
  }

  setOptionDependencia(){
    this.optionsDep.forEach(item => {
      if(item.extras.data == this.solicitud.dependencia){
        this.currentDep = item;
      }
    });
  }

  initOptionsTipoTramite(){
    this.optionsTipoTramite = Smartwfm.createSelectOptions(this.dataTramite, 'data');
    if(this.solicitud.tipoTramite != ''){
      this.setOptionTipoTramite();
    }
  }

  setOptionTipoTramite(){
    this.optionsTipoTramite.forEach(item => {
      if(item.extras.data == this.solicitud.tipoTramite){
        this.currentTipoTramite = item;
      }
    });
  }

  initOptionsBanco(){
    this.optionsBanco = Smartwfm.createSelectOptions(this.dataBancos, 'data');
    if(this.solicitud.nombreBanco != ''){
      this.optionsBanco.forEach(item => {
        if(item.extras.data == this.solicitud.nombreBanco){
          this.currentBanco = item;
        }
      });
    }
  }


  initOptionsTipoPago(){
    this.optionsTipoPago = Smartwfm.createSelectOptions(this.dataPago, 'data');
      if(this.solicitud.tipoPago != ''){
        this.setOptionTipoPago();
      }
  }

  setOptionTipoPago(){
    this.optionsTipoPago.forEach(item => {
      if(item.extras.data == this.solicitud.tipoPago){
        this.currentTipoPago = item;
      }
    });
  }

  initOptionsMonto(){
    this.optionsMonto = Smartwfm.createSelectOptions(this.dataPorcentaje, 'data');
    if(this.solicitud.importeSolicitado != ''){
      this.optionsMonto.forEach(item => {
        if(item.extras.data == this.solicitud.importeSolicitado){
          this.currentMonto = item;
        }
      });
    }

  }

  getDep(event: { extras: { data: string; }; }){
    this.solicitud.dependencia = event.extras.data;
  }

  getTipoTramite(event){
    this.solicitud.tipoTramite = event.extras.data;
  }

  getTipoTramiteDoc(event){
    this.solicitud.tipoTramite = event.extras.data;
  }

  getBanco(event){
    this.solicitud.nombreBanco = event.extras.data;
    this.solicitud.idBanco = event.extras.id;
  }

  getTipoPago(event){
    this.solicitud.tipoPago = event.extras.data;
  }
  getMonto(event){
    this.solicitud.importeSolicitado = event.extras.data;
  }

  getFechaFinLab(type: string, event: MatDatepickerInputEvent<Date>) {
    this.myDatepickerFechaFinLab = event.value;
    this.solicitud.fechaFinLaboral = moment(this.myDatepickerFechaFinLab).format('YYYY-MM-DD HH:mm:ss');
  }

  getFechaSolic(type: string, event: MatDatepickerInputEvent<Date>) {
    this.myDatepickerFechaSolic = event.value;
    this.solicitud.fechaSolicitudAPV = moment(this.myDatepickerFechaSolic).format('YYYY-MM-DD HH:mm:ss');
    this.getDiasTranscurridos();
  }

  getFechaPago(type: string, event: MatDatepickerInputEvent<Date>) {
    this.myDatepickerFechaPago = event.value;
    this.solicitud.fechaPago = moment(this.myDatepickerFechaPago).format('YYYY-MM-DD HH:mm:ss');
  }

  validateTipoPago(){
    if(this.solicitud.tipoPago === 'Cheque'){
      return true;
    }else{
      if(this.solicitud.nombreBanco !== '' && this.solicitud.clabe !== ''){
        return true;
      }else{
        return false;
      }
    }
  }

  getArchivo(archivo, opt){
    switch (opt) {
      case 1:
          // Ultimo recibo
        this.arrayFiles[0].file = archivo.item(0);
        this.arrayFiles[0].label = archivo[0].name;
        break;
      case 2:
        //FUM de baja
        this.arrayFiles[1].file = archivo.item(0);
        this.arrayFiles[1].label = archivo[0].name;
        break;
      case 3:
        // Identificación
        this.arrayFiles[2].file = archivo.item(0);
        this.arrayFiles[2].label = archivo[0].name;
        break;
      case 4:
        // Fallecimiento
        this.arrayFiles[3].file = archivo.item(0);
        this.arrayFiles[3].label = archivo[0].name;
        break;
      case 5:
        // Comprobante de domicilio
        this.arrayFiles[4].file = archivo.item(0);
        this.arrayFiles[4].label = archivo[0].name;
        break;
      case 6:
        // Estado de cuenta
        this.arrayFiles[5].file = archivo.item(0);
        this.arrayFiles[5].label = archivo[0].name;
        break;
      case 7:
        // Sustento en el cual observe diferencia
        this.arrayFiles[6].file = archivo.item(0);
        this.arrayFiles[6].label = archivo[0].name;
        break;
      case 8:
        // Invalidez total y permanente
        this.arrayFiles[7].file = archivo.item(0);
        this.arrayFiles[7].label = archivo[0].name;
        break;
      case 9:
        // Formato de Solicitud de Pago
        this.arrayFiles[8].file = archivo.item(0);
        this.arrayFiles[8].label = archivo[0].name;
        break;
      case 10:
        // Formato de FINIQUITO
        this.arrayFiles[9].file = archivo.item(0);
        this.arrayFiles[9].label = archivo[0].name;
        break;

      default:
        break;
    }
  }

  donwloadPdf(id, name, tipoArchivo){
    let dataArchivo = tipoArchivo === 1 ? 'data:application/pdf;base64,' : 'data:image/png;base64,' ;
    this.subResourceService.read(SolicitudVariable.GET_DOCUMENTO, {id: id})
    .subscribe(data=>{
      let element = document.createElement('a');
        element.setAttribute('href', dataArchivo+''+data.stringPdf);
        element.setAttribute('download', name + '_'+this.solicitud.rfcAsegurado);
        element.setAttribute('target', '_blank');
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
    }, error=>{
      console.log(error);
    });
  }

  formIncomplet = ( forms : NgForm ) => {
    this.llenarForm = true;
      Object.values( forms.controls ).forEach( control =>{
        control.markAsTouched();
      });

    this.solicitud.statusSolicitud === 'En proceso' ?
    swal( '¡Atención!', 'Debes guardar el documento de FINIQUITO', 'warning' ) : swal( '¡Atención!', 'Falta información', 'warning' );
  }

  // Formulario template
  guardar = ( forms : NgForm ) => {


    // Validaciones antes de proceder a guardar
    if( forms.invalid ){
      this.formIncomplet( forms )
      return;
    }

    if ( this.solicitud.tipoTramite === 'Retiro Total' && this.solicitud.statusSolicitud === 'Nueva') {
      if(this.arrayFiles[8].file === null ){
        this.formIncomplet( forms );
        return;
      }
    }if( this.solicitud.tipoTramite === 'Retiro Parcial' && this.solicitud.statusSolicitud === 'Nueva'){
      if(this.arrayFiles[8].file === null ){
        this.formIncomplet( forms );
        return;
      }
    }
    // Validacion de retiro complementario
    // if( this.solicitud.tipoTramite === 'Retiro Complementario' && this.solicitud.statusSolicitud == 'Nueva' ){
    //   console.log(this.solicitud.statusSolicitud);
    //   console.log('2');
    //   if(this.arrayFiles[0].file === null || this.arrayFiles[6].file === null ){
    //     this.formIncomplet( forms );
    //     return;
    //   }
    // }


    // Si todo esta 'OK', proceder a guardar
    this.llenarForm = false;
    this.solicitud.isValid = true;

     if (this.solicitud.rfcGEM === '') {
       this.solicitud.rfcGEM = this.solicitud.rfcAsegurado
     }

    if(this.solicitud.isValid){
      swal({
        title: 'Atención',
        text: "¿Esta seguro de continuar?",
        type: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Aceptar'
      }).then((result) => {
        if(result.value){
          this.solicitud.fechaSolicitud = this.solicitud.fechaSolicitudAPV;
          this.solicitud.rfcEmpleado = this.isExterno ? '' : this.userApp.username;
          this.solicitud.analistaComercialValida = this.solicitud.rfcEmpleado;
          this.solicitud.empleadoAsignacion = this.isExterno ? '' : this.userApp.username;
          this.solicitud.tipoTramite == 'Retiro Total'  ? this.solicitud.importeSolicitado = '100%' : this.solicitud.tipoTramite == 'Retiro Complementario' ? this.solicitud.importeSolicitado = '0%' : this.solicitud.tipoTramite == 'Aclaracion' ? this.solicitud.importeSolicitado = '0%' : null;
          this.solicitud.sueldo == '' ? this.solicitud.sueldo = '0' : null;
          if (this.solicitud.tipoTramite == "Aclaracion") {
            this.solicitud.diasTranscurridos = "0",
            this.solicitud.fechaImporteContable = this.solicitud.fechaSolicitud,
            this.solicitud.fechadeTransferencia = this.solicitud.fechaSolicitud,
            this.solicitud.numChequeTransf = 0,
            this.solicitud.pagoAnterior = "100",
            this.solicitud.tipoPago = "Cheque",
            this.solicitud.statusSolicitud = "Nueva"
            this.solicitud.obsSiniestros = "",
            this.solicitud.estPagRechPen = ""
          }
          // actualizacion de la solicitud
          if( this.solicitud.statusSolicitud === 'Falta de información' || this.solicitud.statusSolicitud === 'PENDIENTE DE DOCS'  ){
            this.subResourceService.create( this.solicitud, SolicitudVariable.UPDATE_SOLICITUD ).subscribe(
              data => {
                this.uploadDocumentos(data.idSolicitud);
                this.solicitud.statusSolicitud = 'Actualizada';
                this.changeStatus(this.solicitud);
                swal('Éxito', 'La solicitud ha sido actualizada', 'success').then(()=>{
                    this.end();
                  });
                return;
              }, error => {
                console.log({error});
                swal('Alerta', error, 'info');
                return;
              }
            )
            return;
          }
          if (this.solicitud.statusSolicitud === 'En proceso') {
            if(this.arrayFiles[9].file === null ){
              this.formIncomplet( forms );
              return;
            }
            this.uploadDocumentos(this.solicitud.idSolicitud);
            location.reload();
            return;
          }
          this.subResourceService.create( this.solicitud, SolicitudVariable.CREAR_SOLICITUD)
            .subscribe(data => {

              if (data === null || data.fechaSolicitudAPV === null ) {
                this.loading = true;
                swal('Error', 'La solicitud ha sido rechazada, verifique los datos registrados', 'error').then(()=>{
                  this.end();
                  this.loading = false;
                });
                return;
              }

              if(data.solicActiva && data.statusSolicitud === 'Nueva' ){
                swal('Información', 'Ya existe una solicitud con estatus: ' + data.statusSolicitud + ' del RFC GEM: ' + data.rfcGEM + ' con el folio: ' + data.numeroRegistro, 'info').then(()=>{
                  this.end();
                });
              }
              this.uploadDocumentos(data.idSolicitud);
              swal('Éxito', 'La solicitud ha sido enviada con el folio ' + data.numeroRegistro, 'success').then(()=>{
                this.end();
              });
            }, error => {
              swal('Alerta', error, 'info');
            });
        }
      });
    }else {
      swal('Información', this.solicitud.messageError, 'info');
    }
  }

  uploadDocumentos(idSolicitud){
    this.arrayFiles.forEach((item, index) => {
      if(item.file !== null){
        let tipo = item.file.type == 'application/pdf' ? '1' : '2';
        const imgBlob = new Blob([item.file], { type:  item.file.type })
        let formData: FormData = new FormData();
        formData.append('documento', imgBlob, item.label);
        formData.append('fechaCreacion', moment().format('YYYY-MM-DD HH:mm:ss'));
        formData.append('tipoDocumento', item.id.toString());
        formData.append('idSolicitud', idSolicitud);
        formData.append('tipoAccion', '1');
        formData.append('idDocumento', '0');
        formData.append('tipoArchivo', tipo);
        this.subResourceService.readPostMultipart( SolicitudVariable.DOCUMENTO_SOLICITUD, formData)
        .subscribe((response : any) => {

        });
      }
    });
  }

  end(){
    this.isExterno ? this._router.navigate(['/angular/list-solicitudes']) : this._router.navigate(['/angular/dashboard-solicitudes']);
  }

  changeStatusDoc(){
    this.solicitud.statusSolicitud = 'PENDIENTE DE DOCS';
    this.solicitud.validadoModulo = 0;
    this.solicitud.validadoSiniestros = 0;
    this.solicitud.validadoContabilidad = 0;
    this.changeStatus(this.solicitud);
  }

  saveObs(){
      let obs = {
        fechaCreacion: moment().format('YYYY-MM-DD HH:mm:ss'),
        observacion: this.observacionDoc,
        idSolicitud: this. solicitud.idSolicitud
      };
      this.crearObservacion(obs);
  }

  crearObservacion(obs){
    this.subResourceService.create(obs, SolicitudVariable.CREAR_OBSERVACION)
      .subscribe(data=>{
        this.changeStatusDoc();
      }, error=>{
        console.log(error);
      });
  }

  changeStatus(solicitud){
    this.subResourceService.create(solicitud, SolicitudVariable.UPDATE_STATUS_SOLICITUD_ANALISTAS)
      .subscribe(data=>{
        swal('Éxito', data.message, 'success')
        this.end();
      }, error=>{
        console.log(error);
      });
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

}
