import { Location } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import moment from 'moment';
import { solicitudAP } from 'src/app/core/interface/apUser/apUser';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { SolicitudVariable } from 'src/app/core/static/variables/url/URLImages';
import { RoutingUtilities } from 'src/app/core/Util/routing/routing-utilities';
import swal from 'sweetalert2';

@Component({
  // Router ['Angular/Form-solicitudes']
  selector: 'app-main-form-alta-solicitud',
  templateUrl: './main-form-alta-solicitud.component.html',
  styleUrls: ['./main-form-alta-solicitud.component.css']
})
export class MainFormAltaSolicitudComponent implements OnInit {

  prueba = 'Pruebaasss'

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
    statusSolicitud: 'Nueva',
    faltanteAPagar: '',
    valorQuincValidar: '',
    quincAgoFeb: '',
    montoCalculado: '',
    analistaComercialValida: '',
    pagoAnterior: '',
    fechaPago: '',
    sueldo: '',
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

  currentUser: any;

  opt = 0;

  isContable = false;
  isSiniestros = false;
  isComercial = false;
  isExterno = false;

  constructor(private router: Router, @Inject('ServiceResource') private subResourceService: SubResourceService<any>, private _activatedRoute: ActivatedRoute, private _location: Location ) {
    this.opt = Number(RoutingUtilities.getParamsFromUrl(this._activatedRoute, 'opt'));
   }

  ngOnInit() {
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
      this.isContable = true;
    }else
    if(userExterno !== null){
      this.currentUser = userExterno;
      this.isExterno = true;
      this.getEmpleado();
    }

  }

  getEmpleado(){
    this.subResourceService.read("usuario-acceso/getEmpleadoAP", {usr: this.currentUser.username})
    .subscribe(data=>{
      console.log(data);
      this.buildDataDefault(data);
    }, error=>{
      swal('Alerta', error, 'info');
    });
  }

  buildDataDefault(data){
    let solic = {
      idSolicitud: 0,
      tipoTramite: '',
      rfcAsegurado: data.rfc,
      rfcConfirmar: '',
      nombre: data.nombre,
      apellidoPaterno: data.apellidoPaterno,
      apellidoMaterno: data.apellidoMaterno,
      dependencia: data.dependencia,
      telefono: '',
      email: data.mail,
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
      tipoPago:'',
      isValid: false,
      messageError: '',
      rfcGEM: '',
      rfcEmpleadoGeneraOrden: '',
      sexo: '',
      fechaNac: '',
      fechaOrdenPago: '',
      idEmpleadoGeneraOrden: 0,
      nombreEmpleadoGeneraOrden: '',
      faltanteAPagar: '',
      valorQuincValidar: '',
      quincAgoFeb: '',
      montoCalculado: '',
      analistaComercialValida: '',
      pagoAnterior: '',
      sueldo: '',
      fechaPago: ''
    }
    this.solicitud = Object.assign({}, solic);
  }

  uploadDocumentos(idSolicitud){
    this.arrayFiles.forEach((item, index) => {
      if(item.file !== null){
        console.log(item);
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
        .subscribe((response : any) => {});
      }
    });

  }


  end(){
    this._location.back();
  }

}
