import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../../interface/auth-user/auth';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { URLUtilities } from '../../static/variables/url/URLUtilities';
import { map } from 'rxjs/operators';
import { RoutingUtilities } from '../../Util/routing/routing-utilities';
import { Router, ActivatedRoute } from '@angular/router';
import { Menu } from '../../interface/menu/mat-panel-menu';
import { UsuarioAcceso, GlobalVariable } from '../../static/variables/url/URLImages';
import { Location } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService{
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  userApp : any;


  constructor(private httpClient: HttpClient, private location : Location, private router: Router, private activatedRoute: ActivatedRoute) {
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
          }else if(user.authorities[0].authority == 'ROLE_PUEBLA'){
            localStorage.setItem('currentUserPuebla', JSON.stringify(user));
          }else if(user.authorities[0].authority == 'ROLE_FUNACOT'){
            localStorage.setItem('currentUserFunacot', JSON.stringify(user));
          }
          this.currentUserSubject.next(response);
        }
        return user;
      }));
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }
  logout(router: Router, activatedRouter: ActivatedRoute, validation : boolean) {
    this.httpClient.get(URLUtilities.getLogout(), {}).subscribe(() => {
      this.removeCredentials(router, activatedRouter, validation);
    });
  }
  removeCredentials(router: Router, activatedRouter: ActivatedRoute, validation : boolean): void {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentUserAdmin');
    localStorage.removeItem('currentUserComercial');
    localStorage.removeItem('currentUserSiniestros');
    localStorage.removeItem('currentUserContabilidad');
    localStorage.removeItem('currentUserPuebla');
    localStorage.removeItem('currentUserFunacot');
    this.currentUserSubject.next(null);
    if (validation) {
      this.router.navigate(['login']);
      return
    }else{
      RoutingUtilities.goToComponent(router, activatedRouter, '../' + URLUtilities.getLogin(), { logout: 'true' });
    }

  }
  getMenu(): Observable<Array<Menu>> {
    return this.httpClient.get<Array<Menu>>(URLUtilities.getMenuOptions(), {});
  }

  validacionUser = () => {
    this.logout( this.router, this.activatedRoute, true )
  }

  validacionUserPuebla = () => {
    let puebla = JSON.parse(localStorage.getItem("currentUserPuebla"));
    if (puebla === null) {
      this.logout( this.router, this.activatedRoute, true )
    }
  }

  validacionUserFunacot = () => {
    let funacot = JSON.parse(localStorage.getItem("currentUserFunacot"));
    if (funacot === null) {
      this.logout( this.router, this.activatedRoute, true )
    }
  }

  validacionAdmin = () => {
    let admin = JSON.parse(localStorage.getItem("currentUserAdmin"));
    if(admin === null  ){
      this.logout( this.router, this.activatedRoute, true )
    }
  }





}
