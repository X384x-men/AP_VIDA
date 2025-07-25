import { AfterViewInit, Component, Inject, OnInit, ViewChild } from "@angular/core";
import swal from "sweetalert2";
import { LiveAnnouncer } from "@angular/cdk/a11y";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { MatSort } from "@angular/material/sort";
import { ActivatedRoute, Router } from "@angular/router";
import moment from "moment";
import { UserAp } from "src/app/core/interface/apUser/apUser";
import { SelectMenu } from "src/app/core/interface/menu/select-menu";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { EmpleadoVariable, GlobalVariable, UsuarioAcceso} from "src/app/core/static/variables/url/URLImages";
import { downloadCSV } from "src/app/core/Util/download-file";
import { Smartwfm } from "src/app/core/Util/smartwfm/smartwfm";
import { DependenciesService } from "src/app/shared/services/dependencies.service";

@Component({
  selector: "app-main-dashboard-analistas",
  templateUrl: "./main-dashboard-analistas.component.html",
  styleUrls: ["./main-dashboard-analistas.component.css"],
})

export class MainDashboardAnalistasComponent implements OnInit, AfterViewInit {
  apvidaBackground = GlobalVariable.BACKGROUND_IMG_APVIDA;
  dataSource = new MatTableDataSource<UserAp>();
  userApp: any;
  dependencias=[];
  selectedDependency='';
  classRutaBtn : any;
  loading = true;

  user: UserAp = {
    nombre: "",
    apellidoPaterno: "",
    apellidoMaterno: "",
    calle: "",
    colonia: "",
    noInt: 0,
    noExt: 0,
    cp: "",
    rfc: "",
    curp: "",
    sexo: "",
    telCasa: 0,
    telMovil: 0,
    mail: "",
    mail2: "",
    noEmpleado: "",
    cuenta: "",
    fechaNacimiento: "",
    dependencia: "",
    // unidadAdministrativa: "",
    fechaIngresoSeguro: "",
    banco: "",
    psw: "",
    confirmpsw: "",
    isValid: false,
    messageError: "",
    idUsuarioAcceso: 0,
    fechaCreacion: "",
    estado: "",
    tipoCuenta: "",
  };

  optionsDep: SelectMenu[];
  currentDep: SelectMenu;

  optionsUnid: SelectMenu[];
  currentUnid: SelectMenu;
  selectedUnitAdmin = '';
  empleados = [];

  csvOptions = {
    fieldSeparator: ",",
    quoteStrings: '"',
    decimalseparator: ".",
    showLabels: true,
    showTitle: false,
    useBom: true,
    noDownload: false,
    headers: [
      "Nombre",
      "RFC",
      "E-mail",
      "Numero de Empleado",
      "Dependencia",
      "Unidad Administrativa",
      "Fecha de Nacimiento",
      "Sexo",
      "Fecha de Alta",
    ],
  };

  list: number = 1;

  isAnalista: boolean = false;

  currentUser: any;

