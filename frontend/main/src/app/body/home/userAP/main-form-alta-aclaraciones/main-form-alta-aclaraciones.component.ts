import { Location } from '@angular/common';
import { Component, Inject, Input} from '@angular/core';
import { NgForm, UntypedFormControl } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import moment from 'moment';
import { AcaracionesAp } from 'src/app/core/interface/apUser/apUser';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { AclaracionVariable, SolicitudVariable, UsuarioAcceso } from 'src/app/core/static/variables/url/URLImages';
import swal from 'sweetalert2';

@Component({
  selector: 'app-main-form-alta-aclaraciones',
  templateUrl: './main-form-alta-aclaraciones.component.html',
  styleUrls: ['./main-form-alta-aclaraciones.component.css']
})
export class MainFormAltaAclaracionesComponent{

  today: Date = new Date();
  maxDate: Date = new Date();
  loading = true;
  llenarForm = false;
  isValid = false;
  isEmpleadoValid = false;
  archivoBase64 : any;
  aclaracionTotales : any;
  tipoRfc : string = 'Inexistente';


  myDatepickerFechaRegistro: Date = new Date();
  myDatepickerFechaAclaracion : Date = new Date();
  date4 = new UntypedFormControl();
  date3 = new UntypedFormControl();

  optionsDep: SelectMenu[];
  currentDep: SelectMenu;

  optionsTipoTramite: SelectMenu[];
  currentTipoTramite: SelectMenu;

  optionsTipoTramiteDoc: SelectMenu[];
  currentTipoTramiteDoc: SelectMenu;

  optionsBanco: SelectMenu[];
  currentBanco: SelectMenu;

  optionsRFC: SelectMenu[] = null;
  currentRFC: SelectMenu;

  optionsTipoPago: SelectMenu[];
  currentTipoPago: SelectMenu;

  optionsMonto: SelectMenu[];
  currentMonto: SelectMenu;

  empleados = [];

  // probando
  mostrar = false;
  dataTramite2 : any;


   @Input() aclaracion: AcaracionesAp = {
    idEmpleadoAP:0,
    idAclaracion:'',
    documentoTipo:'1', // Se enviará siempre 1 ya que el cliente no quizo este campo y para no cmabiar tanto el EP simplemente se enviara 1 por defecto y no se mostrara al usuario
    tipoAclaracion:'',
    nombre: '',
    rfc:'',
    dependencia: 'CONSEJO MEXIQUENSE DE CIENCIA Y TECNOLOGIA',
    fechaRegistroPortal:'',
    descripcionEmpleado: '',
    telefono:'',
    email:'',
    comentarios:'',
    fechaReal: '',
    fechaAclaracion: '',
    status: 0,
    documentoList: [],
    nombreAclaracion : '',
    emailAclaracion: '',
    categoriaAclaracion : false,
    rfcAclaracion: ''
  }

  @Input() isAnalista: boolean = false;

  @Input() prueba : any;

  @Input() arrayFiles = [
    {id: 1, label:'Seleccionar archivo', file: null }
  ]

  @Input() arrayPdf = [
    {id: 0, tipoDocumento: 1, tipoArchivo: 0 }
  ];

  observacionesAPVida = ''; // Se cambiara a arreglo de observaciones AP vida

  userApp: any;

  fecha = '';
  isContable = false;
  isSiniestros = false;
  isComercial = false;
  isExterno = false;
  observacionDoc: any;

  constructor(@Inject('ServiceResource') private subResourceService: SubResourceService<any>, private AuthenticationService : AuthenticationService, private _location: Location ) {
    this.fecha = moment().format('YYYY-MM-DD');
    this.initOptionsTipoTramite();
    this.roles();
    setTimeout(() => {
    console.log(this.aclaracion);
    }, 1000);
  }

