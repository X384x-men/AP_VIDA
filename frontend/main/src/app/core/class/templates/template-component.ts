import { Input, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import { Smartwfm } from '../../Util/smartwfm/smartwfm';

export class TemplateComponent {
  @Input() isUpdate = false;
}
export class HeaderComponent<T> {
  @Input() useMock = false;
  /**
   * Indica si se usara una direccion distinta a la por defecto para consultar un servicio
   */
  @Input() url: string;
  /*
   * Indica los parametros que llevara la consulta hacia el servicio
   */
  @Input() params: any;
  /**
   * objeto que se emite al terminar la carga del servicio que se consulta
   */
  @Output() data = new EventEmitter<T>();
  /**
   * Indica el tipo de objeto que sera devuelo en la respuesta del servicio java
   */
  public dataCurrent: T;
  /**
   * Indica si la pagina ha sido recargada
   */
  public isLoaded = false;
  /**
   * Se usa en caso de que url y params sehan indefinidos, indica que se tomara la url por defecto para hacer
   * la consulta al servicio java
   */
  protected urlParam: string;
  /**
   * Informacion que se mostrara de manera asincrona
   */
  public dataCurrent$: Observable<T>;
  constructor() { }
  getDate(date: number): string {
    return Smartwfm.getDateFromNumber(date);
  }
}
