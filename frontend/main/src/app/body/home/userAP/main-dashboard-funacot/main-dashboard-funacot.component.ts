import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import moment from 'moment';
import { UserAp } from 'src/app/core/interface/apUser/apUser';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { ExcelService } from 'src/app/core/services/excel-service/excel-service.service';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { GlobalVariable, SolicitudVariable} from 'src/app/core/static/variables/url/URLImages';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import { ModalService } from 'src/app/shared/services/modal.service';
import swal from 'sweetalert2';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';

@Component({
  selector: 'app-main-dashboard-funacot',
  templateUrl: './main-dashboard-funacot.component.html',
  styleUrls: ['./main-dashboard-funacot.component.css']
})
export class MainDashboardFunacotComponent {


  apvidaBackground  = GlobalVariable.BACKGROUND_IMG_APVIDA;
  userApp: any;
  list: number = 1;
  solicitudes = [];
  isPuebla = true;
  optionsTipoTramite: SelectMenu[];
  currentTipoTramite: SelectMenu;
  optionsEstatus: SelectMenu[];
  currentEstatus: SelectMenu;
  tipoTramite = 'Todos';
  estatus = 'Todos';
  rfc = '';
  folio = '';
  solicitudesAux = [];
  allLayout: boolean = false;
  allReporteActuaria: boolean = false;
  fileNameExcel = 'Cargar Archivo';
  fileNameExcel2 = '';
  jsonExcel = '';
  listCalc = [];
  showList = 1;
  loading = true;
  selectedEstatus = '';
  selectedTramite='';
  eventoHistorico : any;
  idSolciitudes : number;
  analistas : Array<any> = [];
  today = new Date();
  public dataSource = new MatTableDataSource<UserAp>();
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  solicitud = {
    nombre : "",
    RFC: "",
    tramite: "",
    status: ""
  };

  dataBancos = [
    { id: 1	, data:'HSBC'},
    { id: 2	, data:'BANAMEX'},
    { id: 3	, data:'SCOTIABANK INVERLAT'},
    { id: 4	, data:'BANCOMER'},
    { id: 5	, data:'Banco Mercantil Del Norte S.A.'},
    { id: 6	, data:'IXE'},
    { id: 7	, data:'MIFEL'},
    { id: 8	, data:'BANCO MULTIVA SA'},
    { id: 9	, data:'BANCO AUTOFIN MEXICO'},
    { id: 10, data:'	ACTINVER'},
    { id: 11, data:'	BANCO DEL BAJIO'},
    { id: 12, data:'	BANCO NACIONAL DEL EJERCITO'},
    { id: 13, data:'	BANCO COPPEL'},
    { id: 14, data:'	Banco Santander (México) S.A.'},
    { id: 15, data:'	AMEX'},
    { id: 16, data:'	BANREGIO'},
    { id: 17, data:'	INBURSA'},
    { id: 18, data:'	BANK OF AMERICA MEXICO, S.A.'},
    { id: 19, data:'	BANCO AZTECA'},
    { id: 20, data:'	BANSEFI'},
    { id: 21, data:'	CIBANCO'},
    { id: 22, data:'	BANCO FAMSA'},
    { id: 23, data:'	LIBERTAD SERVICIOS FINANCIEROS, S.A. DE C.V., SFP'},
    { id: 24, data:'	BANCO COMPARTAMOS, S.A.'},
    { id: 25, data:'	BANCO VE POR MAS'},
    { id: 26, data:'	BANCA AFIRME'},
    { id: 27, data:'	BANCO MONEX, S.A.'},
    { id: 58, data:'	GLOBAL BANK CORPORATION'}
  ]

  constructor(
    private router: Router,
    @Inject('ServiceResource') private subResourceService: SubResourceService<any>,
    private excelService:ExcelService,
    private modal: ModalService,
    private announcer: LiveAnnouncer,
    private authencationService: AuthenticationService
  ) {  }

  ngOnInit() {
    this.authencationService.validacionUserFunacot();
    this.userApp = JSON.parse(localStorage.getItem("currentUserFunacot"));
    this.refreshList();
  }

  ngAfterViewInit():void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  refreshList(){
    this.getSolicitudes();
    this.initOptionsTipoTramite();
    this.initOptionsEstatus();
  }

  addSolicitud(){
    this.router.navigate(['/angular/form-solicitudes'], {queryParams: {opt: 1}});
  }


  getSolicitudes(){
    this.subResourceService.list(SolicitudVariable.GET_SOLIITUDES_ANALISTAS,'' ,{nombre: '', RFC: '', tramite: '', status: '', categoriaSolicitud: 'fonacot'})
      .subscribe( data=> {
        data.forEach(item => {
          item.isLayout = false;
          item.isReporteContable = false;
        });
       this.solicitudes = data.sort(((a, b) => a.idSolicitud - b.idSolicitud));
       this.solicitudesAux = Object.assign([],data);
       this.dataSource.data = this.solicitudes
       setTimeout(() => {
        this.loading = false;
      }, 500);
      }, error=>{
        console.log(error);
      });

  }

