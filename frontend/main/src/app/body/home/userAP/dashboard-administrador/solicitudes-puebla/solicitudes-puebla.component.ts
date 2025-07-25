import { Component, Inject, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { GlobalVariable, SolicitudVariable} from "src/app/core/static/variables/url/URLImages";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { MatPaginator } from "@angular/material/paginator";
import { ExcelService } from 'src/app/core/services/excel-service/excel-service.service';
import { ModalService } from 'src/app/shared/services/modal.service';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import { MatTableDataSource } from '@angular/material/table';
import { UserAp } from 'src/app/core/interface/apUser/apUser';
import { MatSort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import swal from 'sweetalert2';
import moment from 'moment';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-solicitudes-puebla',
  templateUrl: './solicitudes-puebla.component.html',
  styleUrls: ['./solicitudes-puebla.component.css']
})
export class SolicitudesPueblaComponent {



  apvidaBackground = GlobalVariable.BACKGROUND_IMG_APVIDA;
  userApp: any;
  isAnalista: boolean = false;
  solicitudes : any;
  loading = true;
  estatus = 'Todos';
  tipoTramite = 'Todos';
  optionsTipoTramite: SelectMenu[];
  optionsEstatus: SelectMenu[];
  rfc = '';
  folio = '';
  solicitudesAux = [];
  today = new Date();
  selectedEstatus = '';
  selectedTramite='';
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
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private excelService:ExcelService,
    private modal: ModalService,
    private announcer: LiveAnnouncer,
    private authencationService: AuthenticationService,
    private router: Router
  ) {
    this.getSolicitudes();
    this.initOptionsEstatus();
    this.initOptionsTipoTramite();
  }

  ngOnInit(): void {
    this.authencationService.validacionAdmin();
  }

  ngAfterViewInit():void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  initOptionsTipoTramite(){
    let data = [
      {data: 'Todos', id: 0},
      {data: 'Retiro por edad y tiempo de servicio', id: 0},
      {data: 'Cesantía en edad avanzada', id: 0},
      {data: 'Régimen cuentas individuales' ,id: 0}
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

  // probando lista de solicitudes
  async getSolicitudes(){
    let infoNew = []
    this.subResourceService.list(SolicitudVariable.GET_SOLIITUDES_ANALISTAS,'' ,{nombre: '', RFC: '', tramite: '', status: '', categoriaSolicitud: 'puebla'})
      .subscribe(data=>{
        data.forEach(async item => {
          item.isLayout = false;
          item.isReporteContable = false;
        });
      this.solicitudes = data;
      this.solicitudesAux = Object.assign([],data);
      this.dataSource.data = this.solicitudes
      }, error=>{
        console.log(error);
      });
    setTimeout(() => {
      this.loading = false;
    }, 500);
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
        FechaDeSolicitud: moment(item.fechaSolicitud).format('DD/MM/YYYY'),
        Ap_Paterno: item.apellidoPaterno,
        Ap_Materno:  item.apellidoMaterno,
        Nombre: item.nombre,
        rfcAsegurado: item.rfcAsegurado,
        TipoDeTramite: item.tipoTramite,
        telefono: item.telefono,
        eMail: item.email,
        FechaFinLaboral: item.fechaFinLaboral ? moment(item.fechaFinLaboral).format('DD/MM/YYYY') : '',
        banco: item.idBanco !== 0 ? prueba[0] : '',
        CLABE: item.clabe,
        TipoDePago: item.tipoPago,
        Observaciones: item.observaciones,
        importe: item.sueldo
      }
      dataLayout.push(data);
    })
    this.excelService.exportAsExcelFile(dataLayout, 'SolicitudesTotales' + this.today);
    this.modal.success('Éxito', 'Se generó correctamente la descarga de solicitudes');
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
        categoriaSolicitud: 'puebla'
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

  back( ruta ){
    if (ruta === 'Listsolicitudes') {
      this.router.navigate(['/angular/dashboard-admin/listas-de-solicitudes']);
    }
    else if  (ruta === 'puebla') {
      this.router.navigate(['/angular/dashboard-admin/solicitudes-puebla']);
    }
    else if (ruta === 'fonacot') {
      this.router.navigate(['/angular/dashboard-admin/solicitudes-fonacot']);
    }
  }

}
