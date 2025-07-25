// Moduls
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { DirectivesModule } from '../../Directives/directives.module';
import { ChildCommonModule } from '../../../core/class/main-modules/child-common-module';
import { LazyTranslateModule } from '../lazy-translate/lazy-translate.module';

// Components
import { MenuComponentComponent } from './menu-component/menu-component.component';
import { SelectComponentComponent } from './select-component/select-component.component';
import { RequiredInputTextComponent } from './required-input-text/required-input-text.component';
import { RequiredOptionalInputTextComponent } from './required-optional-input-text/required-optional-input-text.component';
import { OptionalInputTextComponent } from './optional-input-text/optional-input-text.component';
import { ButtonBackComponent } from './button-back/button-back.component';
import { ButtonCreateComponent } from './button-create/button-create.component';
import { ButtonBackAndCreateComponent } from './button-back-and-create/button-back-and-create.component';
import { SelectComponentScrollComponent } from './select-component-scroll/select-component-scroll.component';
import { ModalComponent } from '../modal/modal.component';
import { ResetPasswordButton } from '../reset-password-button/reset-password-button.component';
import { FilterCSVComponent } from '../filterCSV/filtercsv.component';

@NgModule({
  imports: [
    ChildCommonModule,
    ReactiveFormsModule,
    DirectivesModule,
    LazyTranslateModule,
  ],
  declarations: [
    MenuComponentComponent,
    SelectComponentComponent,
    RequiredInputTextComponent,
    RequiredOptionalInputTextComponent,
    OptionalInputTextComponent,
    ButtonBackComponent,
    ButtonCreateComponent,
    ButtonBackAndCreateComponent,
    SelectComponentScrollComponent,
    ModalComponent,
    ResetPasswordButton,
    FilterCSVComponent
  ], exports: [
    MenuComponentComponent,
    SelectComponentComponent,
    RequiredInputTextComponent,
    RequiredOptionalInputTextComponent,
    OptionalInputTextComponent,
    ButtonBackComponent,
    ButtonCreateComponent,
    ButtonBackAndCreateComponent,
    SelectComponentScrollComponent,
    ResetPasswordButton,
    FilterCSVComponent
  ]
})
export class ComponentsModule { }
