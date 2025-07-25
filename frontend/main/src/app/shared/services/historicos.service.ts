import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalVariable } from 'src/app/core/static/variables/url/URLImages';

@Injectable({
  providedIn: 'root'
})
export class HistoricosService {

  //¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡CLAUSUARADO DE MOMENTO!!!!!!!!!!!!!!!!


  // private url = GlobalVariable.URL_SERVICES + '/solicitud';

  // constructor( private httpClient: HttpClient ) { }

  // reAsignarSolicitud(idSolicitud : number, rfc : string) {
  //   const parametross = new HttpParams().set( 'idSolicitud', idSolicitud ).set('RFCEmpleado', rfc);
  //     return this.httpClient.post(`${this.url}/asignaSolicitud`, parametross);
  // }
}
