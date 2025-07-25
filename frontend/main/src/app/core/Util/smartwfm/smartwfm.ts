import { InputJsonMap } from '../../interface/input-options/input';
import { SelectMenu } from '../../interface/menu/select-menu';
import { DateInfo } from '../../interface/date/locale/fechas';
import { SimpleDate } from '../../interface/date/DateValue';
import _moment from 'moment';
import { Calendar } from '../../static/variables/calendar/calendar.enum';
import { FormUsuarioValid, UserInserteOrUpdate } from '../../interface/user/user';
import { MatPanelMenu, Options } from '../../interface/menu/mat-panel-menu';
import { TableParams } from '../../class/http-resource/http-resource';
import { Authorities } from '../../interface/auth-user/auth';
const UPDATE_USER = 'U';
const CREATE_USER = 'A';
export class Smartwfm {
  public static addIndex(data: Array<any>): Array<any> {
    let index = 0;
    data.forEach(element => {
      element.position = index;
      index += 1;
    });
    return data;
  }
  public static createSelectOptions(obj: Array<any>, pathOpt: string): Array<SelectMenu> {
    const options = new Array<SelectMenu>();
    try {
      if (obj.length > 0 && pathOpt && pathOpt !== null && pathOpt.length > 0) {
        obj.forEach((value, ind) => {
          if (value) {
            options.push({
              index: ind,
              name: this.getObjectProperty(pathOpt, value),
              extras: value
            });
          }
        });
      }else if(obj.length > 0 && pathOpt.length == 0){
        obj.forEach((value, ind) => {
          if (value) {
            options.push({
              index: ind,
              name: value,
              extras: value
            });
          }
        });
      }
    } catch (error) {
    }
    return options;
  }
  /**
   * Remueve elementos en un arreglo, agrega un indice a cada una de los elementos en ese arreglo
   * @param array
   * @param data
   * @returns new Array
   */
  public static removeItemsFromArray(array: Array<any>, data: Array<any>) {
    data.forEach((value) => {
      array = array.filter(item => item !== value);
    });
    return array;
  }
  /**
   * Remueve un elemento en un arreglo, agrega un indice a cada una de los elementos en ese arreglo
   * @param array
   * @param data
   * @returns new Array
   */
  public static removeItemFromArray(array: Array<any>, data: any) {
    array = array.filter(item => item !== data);
    array = this.addIndex(array);
    return array;
  }

  public static addItemToArray(array: Array<any>, data: any) {
    array.push(data);
    array = this.addIndex(array);
    return array;
  }
  public static getObjectProperty(path: string, obj: any) {
    return path.split('.').reduce((prev: any, curr: string): any => {
      return prev ? prev[curr] : null;
    }, obj || self);
  }

  public static setObjectProperty(path: string, obj: any, value: any): any {
    const tot = (path.split('.').length) - 1;
    return path.split('.').reduce((prev: any, curr: string, index: number): any => {
      if (index === tot && prev[curr] !== undefined) {
        prev[curr] = value;
        return obj;
      } else if (!prev[curr]) {
        return obj;
      }
      return prev ? prev[curr] : null;
    }, obj || self);
  }

  public static valuesNotNull(paths: string[], obj: any, minLength: number): boolean {
    let res = true;
    paths.forEach(element => {
      const value = this.getObjectProperty(element, obj);
      if (!(value && value.length >= minLength)) {
        res = false;
        return;
      }
    });
    return res;
  }

