import { Component, OnInit } from "@angular/core";
import { UntypedFormGroup, UntypedFormBuilder, Validators, FormControl, AbstractControl } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthenticationService } from "src/app/core/services/authentication-service/authentication.service";
import { first } from "rxjs/operators";
import { GlobalVariable } from "src/app/core/static/variables/url/URLImages";
import { TranslateService } from "@ngx-translate/core";
import { Store } from "@ngrx/store";

import * as languajeState from "../../../../reducers";

import { Subject, Observable } from "rxjs";
import { LanguageTheme } from "src/app/core/interface/app-state/app-state";
import { AUTH } from "src/app/core/Util/constants/auth-routes";
import {
  AVISO_DE_PRIVACIDAD,
  DERECHOS_BASICOS_DE_ASEGURADOS,
} from "src/app/core/Util/constants/documents";
import { ModalService } from "src/app/shared/services/modal.service";
import swal from "sweetalert2";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  lang = new Subject<string>();
  lang$: Observable<LanguageTheme>;
  integraIcon = GlobalVariable.MAIN_LOGO_INTEGRA;
  loginForm: UntypedFormGroup;
  returnUrl: string;
  btnOptions = {
    disabled: undefined,
    btnName: undefined,
    opt: undefined,
  };
  style = {
    width: "100%",
    "max-width": "400px",
  };
  fieldTextType: string = 'password';
  AVISO_DE_PRIVACIDAD = AVISO_DE_PRIVACIDAD;
  DERECHOS_BASICOS_DE_ASEGURADOS = DERECHOS_BASICOS_DE_ASEGURADOS;

  constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private translate: TranslateService,
    private store: Store<languajeState.Language>,
    private modal: ModalService
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(["/angular/dashboardAP"]);
       this.router.navigate(["/angular/reporte"]);
    }
  }

  ngOnInit() {
    this.lang$ = this.store.select(languajeState.getActiveLanguaje);
    this.lang$.subscribe((lang) => {
      this.translate.use(lang.language);
    });
    const {required, max, min} = Validators
    this.loginForm = this.formBuilder.group({
      username: ["", [required, max(13), min(13)]],
      password: ["", required],
    });
    // const username: AbstractControl = this.loginForm.get('username');
    // username.valueChanges.subscribe( () => {
    //   console.log(username.value)
    //   username.patchValue(username.value, {emitEvent: false})
    // })

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams["returnUrl"] || "";
  }
  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  submitLogin() {
    if (this.loginForm.get("username").invalid) {
      swal('Alerta', 'Favor de introducir su RFC correcto en el campo Usuario', 'warning')
      return;
    }
    if (this.loginForm.get("password").invalid) {
      swal('Alerta', 'Favor de introducir su contraseña', 'warning')
      return;
    }
    this.authenticationService
      .auth(this.loginForm.value)
      .pipe(first())
      .subscribe(() => {
        const data = this.params();
        if (data && data !== null) {
          let user = JSON.parse(localStorage.getItem("currentUser"));
          if (user != null) {
            if (user.username == "GAFA700810G45") {
              document.location.href = "http://www.e-vector.com.mx/ap1Jul.htm";
            } else if (user.username == "MABL910219T18") {
              document.location.href = "http://www.e-vector.com.mx/ap2Jul.htm";
            } else if (user.username == "MEJM551001NH7") {
              document.location.href = "http://www.e-vector.com.mx/ap3Jul.htm";
            } else {
              this.router.navigate([data.url], { queryParams: data.opt });
            }
          } else {
            this.router.navigate([data.url], { queryParams: data.opt });
          }
        } else {
          console.log("error");
        }
      }, error=>{
        swal('Error', error, 'error');
        return;
      });
  }
  private params(): any {
    return AUTH.find(
      (value) =>
        this.authenticationService.currentUserValue.authorities.find(
          (data) => data.authority === value.role
        ) !== undefined
    );
  }

  showPwd($event): void {
    this.fieldTextType = $event.checked ? 'text' : 'password';
  }

}
