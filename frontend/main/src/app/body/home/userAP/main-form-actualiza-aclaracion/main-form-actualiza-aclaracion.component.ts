import { Location } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AcaracionesAp } from 'src/app/core/interface/apUser/apUser';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { AclaracionVariable } from 'src/app/core/static/variables/url/URLImages';
import { RoutingUtilities } from 'src/app/core/Util/routing/routing-utilities';
import swal from 'sweetalert2';

@Component({
  // router ['Angular/form-edit-aclaraciones']
  selector: 'app-main-form-actualiza-aclaracion',
  templateUrl: './main-form-actualiza-aclaracion.component.html',
  styleUrls: ['./main-form-actualiza-aclaracion.component.css']
})
export class MainFormActualizaAclaracionComponent implements OnInit {

  today: Date = new Date();
  maxDate: Date = new Date();

  prueba = 'llegueLadoUno';

  aclaracion: AcaracionesAp = {
    // prueba aclaracion
    idEmpleadoAP:0,
    idAclaracion:'',
    documentoTipo:'',
    tipoAclaracion:'',
    nombre: '',
    rfc:'',
    dependencia: '',
    fechaRegistroPortal:'',
    telefono: '',
    email: '',
    comentarios:'',
    descripcionEmpleado : '',
    status: 0,
    fechaReal: '',
    documentoList: []
  }

  currentUser: any;

  idAclaracion = 0;
  funcioamiento = 0;
  opt = 0;

  isComercial = false;
  isSiniestros = false;
  isContabilidad = false;
  isExterno = false;

  observacionDoc = '';

  constructor( @Inject('ServiceResource') private subResourceService: SubResourceService<any>, private _activatedRoute: ActivatedRoute, private _location: Location) {
    this.idAclaracion = Number(RoutingUtilities.getParamsFromUrl(this._activatedRoute, 'solicitud'));
    this.opt = Number(RoutingUtilities.getParamsFromUrl(this._activatedRoute, 'opt'));
    this.funcioamiento = Number(RoutingUtilities.getParamsFromUrl(this._activatedRoute, 'tipo'));

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
      this.isContabilidad = true;
    }else
    if(userExterno !== null){
      this.currentUser = userExterno;
      this.isExterno = true;
    }

    this.getAclaracion();
  }


  getAclaracion(){
    this.subResourceService.read(AclaracionVariable.GET_ACLARACION_UNIQUE, {idAclaracion : this.idAclaracion, funcioamiento: this.funcioamiento})
      .subscribe(data=>{
        this.aclaracion = data;
      }, error=>{
        console.log(error);
        swal('Alerta', error, 'info');
      });
  }

  end(){
    this._location.back()
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

  actualizarAclaracion = ( numero: number ) =>{
    this.subResourceService.updateAny( '', AclaracionVariable.UPDATE_STATUS_ACLARACION, { idAclaracionStatus: this.aclaracion.idAclaracion, status : numero }).subscribe(data => {
      if (data) {
        swal('Éxito', data.message + ' del Folio ' + this.aclaracion.idAclaracion , 'success').then(()=>{
          this.end();
        });
      }
      return;
    }, error => {
      console.log({error});
      swal('Alerta', error, 'info');
    });
  }

  validarAclaracion = (numero : number) => {
    if (numero === 1) {
      this.actualizarAclaracion(numero);
    }else if( numero === 2 ){
      this.aclaracion.comentarios === "" ? "" : this.aclaracion.comentarios;
      if (this.aclaracion.fechaReal === null && this.aclaracion.telefono === null) {
        this.aclaracion.fechaReal = "";
        this.aclaracion.telefono = "";
      }
      this.subResourceService.update(this.aclaracion, "aclaraciones/updateAclaracionEmpleado")
      .subscribe(data=>{
        this.actualizarAclaracion(numero);
      }, error=>{
        console.log({error});
      });
    }else if( numero === 3 ){
      this.actualizarAclaracion(numero);
    }

  }
}
