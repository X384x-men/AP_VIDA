import { Component, Inject } from '@angular/core';
import { UsuarioAcceso } from 'src/app/core/interface/user/user';
import { Router, ActivatedRoute } from '@angular/router';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { EmailVariable } from 'src/app/core/static/variables/url/URLImages';
import swal from 'sweetalert2';
import { RoutingUtilities } from 'src/app/core/Util/routing/routing-utilities';
import { UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-main-change-password',
  templateUrl: './main-change-password.component.html',
  styleUrls: ['./main-change-password.component.css']
})
export class MainChangePasswordComponent {
  recoveryForm: UntypedFormGroup;
  code: any;

  constructor(private router: Router,private _activatedRoute: ActivatedRoute, @Inject('ServiceResource') private subResourceService: SubResourceService<any>) {
    this.code = RoutingUtilities.getParamsFromUrl(this._activatedRoute, 'code') || 0;
    this.recoveryForm = new UntypedFormGroup({
      email: new UntypedFormControl('', Validators.email),
      rfc: new UntypedFormControl(''),
      psw: new UntypedFormControl(''),
      confirmpsw: new UntypedFormControl(''),
      isValid: new UntypedFormControl(true)
    })
  }

  getUsuario(usuario: UsuarioAcceso) {
    if (usuario) this.router.navigate(['/dashboardAP']);
  }

  end(){
    this.router.navigate(['/login']);
  }

  get hasEmailAndRfc(): boolean {
    const {email, rfc} = this.recoveryForm.value;
    return email != '' && rfc != '';
  }

  get isPasswordValid(): boolean {
    const {psw, confirmpsw } = this.recoveryForm.value;
    return psw != '' && psw == confirmpsw;
  }

  guardar(){
    if(this.recoveryForm.invalid) return;
    this.subResourceService.create( this.recoveryForm.value,  EmailVariable.SEND_EMAIL)
      .subscribe(data=>{
        console.log(data);
        swal('Notificación', data.message, 'success').then(()=>{
          //this.router.navigate(['/login']);
        });
      }, error=>{
        console.log(error);
        swal('Alerta', error, 'info').then(()=>{
        });
      }
    );
  }

  resetPwd(){
    const { psw } = this.recoveryForm.value;
    if(!this.isPasswordValid){ swal('Verifique las contraseñas')}
    this.subResourceService.read(EmailVariable.RESET_PASSWORD, {code: this.code, pw: psw})
    .subscribe(data=>{
      console.log(data);
      swal('Notificación', data.message, 'success').then(()=>{
        this.router.navigate(['/login']);
      });
    }, error=>{
      console.log(error);
      swal('Alerta', error, 'info').then(()=>{
        this.router.navigate(['/login']);
      });
    });
  }

}
