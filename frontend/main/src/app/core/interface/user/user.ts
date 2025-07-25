import { Roles } from '../Roles';

export interface UsuarioAcceso {
  id?: number;
  usuario?: string;
  pwd?: string;
  rolAcceso?: Roles;
  pwdConf?: string;
}
export interface FormUsuarioValid {
  disabeButton: boolean;
  messageError: string;
  btnOptions: UserInserteOrUpdate;
  disableDiv: boolean;
}
export interface UserInserteOrUpdate {
  disabled: boolean;
  opt: string;
  btnName: string;
}
