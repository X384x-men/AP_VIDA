import { GlobalVariable } from './URLImages';

export class URLUtilities {
  private static getURL(): string {
    return GlobalVariable.BASE_URL_API;
  }
  public static getMainUrl(): string {
    return 'angular';
  }
  public static getLogin(): string {
    return 'login';
  }
  public static getLogout(): string {
    return this.getURL() + 'logout';
  }
  public static LoginRequest(): string {
    return this.getURL() + 'login';
  }
  public static getMenuOptions(): string {
    return this.getURL().concat('acceso/menu');
  }
  public static postFileBatch(): string {
    return this.getURL() + 'cargaBatch';
  }

  public static postCatalogos(): String{
    return this.getURL()+'catalogos';
  }
  public static getMenuReportes(): string {
    return 'reportes';
  }
  public static getDescargarReporte(): string {
    return 'descargarReporte';
  }

  public static getBuscarReportesParam() {
    return 'buscarReportesParam';
  }

  public static getReportes(): string {
    return this.getURL() + 'reporte';
  }
  public static getReporteFull(): string {
    return this.getURL() + 'reportes/reporteFull';
  }
  public static getBuscarReporte() {
    return this.getURL() + 'reportes/buscarReporte';
  }
  public static getBuscarReporteporYear() {
    return this.getURL() + 'reportes/buscarReportebyYear';
  }
  public static getCancelarReporte() {
    return 'cancelarReporte';
  }
  public static getreporteCanceladas() {
    return this.getURL() + 'reportes/reporteCanceladas';
  }
  public static getMaterialOrden() {
    return 'materialOrden';
  }
  public static getReporteMaterialOrden() {
    return this.getURL() + 'reportes/reporteMaterialOrden';
  }
  public static getMaterialCliente() {
    return 'materialCliente';
  }
  public static getReporteMaterialCliente() {
    return this.getURL() + 'reportes/reporteMaterialCliente';
  }
  public static getYearsReporte(): string {
    return this.getURL() + 'reportes/getYearsReporte';
  }
  public static getClientes(): string {
    return this.getURL() + 'reportes/getClientes';
  }

  public static getBuscarReporteCanceladas() {
    return this.getURL() + 'reportes/buscarRaporteCanceladas';
  }

  public static getBuscarReporteporAnioCanceladas() {
    return this.getURL() + 'reportes/buscarReportebyAnioCanceladas';
  }

  public static getBuscarReporteMaterial() {
    return this.getURL() + 'reportes/buscarRaporteMaterialOrden';
  }
  public static getBuscarReporteporAnioMaterial() {
    return this.getURL() + 'reportes/buscarReportebyAnioMaterialOrden';
  }

  public static getBuscarReporteMaterialCliente() {
    return this.getURL() + 'reportes/buscarRaporteMaterialCliente';
  }
  public static getBuscarReporteporAnioCliente() {
    return this.getURL() + 'reportes/buscarReportebyAnioMaterialCliente';
  }
  public static getUsuariosByNumeroCuadrilla(): string {
    return this.getURL() + 'usuarios-acceso/usuarios-cuadrilla?cuadrilla=';
  }
  public static getUsuarioByNumeroCuadrilla(): string {
    return this.getURL() + 'usuario-acceso/usuario-cuadrilla?cuadrilla=';
  }
  public static getUsuarioByType(): string {
    return this.getURL() + 'usuario-acceso/usuarios?type=';
  }
  public static postFindUser(): string {
    return this.getURL() + 'usuario-acceso/find-user';
  }
  public static createUsuarioAcceso(): string {
    return this.getURL() + 'usuario-acceso/create?type=';
  }
  public static updateUsuarioAcceso(): string {
    return this.getURL() + 'usuario-acceso/update?param=';
  }
  public static registerRequest(): string {
    return 'createEmpleadoAP';
  }

  public static uploadEmployees(): string {
    return 'carga-empleados';
  }
  public static getAccessDenied(): string {
    return 'access-denied';
  }
  public static getScheduler(): string {
    return 'scheduler';
  }

  public static getChangePswd(): string {
    return 'changePswd'
  }

  public static getDashboardAP(): string {
    return 'dashboardAP'
  }


  public static getAddAP(): string {
    return 'register'
  }

  public static getUpdateAP(): string {
    return 'update'
  }
  public static getReporteAP(): string {
    return 'reporte'

  }
  public static dashboardAdmin(): string {
    return 'dashboard-admin'
  }
  public static getReporteAdmin(): string {
    return 'reporte-admin'
  }

  public static getAddAPAnalista(): string {
    return 'register-analista'
  }
  public static getUpdateAnalistaAP(): string {
    return 'update-analista'
  }

  public static dashboardAnalista(): string {
    return 'dashboard-analista'
  }

  public static dashboardAllAnalista(): string {
    return 'dashboard-all-analista'
  }

  public static listSolicitudes(): string {
    return 'list-solicitudes'
  }

  public static listSolicitudesPuebla(): string {
    return 'solicitudes-puebla'
  }

  public static listSolicitudesFonacot(): string {
    return 'solicitudes-fonacot'
  }

  public static emailList(): string {
    return 'email-list'
  }

  public static formSolicitudes(): string {
    return 'form-solicitudes'
  }

  public static formEditSolicitudes(): string {
    return 'form-edit-solicitudes'
  }

  public static formEditAclaraciones(): string {
    return 'form-edit-aclaraciones'
  }

  public static dashboardSolicitudes(): string {
    return 'dashboard-solicitudes'
  }

  public static dashboardPuebla(): string {
    return 'dashboard-puebla'
  }

  public static dashboardFunacot(): string {
    return 'dashboard-fonacot'
  }

  public static dashboardMySolicitudes(): string {
    return 'dashboard-analista-solicitud'
  }

  public static processAccountFile(): string {
    return 'processBatch'
  }

  public static mainCharts(): string {
    return 'app-main-chart'
  }

  public static mainAclaraciones(): string {
    return 'main-aclaraciones'
  }

  public static mainFomrAclaraciones(): string {
    return 'form-aclaraciones'
  }
}





