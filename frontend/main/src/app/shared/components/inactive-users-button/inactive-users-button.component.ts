import { Component, ElementRef, ViewChild } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { HttpErrorResponse, HttpResponse } from "@angular/common/http";
import { CargasBatchService } from "src/app/core/services/service-cargas-Batch/cargas-batch.service";
import swal from "sweetalert2";


@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule
  ],
  selector: "app-inactive-users-button",
  template: `
    <button type="button" class="btn btn-light" style="border-radius: 10px !important;">
      <input
        id="uploadInput"
        type="file"
        style="display: none;"
        (change)="bajaEmpleados($event)"
        multiple
      />
      <i class="fa fa-solid fa-person-circle-minus"></i>
        Baja de empleados
    </button>
  `,
  styles: [
    `
           button label {
             align-items: center;
             display: flex;
             margin: 0;
           }
         `,
  ],
})
export class InactiveUsersButtonComponent {
  @ViewChild("uploadFile") inputFile: ElementRef;

  constructor(
    private batchService : CargasBatchService,
  ) {}

  public bajaEmpleados(event : Event): void {
    const target = event.target as HTMLInputElement;
    const files : FileList | null = target.files
    if (!this.isValidType(files[0])) {
      swal("error", "El archivo no es válido", "error");
      return;
    }
    if (files) {
      const formData = new FormData();
      Array.prototype.forEach.call( files, (file : File ) => {
      formData.append( "file", file );
      console.log(formData);
     });
     this.batchService.postUserAct( formData ).subscribe({
      next: ( result : HttpResponse<FileList> ) => {
         console.log({result});
      },
      error: ( error : HttpErrorResponse ) => {
        console.log({error});
      },
      complete: () => {
        console.log("finalizo servicio");
      }
     } )
    }

  }

  private isValidType(file: File): boolean {
    const { name } = file;
    console.log({file});
    if (!name.match(/Actualiza_usuarios/) || !name.match('xls') || !name.match('xlsx')) return false;
    return true;
  }


}
