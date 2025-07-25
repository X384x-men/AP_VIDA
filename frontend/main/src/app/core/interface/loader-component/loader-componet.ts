import { Type, EventEmitter } from '@angular/core';

export interface LoaderComponet {
  data?: any;
  compInteraction?: any;
  selfRef?: any;
  index: number;
  directive?: any;
  parent?: Type<any>;
  value?: EventEmitter<any>;
}
export interface LoaderComponentType {
  type: Type<any>;
  data: any;
  parent: Type<any>;
  reload?: boolean;
  isUpdate?: boolean;

}
