import { Injectable } from '@angular/core';
import { RouterStateSnapshot, ActivatedRouteSnapshot, CanActivate, Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../authentication-service/authentication.service';
import { URLUtilities } from '../../static/variables/url/URLUtilities';
import { HttpClient } from '@angular/common/http';
import { LocationStrategy } from '@angular/common';
import { Observable } from 'rxjs';
import { GlobalVariable } from '../../static/variables/url/URLImages';
const URL = GlobalVariable.BASE_URL_API;
const LOGOUT = 'logout';
const LOGIN = 'login';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private authenticationService: AuthenticationService, private http: HttpClient,
              private locationStrategy: LocationStrategy) { }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser && currentUser != null) {
      const url = URL.concat(state.url.substring(1, state.url.length));
      if (!(url.search(LOGIN) > 0 || url.search(LOGOUT) > 0)) {
        return true;
      }
      return true;
    }
    //this.router.navigate([URLUtilities.getLogin()], { queryParams: { error: true } });
    return true;
  }

}

