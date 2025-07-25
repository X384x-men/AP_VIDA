import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { MainLayoutComponent } from "./main-layout/main-layout.component";
import { URLUtilities } from "../core/static/variables/url/URLUtilities";
import { AuthGuardService } from "../core/services/auth-guard-service/auth-guard.service";
import { MainChangePasswordComponent } from "./home/userAP/main-change-password/main-change-password.component";
import { MainDashboardComponent } from "./home/userAP/main-dashboard/main-dashboard.component";
import { MainFormAltaUsrComponent } from "./home/userAP/main-form-alta-usr/main-form-alta-usr.component";
import { MainFormActualizaUsrComponent } from "./home/userAP/main-form-actualiza-usr/main-form-actualiza-usr.component";
import { ReporteMovimientosComponent } from "./home/userAP/reporte-movimientos/reporte-movimientos.component";
import { DashboardAdministradorComponent } from "./home/userAP/dashboard-administrador/dashboard-administrador.component";
import { ReporteAdminComponent } from "./home/userAP/reporte-admin/reporte-admin.component";
import { MainFormAltaAnalistaComponent } from "./home/userAP/main-form-alta-analista/main-form-alta-analista.component";
import { MainFormActualizaAnalistaComponent } from "./home/userAP/main-form-actualiza-analista/main-form-actualiza-analista.component";
import { DashboardAnalistasComponent } from "./home/userAP/dashboard-analistas/dashboard-analistas.component";
import { ListSolicitudesComponent } from "./home/userAP/list-solicitudes/list-solicitudes.component";
import { MainFormAltaSolicitudComponent } from "./home/userAP/main-form-alta-solicitud/main-form-alta-solicitud.component";
import { MainFormActualizaSolicitudComponent } from "./home/userAP/main-form-actualiza-solicitud/main-form-actualiza-solicitud.component";
import { MainDashboardAnalistasComponent } from "./home/userAP/main-dashboard-analistas/main-dashboard-analistas.component";
import { DependenciesComponent } from "./home/userAP/dependencies/dependencies.component";
import { AccountStatusComponent } from "./home/userAP/dashboard-administrador/account-status.component";
import { EmployeesComponent } from "./home/userAP/dashboard-administrador/employees.component";
import { AnalystsComponent } from "./home/userAP/dashboard-administrador/analysts.component copy";
import { ConceptsComponent } from "./home/userAP/concepts/concepts.component";
import { AdminUnitComponent } from "./home/userAP/admin-units/admin-units.component";
import { ListaSolicitudComponent } from "./home/userAP/dashboard-administrador/listaSolicitudes.component";
import { MainChartComponent } from "./home/userAP/main-chart/main-chart.component";
import { DashboardAnalistaSolicitudComponent } from "./home/dashboard-analista-solicitud/dashboard-analista-solicitud.component";
import { DashboardAllAnalistasComponent } from "./home/userAP/dashboard-all-analistas/dashboard-all-analistas.component";
import { MainAclaracionesComponent } from "./home/userAP/main-aclaraciones/main-aclaraciones.component";
import { MainFormAltaAclaracionesComponent } from "./home/userAP/main-form-alta-aclaraciones/main-form-alta-aclaraciones.component";
import { MainFormActualizaAclaracionComponent } from "./home/userAP/main-form-actualiza-aclaracion/main-form-actualiza-aclaracion.component";
import { EmailListComponent } from "./home/userAP/email-list/email-list.component";
import { MainDashboardPueblaComponent } from "./home/userAP/main-dashboard-puebla/main-dashboard-puebla.component";
import { MainDashboardFunacotComponent } from "./home/userAP/main-dashboard-funacot/main-dashboard-funacot.component";
import { SolicitudesPueblaComponent } from "./home/userAP/dashboard-administrador/solicitudes-puebla/solicitudes-puebla.component";
import { SolicitudesFonacotComponent } from "./home/userAP/dashboard-administrador/solicitudes-fonacot/solicitudes-fonacot.component";

const routes: Routes = [
  { path: "", redirectTo: URLUtilities.getMainUrl(), pathMatch: "full" },
  {
    path: URLUtilities.getMainUrl(),
    component: MainLayoutComponent,
    canActivate: [AuthGuardService],
    children: [
      {
        path: URLUtilities.getChangePswd(),
        component: MainChangePasswordComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.getDashboardAP(),
        component: MainDashboardComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.getAddAP(),
        component: MainFormAltaUsrComponent,
      },
      {
        path: URLUtilities.getUpdateAP(),
        component: MainFormActualizaUsrComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.getReporteAP(),
        component: ReporteMovimientosComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.dashboardAdmin(),
        component: DashboardAdministradorComponent,
        canActivate: [AuthGuardService],
        children: [
          { path: "asegurados", component: EmployeesComponent },
          { path: "analistas", component: AnalystsComponent },
          { path: "estados-de-cuenta", component: AccountStatusComponent },
          { path: "dependencias", component: DependenciesComponent },
          { path: "unidades-administrativas", component: AdminUnitComponent },
          { path: "catalogo-de-conceptos", component: ConceptsComponent },
          { path: "listas-de-solicitudes", component: ListaSolicitudComponent },
          { path: "solicitudes-fonacot", component: SolicitudesFonacotComponent },
          { path: "solicitudes-puebla", component: SolicitudesPueblaComponent },
          { path: "email-list", component: EmailListComponent },
          { path: "graficas", component: MainChartComponent },
          { path: "", redirectTo: "asegurados", pathMatch: "full" },
          { path: "**", redirectTo: "asegurados", pathMatch: "full" },
        ],
      },
      {
        path: URLUtilities.getReporteAdmin(),
        component: ReporteAdminComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.getAddAPAnalista(),
        component: MainFormAltaAnalistaComponent,
      },
      {
        path: URLUtilities.getUpdateAnalistaAP(),
        component: MainFormActualizaAnalistaComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.dashboardAnalista(),
        component: MainDashboardAnalistasComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.dashboardAllAnalista(),
        component: DashboardAllAnalistasComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.dashboardSolicitudes(),
        component: DashboardAnalistasComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.dashboardPuebla(),
        component: MainDashboardPueblaComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.dashboardFunacot(),
        component: MainDashboardFunacotComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.dashboardMySolicitudes(),
        component: DashboardAnalistaSolicitudComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.listSolicitudes(),
        component: ListSolicitudesComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.formSolicitudes(),
        component: MainFormAltaSolicitudComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.formEditSolicitudes(),
        component: MainFormActualizaSolicitudComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.formEditAclaraciones(),
        component: MainFormActualizaAclaracionComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.mainAclaraciones(),
        component: MainAclaracionesComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: URLUtilities.mainFomrAclaraciones(),
        component: MainFormAltaAclaracionesComponent,
        canActivate: [AuthGuardService],
      }

    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BodyContentRouting {}
