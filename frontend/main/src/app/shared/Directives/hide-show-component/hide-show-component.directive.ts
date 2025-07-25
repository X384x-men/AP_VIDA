import { Directive, Input, ViewContainerRef, TemplateRef, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

@Directive({
  selector: '[appHideShow]'
})
export class HideShowComponentDirective implements OnInit, OnDestroy {
  @Input() appHideShow = true;
  stop$ = new Subject();

  isVisible = false;

  /**
   * @param {ViewContainerRef} viewContainerRef
   * 	-- the location where we need to render the templateRef
   * @param {TemplateRef<any>} templateRef
   *   -- the templateRef to be potentially rendered
   * @param {RolesService} rolesService
   *   -- will give us access to the roles a user has
   */
  constructor(private viewContainerRef: ViewContainerRef, private templateRef: TemplateRef<any>) {
  }

  ngOnInit(): void {
    if (this.appHideShow) {
      this.isVisible = true;
      this.viewContainerRef.createEmbeddedView(this.templateRef);
    } else {
      this.isVisible = false;
      this.viewContainerRef.clear();
    }
  }
  ngOnDestroy(): void {
    this.stop$.next();
  }
}
