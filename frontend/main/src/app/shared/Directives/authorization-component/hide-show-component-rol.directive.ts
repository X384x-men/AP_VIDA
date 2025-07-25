import { Directive, Input, OnDestroy, OnInit, ViewContainerRef, TemplateRef } from '@angular/core';
import { Subject } from 'rxjs';
import { AuthenticationService } from 'src/app/core/services/authentication-service/authentication.service';

@Directive({
  selector: '[appHasRole]'
})
export class HideShowComponentRolDirective implements OnInit, OnDestroy {

  @Input() appHasRole: string;
  private roles = new Array<string>();
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
  constructor(authenticationService: AuthenticationService, private viewContainerRef: ViewContainerRef,
              private templateRef: TemplateRef<any>) {
    this.roles = ['ADMIN'];
  }

  ngOnInit(): void {
    if (this.roles.includes(this.appHasRole)) {
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
