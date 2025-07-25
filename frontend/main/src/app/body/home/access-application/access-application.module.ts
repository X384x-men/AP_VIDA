import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccessAplicationRoutingModule } from './access-aplication.routing.module';
import { LoginComponent } from './login/login.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ComponentsModule } from 'src/app/shared/components/inputs-module/components.module';
import { MaterialModule } from 'src/app/shared/material/material-module';
import { DivModule } from 'src/app/shared/components/div-module/div.module';
import { LazyTranslateModule } from 'src/app/shared/components/lazy-translate/lazy-translate.module';
import { MainPageComponent } from './main-page/main-page.component';
import { LoginHeadComponent } from './login-head/login-head.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AccessAplicationRoutingModule,
    ComponentsModule,
    MaterialModule,
    DivModule,
    LazyTranslateModule,
  ],
  declarations: [
    LoginComponent,
    AccessDeniedComponent,
    MainPageComponent,
    LoginHeadComponent
  ]
})
export class AccessApplicationModule { }
