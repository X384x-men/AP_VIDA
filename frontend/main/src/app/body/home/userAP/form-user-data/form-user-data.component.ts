import {
  Component,
  OnInit,
  Input,
  Inject,
} from "@angular/core";
import {
  Dependencias,ObtencionCatalogos
 } from "src/app/core/static/variables/url/URLImages";
import { UntypedFormGroup } from "@angular/forms";
import { validRFC } from "src/app/core/Util/validations";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";

import { DependenciesService } from "src/app/shared/services/dependencies.service";
import { AdminUnitsService } from "src/app/shared/services/admin-units.service";

@Component({
  selector: "app-form-user-data",
  templateUrl: "./form-user-data.component.html",
  styleUrls: ["./form-user-data.component.css"],
})
export class FormUserDataComponent implements OnInit {
  @Input() isAnalista: boolean = false;

  myDatepickerIni: Date = new Date();
  @Input() isUpdate: boolean = false;
  @Input() user: UntypedFormGroup;
  @Input() dependenci ='';

  isValid: boolean = false;
  dependencias = [];
  unidades : any = [];
  dependencia='';
  selected = '';
  dependenciesOptions: any;

  today: Date = new Date();
  maxDate: Date = new Date();

  myDatepickerNac: Date = new Date();
  myDatepickerInc: Date = new Date();

  fieldTextType: boolean = false;

  constructor(
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>,
    private adminUnitsService: AdminUnitsService
    ) {}

  ngOnInit() {
    //this.dependencies_.dependencies.toPromise().then( result => {
      //this.dependenciesOptions = result;
    //})
    if (!this.isAnalista) {
      console.log('pase');
      this.getDependencies();
      this.getUnidadesAdmin()
    }

  }

  validateRfc(rfc: string) {
    validRFC(rfc);
  }


	onSelected(value:string): void {
		  this.selected = value;
	}

  stateOptions = [
    { data: "Aguascalientes", id: 0 },
    { data: "Baja California", id: 0 },
    { data: "Baja California Sur", id: 0 },
    { data: "Campeche", id: 0 },
    { data: "Coahuila de Zaragoza", id: 0 },
    { data: "Colima", id: 0 },
    { data: "Chiapas", id: 0 },
    { data: "Chihuahua", id: 0 },
    { data: "Ciuda de Mexico", id: 0 },
    { data: "Durango", id: 0 },
    { data: "Guanajuato", id: 0 },
    { data: "Guerrero", id: 0 },
    { data: "Hidalgo", id: 0 },
    { data: "Jalisco", id: 0 },
    { data: "México", id: 0 },
    { data: "Michoacán de Ocampo", id: 0 },
    { data: "Morelos", id: 0 },
    { data: "Nayarit", id: 0 },
    { data: "Nuevo León", id: 0 },
    { data: "Oaxaca", id: 0 },
    { data: "Puebla", id: 0 },
    { data: "Querétaro", id: 0 },
    { data: "Quintana Roo", id: 0 },
    { data: "San Luis Potosí", id: 0 },
    { data: "Sinaloa", id: 0 },
    { data: "Sonora", id: 0 },
    { data: "Tabasco", id: 0 },
    { data: "Tamaulipas", id: 0 },
    { data: "Tlaxcala", id: 0 },
    { data: "Veracruz de Ignacio de la Llave", id: 0 },
    { data: "Yucatán", id: 0 },
    { data: "Zacatecas", id: 0 },
  ]

  accountTypeOptions = [
    { data: "Cuenta bancaria", id: 0 },
    { data: "Cuenta Clabe", id: 0 },
    { data: "Tarjeta de debito", id: 0 },
  ];

  genreOptions = [
    { data: "HOMBRE", id: 0 },
    { data: "MUJER", id: 1 },
  ];


