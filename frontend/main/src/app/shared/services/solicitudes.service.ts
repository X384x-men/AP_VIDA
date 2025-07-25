import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable,Inject } from "@angular/core";
import { Observable } from "rxjs";
import { Dependency } from "../interfaces/dependency.interface";
import { GlobalVariable, ObtencionCatalogos } from "src/app/core/static/variables/url/URLImages";
 import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";

@Injectable({ providedIn: "root" })
export class SolicitudesServices {
  private url = GlobalVariable.URL_SERVICES + '/solicitud';

  constructor(private httpClient: HttpClient,
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>) { }

    reAsignarSolicitud(idSolicitud : number, rfc : string) {
      const parametross = new HttpParams().set( 'idSolicitud', idSolicitud ).set('RFCEmpleado', rfc);
        return this.httpClient.post(`${this.url}/asignaSolicitud`, parametross);
    }

}
