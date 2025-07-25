import { Injectable } from "@angular/core";
import { Database, list, query, push, onValue, update } from "@angular/fire/database";
import { DatabaseReference, ref } from "@firebase/database";
import { Observable, Subject } from "rxjs";
import { filter } from "rxjs/operators";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { CargaBatchVariable } from "src/app/core/static/variables/url/URLImages";
import { URLUtilities } from "src/app/core/static/variables/url/URLUtilities";
import { now } from "src/app/core/Util/date";
import swal from "sweetalert2";

@Injectable({providedIn: 'root'})
export class AccountStatusService {
  accountStatusRef: DatabaseReference;
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

  constructor(private db: Database, private subResourceService: SubResourceService<any>) {
    this.accountStatusRef = ref(db, '/accountStatusFiles')
    this.accountStatusData_ = new Subject();
    this.getAll();
  }

  getAll(): void{
    onValue(this.accountStatusRef, (snapshot) => {
      const array = Object.entries(snapshot.val()).map(([key, values]) => {
        return Object.assign({}, values, {key});
      });
      this.accountStatusData_.next(array.reverse());
    });
  }

  async fileInProcess(): Promise<any> {
    try {
      const filesInProcess = await this.accountStatusData_.pipe(
        filter( file => file.status == 'en proceso')
      ).toPromise();
      return filesInProcess;
    } catch {
      return false;
    }
  }

  process(id: string): Promise<any> {
    this.subResourceService
    .create(id, URLUtilities.processAccountFile())
    return this.updateStatus(id, 'en proceso');
  }

  async updateStatus(key: string, status: string): Promise<any> {
    try {
      await update(ref(this.db, 'accountStatusFiles/' + key), {status});
      return true;
    } catch {
      return false;
    }
  }

  async save(filename: string): Promise<any> {
    try {
      await push(this.accountStatusRef, {filename, status: 'pendiente', uploadDate: now });
      return true;
    } catch {
      return false;
    }
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
