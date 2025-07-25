import { Location } from '@angular/common';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { UntypedFormControl } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { ActivatedRoute, Router } from '@angular/router';
import moment from 'moment';
import { solicitudAP } from 'src/app/core/interface/apUser/apUser';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { SolicitudVariable } from 'src/app/core/static/variables/url/URLImages';
import { RoutingUtilities } from 'src/app/core/Util/routing/routing-utilities';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import swal from 'sweetalert2';

@Component({
  // router ['Angular/form-edit-solicitudes']
  selector: 'app-main-form-actualiza-solicitud',
  templateUrl: './main-form-actualiza-solicitud.component.html',
  styleUrls: ['./main-form-actualiza-solicitud.component.css']
})
export class MainFormActualizaSolicitudComponent implements OnInit {

  today: Date = new Date();
  maxDate: Date = new Date();
  optionsTipoTramite: SelectMenu[];

  myDatepickerFechaImp: Date = new Date();
  myDatepickerFechaTrans: Date = new Date();

  date1 = new UntypedFormControl();
  date2 = new UntypedFormControl();

  prueba = 'llegueLadoUno';

  solicitud: solicitudAP = {
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
    fechaSolicitudAPV: moment().format('YYYY-MM-DD HH:mm:ss'),
    nombreBanco: '',
    clabe: '',
    idBanco: 0,
    observaciones: '',
    fechaSolicitud: moment().format('YYYY-MM-DD HH:mm:ss'),
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
    faltanteAPagar: '',
    valorQuincValidar: '',
    quincAgoFeb: '',
    montoCalculado: '',
    analistaComercialValida: '',
    pagoAnterior: '',
    sueldo:'',
  }

  arrayFiles = [
    {id: 1, label:'Seleccionar archivo', file: null },
    {id: 2, label:'Seleccionar archivo', file: null },
    {id: 3, label:'Seleccionar archivo', file: null },
    {id: 4, label:'Seleccionar archivo', file: null },
    {id: 5, label:'Seleccionar archivo', file: null },
    {id: 6, label:'Seleccionar archivo', file: null },
    {id: 7, label:'Seleccionar archivo', file: null },
    {id: 8, label:'Seleccionar archivo', file: null },
    {id: 9, label:'Seleccionar archivo', file: null },
    {id: 10, label:'Seleccionar archivo', file: null }
  ]

  arrayPdf = [
    {id: 0, tipoDocumento: 1, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 2, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 3, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 4, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 5, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 6, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 7, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 8, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 9, tipoArchivo: 0 },
    {id: 0, tipoDocumento: 10, tipoArchivo: 0 }
  ];

 dataEstatus = [
    {data: 'FINALIZADO', id: 0},
    {data: 'PENDIENTE', id: 0},
  ]

  currentUser: any;

  idSolicitud = 0;
  opt = 0;

  isComercial = false;
  isSiniestros = false;
  isContabilidad = false;
  isExterno = false;

  observacionDoc = '';

  constructor(private router: Router, @Inject('ServiceResource') private subResourceService: SubResourceService<any>, private _activatedRoute: ActivatedRoute, private _location: Location) {
    this.idSolicitud = Number(RoutingUtilities.getParamsFromUrl(this._activatedRoute, 'solicitud'));
    this.opt = Number(RoutingUtilities.getParamsFromUrl(this._activatedRoute, 'opt'));

  }

  ngOnInit() {
    this.date1 = new UntypedFormControl({ value: this.today, disabled: true });
    this.date2 = new UntypedFormControl({ value: this.today, disabled: true });

    let userExterno = JSON.parse(localStorage.getItem('currentUser'));
    let com = JSON.parse(localStorage.getItem('currentUserComercial'));
    let sin = JSON.parse(localStorage.getItem('currentUserSiniestros'));
    let cont = JSON.parse(localStorage.getItem('currentUserContabilidad'));
    if(com !== null){
      this.currentUser = com;
      this.isComercial = true;
    }else
    if(sin !== null){
      this.currentUser = sin;
      this.isSiniestros = true;
    }else
    if(cont !== null){
      this.currentUser = cont;
      this.isContabilidad = true;
    }else
    if(userExterno !== null){
      this.currentUser = userExterno;
      this.isExterno = true;
    }

    this.optionsTipoTramite = Smartwfm.createSelectOptions(this.dataEstatus, 'data');

    this.getSolicitud();
  }


  getSolicitud(){
    this.subResourceService.read(SolicitudVariable.GET_SOLICITUD, {idSolicitud: this.idSolicitud})
      .subscribe(data=>{
        this.solicitud = data;
        this.buildArrayPdf(data.documentos);
      }, error=>{
        console.log(error);
        swal('Alerta', error, 'info');
      });
  }

