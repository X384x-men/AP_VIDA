import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../../interface/auth-user/auth';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { URLUtilities } from '../../static/variables/url/URLUtilities';
import { map } from 'rxjs/operators';
import { RoutingUtilities } from '../../Util/routing/routing-utilities';
import { Router, ActivatedRoute } from '@angular/router';
import { Menu } from '../../interface/menu/mat-panel-menu';
import { UsuarioAcceso, GlobalVariable } from '../../static/variables/url/URLImages';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  constructor(private httpClient: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }
  auth(user: User): Observable<any> {
    // return this.httpClient.post(URLUtilities.LoginRequest(), user);
    return this.httpClient.post<any>(URLUtilities.LoginRequest(), user)
      .pipe(map(response => {
        // login successful if there's a jwt token in the response
        if (user && response.token) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          if (response.authorities) {
            user.authorities = response.authorities;
          }
          user.password = undefined;
          if(user.authorities[0].authority == 'ROLE_USRAP'){
            localStorage.setItem('currentUser', JSON.stringify(user));
          } else if(user.authorities[0].authority == 'ROLE_ADMAP'){
            localStorage.setItem('currentUserAdmin', JSON.stringify(user));
          } else if(user.authorities[0].authority == 'ROLE_ACOME'){
            localStorage.setItem('currentUserComercial', JSON.stringify(user));
          }else if(user.authorities[0].authority == 'ROLE_ASINI'){
            localStorage.setItem('currentUserSiniestros', JSON.stringify(user));
          }else if(user.authorities[0].authority == 'ROLE_ACONT'){
            localStorage.setItem('currentUserContabilidad', JSON.stringify(user));
          }
          this.currentUserSubject.next(response);
        }
        return user;
      }));
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }
  logout(router: Router, activatedRouter: ActivatedRoute) {
    this.httpClient.get(URLUtilities.getLogout(), {}).subscribe(() => {
      this.removeCredentials(router, activatedRouter);
    });
  }
  removeCredentials(router: Router, activatedRouter: ActivatedRoute): void {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentUserAdmin');
    localStorage.removeItem('currentUserComercial');
    localStorage.removeItem('currentUserSiniestros');
    localStorage.removeItem('currentUserContabilidad');
    this.currentUserSubject.next(null);
    RoutingUtilities.goToComponent(router, activatedRouter, '../' + URLUtilities.getLogin(), { logout: 'true' });
  }
  getMenu(): Observable<Array<Menu>> {
    return this.httpClient.get<Array<Menu>>(URLUtilities.getMenuOptions(), {});
  }



}
