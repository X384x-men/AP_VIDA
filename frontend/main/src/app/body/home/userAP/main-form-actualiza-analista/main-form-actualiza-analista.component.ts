import { Component, Inject, OnInit } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserAp } from 'src/app/core/interface/apUser/apUser';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { RoutingUtilities } from 'src/app/core/Util/routing/routing-utilities';
import { AdminUnitsService } from 'src/app/shared/services/admin-units.service';
import { ModalService } from 'src/app/shared/services/modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-main-form-actualiza-analista',
  templateUrl: './main-form-actualiza-analista.component.html',
  styleUrls: ['./main-form-actualiza-analista.component.css']
})
export class MainFormActualizaAnalistaComponent implements OnInit {

  user: UntypedFormGroup;
  nameUsr= '';
  welcome: boolean = false;
  opt = 0;
  idEmpleado = ""
  fechaNacimiento = ""

  constructor(
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private router: Router,
    private modal: ModalService,
    private _activatedRoute: ActivatedRoute
  ) {
    this.opt = Number(RoutingUtilities.getParamsFromUrl(this._activatedRoute, 'opt'));
    const {required, email} = Validators;
    this.user = new UntypedFormGroup({
      nombre: new UntypedFormControl("", required),
      apellidoPaterno: new UntypedFormControl( "", required),
      apellidoMaterno: new UntypedFormControl( "", required),
      calle: new UntypedFormControl( ""),
      colonia: new UntypedFormControl( ""),
      noInt: new UntypedFormControl( 0),
      noExt: new UntypedFormControl( 0),
      cp: new UntypedFormControl( ""),
      rfc: new UntypedFormControl( "", required),
      curp: new UntypedFormControl( ""),
      sexo: new UntypedFormControl( "", required),
      telCasa: new UntypedFormControl( 0),
      telMovil: new UntypedFormControl( 0),
      mail: new UntypedFormControl( "", [required, email]),
      noEmpleado: new UntypedFormControl( "", required),
      cuenta: new UntypedFormControl( ""),
      fechaNacimiento: new UntypedFormControl( "", required),
      dependencia: new UntypedFormControl( null ),
      unidadAdministrativa: new UntypedFormControl( null ),
      fechaIngresoSeguro: new UntypedFormControl( ""),
      banco: new UntypedFormControl( ""),
      psw: new UntypedFormControl( "", required),
      confirmpsw: new UntypedFormControl( "", required),
      pswValidate: new UntypedFormControl( "", required),
      idUsuarioAcceso: new UntypedFormControl( 0),
      fechaCreacion: new UntypedFormControl( ""),
      estado: new UntypedFormControl( ""),
      tipoCuenta: new UntypedFormControl( ""),
      estatus: new UntypedFormControl( 1 ),
      fechaCambioEstatus: new UntypedFormControl( ""),
      tipoAnalista: new UntypedFormControl( 0 ),
      idUnidad: new UntypedFormControl( null ),
      ididDependencia: new UntypedFormControl(null ),
      unidadCatalogo: new UntypedFormControl( null ),
      dependenciaCatalogo: new UntypedFormControl( null ),
      idEmpleado : new UntypedFormControl( null ),
    })
  }

  isSamePassword(): boolean {
    const {psw, confirmpsw } = this.user.value;
    return psw !== confirmpsw && (psw !== '' && confirmpsw !== '')
  }

  guardar() {
    if(this.user.invalid){
      swal('Información', 'Datos incorrectos', 'info');
       return;
    }
    if ( !this.user.value.dependencia && !this.user.value.dependenciav && !this.user.value.dependencia && !this.user.value.dependencia && !this.user.value.unidadCatalogo && !this.user.value.dependenciaCatalogo) {
      this.user.value.dependencia = "GOBIERNO DEL ESTADO DE MEXICO, SECTOR CENTRALs";
      this.user.value.unidadAdministrativa = "GOBIERNO DEL ESTADO DE MEXICO, SECTOR CENTRAL"
      this.user.value.idUnidad = 1;
      this.user.value.ididDependencia = 1;
      this.user.value.dependenciaCatalogo = "GOBIERNO DEL ESTADO DE MEXICO, SECTOR CENTRALs",
      this.user.value.unidadCatalogo = "GOBIERNO DEL ESTADO DE MEXICO, SECTOR CENTRAL"
    }
    this.user.value.idEmpleado = this.idEmpleado;
    this.user.value.fechaNacimiento = this.fechaNacimiento;
    this.modal.confirm('¿Está seguro de actualizar sus datos?').then((result) => {
        this.subResourceService.update( this.user.value,  "usuario-acceso/updateEmpleadoAP")
          .subscribe(data=>{
            swal('Exitoso', data.message, 'success').then(() => {
              //this.subResourceService.getInfoUser();
              this.end()
            });
          }, error=>{
            console.log({error});
            swal('Alerta', error, 'info');
          });
      // }else{
      //   console.log('llegue');
      // }
    });
  }

  end(){
    swal( 'Actualización correcta', '', 'success' )
    this.opt == 0 ? this.router.navigate(['/angular/dashboard-admin']) : this.router.navigate(['/angular/dashboard-analista']);
  }

    ngOnInit() {
      if( JSON.parse(localStorage.getItem('currentUser'))){
        let user = JSON.parse(localStorage.getItem('currentUser'));
        this.nameUsr = user.username;
      }
      this.getEmpleado();
    }

  getEmpleado(){
    this.subResourceService.read("usuario-acceso/getEmpleadoAP", {usr: this.nameUsr}).subscribe( data => {
      // this.user = data;
      // this.user.value.psw = '';
      // this.user.value.confirmpsw = '';
      // this.user.value.pswValidate = '';
      this.idEmpleado = data.idEmpleado;
      this.fechaNacimiento = data.fechaNacimiento;
    }, error=>{
      swal('Alerta', error, 'info');
    });
  }

}
