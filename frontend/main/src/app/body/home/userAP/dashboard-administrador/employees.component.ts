import { LiveAnnouncer } from "@angular/cdk/a11y";
import { CommonModule } from "@angular/common";
import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatNativeDateModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatPaginator, MatPaginatorModule } from "@angular/material/paginator";
import { MatSelectModule } from "@angular/material/select";
import { MatSort, MatSortModule } from "@angular/material/sort";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import moment from "moment";
import { UserAp } from "src/app/core/interface/apUser/apUser";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { EmpleadoVariable, UsuarioAcceso } from "src/app/core/static/variables/url/URLImages";
import { downloadCSV } from "src/app/core/Util/download-file";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";
import { DependenciesService } from "src/app/shared/services/dependencies.service";
import { ModalService } from "src/app/shared/services/modal.service";
import { UploadEmployeesButton } from "../upload-employees/upload-employes-button.component";
import { FormsModule } from "@angular/forms";
import { InactiveUsersButtonComponent } from "src/app/shared/components/inactive-users-button/inactive-users-button.component";
import { tableHeaders } from "src/app/shared/constants/employees-table-headers";
import { MatMenuModule } from '@angular/material/menu';
import { Router } from "@angular/router";
import { AuthenticationService } from "src/app/core/services/authentication-service/authentication.service";
import { ExcelService } from "src/app/core/services/excel-service/excel-service.service";
import swal from "sweetalert2";


@Component({
  standalone: true,
  imports: [
    MatMenuModule,
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    UploadEmployeesButton,
    FormsModule,
    UploadEmployeesButton,
    InactiveUsersButtonComponent
  ],
  templateUrl: "./employees.component.html",
  styleUrls: ['./dashboard-administrador.component.css']
})
export class EmployeesComponent implements OnInit, AfterViewInit {
  tableHeaders = tableHeaders;
  user: {
    rfc: string;
    nombre: string;
    dependencia: string;
    unidadAdministrativa: string;
  };
  userApp: any;
  selectedUnitAdmin = '';
  selectedDependency='';
  unidadesAdministrativas=[];
  dependencias=[];
  empleados: [];
  dependenciesOptions: any;
  adminUnitsOptions: any[];
  public dataSource = new MatTableDataSource<UserAp>();
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;


  constructor(
    private router: Router,
    private subResourceService: SubResourceService<any>,
    public dependencies_: DependenciesService,
    public adminUnits_: AdminUnitsService,
    private announcer: LiveAnnouncer,
    private modal: ModalService,
    private dependenciesService: DependenciesService,
    private adminUnitsService: AdminUnitsService,
    private authencationService: AuthenticationService,
    private excelService: ExcelService
    ) {
    this.user = {
      rfc: '',
      nombre: '',
      unidadAdministrativa: '',
      dependencia: ''
    };
  }

  ngOnInit() {
    this.authencationService.validacionAdmin();
    this.refreshList();
  }


  ngAfterViewInit():void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }


  refreshList(){
    this.getAdminUnitsInfo();
    this.getDependenciesInformation();
    this.filter();
  }

  goToDashboard(rfc) {
    let user = {
      authorities: [{ authority: "ROLE_USRAP" }],
      username: rfc,
    };

    localStorage.setItem("currentUser", JSON.stringify(user));
    this.router.navigate(["/angular/dashboardAP"]);
  }

  goToReporteMovimientos(rfc:any ) {
    let user = {
      authorities: [{ authority: "ROLE_USRAP" }],
      username: rfc,
    };

    localStorage.setItem("currentUser", JSON.stringify(user));
    this.router.navigate(["/angular/reporte"]);
  }

  goToUpdate(rfc : any ) {
    let user = {
      authorities: [{ authority: "ROLE_USRAP" }],
      username: rfc,
    };

    localStorage.setItem("currentUser", JSON.stringify(user));
    this.router.navigate(["/angular/update"]);
  }

  setCurrentUser(rfc) {
    let user = {
      authorities: [{ authority: "ROLE_USRAP" }],
      username: rfc,
    };
  }

  onSelectedDependencies(value:string): void {
    this.selectedDependency = value;
  }

  onSelectedUnitAdmin(value:string): void {
    this.selectedUnitAdmin = value;
  }


  filter() {
    this.empleados = [];
    this.dataSource.data = [];
    const batchSize = 100;
    this.user.dependencia= this.selectedDependency;
    this.user.unidadAdministrativa=this.selectedUnitAdmin;
    this.subResourceService
      .read(UsuarioAcceso.GET_LIST_EMPLEADOS_SEARCH, {
        rfc: this.user.rfc || null,
        nombre: this.user.nombre || null,
        dependencia: this.user.dependencia || null,
        unidadAdmin: this.user.unidadAdministrativa || null,
      }
    ).subscribe( (data) => {
      this.empleados = data.filter( (user : any ) => user.tipoAnalista == 0);
      let currentIndex = 0;
      const addEmployees = setInterval(() => {
        const batch = this.empleados.slice(currentIndex, currentIndex + batchSize);
        this.dataSource.data.push(...batch);
        currentIndex += batchSize;
        this.dataSource._updateChangeSubscription();
        if (currentIndex >= this.empleados.length) {
          clearInterval(addEmployees)
          this.sort.sortChange.subscribe(event => {
            if (event.direction) {
              this.announcer.announce(`Sorted ${event.direction}ending`);
            } else {

              this.announcer.announce('Sorting cleared');
            }
          })
        }
      }, 500);

    });
  }

  descargaReporte() {
    let arrayReporte = this.empleados.map((item: any) => {
      return {
        Nombre: item.nombre + " " + item.apellidoPaterno + " " + item.apellidoMaterno,
        RFC: item.rfc,
        Email: item.mail,
        Status: item.estatus === 1 ? "Activo" : "Inactivo",
        NumeroDeEmpleado: item.noEmpleado,
        Dependencia: item.dependenciaCatalogo,
        UnidadAdministrativa: item.unidadCatalogo,
        FechaDeNacimiento: item.fechaNacimiento ? moment(item.fechaNacimiento).format('DD/MM/YYYY') : '',
        Sexo: item.sexo !== "1" ? item.sexo : item.sexo !== "0" ? item.sexo : item.sexo === "1" ? "" : item.sexo === "0" ? "" : "",
        FechaDeAlta: item.fechaCreacion ? moment(item.fechaCreacion).format('DD/MM/YYYY') : '',
      };
    });
    this.excelService.exportAsExcelFile(arrayReporte, "Reporte_Empleados_" + moment().format("YYYY-MM-DD"));
    swal('Éxito', 'Se generó correctamente la descarga de empleados', 'success');
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

  getAdminUnitsInfo(){
    this.adminUnitsService.getAdminUnits()
      .subscribe((data)=>{
        this.unidadesAdministrativas = data;
      })
  }

  getDependenciesInformation(){
    this.dependenciesService.getDependencies()
    .subscribe(data=>{;
      this.dependencias = data;
    }, error=>{
      console.log(error);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


}
