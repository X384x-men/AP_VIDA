import { Location } from "@angular/common";
import { Component, Inject } from "@angular/core";
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import moment from "moment";
import { Observable } from "rxjs";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { EmpleadoVariable, ObtencionCatalogos } from "src/app/core/static/variables/url/URLImages";
import { now } from "src/app/core/Util/date";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";
import swal from "sweetalert2";

@Component({
  selector: "app-main-form-alta-analista",
  templateUrl: "./main-form-alta-analista.component.html",
  styleUrls: ["./main-form-alta-analista.component.css"],
})
export class MainFormAltaAnalistaComponent {
  user: UntypedFormGroup;
  welcome: boolean = false;
  loading : boolean = false;

  constructor(
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private adminUnitsService : AdminUnitsService,
    private location: Location
  ) {
    const { email, required } = Validators;
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
      fechaNacimiento: new UntypedFormControl("2024-01-10 00:00:00"),
      dependencia: new UntypedFormControl( ""),
      unidadAdministrativa: new UntypedFormControl( ""),
      fechaIngresoSeguro: new UntypedFormControl( ""),
      banco: new UntypedFormControl( ""),
      psw: new UntypedFormControl( "", required),
      confirmpsw: new UntypedFormControl( "", required),
      idUsuarioAcceso: new UntypedFormControl( 0),
      fechaCreacion: new UntypedFormControl( ""),
      estado: new UntypedFormControl( ""),
      tipoCuenta: new UntypedFormControl( ""),
      estatus: new UntypedFormControl( 1 ),
      fechaCambioEstatus: new UntypedFormControl( ""),
      tipoAnalista: new UntypedFormControl( "", required),
      idUnidad: new UntypedFormControl( ""),
      ididDependencia: new UntypedFormControl(""),
    });
  }

  isSamePassword(): boolean {
    const {psw, confirmpsw } = this.user.value;
    return psw !== confirmpsw && (psw !== '' && confirmpsw !== '')
  }

  guardar() {
    console.log(this.user);
    if(this.user.invalid){
      swal("Alerta", 'Debes completar toda la información solicitada', "info");
      return;
    }
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

  private createAnalista( value ): Observable<any> {
    return this.subResourceService.create(value, EmpleadoVariable.CREATE_ANALISTA_AP);
  }

  private getDependencia(){
    this.subResourceService.list(ObtencionCatalogos.GET_CATALOGO_DEPENDENCIAS,'' ,'').subscribe(data=>{
      this.user.value.ididDependencia =  data[0].idCatalogo;
      this.user.value.dependencia =  data[0].descripcionCatalogo;

      if (this.user.value.ididDependencia && this.user.value.idUnidad) {
        console.log('pase');
        this.createAnalista(this.user.value).subscribe(
          (result) => {
            this.loading = false;
            console.log({result});
            this.welcome = true;
          },
          (error) => {
            this.loading = false;
            console.log('ErrorCrearAnalista',{error});
            swal("Alerta", error, "info");
          }
        );
      }
    });
  }

  private getUnidades(){
    this.adminUnitsService.getAdminUnits().subscribe((data)=>{
      this.user.value.idUnidad =  data[0].idUnidadAdministrativa
      this.user.value.unidadAdministrativa = data[0].descripcion
    })
  }

  back (){
    this.location.back()
  }

}
