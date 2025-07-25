import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, Inject, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { UserAp } from 'src/app/core/interface/apUser/apUser';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';
import { ExcelService } from 'src/app/core/services/excel-service/excel-service.service';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { AclaracionVariable, GlobalVariable, ObtencionCatalogos } from 'src/app/core/static/variables/url/URLImages';
import { ModalService } from 'src/app/shared/services/modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-main-aclaraciones',
  templateUrl: './main-aclaraciones.component.html',
  styleUrls: ['./main-aclaraciones.component.css']
})
export class MainAclaracionesComponent{

  apvidaBackground  = GlobalVariable.BACKGROUND_IMG_APVIDA;
  userApp: any;
  list: number = 1;
  aclaraciones = [];
  isComercial = false;
  isSiniestros = false;
  isContabilidad = false;
  isExterno = false;
  optionsEstatus: SelectMenu[];
  tipoTramite = 'Todos';
  estatus = 'Todos';
  rfc = '';
  folio = '';
  aclaracionesAux = [];
  allLayout: boolean = false;
  allReporteActuaria: boolean = false;
  fileNameExcel = 'Cargar Archivo';
  fileNameExcel2 = '';
  jsonExcel = '';
  listCalc = [];
  showList = 1;
  loading = true;
  selectedEstatus = '';
  today = new Date();
  public dataSource = new MatTableDataSource<UserAp>();
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  aclaracion = {
    nombre : "",
    rfc: "",
    email: "",
    tramite: ""
  };

  constructor(
    private router: Router,
    @Inject('ServiceResource') private subResourceService: SubResourceService<any>,
    private excelService:ExcelService,
    private modal: ModalService,
    private announcer: LiveAnnouncer,
    private authencationService: AuthenticationService
  ) {  }

  ngOnInit() {
    let allUsuarios = [];
    allUsuarios.push(JSON.parse(localStorage.getItem("currentUser")))
    allUsuarios.push(JSON.parse(localStorage.getItem("currentUserComercial")));
    allUsuarios.push(JSON.parse(localStorage.getItem("currentUserSiniestros")));
    allUsuarios.push(JSON.parse(localStorage.getItem("currentUserContabilidad")));
    allUsuarios.push(JSON.parse(localStorage.getItem("currentUserAdmin")));
    allUsuarios.push(JSON.parse(localStorage.getItem('idCuenta')));
    this.userApp = allUsuarios.find( (value) => value !== null );
    switch (this.userApp.authorities[0]['authority']) {
        case 'ROLE_ACOME':
          this.isComercial = true;
        break;
        case 'ROLE_ASINI':
          this.isSiniestros = true;
        break;
        case 'ROLE_ACONT':
          this.isContabilidad = true;
        break;

      default:
        this.authencationService.validacionUser();
        break;
    }

    this.refreshList();
  }

  ngAfterViewInit():void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  refreshList(){
    this.getDependencias();
    this.getAclaraciones();
    //this.authencationService.validacionUser();
  }

  addAclaracion(){
    this.router.navigate(['/angular/form-aclaraciones'], {queryParams: {opt: 1}});
  }

  // dejar para editar aclaracion
  verDetalleAclaracion(item){
    this.router.navigate(['/angular/form-edit-aclaraciones'], {queryParams: {solicitud: item.idAclaracion, tipo: item.categoriaAclaracion === true ? 2 : 1,  opt: 1}});
  }


  getDependencias = () => {
    this.subResourceService.list(ObtencionCatalogos.GET_CATALOGO_DEPENDENCIAS,'' ,'').subscribe(data=>{
      this.optionsEstatus = data
    });
  }