  bankOptions = [
    { data: "BANAMEX", id: 0 },
    { data: "BANCOMEXT", id: 0 },
    { data: "BANOBRAS", id: 0 },
    { data: "BBVA BANCOMER", id: 0 },
    { data: "SANTANDER", id: 0 },
    { data: "BANJERCITO", id: 0 },
    { data: "HSBC", id: 0 },
    { data: "BAJIO", id: 0 },
    { data: "IXE", id: 0 },
    { data: "INBURSA", id: 0 },
    { data: "INTERACCIONES", id: 0 },
    { data: "MIFEL", id: 0 },
    { data: "SCOTIABANK", id: 0 },
    { data: "BANREGIO", id: 0 },
    { data: "INVEX", id: 0 },
    { data: "BANSI", id: 0 },
    { data: "AFIRME", id: 0 },
    { data: "BANORTE", id: 0 },
    { data: "THE ROYAL BANK", id: 0 },
    { data: "AMERICAN EXPRESS", id: 0 },
    { data: "BAMSA", id: 0 },
    { data: "TOKYO", id: 0 },
    { data: "JP MORGAN", id: 0 },
    { data: "BMONEX", id: 0 },
    { data: "VE POR MAS", id: 0 },
    { data: "ING", id: 0 },
    { data: "DEUTSCHE", id: 0 },
    { data: "CREDIT SUISSE", id: 0 },
    { data: "AZTECA", id: 0 },
    { data: "AUTOFIN", id: 0 },
    { data: "BARCLAYS", id: 0 },
    { data: "COMPARTAMOS", id: 0 },
    { data: "BANCO FAMSA", id: 0 },
    { data: "BMULTIVA", id: 0 },
    { data: "ACTINVER", id: 0 },
    { data: "WAL-MART", id: 0 },
    { data: "NAFIN", id: 0 },
    { data: "INTERBANCO", id: 0 },
    { data: "BANCOPPEL", id: 0 },
    { data: "ABC CAPITAL", id: 0 },
    { data: "UBS BANK", id: 0 },
    { data: "CONSUBANCO", id: 0 },
    { data: "VOLKSWAGEN", id: 0 },
    { data: "CIBANCO", id: 0 },
    { data: "BBASE", id: 0 },
    { data: "BANSEFI", id: 0 },
    { data: "HIPOTECARIA FEDERAL", id: 0 },
    { data: "MONEXCB", id: 0 },
    { data: "GBM", id: 0 },
    { data: "MASARI", id: 0 },
    { data: "VALUE", id: 0 },
    { data: "ESTRUCTURADORES", id: 0 },
    { data: "TIBER", id: 0 },
    { data: "VECTOR", id: 0 },
    { data: "B&B", id: 0 },
    { data: "ACCIVAL", id: 0 },
    { data: "MERRILL LYNCH", id: 0 },
    { data: "FINAMEX", id: 0 },
    { data: "VALMEX", id: 0 },
    { data: "UNICA", id: 0 },
    { data: "MAPFRE", id: 0 },
    { data: "PROFUTURO", id: 0 },
    { data: "CB ACTINVER", id: 0 },
    { data: "OACTIN", id: 0 },
    { data: "SKANDIA", id: 0 },
    { data: "CBDEUTSCHE", id: 0 },
    { data: "ZURICH", id: 0 },
    { data: "ZURICHVI", id: 0 },
    { data: "SU CASITA", id: 0 },
    { data: "CB INTERCAM", id: 0 },
    { data: "CI BOLSA", id: 0 },
    { data: "BULLTICK CB", id: 0 },
    { data: "STERLING", id: 0 },
    { data: "FINCOMUN", id: 0 },
    { data: "HDI SEGUROS", id: 0 },
    { data: "ORDER", id: 0 },
    { data: "AKALA", id: 0 },
    { data: "CB JPMORGAN", id: 0 },
    { data: "REFORMA", id: 0 },
    { data: "STP", id: 0 },
    { data: "TELECOMM", id: 0 },
    { data: "EVERCORE", id: 0 },
    { data: "SKANDIA", id: 0 },
    { data: "SEGMTY", id: 0 },
    { data: "ASEA", id: 0 },
    { data: "KUSPIT", id: 0 },
    { data: "SOFIEXPRESS", id: 0 },
    { data: "UNAGRA", id: 0 },
    { data: "OPCIONES EMPRESARIALES DEL NOROESTE", id: 0 },
    { data: "CLS", id: 0 },
    { data: "INDEVAL", id: 0 },
    { data: "LIBERTAD", id: 0 },
  ];

  analistOptions = [
    { data: "Ejecutivo de atención", id: 1 },
    { data: "Coordinador", id: 2 },
    { data: "Actuarial", id: 3 },
  ];

  // this.user[attr].replace(/[^0-9a-zA-ZñÑáéíóúÁÉÍÓÚ.()/::,@ _-]/g,'');
  mayus(attr) {
    if (
      attr == "nombre" ||
      attr == "apellidoPaterno" ||
      attr == "apellidoMaterno"
    ) {
      this.user[attr] = this.user[attr].replace(/[^a-zA-ZñÑáéíóúÁÉÍÓÚ ]/g, "");
    } else if (attr == "noEmpleado" || attr == "rfc") {
      this.user[attr] = this.user[attr].replace(
        /[^0-9a-zA-ZñÑáéíóúÁÉÍÓÚ]/g,
        ""
      );
    }
    this.user[attr] = this.user[attr].toUpperCase();
  }

  mostrarContrasena(value) {
    this.fieldTextType = value;
  }

  // Para traer las dependencias a mostrar fue comentado por fernando
  // Dependencias que no se utilizan
  getDependencies(){
    if(this.isUpdate){
      let user=JSON.parse(localStorage.getItem("currentUser"));
      this.subResourceService.read(ObtencionCatalogos.GET_DEPENDENCIAS_USUARIO, {
        rfc: user.username
         }).subscribe( data=>{;
           this.selected=data.descripcionCatalogo;
         }, error=>{
           console.log({error});
         });

      this.subResourceService.list(ObtencionCatalogos.GET_CATALOGO_DEPENDENCIAS,'' ,'')
    .subscribe(data=>{
      this.dependencias = data;
    }, error=>{
      console.log(error);
    }); }else{
      this.subResourceService.list(Dependencias,'' ,'').subscribe(data=>{
      this.dependencias = data;
    }, error=>{
      console.log({error});
    });
    }
  }

  getUnidadesAdmin(){
    this.adminUnitsService.getAdminUnits()
      .subscribe((data)=>{
        this.unidades = data;
      })
  }
}
