import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appAddComponent]'
})
export class AddComponentDirective {

  constructor(public viewContainerRef: ViewContainerRef) { }


}
