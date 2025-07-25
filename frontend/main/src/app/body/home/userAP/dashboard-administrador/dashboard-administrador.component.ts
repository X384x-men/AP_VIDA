import { Component, OnInit, Inject } from "@angular/core";
import {
  GlobalVariable,
  EmpleadoVariable,
  SolicitudVariable
} from "src/app/core/static/variables/url/URLImages";
import { Router, RouterModule } from "@angular/router";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import moment from "moment";
import { DependenciesService } from "src/app/shared/services/dependencies.service";
import { downloadCSV } from "src/app/core/Util/download-file";
import { ModalService } from "src/app/shared/services/modal.service";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatDividerModule } from "@angular/material/divider";

@Component({
  standalone: true,
  imports: [
    MatButtonModule,
    MatCardModule,
    RouterModule,
    MatDividerModule
  ],
  templateUrl: "./dashboard-administrador.component.html",
  styleUrls: ["./dashboard-administrador.component.css"],
})
export class DashboardAdministradorComponent implements OnInit {
  apvidaBackground = GlobalVariable.BACKGROUND_IMG_APVIDA;

  userApp: any;
  isAnalista: boolean = false;

  resumenBatch = {
    nombreArchivo: "",
    totalRegistros: "",
    registrosRechazados: "",
    regristrosValidos: "",
    fechaCarga: "",
    tipo: "",
    id: 0,
    batchInfo: "",
    processStatus: "",
    mensaje: "",
  };

  showDependencies: boolean;
  solicitudes : any;

  constructor(
    private router: Router,
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private dependencies_: DependenciesService,
    private modal: ModalService
  ) {
    this.showDependencies = false;
  }

  ngOnInit() {
    this.userApp = JSON.parse(localStorage.getItem("currentUserAdmin"));
  }

  downloadSaldo(rfc: string) {
    window.open('http://apvida.mx/gem/movimientos.php?RFC=' + rfc.toUpperCase(), '_blank');
  }

  changeEstatus(item, estatus) {
    item.fechaCambioEstatus = moment().format("YYYY-MM-DD HH:mm:ss");
    item.estatus = estatus;
    this.subResourceService
      .update(item, EmpleadoVariable.UPDATE_ESTATUS)
      .subscribe((data) => {
        this.modal.success("Éxito", data.message);
      });
  }

  altaAnalista() {
    this.router.navigate(["/angular/register-analista"]);
  }

  editAnalista(rfc) {
    console.log(rfc);
    let user = {
      authorities: [{ authority: "ROLE_USRAP" }],
      username: rfc,
    };

    localStorage.setItem("currentUser", JSON.stringify(user));
    this.router.navigate(["/angular/update-analista"], {
      queryParams: { opt: 0 },
    });
  }

  // probando lista de solicitudes
  getSolicitudes(){
    this.subResourceService.list(SolicitudVariable.GET_SOLIITUDES_ANALISTAS,'' ,{nombre: '', RFC: '', tramite: '', status: ''})
      .subscribe(data=>{
        console.log(data);
        data.forEach(item => {
          item.isLayout = false;
          item.isReporteContable = false;
        });
      this.solicitudes = data;
      //  this.solicitudesAux = Object.assign([],data);
      }, error=>{
        console.log(error);
      });
  }
}
