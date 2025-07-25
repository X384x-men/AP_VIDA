import { Injectable } from "@angular/core";
import { Subject } from "rxjs";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { CargaBatchVariable } from "src/app/core/static/variables/url/URLImages";
import swal from "sweetalert2";

@Injectable({providedIn: 'root'})
export class AccountStatusService {
  accountStatusData_: Subject<any>;
  modalBatch = false;
  resumenBatch = {
    nombreArchivo: "",
    totalRegistros: "",
    registrosRechazados: "",
    regristrosValidos: "",
    fechaCarga: "",
    tipo: "",
    id: 0,
    batchInfo: "",
    processStatus: "",
    mensaje: "",
  };

  constructor(
     private subResourceService: SubResourceService<any>) {
    this.accountStatusData_ = new Subject();
  }

  /**
   * Método anterior
   */
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
                    //swal('Error', error.meesage, 'error');
                  }
                );
            } else {
              swal("Error", response.mensaje, "error");
            }
          },
          (error) => {
            //swal('Error', error.meesage, 'error');
          }
        );
    }
    ev.target.value = null;
  }
}
