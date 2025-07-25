import { NgModule } from '@angular/core';
import { LoaderComponentTamplateComponent } from './loader-component-tamplate/loader-component-tamplate.component';
import { LoaderComponentService } from 'src/app/core/services/loader-componet/loader-component.service';
import { DirectivesModule } from '../../Directives/directives.module';
import { ComponentsModule } from '../inputs-module/components.module';
import { ChildCommonModule } from '../../../core/class/main-modules/child-common-module';


import { ReactiveFormsModule } from '@angular/forms';
import { PipeModule } from 'src/app/core/Util/pipe/pipe.module';




@NgModule({
  imports: [
    ChildCommonModule,
    DirectivesModule,
    ComponentsModule,
    ReactiveFormsModule,
    PipeModule
  ],
  declarations: [
    LoaderComponentTamplateComponent
  ],
  exports: [
    LoaderComponentTamplateComponent
  ],
  providers: [
    LoaderComponentService
  ],
  entryComponents: []
})
export class TemplatesModule { }
