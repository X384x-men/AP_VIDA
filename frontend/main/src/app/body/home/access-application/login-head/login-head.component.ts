import { Component, OnInit } from '@angular/core';
import { HeadComponent } from 'src/app/body/head/head.component';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Store } from '@ngrx/store';
import * as languajeState from '../../../../reducers';
import { nameApp } from 'src/app/core/static/variables/url/URLImages';


@Component({
  selector: 'app-login-head',
  templateUrl: './login-head.component.html',
  styleUrls: ['./login-head.component.css']
})
export class LoginHeadComponent extends HeadComponent implements OnInit {

  context = '/'+nameApp+'/angular'; 

  constructor(private authentication: AuthenticationService, private r: Router, private activated: ActivatedRoute,
              private translateService: TranslateService, private s: Store<languajeState.Language>) {
    super(authentication, r, activated, translateService, s);
  }

  ngOnInit() {
  }

}