  getAclaracion = (rfc : string) => {
    this.subResourceService.list(AclaracionVariable.GET_ACLARACION,'' ,{rfc: rfc, nombre: '', dependencia: '', fechaRegistroPortal: '', telefono: '', email: '', aclaracionEmpleados: 0})
    .subscribe( data=> {
      data.forEach(item => {
        item.isLayout = false;
        item.isReporteContable = false;
      });
     let filtradoData = data.filter( item => item.status === 1 || item.status === 3 );
     let filtradoComercial = data.filter( item => item.status === 0 || item.status === 3 || item.status === 2 );
     if (this.isSiniestros) {
        this.aclaraciones = filtradoData.sort(((a, b) => a.idAclaracion - b.idAclaracion));
        this.dataSource.data = this.aclaraciones
        setTimeout(() => {
          this.loading = false;
        }, 500);
     }else if( this.isComercial ){
        this.aclaraciones = filtradoComercial.sort(((a, b) => a.idAclaracion - b.idAclaracion))
        this.dataSource.data = this.aclaraciones
        setTimeout(() => {
          this.loading = false;
        }, 500);
     }
    //  this.aclaracionesAux = Object.assign([],data);
    }, error=>{
      console.log({error});
      this.loading = false;
    });
  }

  getAclaraciones(){
    if (!this.isExterno) {
      this.getAclaracion('')
    }else{
      this.getAclaracion(this.userApp.username)
    }
  }

  showLists(opt){
    this.showList = opt;
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

  back( ruta ){
    if  (ruta === 'solicitudes') {
      this.router.navigate(['/angular/dashboard-solicitudes']);
    }
    else if (ruta === 'asegurado') {
      this.router.navigate(['/angular/dashboard-analista']);
    }else if( ruta === 'analista' ){
      this.router.navigate(['/angular/dashboard-all-analista']);
    }else if( ruta === 'solicitudesExterno' ){
      this.router.navigate(['/angular/list-solicitudes']);
    }else if (ruta === 'mySolicitud') {
      this.router.navigate(['/angular/dashboard-analista-solicitud']);
    }

  }

  downloadAclaracionBatch = () => {
    let dataLayout = [];
    this.aclaraciones.forEach((item, index) => {
      let data = {
        id: item.idAclaracion,
        Dependencia: item.dependencia,
        Nombre: item.nombre,
        rfcAsegurado: item.rfc,
        FechaRegistro: item.fechaReal,
        tipoAclaracion: item.tipoAclaracionString,
        telefono: item.telefono,
        eMail: item.email,
        Observaciones: item.comentarios,
      }
      dataLayout.push(data);
    })
    this.excelService.exportAsExcelFile(dataLayout, 'AclaracionesTotales' + this.today);
    this.modal.success('Éxito', 'Se generó correctamente la descarga de aclaraciones');
  }


  // Filtros nuevos de tabla nueva
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onSelectedoptionsEstatus(value:string): void {
    if (value === 'Todos' || value === '- Seleccionar un opción  -') {
      this.selectedEstatus = ''
    }else{
      this.selectedEstatus = value;
    }
  }

  filter() {
    this.aclaraciones = [];
    this.dataSource.data = [];
    const batchSize = 100;
    this.aclaracion.tramite=this.selectedEstatus;
    this.subResourceService.list(AclaracionVariable.GET_ACLARACION,'' ,{
      rfc: this.aclaracion.rfc || '',
      nombre: '',
      dependencia: this.aclaracion.tramite || '',
      fechaRegistroPortal: '',
      telefono: '',
      email: this.aclaracion.email || '',
      aclaracionEmpleados: 0
    }
    ).subscribe( (data) => {
      this.aclaraciones = data;
      let currentIndex = 0;
      const addEmployees = setInterval(() => {
        const batch = this.aclaraciones.slice(currentIndex, currentIndex + batchSize);
        this.dataSource.data.push(...batch);
        currentIndex += batchSize;
        this.dataSource._updateChangeSubscription();
        if (currentIndex >= this.aclaraciones.length) {
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

    }, error=>{
      swal(error, '', 'error')
      console.log({error});
    });
  }

}
