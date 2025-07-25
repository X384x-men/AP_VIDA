import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable, Inject } from "@angular/core";
import { Observable } from "rxjs";
import { AdminUnit } from "../interfaces/admin-unit.interface";
import { GlobalVariable, ObtencionCatalogos } from "src/app/core/static/variables/url/URLImages";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";

@Injectable({ providedIn: "root" })
  export class AdminUnitsService {

    dependenciesUrl = '/catalogos';
    private url = GlobalVariable.URL_SERVICES;

    constructor(private httpClient: HttpClient,
      @Inject("ServiceResource")
      private subResourceService: SubResourceService<any>) {}

     getAdminUnits(): Observable<AdminUnit[]> {
        return this.subResourceService.list(ObtencionCatalogos.GET_CATALOGO_UNIDADES_ADMINISTRATIVAS,'' ,'');
    }

    get adminUnits(): Observable<AdminUnit[]> {
      return  null;
    }

    updateAdminUnit(id : number, descripcion : string){
      const parametross = new HttpParams().set( 'id', id ).set('descripcion', descripcion);
      return this.httpClient.post(`${this.url}${this.dependenciesUrl}/postActualizaUnidadDesc`, parametross).subscribe( (resp : any) => {  });
    }

    deleteAdminUnit(id : number ,status: number){
      const parametross = new HttpParams().set( 'id', id ).set('status', status);
      return this.httpClient.post(`${this.url}${this.dependenciesUrl}/postActualizaUnidadStatus`, parametross).subscribe( (resp : any) => { });
    }

    newAdminUnit(descripcion : string){
      const parametross = new HttpParams().set( 'descripcion', descripcion )
      return this.httpClient.post(`${this.url}${this.dependenciesUrl}/postInsertaUnidad`, parametross).subscribe( (resp : any) => { });
    }

  }