  roles(){
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
          this.isContable = true;
        break;

      default:
        this.AuthenticationService.validacionUser();
        break;
    }
  }

  initOptionsTipoTramite(){

    let opciones = []
    this.subResourceService.read(AclaracionVariable.GET_TIPO_ACLARACION)
    .subscribe(data=>{
      this.optionsTipoTramite = data
    }, error=>{
      console.log(error);
    });

    this.subResourceService.read(AclaracionVariable.GET_CATALOGO_ACLARACION)
    .subscribe(data=>{
      this.optionsTipoTramiteDoc = data
      this.loading = false;
    }, error=>{
      console.log(error);
    });

    setTimeout(() => {
      if(this.aclaracion.fechaReal !== ''){
        if (this.aclaracion.idAclaracion === 0) {
           this.myDatepickerFechaRegistro = moment(this.aclaracion.fechaReal).toDate();
        }else{
         this.date4 = new UntypedFormControl({ value: moment(this.aclaracion.fechaReal).toDate(), disabled: true });
         this.myDatepickerFechaRegistro = moment(this.aclaracion.fechaReal).toDate();
        }
     }
     if(this.aclaracion.fechaAclaracion !== ''){
      if (this.aclaracion.idAclaracion === 0) {
         this.myDatepickerFechaRegistro = moment(this.aclaracion.fechaAclaracion).toDate();
      }else{
       this.date3 = new UntypedFormControl({ value: moment(this.aclaracion.fechaAclaracion).toDate(), disabled: true });
       this.myDatepickerFechaRegistro = moment(this.aclaracion.fechaAclaracion).toDate();
      }
     }
    }, 1000);

    this.searchEmpleado();

  }

  searchEmpleado = () => {
    this.subResourceService.read(UsuarioAcceso.GET_LIST_EMPLEADOS_SEARCH, {
      rfc:null,
      nombre: null,
      dependencia: null,
      unidadAdmin: null
    }
    ).subscribe((data) => {
      this.empleados = data;
     })
  }


  donwloadPdf(name, tipoArchivo){
    const byteArray = new Uint8Array( atob(tipoArchivo).split('').map(( char ) => char.charCodeAt(0) ) );
    const file = new Blob([byteArray], {type: 'application/pdf'});
    const fileUrl = URL.createObjectURL(file);
    let fileName = name;
    let link = document.createElement('a');
    link.download = fileName;
    link.target = '_blank';
    link.href = fileUrl;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  getArchivo = (archivo) => {
    let file: File = archivo[0]
    let fileReader : FileReader = new FileReader();
    this.arrayFiles[0].file = archivo.item(0).lastModifiedDate;
    this.arrayFiles[0].label = archivo[0].name;
    let regex = "data:application/pdf;base64"

    fileReader.onload = ( e ) => {
      this.archivoBase64 = fileReader['__zone_symbol__originalInstance'].result.split(',')[1];
    }
    fileReader.readAsDataURL(file)

    setTimeout(() => {
      // Ultimo recibo
      this.aclaracion.documentoList = [
        {
          "idDocumento" : 1,
          "documento"   : this.arrayFiles[0].label,
          "fecha"       : moment(this.arrayFiles[0].file).format('YYYY-MM-DD'),
          "documentoString": this.archivoBase64
        }
      ]
      console.log(this.aclaracion.documentoList);
    }, 500);
  }

  getFechaRegistro(type: string, event: MatDatepickerInputEvent<Date>) {
    this.myDatepickerFechaRegistro = event.value;
    this.aclaracion.fechaReal = moment(this.myDatepickerFechaRegistro).format('YYYY-MM-DD HH:mm:ss');
  }

  getFechaAclaracion(type: string, event: MatDatepickerInputEvent<Date>) {
    this.myDatepickerFechaAclaracion = event.value;
    this.aclaracion.fechaAclaracion = moment(this.myDatepickerFechaAclaracion).format('YYYY-MM-DD HH:mm:ss');
  }

  formIncomplet = ( forms : NgForm ) => {
    this.llenarForm = true;
      Object.values( forms.controls ).forEach( control =>{
        control.markAsTouched();
      });

    this.aclaracion.status === 2 ?
    swal( '¡Atención!', 'Debes guardar el documento de FINIQUITO', 'warning' ) : swal( '¡Atención!', 'Falta información', 'warning' );
  }

  getEmpleadosAsync = async (rfc : string) => {
      const data = this.empleados.find( data => data.rfc === rfc );
      if (!data) {
        return 'negativo'
        //throw swal('Alerta','no existe un empleado con este rfc', 'warning');
      }
      return data;
  }

  getAclaracionesList = async () => {
    this.subResourceService.list(AclaracionVariable.GET_ACLARACION,'' ,{rfc: '', nombre: '', dependencia: '', fechaRegistroPortal: '', telefono: '', email: '', aclaracionEmpleados: 0 })
    .subscribe( data=> {
      data.forEach(item => {
        item.isLayout = false;
        item.isReporteContable = false;
      });
      let aclaracionOrder = data.sort(((a, b) => a.idAclaracion - b.idAclaracion))
     this.aclaracionTotales = aclaracionOrder.pop()
     console.log(this.aclaracionTotales);
    }, error=>{
      console.log({error});
      this.loading = false;
    });
  }

  // Formulario template
  guardar = async ( forms : NgForm ) => {

    // Validaciones antes de proceder a guardar
    if( forms.invalid ){
      this.formIncomplet( forms )
      return;
    }

    // Si todo esta 'OK', proceder a guardar
    const respuesta = await this.getEmpleadosAsync( this.aclaracion.rfc );
    if (respuesta !== 'negativo') {
      this.aclaracion.idEmpleadoAP = respuesta.idEmpleado;
      this.aclaracion.fechaRegistroPortal = respuesta.fechaCreacion;
      this.tipoRfc = 'Existente'
    }else{
    	this.aclaracion.categoriaAclaracion = true;
      this.aclaracion.rfcAclaracion = this.aclaracion.rfc
      this.aclaracion.nombreAclaracion = this.aclaracion.nombre
    }

    this.llenarForm = false;
    this.isValid = true;

    if(this.isValid){
      swal({
        title: 'Atención',
        text: "¿Esta seguro de continuar?",
        type: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Aceptar'
      }).then( async (result) => {
        if (result.value){
          this.subResourceService.create( this.aclaracion, AclaracionVariable.POST_ACLARACION).subscribe(async data => {
            if (this.tipoRfc === 'Inexistente') {
              swal({ title: 'Información', text: 'RFC ' + this.tipoRfc, timer: 1000, showConfirmButton: false });
            }
            if (data) {
              this.loading = true;
              await this.getAclaracionesList();
              setTimeout(() => {
                swal('Éxito', data.message + ', Folio ' + this.aclaracionTotales.idAclaracion , 'success').then(()=>{
                  this.end();
                });
              }, 1000);
            }
            return;
          }, error => {
            console.log({error});
            swal('Alerta', error, 'info');
          });
          return;

        }
      });
    }else {
      swal('Información', ' El formulario no esta valido', 'info');
    }
  }

  end(){
    this._location.back()
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


}
