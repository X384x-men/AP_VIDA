import { NgModule } from '@angular/core';
import { MaterialModule } from '../../../shared/material/material-module';
import { LazyTranslateModule } from '../../../shared/components/lazy-translate/lazy-translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DirectivesModule } from '../../../shared/Directives/directives.module';
import { ComponentsModule } from '../../../shared/components/inputs-module/components.module';
import { TemplatesModule } from '../../../shared/components/templates/templates.module';

import { CommonModule } from '@angular/common';
import { PROVIDERS } from './child-common-module';
import { FormsTemplatesModule } from '../../../shared/forms-templates/forms-templates.module';

/**
 * IMPORTA Y EXPORTA LOS MODULOS MAS USADOS ENTRE LOS COMPONENTES, ASI COMO SERVICIOS MAS USADOS
 */

@NgModule({
  imports: [
    LazyTranslateModule.forChild(),
  ],
  providers: PROVIDERS,
  exports: [
    CommonModule,
    FormsModule,
    DirectivesModule,
    ComponentsModule,
    TemplatesModule,
    MaterialModule,
    LazyTranslateModule,
    ReactiveFormsModule,
    FormsTemplatesModule
  ]
})
export class MainExportModule {
}
