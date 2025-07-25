import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsuarioAcceso } from '../../interface/user/user';
import { URLUtilities } from '../../static/variables/url/URLUtilities';
import swal from 'sweetalert2';
import { EventMessage } from '../../interface/message/event-message';

@Injectable({
  providedIn: 'root'
})
export class UsuarioAccesoService {

  constructor(private httpClient: HttpClient) { }
  getUsuariosByNumeroCuadrilla(cuadrilla: string): Observable<Array<UsuarioAcceso>> {
    return this.httpClient.get<Array<UsuarioAcceso>>(URLUtilities.getUsuariosByNumeroCuadrilla() + cuadrilla, {});
  }
  getUsuarioByNumeroCuadrilla(cuadrilla: string, sec: number): Observable<UsuarioAcceso> {
    return this.httpClient.get<UsuarioAcceso>(URLUtilities.getUsuarioByNumeroCuadrilla() + cuadrilla + '&&sec=' + sec, {});
  }

  getUsuarioByUserType(type: string): Observable<Array<UsuarioAcceso>> {
    return this.httpClient.get<Array<UsuarioAcceso>>(URLUtilities.getUsuarioByType() + type, {});
  }
  findUser(user: UsuarioAcceso): Observable<UsuarioAcceso> {
    return this.httpClient.post<UsuarioAcceso>(URLUtilities.postFindUser(), user);
  }
  update(usuario: UsuarioAcceso, cuadrilla: string, option: number) {
    const extras = cuadrilla && cuadrilla !== null && cuadrilla.length > 0 ? '&&cuadrilla=' + cuadrilla : '';
    this.httpClient.put<EventMessage>(URLUtilities.updateUsuarioAcceso() + option + extras, usuario)
      .subscribe(response => { swal('Exitoso', response.message, 'success'); });
  }
  createUsuarioAcceso(usuario: UsuarioAcceso, type: string): void {
    this.httpClient.post<EventMessage>(URLUtilities.createUsuarioAcceso().concat(type), usuario)
      .subscribe(response => { swal('Exitoso', response.message, 'success'); },
      );
  }
}