  buildArrayPdf(array){
    array.forEach(item => {
      this.arrayPdf.forEach(item2 => {
        if(item.tipoDocumento == item2.tipoDocumento){
          item2.id = item.idDocumento;
          item2.tipoArchivo = item.tipoArchivo
        }
      });
    });
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
        formData.append('tipoAccion', this.arrayPdf[index].id > 0 ? '2': '1');
        formData.append('idDocumento', this.arrayPdf[index].id.toString());
        formData.append('tipoArchivo', tipo);
        this.subResourceService.readPostMultipart( SolicitudVariable.DOCUMENTO_SOLICITUD, formData)
        .subscribe((response : any) => {

        });
      }
    });
  }

  end(){
    this._location.back()
  }

  validarDocumentosAnalista(){
    if(this.solicitud.tipoTramite == 'Retiro Parcial'){
      if(this.arrayPdf[8].id > 0 ){
        return true;
      }else{
        return false;
      }
    }else if(this.solicitud.tipoTramite == 'Retiro Total'){
      if(this.arrayPdf[8].id > 0 ){
        return true;
      }else{
        return false;
      }
    }
    if(this.solicitud.tipoTramite == 'Retiro Complementario'){
       return true;
    }
  }


  validar(){
    if(this.isComercial){
      if(this.validarDocumentosAnalista()){
        if(this.solicitud.idSolicitud > 0){
          this.question('¿Estas seguro que quieres validar la solicitud?').then(data=>{
            if(data){
              this.solicitud.statusSolicitud = 'En proceso';
              this.solicitud.validadoModulo = 1;
              this.solicitud.validadoSiniestros = 0;
              this.solicitud.validadoContabilidad = 0;
              this.solicitud.analistaComercialValida = this.currentUser.username;
              this.changeStatus(this.solicitud);
            }
          });
        }else{
          swal('Información', 'Debe ser una solicitud registrada', 'info');
        }
      }else{
        swal('Información', 'Debe completar la carga de documentos para poder validar', 'info');
      }
    }
  }

  changeStatus(solicitud){
    this.subResourceService.create(solicitud, SolicitudVariable.UPDATE_STATUS_SOLICITUD_ANALISTAS)
      .subscribe(data=>{
        swal('Éxito', data.message, 'success')
        this.end();
      }, error=>{
      });
  }


  changeStatusDoc( status : string ){
    this.solicitud.statusSolicitud = status;
      this.solicitud.validadoModulo = 0;
      this.solicitud.validadoSiniestros = 0;
      this.solicitud.validadoContabilidad = 0;
      this.changeStatus(this.solicitud);
  }

  saveObs( status : string ){
      let obs = {
        fechaCreacion: moment().format('YYYY-MM-DD HH:mm:ss'),
        observacion: this.observacionDoc,
        idSolicitud: this. solicitud.idSolicitud
      };
      this.crearObservacion(obs, status);
  }

  crearObservacion(obs, status : string){
    this.subResourceService.create(obs, SolicitudVariable.CREAR_OBSERVACION)
      .subscribe(data=>{
        this.changeStatusDoc(status);
      }, error=>{
        console.log({error});
      });
  }

  // La api esta mala, ya que da error de respuesta 'Comentado por fernando'
  // cancelSolicitud(){
  //   this.question('¿Estas seguro que quieres cancelar la solicitud?').then(data=>{
  //     if(data){
  //       this.solicitud.statusSolicitud = 'Cancelada';
  //       console.log(this.solicitud);
  //       this.subResourceService.create(this.solicitud, SolicitudVariable.UPDATE_STATUS_SOLICITUD)
  //       .subscribe(data=>{
  //         console.log('pase la actualizacion');
  //         swal('Éxito', data.message, 'success')
  //         this.end();
  //       }, error=>{
  //         console.log({error});
  //       });
  //     }
  //   });
  // }


  changeStatusProcesoPago(){
    // this.solicitud.isValid --> solicitdaba esta informacion, que no llega aca
    if(this.solicitud.idSolicitud > 0){
    this.question('¿Estas seguro que quieres mandar a revisión de pago la solicitud?').then(data=>{
      if(data){
        this.solicitud.statusSolicitud = 'Proceso de revision de pago';
          this.solicitud.validadoModulo = 1;
          this.solicitud.validadoSiniestros = 1;
          this.solicitud.validadoContabilidad = 0;
          this.changeStatus(this.solicitud);
      }
    });
  }else{
    swal('Información', this.solicitud.messageError, 'info');
  }
  }

  procesoPago(){
    this.changeStatusProcesoPago();
  }

  mayus(attr) {
    if(attr == 'nombre' || attr == 'apellidoPaterno' || attr == 'apellidoMaterno' || attr == 'estPagRechPen' ){
      this.solicitud[attr] = this.solicitud[attr].replace(/[^a-zA-ZñÑáéíóúÁÉÍÓÚ ]/g,'');
      this.solicitud[attr] = this.solicitud[attr].toUpperCase()
    }else if(attr == 'noEmpleado' || attr == 'rfc'){
      this.solicitud[attr] = this.solicitud[attr].replace(/[^0-9a-zA-ZñÑáéíóúÁÉÍÓÚ]/g,'');
      this.solicitud[attr] = this.solicitud[attr].toUpperCase()
    } else if(attr == 'telefono' || attr == 'diasTranscurridos'){
      this.solicitud[attr] = this.solicitud[attr] ? this.solicitud[attr].replace(/[^0-9]/g,'') : '';
    } else if(attr == 'aportacionTotal' || attr == 'retiroMaximo' || attr == 'importeSolicitado' || attr == 'importeContable' || attr == 'importeApagar' || attr == 'numChequeTransf' || attr == 'intereses' || attr == 'montoCalculado' || attr == 'pagoAnterior' || attr == 'sueldo' || attr == 'saldoFinal'){
      this.solicitud[attr] = this.solicitud[attr] ? this.solicitud[attr].replace(/[^0-9.]/g,'') : '';
    }
  }

  validate(){

  }


  validarImportes(){
    if(Number(this.solicitud.aportacionTotal) !== 0 && Number(this.solicitud.retiroMaximo) !== 0 && this.solicitud.importeSolicitado !== '' && Number(this.solicitud.importeContable) !== 0 && Number(this.solicitud.importeApagar) !== 0 && Number(this.solicitud.intereses) !== 0 && Number(this.solicitud.montoCalculado) !== 0){
      this.question('¿Estas seguro que quieres validar los importes de la solicitud?').then(data=>{
        if(data){
          this.solicitud.statusSolicitud = 'Importes validados';
          this.subResourceService.create(this.solicitud, SolicitudVariable.VALIDAR_IMPORTES)
          .subscribe(data=>{
            swal('Éxito', data.message, 'success')
            this.end();
          }, error=>{
            console.log(error);
          });
        }
      });
    }else{
      swal('Información', 'Debe llenar todos los campos para poder validar', 'info');
    }
  }


  getFechaImp(type: string, event: MatDatepickerInputEvent<Date>) {
    this.myDatepickerFechaImp = event.value;
    this.solicitud.fechaImporteContable = moment(this.myDatepickerFechaImp).format('YYYY-MM-DD HH:mm:ss');
    this.validate();
  }

  getFechaTransf(type: string, event: MatDatepickerInputEvent<Date>) {
    this.myDatepickerFechaTrans = event.value;
    this.solicitud.fechadeTransferencia = moment(this.myDatepickerFechaTrans).format('YYYY-MM-DD HH:mm:ss');
    this.validate();
  }

  validarSolicitud(status){
    if(status === 'Terminada' || status == 'Rechazada'){

      //--------------------- VALIDACION LUEGO DE QUE HAYA PASADO POR ACTUARIAL -------------
      // if(Number(this.solicitud.importeApagar) !== 0 && this.solicitud.fechaImporteContable !== '' && this.solicitud.fechadeTransferencia !== '' && this.solicitud.estPagRechPen !== '' && this.solicitud.estPagRechPen !== null){
      //   this.question('¿Estas seguro que quieres poner en estatus ' + status + ' la solicitud y guardar la información de pago?').then(data=>{
      //     if(data){
      //       this.solicitud.statusSolicitud = status;
      //       this.subResourceService.create(this.solicitud, SolicitudVariable.INFORMACION_PAGO)
      //       .subscribe(data=>{
      //         status === 'Terminada' ? this.uploadDocumentos(this.solicitud.idSolicitud): null;
      //         swal('Éxito', data.message + ' ¡Recuerda cargar el documento de Formato Finiquito!', 'success');
      //         this.end();
      //       }, error=>{
      //         console.log(error);
      //       });
      //     }
      //   });
      // }else{
      //   swal('Información', 'Debe llenar todos los campos, solo las observaciones y el número de cheque no son obligatorios', 'info');
      // }

      if ( !this.arrayPdf[9].id ) {
        swal( '¡Atención!', 'Debes guardar el documento de FINIQUITO', 'warning' );
        return;
      }

      this.question('¿Estas seguro que quieres poner en estatus ' + status + ' la solicitud?. ¡NO SE PODRA SEGUIR ANALIZANDO!').then(data=>{
          if(data){
            this.solicitud.statusSolicitud = status;
            this.subResourceService.create(this.solicitud, SolicitudVariable.INFORMACION_PAGO)
            .subscribe(data=>{
              status === 'Terminada' ? this.uploadDocumentos(this.solicitud.idSolicitud): null;
              swal('Éxito', data.message, 'success');
              this.end();
            }, error=>{
              console.log(error);
            });
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
}
