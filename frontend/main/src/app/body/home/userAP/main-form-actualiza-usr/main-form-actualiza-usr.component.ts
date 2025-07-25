import { Component, OnInit, Inject } from "@angular/core";
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import moment, { now } from "moment";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { ObtencionCatalogos } from "src/app/core/static/variables/url/URLImages";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";
import swal from "sweetalert2";
import { Location } from '@angular/common';

@Component({
  selector: "app-main-form-actualiza-usr",
  templateUrl: "./main-form-actualiza-usr.component.html",
  styleUrls: ["./main-form-actualiza-usr.component.css"],
})
export class MainFormActualizaUsrComponent implements OnInit {
  user: UntypedFormGroup;
  nameUsr = "";
  dependenci="";
  rfc : any;

  constructor(
    private router: Router,
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private adminUnitsService: AdminUnitsService,
    private _location : Location
  ) {

    const { email, required } = Validators;
    this.user = new UntypedFormGroup({
      nombre: new UntypedFormControl("", required),
      apellidoPaterno: new UntypedFormControl("", required),
      apellidoMaterno: new UntypedFormControl("", required),
      calle: new UntypedFormControl(""),
      colonia: new UntypedFormControl(""),
      noInt: new UntypedFormControl(0),
      noExt: new UntypedFormControl(0),
      cp: new UntypedFormControl(""),
      rfc: new UntypedFormControl({value: "", disabled: true}, required),
      curp: new UntypedFormControl(""),
      sexo: new UntypedFormControl("", required),
      telCasa: new UntypedFormControl(0),
      telMovil: new UntypedFormControl(0),
      mail: new UntypedFormControl("", [required, email]),
      noEmpleado: new UntypedFormControl("", required),
      cuenta: new UntypedFormControl(""),
      fechaNacimiento: new UntypedFormControl("", required),
      dependencia: new UntypedFormControl("", required),
      unidadAdministrativa: new UntypedFormControl("", required),
      fechaIngresoSeguro: new UntypedFormControl(""),
      banco: new UntypedFormControl(""),
      psw: new UntypedFormControl("", required),
      confirmpsw: new UntypedFormControl("", required),
      pswValidate: new UntypedFormControl(''),
      idUsuarioAcceso: new UntypedFormControl(0),
      idEmpleado: new UntypedFormControl(0),
      fechaCreacion: new UntypedFormControl(""),
      estado: new UntypedFormControl(""),
      tipoCuenta: new UntypedFormControl(""),
      estatus: new UntypedFormControl(1, required),
      fechaCambioEstatus: new UntypedFormControl(""),
      tipoAnalista: new UntypedFormControl(0, required),
      idUnidad: new UntypedFormControl(1),
      ididDependencia: new UntypedFormControl(1)
    });
    setTimeout(() => {
      this.rfc = this.user.controls.rfc.value
    }, 5000);

  }

  ngOnInit() {
    if (JSON.parse(localStorage.getItem("currentUser"))) {
      let user = JSON.parse(localStorage.getItem("currentUser"));
      this.nameUsr = user.username;
    }
    this.getEmpleado();
  }

  getEmpleado() {
    this.subResourceService
      .read("usuario-acceso/getEmpleadoAP", { usr: this.nameUsr })
      .subscribe(
        (data) => {
          data.confirmpsw = '';
          data.psw = '';
          data.fechaNacimiento = new Date(data.fechaNacimiento)
          this.user.setValue(data);
        },
        (error) => {
          swal("Alerta", error, "info");
        }
      );
  }

  guardar() {
    if (this.user.status === "VALID" && this.user.value.psw === this.user.value.confirmpsw) {
      swal({
        title: "Atención",
        text: "¿Esta seguro de actualizar sus datos?",
        type: "question",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        cancelButtonText: "Cancelar",
        confirmButtonText: "Aceptar",
      }).then((result) => {
        if (!result.value) return;
        this.user.get('fechaCambioEstatus').setValue(now);
        const fechaN = moment(this.user.get('fechaNacimiento').value).format('YYYY-MM-DD HH:mm:ss');
        this.user.get('fechaNacimiento').setValue(fechaN);
        this.getDependencia();
        this.getUnidades();
      });
    }else{
      swal('Error', 'Debes completar correctamente la información para guardar', 'error');
      return;
    }

  }



  private getDependencia(){
    let dependencia;
    this.subResourceService.list(ObtencionCatalogos.GET_CATALOGO_DEPENDENCIAS,'' ,'').subscribe(data=>{
      dependencia = data.filter( data => data['descripcionCatalogo'] === this.user.value.dependencia )
      this.user.value.ididDependencia =  dependencia[0].idCatalogo
    });
  }

  private getUnidades(){
    let unidad;
    this.user.value.rfc = this.rfc
    this.adminUnitsService.getAdminUnits().subscribe((data)=>{
        unidad = data.filter( data => data['descripcion'] === this.user.value.unidadAdministrativa )
        this.user.value.idUnidad =  unidad[0].idUnidadAdministrativa
        if (this.user.value.ididDependencia && this.user.value.idUnidad) {
            this.subResourceService.update(this.user.value, "usuario-acceso/updateEmpleadoAP").subscribe(
            (data) => {
              swal("Exitoso", data.message, "success").then(() => {
                this.subResourceService.getInfoUser();
                this.end();
              });
            },
            (error) => {
              console.log(error);
              swal("Alerta", error, "info");
            }
          );
        }

    })
  }

  end() {
    this._location.back()
  }
}
