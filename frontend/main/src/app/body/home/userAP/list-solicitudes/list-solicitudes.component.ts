import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { GlobalVariable, SolicitudVariable } from 'src/app/core/static/variables/url/URLImages';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import swal from 'sweetalert2';

@Component({
  selector: 'app-list-solicitudes',
  templateUrl: './list-solicitudes.component.html',
  styleUrls: ['./list-solicitudes.component.css']
})
export class ListSolicitudesComponent implements OnInit {

  apvidaBackground  = GlobalVariable.BACKGROUND_IMG_APVIDA;

  userApp: any;

  list: number = 1;

  solicitudesAux = [];

  optionsTipoTramite: SelectMenu[];
  currentTipoTramite: SelectMenu;
  optionsEstatus: SelectMenu[];
  currentEstatus: SelectMenu;
  solicitudNgIf = ""
  solicitudes = [];
  tipoTramite = 'Todos';
  estatus = 'Todos';
  rfc = '';
  folio = '';

  constructor(private router: Router, @Inject('ServiceResource') private subResourceService: SubResourceService<any>) {

    this.solicitudes.forEach( data => {
      this.solicitudNgIf = data.statusSolicitud
    } )

  }

  ngOnInit() {
    let userExterno = JSON.parse(localStorage.getItem('currentUser'));
    let com = JSON.parse(localStorage.getItem('currentUserComercial'));
    let sin = JSON.parse(localStorage.getItem('currentUserSiniestros'));
    let cont = JSON.parse(localStorage.getItem('currentUserContabilidad'));
    if(com !== null){
      this.userApp = com;
    }else
    if(sin !== null){
      this.userApp = sin;
    }else
    if(cont !== null){
      this.userApp = cont;
    }else
    if(userExterno !== null){
      this.userApp = userExterno;
    }

    this.refreshList();
  }

  refreshList(){
    this.getSolicitudes();
    this.initOptionsTipoTramite();
    this.initOptionsEstatus();
  }

  addSolicitud(){
    this.router.navigate(['/angular/form-solicitudes'], {queryParams: {opt: 0}});
  }

  verDetalleSolicitud(item){
    this.router.navigate(['/angular/form-edit-solicitudes'], {queryParams: {solicitud: item.idSolicitud, opt: 0}});
  }

  cancelSolicitud(item){
    item.statusSolicitud = 'Cancelada';
    this.subResourceService.create(item, SolicitudVariable.UPDATE_STATUS_SOLICITUD)
    .subscribe(data=>{
      console.log(data);
      swal('Éxito', data.message, 'success')
    }, error=>{
      console.log(error);
    });
  }

  getSolicitudes(){
    this.subResourceService.list(SolicitudVariable.GET_SOLICITUDES_BY_EMPLEADO,'' , {rfc: this.userApp.username})
      .subscribe(data=>{
        this.solicitudes = data;
        this.solicitudesAux = Object.assign([],data);
        data.forEach( item => {
          this.solicitudNgIf = item.statusSolicitud;
          console.log(this.solicitudNgIf);
        } )
      }, error=>{
        console.log({error});
      });
  }

  back(){
    this.router.navigate(['/angular/dashboardAP'])
  }

  initOptionsTipoTramite(){
    let data = [
      {data: 'Todos', id: 0},
      {data: 'Retiro Total', id: 0},
      {data: 'Retiro Parcial', id: 0},
      {data: 'Retiro Complementario' ,id: 0},
      {data: 'Siniestro de Vida' ,id: 0}
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

  getTipoTramite(event){
    console.log(this.solicitudes);
    this.tipoTramite = event;
    this.filterRFC();
  }
  getEstatus(event){
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

}
