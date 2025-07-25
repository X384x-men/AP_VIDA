import { Component, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-recover-pwd',
  templateUrl: './recover-pwd.component.html',
  styleUrls: ['./recover-pwd.component.css']
})
export class RecoverPwdComponent {
  @Input() recoveryForm: UntypedFormGroup;
  @Input() code: any;

  fieldTextType: boolean = false;

  opt: any;

  constructor(private _router: Router, private _activatedRoute: ActivatedRoute) { }

  mostrarContrasena(value){
    this.fieldTextType = value;
  }

}
