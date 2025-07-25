import { HTTP_INTERCEPTORS, HttpClientModule, HttpClient } from '@angular/common/http';
import { AuthInterceptorService } from '../../services/http-service/auth-interceptor-service/auth-interceptor.service';
import { LoaderInterceptorService } from '../../services/http-service/loader-interceptor/loader-interceptor.service';
import { GlobalVariable } from '../../static/variables/url/URLImages';
import { APP_BASE_HREF } from '@angular/common';
import { ROOT_STORAGE_KEYS, ROOT_LOCAL_STORAGE_KEY } from '../../actions/app-actions/app.token';
import { META_REDUCERS, MetaReducer, StoreModule } from '@ngrx/store';
import { StateStorageService } from '../../services/state-storage/state-storage.service';
import { storageMetaReducer } from '../../../reducers/languaje-metareducer';
import { Languaje } from 'src/app/reducers/app-reducer/app-reducer';
import { NgModule } from '@angular/core';
import { LoaderModule } from '../../../shared/loader/loader.module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { BodyModule } from '../../../body/body.module';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../../../shared/components/lazy-translate/lazy-translate.module';
import { reducers } from '../../../reducers/index';
export function getMetaReducers(saveKeys: string[], localStorageKey: string, storageService: StateStorageService): MetaReducer<Languaje> {
  return storageMetaReducer(saveKeys, localStorageKey, storageService);
}
const HTTP_INTERCEPTORS_PROVIDERS = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptorService, multi: true },
];
/**
 * PROVIDERS DE CONFIGURACION
 */
const APP_MODULE_PROVIDERS: any = [
  HTTP_INTERCEPTORS_PROVIDERS,
  {
    provide: APP_BASE_HREF, useValue: GlobalVariable.APLICATION_CONTEXT_PATH
  },
  { provide: ROOT_STORAGE_KEYS, useValue: ['language.name'] },
  { provide: ROOT_LOCAL_STORAGE_KEY, useValue: '__app_storage__' },
  {
    provide: META_REDUCERS, deps: [ROOT_STORAGE_KEYS, ROOT_LOCAL_STORAGE_KEY,
      StateStorageService], useFactory: getMetaReducers, multi: true
  }
];

/***
 *  COMPONENTE Y MODULOS USADOS EN app.module.ts
 */
@NgModule({
  imports: [
    LoaderModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      isolate: false
    }),
    StoreModule.forRoot(reducers),
  ],
  exports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    BodyModule,
    LoaderModule,
  ],
  providers: APP_MODULE_PROVIDERS,
})
export class AppModuleConfig {

}
