import { Injectable } from '@angular/core';
import { Resource } from '../../class/http-resource/resource';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { GlobalVariable, UsuarioAcceso } from '../../static/variables/url/URLImages';

@Injectable({
  providedIn: 'root'
})
export class SubResourceService<T extends Resource> {
  private url = GlobalVariable.URL_SERVICES;
  private proyecto: any = null;
  private msgsource = new BehaviorSubject<string>('');
  headInfo = this.msgsource.asObservable();

  constructor(private httpClient: HttpClient) {}

  public createAny(item: T, soruce: string, urlParams?: any): Observable<any> {
    return this.httpClient.post<T>(`${this.url}/${soruce}`, item, { params: urlParams }).pipe(map((data: any) => data));
  }

  public create(item: T, soruce: string, urlParams?): Observable<T> {
    return this.httpClient.post<T>(`${this.url}/${soruce}` , item, { params: urlParams }).pipe(map((data: any) => data as T
      ));
  }

  public update(item: T, source: string): Observable<any> {
    return this.httpClient
      .put<T>(`${this.url}/${source}`, item)
      .pipe(map(data => data));
  }

  read(source: string, urlParams?: any): Observable<T> {
    return this.httpClient
      .get(`${this.url}/${source}`, { params: urlParams })
      .pipe(map((data: any) => data as T));
  }

  downloadFile(source: string, urlParams?: any): Observable<T>  {
    return this.httpClient
      .get(`${this.url}/${source}`, { params: urlParams})
      .pipe(map((data: any) => data as T ));
  }

  readPost(source: string, item: T): Observable<T> {
    return this.httpClient
      .post(`${this.url}/${source}`, item)
      .pipe(map((data: any) => data as T));
  }

  postList(source: string, item: T, property?: string): Observable<T[]> {
    return this.httpClient
      .post(`${this.url}/${source}`, item)
      .pipe(map((data: any) => this.convertData(data, property)));
  }

  list(source: string, property?: string, urlParams?: string | any, isObject?: boolean): Observable<T[]> {
    return this.httpClient
      .get(`${this.url}/${source}`, { params: urlParams })
      .pipe(map((data: any) => this.convertData(data, property)));
  }


  delete(id: number, source: string) {
    return this.httpClient
      .delete(`${this.url}/${source}/${id}`);
  }

  readPostMultipart(source: string, item: T): Observable<T> {
    return this.httpClient
      .post(`${this.url}/${source}`, item, {headers: {'Content-Type':'multipart/form-data'}})
      .pipe(map((data: any) => data as T));
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

  public updateAny(item: any, source: string, urlParams?: any): Observable<any> {
    return this.httpClient
      .put<T>(`${this.url}/${source}`,
        item, {
        params: urlParams
      })
      .pipe(map(data => data));
  }

  setProyecto(proyecto) {
    this.proyecto = proyecto;
  }

  getProyecto() {
    return this.proyecto;
  }

  getInfoUser(){
    let user1 = JSON.parse(localStorage.getItem('currentUser'));
    let admin = JSON.parse(localStorage.getItem('currentUserAdmin'));
    let comercial = JSON.parse(localStorage.getItem('currentUserComercial'));
    let siniestros = JSON.parse(localStorage.getItem('currentUserSiniestros'));
    let contabilidad = JSON.parse(localStorage.getItem('currentUserContabilidad'));
    let user: any;
    if(admin != null){
      user = admin
    }else if(comercial != null){
      user = comercial;
    }else if(siniestros != null){
      user = siniestros;
    }else if(contabilidad != null){
      user = contabilidad;
    }else if(user1 != null){
      user = user1;
    }
    if(user){
      this.read(UsuarioAcceso.USUARIO_NOMBRE_AP, {user: user.username })
      .subscribe((data:any)=>{
        this.msgsource.next(data.message);
      }, error=>{
        console.log(error);
      });
    }
  }

}
