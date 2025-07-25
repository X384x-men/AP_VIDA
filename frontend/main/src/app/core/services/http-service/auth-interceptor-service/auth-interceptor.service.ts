import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { AuthenticationService } from '../../authentication-service/authentication.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RoutingUtilities } from 'src/app/core/Util/routing/routing-utilities';
import { URLUtilities } from 'src/app/core/static/variables/url/URLUtilities';
import { ModalService } from 'src/app/shared/services/modal.service';
const FORBIDDEN_VIEW = 403;
const UNAUTHORIZED_VIEW = 405;
const UNAUTHORIZED_SECTION = 401;
const SESSION_EXPIRED = 301;
@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private activatedRouter: ActivatedRoute,
    private modal: ModalService
  ) { }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const headersR = new HttpHeaders({ Authorization: 'Basic ' });
    const notClone = request.headers.get('skipInterceptor') == '1' ? true : false;
    request = notClone ? request : request.clone({
      withCredentials: true,
      headers: headersR,
      responseType: 'json',
    });
    return next.handle(request)
      .pipe(
        retry(1),
        catchError((error: HttpErrorResponse) => {
          let errorMessage = '';
          console.log(error);
          if (error.error instanceof ErrorEvent) {
            // Front error
            errorMessage = `Error: ${error.error.message}`;
          } else if (error.status === FORBIDDEN_VIEW) {
            RoutingUtilities.goToComponentNoParams(this.router, this.activatedRouter, URLUtilities.getMainUrl());
            this.modal.danger('Error', error.error.details);
            return throwError(errorMessage);
          }
          else if (error.url.search('logout') > 0) {
            this.modal.danger('Error', 'Debes iniciar sesion')
            this.authenticationService.logout(this.router, this.activatedRouter);
            return throwError(errorMessage);
          } else if (error.error.sucessMessage && error.error.sucessMessage.length > 0) {
            errorMessage = error.error.sucessMessage;
          } else if (UNAUTHORIZED_VIEW === error.status || UNAUTHORIZED_SECTION === error.status) {
            this.authenticationService.logout(this.router, this.activatedRouter);
            const message = this.getMessageError(error);
            errorMessage = !message || message === null ? 'Operacion no autorizada' : message;
          } else if (SESSION_EXPIRED === error.status || error.status === 0){
            this.authenticationService.logout(this.router, this.activatedRouter);
            errorMessage = 'La sesión ha caducado, por favor vuelva a introducir su usuario y contraseña';
          } else {
            // server-side error
            errorMessage = this.getMessageError(error);
          }

          // Realizando una prueba del modal Comentado por fernando
          // this.modal.info('Información', this.setDefaultMessageError(errorMessage));

          return throwError(errorMessage);
        })
      );
  }
  private getMessageError(error: HttpErrorResponse): string {
    return error.error.details || error.error.message || error.error.mensaje;
  }
  private setDefaultMessageError(errorMessage: string) {
    if (errorMessage === undefined || errorMessage === 'undefined' || errorMessage === null || errorMessage.length <= 0) {
      return 'Ha ocurrio un error desconocido, volver a intentar';
    }
    return errorMessage;
  }
}
