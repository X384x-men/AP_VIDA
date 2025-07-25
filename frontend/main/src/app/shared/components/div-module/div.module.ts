import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DivErrorMessageComponent } from './div-error-message/div-error-message.component';

@NgModule({
  declarations: [DivErrorMessageComponent],
  imports: [
    CommonModule
  ],
  exports: [
    DivErrorMessageComponent
  ]
})
export class DivModule { }
