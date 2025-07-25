import { Component, OnInit, Inject } from '@angular/core';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';
import { Router, ActivatedRoute } from '@angular/router';
import { GlobalVariable, nameApp, UsuarioAcceso } from 'src/app/core/static/variables/url/URLImages';
import { TranslateService } from '@ngx-translate/core';
import { LanguageTheme } from 'src/app/core/interface/app-state/app-state';
import { Subject, Observable } from 'rxjs';

import { Store } from '@ngrx/store';
import * as languajeState from '../../reducers';

import * as LanguageActions from '../../core/actions/app-actions/app-actions';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { share } from 'rxjs/operators';

@Component({
  selector: 'app-head',
  templateUrl: './head.component.html',
  styleUrls: ['./head.component.css']
})
export class HeadComponent implements OnInit {
  integraIcon = GlobalVariable.MAIN_LOGO_INTEGRA;
  lang = new Subject<string>();
  lang$: Observable<LanguageTheme>;
  detail: string;
  user: string;
  rol: string = '';
  context = '/'+nameApp+'/';
  login: boolean = true;
  userName: any;
  nameUser:Observable<any>;

  currentUser : any;


  constructor(private authencationService: AuthenticationService, private router: Router, private activatedRoute: ActivatedRoute, private translate: TranslateService, private store: Store<languajeState.Language>, @Inject('ServiceResource') private subResourceService ?: SubResourceService<any>) {
  }

  ngOnInit() {
    //this.userName = JSON.parse(localStorage.getItem('nameUser'));
    this.subResourceService.getInfoUser();
    this.nameUser = this.subResourceService.headInfo.pipe(share());
    this.lang$ = this.store.select(languajeState.getActiveLanguaje);
    this.lang$.subscribe(lang => {
      this.translate.use(lang.language);
    });

      let allUsuarios = [];
      allUsuarios.push(JSON.parse(localStorage.getItem("currentUser")))
      allUsuarios.push(JSON.parse(localStorage.getItem("currentUserComercial")));
      allUsuarios.push(JSON.parse(localStorage.getItem("currentUserSiniestros")));
      allUsuarios.push(JSON.parse(localStorage.getItem("currentUserContabilidad")));
      allUsuarios.push(JSON.parse(localStorage.getItem("currentUserAdmin")));
      allUsuarios.push(JSON.parse(localStorage.getItem('idCuenta')));
      allUsuarios.push(JSON.parse(localStorage.getItem("currentUserPuebla")));
      allUsuarios.push(JSON.parse(localStorage.getItem("currentUserFunacot")));
      this.currentUser = allUsuarios.find( (value) => value !== null );
      this.rol = this.currentUser.authorities[0]['authority'];
      this.user = this.authencationService.currentUserValue ? this.authencationService.currentUserValue.username.toUpperCase() : this.currentUser ? this.currentUser.username.toUpperCase() : '';
      this.infoUser(this.user)
  }

  logout(): void {
    this.authencationService.logout(this.router, this.activatedRoute, false);
    //localStorage.setItem('idRama', null);
    localStorage.clear();
    localStorage.setItem('idProyecto', null);
    localStorage.setItem('idOrganizacion', null);
    localStorage.setItem('idCuenta', null);
  }

  useLanguage(language: string) {
    this.store.dispatch(new LanguageActions.ChangeLanguage(language));
    this.translate.use(language);
  }

  infoUser (rfc){
    this.subResourceService.read(UsuarioAcceso.USUARIO_NOMBRE_AP, {user: rfc })
    .subscribe( data=>{
      localStorage.setItem('nameUserAP', JSON.stringify(data.message));
      this.userName = data.message
    }, error=>{
      console.log(error);
    });
  }
}