  getListCalculo(){
    this.subResourceService.list(SolicitudVariable.GET_LIST_CALCULO,'' ,{})
      .subscribe(data=>{
        this.listCalc = data;
      }, error=>{
        console.log(error);
      });
  }

  showLists(opt){
    this.showList = opt;
  }

  initOptionsTipoTramite(){
    let data = [
      {data: 'Todos', id: 0},
      {data: 'Devolución de prima', id: 0}
    ]

    this.optionsTipoTramite = Smartwfm.createSelectOptions(data, 'data');

  }

  initOptionsEstatus(){
    let data = [
      {data: 'Todos', id: 0},
      {data: 'Nueva', id: 0},
      {data: 'En proceso', id: 0},
      {data: 'PENDIENTE DE DOCS' ,id: 0},
      {data: 'Proceso de revision de pago' ,id: 0},
      {data: 'Importes validados' ,id: 0},
      {data: 'Actualizada' ,id: 0},
      {data: 'Falta de información' ,id: 0},
      {data: 'Terminada' ,id: 0},
      {data: 'Rechazada' ,id: 0}
    ]

    this.optionsEstatus = Smartwfm.createSelectOptions(data, 'data');
  }

  selectAll(){
    if(this.allLayout){
      this.solicitudes.forEach(item => {
        if(item.statusSolicitud === 'Importes validados' && item.fechaOrdenPago == null){
          item.isLayout = true;
        }
      });
    }else{
      this.solicitudes.forEach(item => {
        if(item.statusSolicitud === 'Importes validados' && item.fechaOrdenPago == null){
          item.isLayout = false;
        }
      });
    }
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

  downloadSolicitudBatch = () => {
    let dataLayout = [];
    let optionsBanco = Smartwfm.createSelectOptions(this.dataBancos, 'data');

    this.solicitudes.forEach((item, index) => {
      let prueba = optionsBanco.filter(banco => banco.extras.id === item.idBanco ).map( info => {
        return info.extras.data;
      })
      let data = {
        id: item.idSolicitud,
        FechaRegistro: moment(item.fechaSolicitudAPV).format('DD/MM/YYYY'),
        FechaDeSolicitud: moment(item.fechaSolicitud).format('DD/MM/YYYY'),
        Ap_Paterno: item.apellidoPaterno,
        Ap_Materno:  item.apellidoMaterno,
        Nombre: item.nombre,
        rfcAsegurado: item.rfcAsegurado,
        TipoDeTramite: item.tipoTramite,
        telefono: item.telefono,
        eMail: item.email,
        Plazo: item.sueldo,
        FechaFinLaboral: item.fechaFinLaboral ? moment(item.fechaFinLaboral).format('DD/MM/YYYY') : '',
        banco: item.idBanco !== 0 ? prueba[0] : '',
        CLABE: item.clabe,
        TipoDePago: item.tipoPago,
        Observaciones: item.observaciones
      }
      dataLayout.push(data);
    })
    this.excelService.exportAsExcelFile(dataLayout, 'SolicitudesTotalesFonacot' + this.today);
    this.modal.success('Éxito', 'Se generó correctamente la descarga de solicitudes');
  }

  verDetalleSolicitud(item){
    this.router.navigate(['/angular/form-edit-solicitudes'], {queryParams: {solicitud: item.idSolicitud, categoria: 'fonacot', opt: 2}});
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

  onSelectedTipoTramite(value:string): void {
    if (value === 'Todos' || value === '- Seleccionar un opción  -') {
      this.selectedTramite = ''
    }else{
      this.selectedTramite = value;
    }
  }

  filter() {
    this.solicitudes = [];
    this.dataSource.data = [];
    const batchSize = 100;
    this.solicitud.status= this.selectedEstatus;
    this.solicitud.tramite=this.selectedTramite;
    this.subResourceService.read(SolicitudVariable.GET_SOLIITUDES_ANALISTAS, {
        nombre: this.solicitud.nombre,
        RFC: this.solicitud.RFC,
        tramite: this.solicitud.tramite,
        status: this.solicitud.status,
        categoriaSolicitud: 'fonacot'
      }
    ).subscribe( (data) => {
      this.solicitudes = data;
      let currentIndex = 0;
      const addEmployees = setInterval(() => {
        const batch = this.solicitudes.slice(currentIndex, currentIndex + batchSize);
        this.dataSource.data.push(...batch);
        currentIndex += batchSize;
        this.dataSource._updateChangeSubscription();
        if (currentIndex >= this.solicitudes.length) {
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


  idSolciitud = ( id : number ) => {
      this.idSolciitudes = id
  }

}