  public static getPropertiesInput(obj: InputJsonMap[]): string[] {
    const props: string[] = [];
    obj.forEach(element => {
      if (element.property) {
        props.push(element.property);
      }
    });
    return props;
  }
  public static setImageToOject(object: Array<any>, imageProperty: string, imagePath: string): Array<any> {
    object.forEach(element => {
      const image = this.getObjectProperty(imageProperty, element);
      const uints = new Uint8Array(image);
      const base64 = btoa(String.fromCharCode.apply(null, uints));
      this.setObjectProperty(imageProperty, element, 'data:image/jpeg;base64,' + base64);

    });
    return object;
  }
  public static differenceInDates(endDate: Date, startDate: Date): DateInfo {
    const diffMs = (endDate.getTime() - startDate.getTime());
    const diffDays = Math.floor(diffMs / 86400000);
    const diffHrs = Math.floor((diffMs % 86400000) / 3600000);
    const diffMins = Math.round(((diffMs % 86400000) % 3600000) / 60000);
    const totMins = (diffHrs * 60) + (diffMins) + (diffDays * 24 * 60);
    return {
      miliseconds: diffMs,
      days: diffDays,
      hours: diffHrs,
      minutes: diffMins,
      totminutes: totMins
    };
  }
  public static buildDate(date: Date): SimpleDate {
    const s = _moment();
    const moment1 = _moment(_moment(date).startOf('week'));
    const moment2 = _moment(_moment(date).endOf('week'));
    const startDate = moment1.toDate();
    const lastDate = moment2.toDate();
    const fisrtYear = startDate.getFullYear();
    const lastYearDate = lastDate.getFullYear();
    const firstDayDate = startDate.getDate();
    const lastDayDate = lastDate.getDate();
    const prevMonthName = moment1.subtract(0, 'month').format(Calendar.MONTH_FORMAT).substring(0, 3);
    const lastMonthName = moment2.subtract(0, 'month').format(Calendar.MONTH_FORMAT).substring(0, 3);
    const firstOkYear = ((prevMonthName === lastMonthName ? '' : ' ' + prevMonthName) +
      (fisrtYear === lastYearDate ? '' : ' '.concat(fisrtYear.toString()))).concat(' - ');
    return {
      firstYear: fisrtYear,
      lastYear: lastYearDate,
      firstMonthName: prevMonthName,
      LastMonthmonthName: lastMonthName,
      lastDay: lastDayDate,
      firstDay: firstDayDate,
      fullDate: firstDayDate + firstOkYear + lastDayDate + ' ' + lastMonthName + ' ' + lastYearDate,
    };
  }
  static confirmPasswordAndUser(currentPassword: string, password: string, user: string,
    isUpdate: boolean, withRoles: boolean, hasRol?: boolean): FormUsuarioValid {
    let disableButton = password === currentPassword;
    let message = '';
    if (user.length > 0 && !disableButton) {
      message = 'Las contraseñas no coinciden';
      disableButton = true;
    } else if (user.length <= 0) {
      message = 'El usuario no puede estar vacio';
      disableButton = true;
    } else if (user.length <= 0 || currentPassword.length <= 0 || password.length <= 0) {
      message = 'Asegúrese de llenar todos los campos';
      disableButton = true;
    } else if (withRoles && (hasRol || hasRol === null)) {
      message = 'Debes seleccionar un tipo de usuario';
      disableButton = true;
    } else {
      disableButton = false;
    }
    return {
      disableDiv: disableButton,
      messageError: message,
      btnOptions: this.getBtnUserOptions(isUpdate, !disableButton),
      disabeButton: disableButton
    };
  }
  static getBtnUserOptions(isUpdate: boolean, enableBtn: boolean): UserInserteOrUpdate {
    if (isUpdate) {
      return { disabled: enableBtn ? undefined : true, opt: UPDATE_USER, btnName: 'Actualizar Usuario' };
    }
    return { disabled: enableBtn ? undefined : true, opt: CREATE_USER, btnName: 'Crear usuario' };
  }
  public static validMenuProperties(path: string, objs: Array<any>, valuesToCompare: Array<string>): Array<any> {
    const validObjs = new Array<any>();
    if (objs && objs !== null && objs.length > 0) {
      objs.forEach(obj => {
        const objValue = this.getObjectProperty(path, obj);
        if (objValue && objValue !== null) {
          const menuProperty = this.validMenuProperty(objValue[path], valuesToCompare);
          if (menuProperty && menuProperty !== null) {
            objValue.
              validObjs.push(objValue);
          }
        }
      });
    }

    return validObjs;
  }

  private static validMenuProperty(obj: Array<Options>, valuesToCompare: Array<string>): Array<Options> {
    const optionsMenu = new Array<Options>();
    let menuIndex = 0;
    if (obj && obj !== null && obj.length > 0) {
      obj.forEach(element => {
        let isMenuAssigned = false;
        element.profile.forEach(profile => {
          if (!isMenuAssigned && valuesToCompare.includes(profile)) {
            isMenuAssigned = true;
            element.index = menuIndex;
            optionsMenu.push(element);
            menuIndex += 1;
          }
        });
      });
    }
    return optionsMenu;
  }
  private static validMenuRol(obj: Array<string>, valuesToCompare: Array<string>): boolean {
    for (const iterator of obj) {
      if (valuesToCompare.includes(iterator)) {
        return false;
      }
    }
    return true;
  }
  static createUrlParams(params: Array<TableParams>, obj: any): any {
    const urlParams = {};
    params.forEach((value) => {
      urlParams[value.paramName] = this.getObjectProperty(value.paramName, obj);
    });
    return urlParams;
  }
  static getSuccessMessage(response: any): any {
    if (response.sucessMessage) {
      const message = response.sucessMessage as string;
      return message != null ? message : 'Exitoso';
    } else if (response && response.message) {
      return response.message;
    }
    return 'Exitoso';
  }
  /**
   * Crea las opciones a mostrar en componente mat-select, coloca el indice en -1 si es que la opcion no tiene un indice a colocar
   *
   */
  static createCurrentOption(obj: any, objPath: string): SelectMenu {
    return {
      index: -1,
      name: Smartwfm.getObjectProperty(objPath, obj)
    };
  }
  static find(value: string[], authorities: Array<Authorities>): boolean {
    return authorities.find(data => {
      return value.includes(data.authority);
    }) != null;

  }
  static validateData(data: any): boolean {
    return data && data != null;
  }
  static addIndexRowTable(data: Array<any>) {
    const rowIndex = 'index';
    data.forEach((value, index) => {
      value[rowIndex] = index;
    });
    return data;
  }
  /**
   * Remueve una fila de un arreglo, agrega un indice a cada una de las filas
   * @param array
   * @param data
   * @returns new Array
   */
  public static removeRowTable(array: Array<any>, data: any) {
    array = array.filter(item => item !== data);
    array = this.addIndexRowTable(array);
    return array;
  }
  public static getDateFromNumber(date: number): string {
    const r = new Date();
    r.setTime(date);
    const s = _moment(r);
    return s.format('YYYY-MM-DD HH:mm:ss');
  }
}
