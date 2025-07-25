import { LoaderComponet } from '../../interface/loader-component/loader-componet';
import { Input, EventEmitter } from '@angular/core';

export abstract class LoaderComponentImpl<T> implements LoaderComponet {
  @Input() data: any;
  @Input() index: number;
  @Input() selfRef: T;
  @Input() directive: any;
  @Input() compInteraction: any;
  @Input() parent: any;
  value?: EventEmitter<any>;
  constructor() { }
}
