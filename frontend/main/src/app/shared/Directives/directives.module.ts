import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangeButtonDirective } from './change-button-directive/change-button.directive';
import { DisableInputDirective } from './disable-input-directive/disable-input.directive';
import { AddComponentDirective } from './add-component/add-component.directive';
import { LoadComponentDirective } from './load-component-directive/load-component.directive';
import { MousePositionDirective } from './mouse-position-directive/mouse-position.directive';
import { EnableDisableCheckDirective } from './enable-disable-check/enable-disable-check.directive';


import { BackButtonDirective } from './back-button/back-button.directive';
import { HideShowComponentRolDirective } from './authorization-component/hide-show-component-rol.directive';
import { HideShowComponentDirective } from './hide-show-component/hide-show-component.directive';


@NgModule({
   imports: [
      CommonModule
   ],
   declarations: [
      DisableInputDirective,
      ChangeButtonDirective,
      AddComponentDirective,
      LoadComponentDirective,
      MousePositionDirective,
      EnableDisableCheckDirective,
      HideShowComponentRolDirective,
      BackButtonDirective,
      HideShowComponentDirective
   ],
   exports: [
      DisableInputDirective,
      ChangeButtonDirective,
      AddComponentDirective,
      LoadComponentDirective,
      MousePositionDirective,
      EnableDisableCheckDirective,
      HideShowComponentRolDirective,
      BackButtonDirective,
      HideShowComponentDirective
   ]
})
export class DirectivesModule { }
