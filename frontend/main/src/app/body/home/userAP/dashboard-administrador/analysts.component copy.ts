import { LiveAnnouncer } from "@angular/cdk/a11y";
import { CommonModule } from "@angular/common";
import { Component, OnInit, ViewChild } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatNativeDateModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSelectModule } from "@angular/material/select";
import { MatSort, MatSortModule, Sort } from "@angular/material/sort";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import moment from "moment";
import { UserAp } from "src/app/core/interface/apUser/apUser";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { UsuarioAcceso } from "src/app/core/static/variables/url/URLImages";
import { downloadCSV } from "src/app/core/Util/download-file";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";
import { DependenciesService } from "src/app/shared/services/dependencies.service";
import { UploadEmployeesButton } from "../upload-employees/upload-employes-button.component";
import { FormsModule } from "@angular/forms";
import { InactiveUsersButtonComponent } from "src/app/shared/components/inactive-users-button/inactive-users-button.component";
import { MatMenuModule } from '@angular/material/menu';
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
    InactiveUsersButtonComponent
  ],
  templateUrl: "./employees.component.html",
  styleUrls: ['./dashboard-administrador.component.css']
})
export class AnalystsComponent implements OnInit {
  user: {
    rfc: string;
    nombre: string;
    dependencia: string;
    unidadAdministrativa: string;
  };
  selectedUnitAdmin = '';
  selectedDependency='';
  empleados: [];
  unidadesAdministrativas=[];
  dependencias=[];
  dependenciesOptions: any;
  adminUnitsOptions: any[];
  public dataSource = new MatTableDataSource<UserAp>();
  @ViewChild(MatSort) sort: MatSort;
  tableHeaders = [
    'Nombre',
    'RFC',
    'E-mail',
    'N° Empleado',
    'Dependencia',
    'Unidad Administrativa',
    'Fecha de Nacimiento',
    'Sexo',
    'Fecha de Alta',
  ];

  constructor(
    private subResourceService: SubResourceService<any>,
    public dependencies_: DependenciesService,
    public adminUnits_: AdminUnitsService,
    private announcer: LiveAnnouncer,
    private dependenciesService: DependenciesService,
    private adminUnitsService: AdminUnitsService
  ) {
    this.user = {
      rfc: '',
      nombre: '',
      unidadAdministrativa: '',
      dependencia: ''
    }
  }

  ngOnInit() {
  /*  this.dependencies_.dependencies.toPromise().then( result => {
      this.dependenciesOptions = result;
    });
    this.adminUnits_.adminUnits.toPromise().then( result => {
      this.adminUnitsOptions = result;
    });*/
    this.dataSource.sort = this.sort;
    this.refreshList();
  }
  refreshList(){
    this.getAdminUnitsInfo();
    this.getDependenciesInformation();
  }

  onSelectedDependencies(value:string): void {
    this.selectedDependency = value;
  }

  onSelectedUnitAdmin(value:string): void {
    this.selectedUnitAdmin = value;
  }

  filter() {
    this.empleados = [];
    this.subResourceService
      .read(UsuarioAcceso.GET_LIST_EMPLEADOS_SEARCH, {
        rfc: this.user.rfc || null,
        nombre: this.user.nombre || null,
        dependencia: this.user.dependencia || null,
        unidadAdmin: this.user.unidadAdministrativa || null,
      }
    ).subscribe( (data) => {
      this.empleados = data.filter( (user : any) => user.tipoAnalista > 0);
    });
  }

  sortData(sortState: Sort): void {
    }

  descargaReporte() {
    let arrayReporte = this.empleados.map((item: any, index) => {
      return {
        Nombre: item.nombre + " " + item.apellidoPaterno + " " + item.apellidoMaterno,
        RFC: item.rfc,
        Email: item.mail,
        NumeroDeEmpleado: item.noEmpleado,
        Dependencia: item.dependencia,
        UnidadAdministrativa: item.unidadAdministrativa,
        FechaDeNacimiento: item.fechaNacimiento,
        Sexo: item.sexo,
        FechaDeAlta: item.fechaCreacion,
      };
    });
    downloadCSV("Reporte_Empleados_" + moment().format("YYYY-MM-DD"), arrayReporte);
  }
  getAdminUnitsInfo(){
    this.adminUnitsService.getAdminUnits()
      .subscribe((data)=>{
        console.log(data);
        this.unidadesAdministrativas = data;
      })
  }



  getDependenciesInformation(){
    this.dependenciesService.getDependencies()
    .subscribe(data=>{
      this.dependencias = data;
    }, error=>{
      console.log(error);
    });
  }

  // Agg 7-7-2023

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.announcer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.announcer.announce('Sorting cleared');
    }
  }

}
