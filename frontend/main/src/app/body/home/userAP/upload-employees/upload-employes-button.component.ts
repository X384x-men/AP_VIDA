import { CommonModule } from "@angular/common";
import { HttpClientModule, HttpErrorResponse, HttpResponse } from "@angular/common/http";
import { Component } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { Router } from "@angular/router";
import { CargasBatchService } from "src/app/core/services/service-cargas-Batch/cargas-batch.service";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { UsuarioAcceso } from "src/app/core/static/variables/url/URLImages";
import swal from "sweetalert2";

@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    HttpClientModule
  ],
  selector: "app-upload-employees-button",
  template: `
  <button type="button" class="btn btn-light rounded" (click)="ruta()" >
      <i class="fa fa-solid fa-person-circle-plus"></i>
        Nuevos Analistas
   </button>
   <button type="button" class="btn btn-busqueda" >
      <input
        id="uploadInput"
        type="file"
        style="display: none;"
        (change)="uploadEmployees($event, 1)"
        multiple
      />
      <label for="uploadInput">
        <i class="fa fa-upload" aria-hidden="true"></i>
        Carga de empleados
      </label>
   </button>
   <button type="button" class="btn btn-light" >
      <input
        id="uploadInput2"
        type="file"
        style="display: none;"
        (change)="uploadEmployees($event, 2)"
        multiple
      />
      <label for="uploadInput2">
        <i class="fa fa-solid fa-person-circle-minus"></i>
          Baja de empleados
      </label>
   </button>
  `,
  styles: [
    `
         label{
           margin-bottom: 0rem !important;
         }
         .btn-busqueda{
             color: #FFF;
             background-color: #257aa9;
             border-radius: 10px !important;
         }
     
         .btn-busqueda:hover{
             color: #FFF;
             background-color: #2e5fad;
         }
         `,
  ],
})
export class UploadEmployeesButton {
  formData = new FormData();
  empleados = [];
  user: {
    rfc: string;
    nombre: string;
    dependencia: string;
    unidadAdministrativa: string;
  };

  constructor(
    private batchService : CargasBatchService,
    private subResourceService: SubResourceService<any>,
    private router: Router
  ) { }

  public uploadEmployees(event : Event, type : number): void {
    const target = event.target as HTMLInputElement;
    const files : FileList | null = target.files
    if (!this.isValidType(files[0], type)) {
      swal("error", "El archivo no es válido", "error");
      return;
    }
    Array.prototype.forEach.call( files, (file : File ) => {
        this.formData.append( "file", file );
      });
      this.callService( this.formData, type )
  }

  private isValidType(file: File, type : number): boolean {
    const { name } = file;
    if (type === 1) {
      if (!name.match(/Alta_clientes/) || !name.match('xlsx') && !name.match('xls')) return false;
      return true;
    } else if( type === 2 ){
      if (!name.match(/Actualizacion_usuarios/) || !name.match('xlsx') && !name.match('xls')) return false;
      return true;
    }
  }

  callService = ( file : FormData, type : number ) => {
    let carga : any;
    if (type === 1) {
      carga = this.batchService.postCargaUser
    }else if( type === 2 ){
      carga = this.batchService.postUserAct
    }
    carga( file ).subscribe({
      next: ( result : HttpResponse<FileList> ) => {
        console.log(result);
        if (result === null) {
          swal('Error', 'Hay error de formato, por favor cambiarlo para poder subirlo', 'error');
          return
        }
        swal(result['mensaje'], 'registros efectuados : ' + result['regristrosValidos'] + ', Registros No Efectuados : ' + result['registrosRechazados'],'success' )
        setTimeout(() => {
          this.getEmpleados()
        }, 1000);
      },
      error: ( error : HttpErrorResponse ) => {
        swal('Atención', 'Algunos registros NO fueron efectuados', 'warning')
        console.log(error);
      },
      complete: () => {
        console.log("finalizo servicio");
      }
     } )
  }

  getEmpleados = () => {
    this.subResourceService
      .read(UsuarioAcceso.GET_LIST_EMPLEADOS_SEARCH, {
        rfc: null,
        nombre: null,
        dependencia: null,
        unidadAdmin: null,
      }
    ).subscribe( (data) => {
      this.empleados = data.filter( (user : any ) => user.tipoAnalista == 0);
    });
  }

  ruta = () => {
    this.router.navigate(["/angular/register-analista"]);
  }

}
