import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable, Inject } from "@angular/core";
import { Observable } from "rxjs";
import { Concept } from "../interfaces/concept.interface";
import { GlobalVariable, ObtencionCatalogos } from "src/app/core/static/variables/url/URLImages";
import { SubResourceService } from "src/app/core/services/service-crud-operations/sub-resource.service";

@Injectable({ providedIn: "root" })
export class ConceptsService {

  dependenciesUrl = '/catalogos';
  private url = GlobalVariable.URL_SERVICES;

  constructor(private httpClient: HttpClient,
    @Inject("ServiceResource")
    private subResourceService: SubResourceService<any>) { }


   getConcepts(): Observable<Concept[]> {
    return this.subResourceService.list(ObtencionCatalogos.GET_CONCEPTOS,'' ,'');
  }

  get concepts(): Observable<Concept[]> {
    return null;
  }

  updateConcept(id : number, descripcion : string) {
    const parametross = new HttpParams().set( 'id', id ).set('descripcion', descripcion);
      return this.httpClient.post(`${this.url}${this.dependenciesUrl}/postActualizaConceptoDesc`, parametross).subscribe( (resp : any) => { });
  }

  deleteConcept(id : number, status : number){
    const parametross = new HttpParams().set( 'id', id ).set('status', status);
      return this.httpClient.post(`${this.url}${this.dependenciesUrl}/postActualizaConceptoStatus`, parametross).subscribe( (resp : any) => { });
  }

  newConcept( descripcion : string ){
    const parametross = new HttpParams().set( 'descripcion', descripcion )
    return this.httpClient.post(`${this.url}${this.dependenciesUrl}/posInsertConcepto`, parametross).subscribe( (resp : any) => { });
  }
}
