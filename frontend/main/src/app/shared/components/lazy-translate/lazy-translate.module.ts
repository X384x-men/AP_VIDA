import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { GlobalVariable } from 'src/app/core/static/variables/url/URLImages';

// {
//   loader: {
//     provide: [TranslateLoader],
//     useFactory: HttpLoaderFactory,
//     deps: [HttpClient]
//   },
//   isolate: false
// }

@NgModule({
  imports: [
    CommonModule,
    TranslateModule.forChild()
  ],
  exports: [
    TranslateModule
  ]
})
export class LazyTranslateModule {

  static forChild(): ModuleWithProviders<LazyTranslateModule> {
    return {
      ngModule: LazyTranslateModule
    };
  }
}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, GlobalVariable.I18_FOLDER, '.json');
}

