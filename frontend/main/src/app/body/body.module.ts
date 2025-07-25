import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FootComponent } from './foot/foot.component';
import { BodyContentRouting } from './body-content-routing.module';
import { HeadComponent } from './head/head.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MainLayoutComponent } from './main-layout/main-layout.component';
import { MaterialModule } from '../shared/material/material-module';
import { AccessApplicationModule } from './home/access-application/access-application.module';
import { LazyTranslateModule } from '../shared/components/lazy-translate/lazy-translate.module';
import { PipeModule } from '../core/Util/pipe/pipe.module';
import { MainChangePasswordComponent } from './home/userAP/main-change-password/main-change-password.component';
import { MainDashboardComponent } from './home/userAP/main-dashboard/main-dashboard.component';
import { MainFormAltaUsrComponent } from './home/userAP/main-form-alta-usr/main-form-alta-usr.component';
import { MainFormActualizaUsrComponent } from './home/userAP/main-form-actualiza-usr/main-form-actualiza-usr.component';
import { FormChangePasswordComponent } from './home/userAP/form-change-password/form-change-password.component';
import { FormUserDataComponent } from './home/userAP/form-user-data/form-user-data.component';
import { DivModule } from '../shared/components/div-module/div.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RecoverPwdComponent } from './home/userAP/recover-pwd/recover-pwd.component';
import { ReporteMovimientosComponent } from './home/userAP/reporte-movimientos/reporte-movimientos.component';
import { ReporteAdminComponent } from './home/userAP/reporte-admin/reporte-admin.component';
import { MainFormAltaAnalistaComponent } from './home/userAP/main-form-alta-analista/main-form-alta-analista.component';
import { MainFormActualizaAnalistaComponent } from './home/userAP/main-form-actualiza-analista/main-form-actualiza-analista.component';
import { DashboardAnalistasComponent } from './home/userAP/dashboard-analistas/dashboard-analistas.component';
import { FormSolicitudDataComponent } from './home/userAP/form-solicitud-data/form-solicitud-data.component';
import { MainFormAltaSolicitudComponent } from './home/userAP/main-form-alta-solicitud/main-form-alta-solicitud.component';
import { ListSolicitudesComponent } from './home/userAP/list-solicitudes/list-solicitudes.component';
import { MainFormActualizaSolicitudComponent } from './home/userAP/main-form-actualiza-solicitud/main-form-actualiza-solicitud.component';
import { MainDashboardAnalistasComponent } from './home/userAP/main-dashboard-analistas/main-dashboard-analistas.component';
import { ExcelService } from '../core/services/excel-service/excel-service.service';
import { MainExportModule } from '../core/class/main-modules/main-export-module';
import { AccountStatusComponent } from './home/userAP/dashboard-administrador/account-status.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { BrowserModule } from '@angular/platform-browser';
import { MainChartComponent } from './home/userAP/main-chart/main-chart.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { DashboardAnalistaSolicitudComponent } from './home/dashboard-analista-solicitud/dashboard-analista-solicitud.component';
import { DashboardAllAnalistasComponent } from './home/userAP/dashboard-all-analistas/dashboard-all-analistas.component';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        BodyContentRouting,
        FlexLayoutModule,
        MaterialModule,
        AccessApplicationModule,
        LazyTranslateModule.forChild(),
        PipeModule,
        DivModule,
        FormsModule,
        ReactiveFormsModule,
        MainExportModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatIconModule,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        NgApexchartsModule
    ],
    declarations: [
        MainLayoutComponent,
        FootComponent,
        HeadComponent,
        MainChangePasswordComponent,
        MainDashboardComponent,
        MainFormAltaUsrComponent,
        MainFormActualizaUsrComponent,
        FormChangePasswordComponent,
        FormUserDataComponent,
        RecoverPwdComponent,
        ReporteMovimientosComponent,
        ReporteAdminComponent,
        MainFormAltaAnalistaComponent,
        MainFormActualizaAnalistaComponent,
        DashboardAnalistasComponent,
        FormSolicitudDataComponent,
        MainFormAltaSolicitudComponent,
        ListSolicitudesComponent,
        MainFormActualizaSolicitudComponent,
        MainDashboardAnalistasComponent,
        AccountStatusComponent,
        MainChartComponent,
        DashboardAnalistaSolicitudComponent,
        DashboardAllAnalistasComponent,
    ],
    exports: [
        FootComponent
    ],
    providers: [ExcelService]
})
export class BodyModule { }
