// Angular
import { NgModule } from '@angular/core';
//Moduls
import { HttpClientXsrfModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { LoaderModule } from './shared/loader/loader.module';
import { MainExportModule } from './core/class/main-modules/main-export-module';
import { environment } from 'src/environments/environment';
import { AppModuleConfig } from './core/class/main-modules/app-providers';
// Components
import { AppComponent } from './app.component';
import { GlobalVariable } from './core/static/variables/url/URLImages';
import '../polyfills';
import { ListaSolicitudComponent } from './body/home/userAP/dashboard-administrador/listaSolicitudes.component';

@NgModule({
  declarations: [
    AppComponent,
    ListaSolicitudComponent
  ],
  imports: [
    AppModuleConfig,
    HttpClientXsrfModule.withOptions({ cookieName: GlobalVariable.XSRF_TOKEN }),
    RouterModule.forRoot([], {}),
    MainExportModule,
    ReactiveFormsModule,
    LoaderModule,

  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
