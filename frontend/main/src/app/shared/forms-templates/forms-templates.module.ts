import { NgModule } from '@angular/core';
import {
  CreateOrUpdateUserFormTemplateComponent
} from './create-or-update-user-form-template/create-or-update-user-form-template.component';
import { ChildCommonModule } from '../../core/class/main-modules/child-common-module';
import { DirectivesModule } from '../Directives/directives.module';
import { ComponentsModule } from '../components/inputs-module/components.module';
//import { HeaderModule } from '../components/templates/header-module/header-module.module';

@NgModule({
  imports: [
    ChildCommonModule,
    DirectivesModule,
    ComponentsModule,
    //HeaderModule,
  ],
  declarations: [CreateOrUpdateUserFormTemplateComponent],
  exports: [
    CreateOrUpdateUserFormTemplateComponent
  ]
})
export class FormsTemplatesModule { }
