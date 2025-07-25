import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable, Inject } from '@angular/core';
import { Observable } from "rxjs";
import { map} from 'rxjs/operators';
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";
import { GlobalVariable, ObtencionCatalogos } from "src/app/core/static/variables/url/URLImages";
import { Concept } from "../interfaces/concept.interface";
import { Resource } from "src/app/core/class/http-resource/resource";

@Injectable({ providedIn: "root" })
  export class BatchSolicitudes<T extends Resource> {

    solicitudesUrl = '/batch';
    private url = GlobalVariable.URL_SERVICES;

    constructor(private httpClient: HttpClient,
       @Inject("ServiceResource")
       private subResourceService: SubResourceService<any> ) {}

    getConcepts(): Observable<Concept[]> {
      return this.subResourceService.list(ObtencionCatalogos.GET_CONCEPTOS,'' ,'');
    }

    get concepts(): Observable<Concept[]> {
      return null;
    }

    private convertData(data: any, property?: string): T[] {
      if (data && data != null) {
        if (property && property != null && data[property]) {
          return data[property].map(item => data = item);
        } else if (data instanceof Array) {
          return data.map(item => data = item);
        }
      }
      return new Array<T>();
    }


    batchSolicitud(from : any, to: any, rfc : string, property? : any){

      const parametross = {
        from : from,
        to : to,
        rfc : rfc,
      }

      return this.httpClient.get(`${this.url}${this.solicitudesUrl}/descargaSolicitudesCSV`, {
        params : parametross
      }).pipe(map((data: any) => this.convertData(data, property)));
    }

  }
