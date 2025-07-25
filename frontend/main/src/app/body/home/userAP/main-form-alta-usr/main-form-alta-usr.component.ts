import { Component, Inject } from "@angular/core";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import swal, { SweetAlertResult } from "sweetalert2";
import { URLUtilities } from "src/app/core/static/variables/url/URLUtilities";
import { Observable } from "rxjs";
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { now } from "src/app/core/Util/date";
import moment from "moment";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";
import { ObtencionCatalogos } from "src/app/core/static/variables/url/URLImages";

@Component({
  selector: "app-main-form-alta-usr",
  templateUrl: "./main-form-alta-usr.component.html",
  styleUrls: ["./main-form-alta-usr.component.css"],
})
export class MainFormAltaUsrComponent {
  user: UntypedFormGroup;
  loading : boolean = false;
  welcome: boolean = false;

  constructor(
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private adminUnitsService: AdminUnitsService
  ) {
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
      dependencia: new UntypedFormControl( "", required),
      unidadAdministrativa: new UntypedFormControl( "", required),
      fechaIngresoSeguro: new UntypedFormControl( ""),
      banco: new UntypedFormControl( ""),
      psw: new UntypedFormControl( "", required),
      confirmpsw: new UntypedFormControl( "", required),
      idUsuarioAcceso: new UntypedFormControl( 0),
      fechaCreacion: new UntypedFormControl( ""),
      estado: new UntypedFormControl( ""),
      tipoCuenta: new UntypedFormControl( ""),
      estatus: new UntypedFormControl( 1, required),
      fechaCambioEstatus: new UntypedFormControl( ""),
      tipoAnalista: new UntypedFormControl( 0, required),
      idUnidad: new UntypedFormControl( ""),
      ididDependencia: new UntypedFormControl(""),
    })
  }

  isSamePassword(): boolean {
    const {psw, confirmpsw } = this.user.value;
    return psw !== confirmpsw && (psw !== '' && confirmpsw !== '')
  }

  guardar() {
    if(this.user.invalid) return;
    const fechaN = moment(this.user.get('fechaNacimiento').value).format('YYYY-MM-DD HH:mm:ss');
    this.user.get('fechaNacimiento').setValue(fechaN);
    this.user.get('fechaCambioEstatus').setValue(now);
    this.getUnidades();
    this.loading = true;
    setTimeout(() => {
      this.getDependencia();
    }, 500);

  }

  get rfc(): string {
    return this.user.get('rfc').value;
  }

  private createUser( value ): Observable<any> {
    return this.subResourceService.create(value, URLUtilities.registerRequest())
  }

  private getDependencia(){
    let dependencia;
    this.subResourceService.list(ObtencionCatalogos.GET_CATALOGO_DEPENDENCIAS,'' ,'').subscribe(data=>{
      dependencia = data.filter( data => data['descripcionCatalogo'] === this.user.value.dependencia )
      this.user.value.ididDependencia =  dependencia[0].idCatalogo
      if (this.user.value.ididDependencia && this.user.value.idUnidad) {
        this.createUser(this.user.value).subscribe(
          (result) => {
            this.loading = false;
            this.welcome = true;
          },
          (error) => {
            console.log('ErrorCrearUsuario',{error});
            this.loading = false;
            swal("Alerta", error, "info");
          }
        );
      }
    });
  }

  private getUnidades(){
    let unidad;
    this.adminUnitsService.getAdminUnits().subscribe((data)=>{
        unidad = data.filter( data => data['descripcion'] === this.user.value.unidadAdministrativa )
        this.user.value.idUnidad =  unidad[0].idUnidadAdministrativa
    })
  }
}
