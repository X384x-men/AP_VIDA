import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable,Inject } from "@angular/core";
import { Observable } from "rxjs";
import { Dependency } from "../interfaces/dependency.interface";
import { GlobalVariable, ObtencionCatalogos } from "src/app/core/static/variables/url/URLImages";
 import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";

@Injectable({ providedIn: "root" })
export class DependenciesService {
  dependenciesUrl = '/catalogos';
  private url = GlobalVariable.URL_SERVICES;

  constructor(private httpClient: HttpClient,
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>) { }


  getDependencies(): Observable<Dependency[]>{
    return this.subResourceService.list(ObtencionCatalogos.GET_CATALOGO_DEPENDENCIAS,'' ,'');
  }


  get dependencies(): Observable<Dependency[]> {
    return  null;
  }

  updateDependency(descripcion : string, id : number){
    const parametross = new HttpParams().set( 'id', id ).set('descripcion', descripcion);
    return this.httpClient.post(`${this.url}${this.dependenciesUrl}/postActualizaDependenciaDesc`, parametross).subscribe( (resp : any) => { });
  }

  deleteDependency(status : any, id : any ){
    const parametross = new HttpParams().set( 'id', id ).set('status', status);
    return this.httpClient.post(`${this.url}${this.dependenciesUrl}/postActualizaDependenciaStatus`, parametross).subscribe( (resp : any) => { });
  }

  newDependency(descripcion : any){
    const parametross = new HttpParams().set( 'descripcion', descripcion )
    return this.httpClient.post(`${this.url}${this.dependenciesUrl}/postInsertDependencia`, parametross).subscribe( (resp : any) => { });
  }

}