  isComercial = false;
  isSiniestros = false;
  isContabilidad = false;
  isExterno = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private router: Router,
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private dependencies_: DependenciesService,
    private announcer: LiveAnnouncer,
    private _activateddRoute : ActivatedRoute
  ) {
    this.classRutaBtn = this._activateddRoute.snapshot.url[0].path ;
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnInit() {
    this.userApp = JSON.parse(localStorage.getItem("currentUserAdmin"));

    this.initOptionDependencias();

    let userExterno = JSON.parse(localStorage.getItem("currentUser"));
    let com = JSON.parse(localStorage.getItem("currentUserComercial"));
    let sin = JSON.parse(localStorage.getItem("currentUserSiniestros"));
    let cont = JSON.parse(localStorage.getItem("currentUserContabilidad"));
    if (com !== null) {
      this.currentUser = com;
      this.isComercial = true;
    } else if (sin !== null) {
      this.currentUser = sin;
      this.isSiniestros = true;
    } else if (cont !== null) {
      this.currentUser = cont;
      this.isContabilidad = true;
    } else if (userExterno !== null) {
      this.currentUser = userExterno;
      this.isExterno = true;
    }
  }


  getDependenciesInformation(){
    this.dependencies_.getDependencies()
    .subscribe(data=>{;
      this.dependencias = data;
    }, error=>{
      console.log({error});
    });
  }

  onSelectedDependencies(value:string): void {
    this.selectedDependency = value;
  }

  onSelectedUnitAdmin(value:string): void {
    this.selectedUnitAdmin = value;
  }

  initOptionDependencias() {
    this.dependencies_.getDependencies().subscribe((data) => {
      this.dependencias = data;
      this.optionsDep = Smartwfm.createSelectOptions(data, "data");
      if (this.user.dependencia != "") {
        this.setOptionDependencia();
      }
    });
  }

  setOptionDependencia() {
    this.optionsDep.forEach((item) => {
      if (item.extras.data == this.user.dependencia) {
        this.currentDep = item;
      }
    });
  }

  setOptionUnidad() {
    this.optionsUnid.forEach((item) => {
      if (item.extras.data == this.user.unidadAdministrativa) {
        this.currentUnid = item;
      }
    });
  }

  getDep(event:string) {
    this.user.dependencia = event;
  }

  filter() {
    this.empleados = [];
    this.dataSource.data = [];
    const batchSize = 100;
    this.user.unidadAdministrativa = this.selectedUnitAdmin;
    this.subResourceService
      .read(UsuarioAcceso.GET_LIST_EMPLEADOS_SEARCH, {
        rfc: this.user.rfc == '' ? null : this.user.rfc ,
        nombre: this.user.nombre == '' ? null : this.user.nombre ,
        dependencia: this.user.dependencia == '' ? null : this.user.dependencia ,
        unidadAdmin: this.user.unidadAdministrativa == '' ? null : this.user.unidadAdministrativa
      }
    ).subscribe( (data) => {
      this.empleados = this.filterTipoAnalista(data);
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
   },(error) => {
      console.log({error});
       swal("Alerta", error, "info");
    });
  }


  filterTipoAnalista(data) {
    let arrayAux = [];
    if (data.length === 1 && data[0].tipoAnalista === 2 || data.length === 1 &&  data[0].tipoAnalista === 1 || data.length === 1 &&  data[0].tipoAnalista === 3 || data[0].rfc === "XAXX010101000") {
      swal("Alerta", 'No existen resultados para la búsqueda intente de nuevo modificando los filtros', "info");
      return arrayAux;
    }
    if (this.list == 1) {
      this.isAnalista = false;
      data.forEach((item) => {
        if (item.tipoAnalista == 0) {
          arrayAux.push(item);
        }
      });
    }
    return arrayAux;
  }

  // Busca RFC & editarAnalista en una vista completa
  goToDashboard(rfc? : string) {
    let user : object;
    let ruta : string;

    if( rfc !== undefined ){
      user = {
        authorities: [{ authority: "ROLE_USRAP" }],
        username: rfc,
      };
      ruta = "/angular/dashboardAP";
    }else{
      user = {
        authorities: [{ authority: "ROLE_USRAP" }],
        username: this.currentUser.username,
      };

      ruta = "/angular/update-analista";
    }

    localStorage.setItem("currentUser", JSON.stringify(user));
    this.router.navigate([ruta]);
  }

  findRFC() {
    this.router.navigate(["/angular/reporte-admin"]);
  }

  descargaReporte() {
    let arrayReporte = [];
    this.empleados.forEach((item, index) => {
      let data = {
        Nombre:
          item.nombre + " " + item.apellidoPaterno + " " + item.apellidoMaterno,
        RFC: item.rfc,
        "E-mail": item.mail,
        "Numero de Empleado": item.noEmpleado,
        Dependencia: item.dependenciaCatalogo,
        "Unidad Administrativa": item.unidadCatalogo,
        Sexo: item.sexo,
      };
      arrayReporte.push(data);
    });
    downloadCSV("Reporte_Empleados_" + moment().format("YYYY-MM-DD"), arrayReporte);
  }



  changeEstatus(item, estatus) {
    item.fechaCambioEstatus = moment().format("YYYY-MM-DD HH:mm:ss");
    item.estatus = estatus;
    this.subResourceService
      .update(item, EmpleadoVariable.UPDATE_ESTATUS)
      .subscribe((data) => {
        swal("Éxito", data.message, "success");
      });
  }

  altaAnalista() {
    this.router.navigate(["/angular/register-analista"]);
  }

  showLists(opt) {
    this.list = opt;
    this.empleados = [];
  }

  back( ruta ){
    if (ruta === 'mySolicitud') {
      this.router.navigate(['/angular/dashboard-analista-solicitud']);
    }
    else if  (ruta === 'solicitudes') {
      this.router.navigate(['/angular/dashboard-solicitudes']);
    }
    else if (ruta === 'asegurado') {
      this.router.navigate(['/angular/dashboard-analista']);
    }else if( ruta === 'analista' ){
      this.router.navigate(['/angular/dashboard-all-analista']);
    }
  }
}
