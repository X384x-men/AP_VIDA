import { NgModule } from '@angular/core';
import { MaterialModule } from '../../../shared/material/material-module';
import { LazyTranslateModule } from '../../../shared/components/lazy-translate/lazy-translate.module';
import { FormsModule } from '@angular/forms';

import { HTTP_CLIENT } from '../../actions/app-actions/app.token';
import { SubResourceService } from '../../services/service-crud-operations/sub-resource.service';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { DivModule } from '../../../shared/components/div-module/div.module';

/**
 * IMPORTA Y EXPORTA LOS MODULOS MAS USADOS ENTRE LOS COMPONENTES, ASI COMO SERVICIOS MAS USADOS
 */

export const PROVIDERS: any = [
  { provide: HTTP_CLIENT, useClass: HttpClient },
  { provide: 'ServiceResource', useClass: SubResourceService, deps: [HTTP_CLIENT] }
];
@NgModule({
  imports: [
    // LazyTranslateModule.forChild(),
  ],
  providers: PROVIDERS,
  exports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    DivModule
  ]
})

export class ChildCommonModule {
}
