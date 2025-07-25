import { Component } from "@angular/core";
import {
  getDownloadURL,
  Storage,
  percentage,
  ref,
  uploadBytesResumable
} from "@angular/fire/storage";
import { Observable } from "rxjs";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { CargaBatchVariable } from "src/app/core/static/variables/url/URLImages";
import { AccountStatusService } from "src/app/shared/services/account-status.service";
import { ModalService } from "src/app/shared/services/modal.service";
import swal from "sweetalert2";
@Component({
  template: `
    <div
      style="display: flex; justify-content: space-between; padding: 8px; flex-wrap: wrap; gap: 8px"
    >
      <app-reporte-admin></app-reporte-admin>
      <h3 style="flex-basis: 100%; text-align: center">
        <label for="cargaEstados">Carga de estados de cuenta</label>
        <!-- <input type="file" id="cargaEstados" hidden (change)="subirCargaBatch($event)"> -->
      </h3>
      <button
        mat-stroked-button
        color="primary"
        style="flex-basis: calc(50% - 8px)"
      >
        <input
          hidden
          type="file"
          (change)="subirCargaBatch($event)"
          id="file1"
          accept="text/plain"
        />
        <label
          for="file1"
          style="margin: 0; justify-content: center; align-items: center;display: flex; padding-top: 4px;"
        >
          <mat-icon>file_upload</mat-icon>
          Carga de estados de cuenta
        </label>
      </button>
      <div
        *ngIf="uploadPercent | async as percent"
        style="flex-basis: 50%; flex-direction: column"
      >
        <p>archivo: {{ fileName }}</p>
        <mat-progress-bar [value]="percent.progress"></mat-progress-bar>
      </div>
      <!-- <mat-list style="flex-basis: 100%" *ngIf="false">
        <mat-list-item *ngFor="let uploadedFile of uploadedFiles">
          <h4 matLine>{{uploadedFile.filename}}</h4>
          <mat-hint matLine style="display: flex; justify-content: space-between">{{uploadedFile.uploadDate }} - {{uploadedFile.status | uppercase}}</mat-hint>
          <button mat-stroked-button *ngIf="uploadedFile.status == 'validado'" (click)="process(uploadedFile.key)">Procesar</button>
          <mat-divider></mat-divider>
        </mat-list-item>
      </mat-list> -->
    </div>
    <div class="modalBatchClass" *ngIf="accountStatus_.modalBatch" style="width: 650px;background-color: #FFF;">
        <div  [style.background-color]="'#257aa9'" [style.color]="'#FFF'" style="display: flex;justify-content: space-between;align-items: center; padding: 10px;">
          <label style="font-size: 18px;font-weight: 600; margin: 0 !important;">Resumen Carga Batch</label>
          <div (click)="accountStatus_.modalBatch = false" style="background-color: transparent;border-radius: 50px;border: none;font-size: 20px; color: #FFF; outline: none; font-weight: 600; cursor: pointer;">
   x
          </div>
        </div>
        <div class="" [ngStyle]="{'display': 'flex', 'flex-direction': 'column', 'padding': '40px'}">
          <div>
            <div style="display: flex; padding: 10px 0px; border-bottom: 1px solid #dfd6d6;"><p style="margin: 0 !important; width: 50%;">Fecha de carga: </p><p style="margin: 0 !important; width: 50%; font-weight: 600; color: #000">{{accountStatus_.resumenBatch.fechaCarga}}</p></div>
            <div style="display: flex; padding: 10px 0px; border-bottom: 1px solid #dfd6d6;"><p style="margin: 0 !important; width: 50%;">Nombre del archivo: </p><p style="margin: 0 !important; width: 50%; font-weight: 600; color: #000">{{accountStatus_.resumenBatch.nombreArchivo}}</p></div>
            <div style="display: flex; padding: 10px 0px; border-bottom: 1px solid #dfd6d6;"><p style="margin: 0 !important; width: 50%;">Total de registros: </p><p style="margin: 0 !important; width: 50%; font-weight: 600; color: rgb(110, 30, 216)">{{accountStatus_.resumenBatch.totalRegistros | number}}</p></div>
            <div style="display: flex; padding: 10px 0px; border-bottom: 1px solid #dfd6d6;"><p style="margin: 0 !important; width: 50%;">Registros validos: </p><p style="margin: 0 !important; width: 50%; font-weight: 600; color: rgb(42, 216, 48)">{{accountStatus_.resumenBatch.regristrosValidos | number}}</p></div>
            <div style="display: flex; padding: 10px 0px; border-bottom: 1px solid #dfd6d6;"><p style="margin: 0 !important; width: 50%;">Registros rechazados: </p><p style="margin: 0 !important; width: 50%; font-weight: 600; color: rgb(228, 39, 39)">{{accountStatus_.resumenBatch.registrosRechazados | number}}</p></div>
          </div>
        </div>
        <div class="" style="display: flex;justify-content: flex-end;padding: 10px 20px 10px 20px;">
          <button type="button" class="btn btn-primary" data-dismiss="modal" [style.height]="'35px'" (click)="accountStatus_.modalBatch = false">Aceptar</button>
        </div>
  </div>
  `,
})
export class AccountStatusComponent {
  fileName: string;
  uploadPercent: Observable<{}>;
  isUploading: boolean;
  uploadedFiles$: Observable<any>;
  resumenBatch = this.accountStatus_.resumenBatch;
  modalBatch = false;

