import { Component, OnInit, OnChanges, EventEmitter, Input, Output, SimpleChanges, Inject } from '@angular/core';
import { UsuarioAcceso, FormUsuarioValid, UserInserteOrUpdate } from 'src/app/core/interface/user/user';

import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
import swal from 'sweetalert2';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { SubResourceService } from '../../../core/services/service-crud-operations/sub-resource.service';
import { EmpleadoVariable } from '../../../core/static/variables/url/URLImages';

@Component({
  selector: 'app-create-or-update-user-form-template',
  templateUrl: './create-or-update-user-form-template.component.html',
  styleUrls: ['./create-or-update-user-form-template.component.css']
})
export class CreateOrUpdateUserFormTemplateComponent implements OnInit, OnChanges {
  @Input() title = 'Crear Usuario';
  @Output() userEmmit = new EventEmitter<UsuarioAcceso>();
  @Input() user: UsuarioAcceso;
  @Input() menuName = '';
  @Input() showRoles = false;
  @Input() options: SelectMenu[];
  @Input() currentOption: SelectMenu;
  @Input() disabled = false;
  @Input() hideCreateButton = false;
  @Input() showSwalError = true;
  currentOptionTipo: SelectMenu[];
  form: FormUsuarioValid = {
    btnOptions: {
      btnName: '',
      disabled: true,
      opt: 'A'
    },
    disabeButton: true,
    disableDiv: false,
    messageError: ''

  };
  btnOptions: UserInserteOrUpdate = Smartwfm.getBtnUserOptions(false, false);
  nombreUsuario = {
    placeholder: 'login.btn.usuario',
    messageValidInput: 'El usuario es invalido',
    messageRequired: 'El usuario es obligatorio',
    hint: 'Agregue un nombre de usuario',
    type: 'text',
    id: 'usrLogin',
    width: '400px'
  };
  password = {
    placeholder: 'login.btn.password',
    messageValidInput: 'La contraseña es invalida',
    messageRequired: 'La contraseña es obligatoria',
    hint: 'Agregue una contraseña',
    type: 'password',
    id: 'pssLogin',
    width: '370px'
  };
  passwordConfirm = {
    placeholder: 'Confirma la contraseña',
    messageValidInput: 'Las contraseñas no coinciden',
    messageRequired: 'Debes de confirmar la contraseña',
    hint: 'Confirma la contraseña',
    type: 'password',
    width: '400px'
  };
  isUpdateUser = false;
  passwordValue = '';
  constructor(@Inject('ServiceResource') private subResourceService: SubResourceService<any>) { }

  ngOnInit() {
    this.validUser();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.validUser();
  }
  private validUser() {
    if (!this.user) {
      this.user = {
        usuario: '',
        pwd: ''
      };
    } else if (this.user.usuario.length > 0) {
      this.passwordValue = this.user.pwd;
      this.isUpdateUser = true;
      this.btnOptions = Smartwfm.getBtnUserOptions(true, false);
    }
  }

  getVal(value: string, jsonPropery: string) {
    this.user[jsonPropery] = value;
    this.validForm(this.showRoles);
    this.doTask();
  }
  private validForm(withRoles: boolean) {
    const userHasRol = !Smartwfm.valuesNotNull(['rol.nombre'], this.user, 1);
    this.form = Smartwfm.confirmPasswordAndUser(this.passwordValue, this.user.pwd, this.user.usuario,
      this.isUpdateUser, withRoles, userHasRol);
    this.btnOptions = this.form.btnOptions;

  }

  getPasswordConfirm(password: string) {
    this.passwordValue = password;
    this.validForm(this.showRoles);
    this.doTask();
  }
  doTask() {
    if (!this.form.disabeButton) {
      this.userEmmit.emit(this.user);
    } else if (this.showSwalError) {
      swal('Error', 'Asegúrese de que las contraseñas sean iguales y que el usuario no este vacio', 'error');
    } else {
      this.userEmmit.emit(null);
    }
  }
  getOption(option: SelectMenu, jsonPropery: string) {
    const res = !(this.showRoles && option !== undefined && option !== null);
    this.user[jsonPropery] = option.name;
    this.user.rolAcceso = option.extras;
    this.validForm(res);
    this.doTask();
  }
  remove() {
  }

}
