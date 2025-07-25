import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appLoadComponent]'
})
export class LoadComponentDirective {

  constructor(public viewContainerRef: ViewContainerRef) { }

}
