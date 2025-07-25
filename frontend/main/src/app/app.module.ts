// Angular
import { NgModule } from '@angular/core';
import { provideFirebaseApp, initializeApp } from '@angular/fire/app';
import { provideStorage, getStorage } from '@angular/fire/storage';
import { provideDatabase, getDatabase } from '@angular/fire/database';
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
import { NgApexchartsModule } from 'ng-apexcharts';

@NgModule({
  declarations: [
    AppComponent,
    ListaSolicitudComponent
  ],
  imports: [
    AppModuleConfig,
    HttpClientXsrfModule.withOptions({ cookieName: GlobalVariable.XSRF_TOKEN }),
    RouterModule.forRoot([], { relativeLinkResolution: 'legacy' }),
    provideFirebaseApp( () => initializeApp(environment.firebaseConfig)),
    provideStorage( () => getStorage()),
    provideDatabase( () => getDatabase()),
    MainExportModule,
    ReactiveFormsModule,
    LoaderModule,

  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