  constructor(private modal: ModalService, private storage: Storage, public accountStatus_: AccountStatusService, private subResourceService: SubResourceService<any>) {
    this.isUploading = false;
    this.uploadedFiles$ = accountStatus_.accountStatusData_.asObservable();
  }

  updateAccountStatusFile(file: File) {
    if (this.isUploading) {
      this.modal.warning(
        "Carga en proceso",
        "Favor de esperar la carga del archivo actual"
      );
      return;
    }
    this.isUploaded(`account-status/${file.name}`).then((result) => {
      if (result) {
        this.modal.warning("", "Este archivo ha sido cargado anteriormente");
        return;
      }
      this.upload("account-status", file.name, file).then(() => {
        this.accountStatus_.save(file.name).then( () => this.accountStatus_.getAll())
        this.isUploading = false;
        this.modal.success("Éxito", "Se cargo el archivo correctamente");
      });
    });
  }

  process(key: string) {
    this.accountStatus_.process(key).then( result =>{
      this.modal.info('Comienza procesamiento', 'El contenido del archivo estará disponible al contar con estado: PROCESADO');
    })
  }

  private async isUploaded(path: string): Promise<boolean> {
    const docRef = ref(this.storage, path);
    try {
      await getDownloadURL(docRef);
      return true;
    } catch (error) {
      console.log({error});
      return false;
    }
  }

  private async upload(
    folder: string,
    name: string,
    file: File | null
  ): Promise<string> {
    if (!file || this.isUploading) return;
    this.isUploading = true;
    const path = `${folder}/${name}`;
    try {
      const storageRef = ref(this.storage, path);
      const task = uploadBytesResumable(storageRef, file);
      this.uploadPercent = percentage(task);
      this.fileName = name;
      await task;
      const url = await getDownloadURL(storageRef);
      return url;
    } catch (error: any) {
      console.error(error);
    }
  }

  // Funcion que permite realizar subida de estados de cuenta en admin sin fireBase... Hay dos funciones anteriores que no estan correctas. No se eliminaron esas funciones para mantenerlas por si acaso trae inconvenientes
  subirCargaBatch(ev) {
    if (ev.target.files[0] !== null) {
      console.log(ev.target.files[0]);
      const imgBlob = new Blob([ev.target.files[0]], {
        type: ev.target.files[0].type,
      });
      let formData: FormData = new FormData();
      formData.append("file", imgBlob, ev.target.files[0].name);
      this.subResourceService
        .readPostMultipart(CargaBatchVariable.POST_BATCH, formData)
        .subscribe(
          (response: any) => {
            console.log(response);
            if (response.processStatus) {
              swal("Éxito", response.mensaje, "success");
              this.subResourceService
                .read(CargaBatchVariable.RESUMEN_BATCH, {
                  idCarga: response.id,
                })
                .subscribe(
                  (data) => {
                    console.log(data);
                    if (data.mensaje === "Operación fallida") {
                      swal("Error", data.mensaje, "error");
                    } else {
                      this.modalBatch = true;
                      this.resumenBatch = data;
                    }
                  },
                  (error) => {
                    swal('Error', error.meesage, 'error');
                  }
                );
            } else {
              swal("Error", response.mensaje, "error");
            }
          },
          (error) => {
            swal('Error', error, 'error');
          }
        );
    }
    ev.target.value = null;
  }
}
