import { environment } from "src/environments/environment";

/**
 * URIS GLOBALES LAS CUALES CARGAN LOS MODULOS DE MANERA LAZY
 */
export const BODY_ROUTING_MODULE = Object.freeze({
  MAIN_PAGE: 'angular'
});

/**
 * NMBRE A INJECTAR DEL SERVICIO subresourservce.ts
 */
export const SUB_RESOURCE_SERVICE = Object.freeze({
  NAME: 'ServiceResource'
});
/**
 * URLS USADAS PARA CONFIGURAR RUTAS
 */

const base = environment.base;

export const nameApp='AP';
const IMAGE_BASE_FOLDER = base + '/assets/integra-icons/';
const SMART_WFM = nameApp;
const I18_FOLDER_APP = base + '/assets/i18n/main/';
const ASSETS_FOLDER = base + '/assets/';
/**
 * URLS DE CONFIGURACION PARA EL PUERTO 8081 (ARCHIVOS TRANSPILADOS)
 * BASE_URL_API = CONTEXT_PATH
 * MAIN_LOGO_INTEGRA = IMAGE_COMPILED_BASE_URL_API
 * URL_SERVICES = URL_SERVICE_COMPILED
 * I18_FOLDER = I18_COMPILED_FOLDER_APP
 * SVG_ICONS =  ASSETS_COMPILED_FOLDER
 */
const CONTEXT_PATH = nameApp+'/';
const IMAGE_COMPILED_BASE_URL_API = SMART_WFM.concat(IMAGE_BASE_FOLDER);
const I18_COMPILED_FOLDER_APP = SMART_WFM.concat(I18_FOLDER_APP);
const URL_SERVICE_COMPILED = nameApp;
const ASSETS_COMPILED_FOLDER = SMART_WFM.concat(ASSETS_FOLDER);
/**
 * URLS DE CONFIGURACION PARA EL PUERTO 4200 (NO TRANSPILADO)
 * BASE_URL_API = LOCAL_PATH
 * MAIN_LOGO_INTEGRA = IMAGE_BASE_URL_API
 * URL_SERVICES = URL_SERVICE_NOT_COMPILED
 * I18_FOLDER = I18_FOLDER_APP
 * SVG_ICONS =  ASSETS_FOLDER
 */

const LOCAL_PATH = environment.url + nameApp+'/';
const IMAGE_BASE_URL_API = IMAGE_BASE_FOLDER;
const URL_SERVICE_NOT_COMPILED = environment.url + nameApp;


/**
 * CONFIGURACION DE URLS DE LA APLICACION
 * BASE_URL_API Y URL_SERVICES: URL SOBRE LA CUAL SE HARAN LAS CONSULTAS A SERVICIOS REST
 * MAIN_LOGO_INTEGRA: MUESTRA LA IMAGEN INICIAL CON EL LOGO DE INTEGRA EN EL LOGIN
 * I18_FOLDER: RUTA DE LOS ARCHIVOS DE LENGUAJE DE CONFIGURACION
 * APLICATION_CONTEXT_PATH: NOMBRE DEL CONTEXTO DE LA APLICACION
 * XSRF_TOKEN: TOKEN DE CONFIGURACION DE SEGURIDAD
 * ADMIN_PROFILE_NAME: NOMBRE DEL PERFIL ADMIN POR DEFECTO
 */

 // Puerto 4200 (NO TRANSPILADO)
  /*export const GlobalVariable = Object.freeze({
  BASE_URL_API: LOCAL_PATH,
  MAIN_LOGO_INTEGRA: IMAGE_BASE_URL_API.concat('Logo.png'),
  BACKGROUND_IMG_APVIDA: IMAGE_BASE_URL_API.concat('familia.png'),
  I18_FOLDER: I18_FOLDER_APP,
  URL_SERVICES: URL_SERVICE_NOT_COMPILED,
  SVG_ICONS: ASSETS_FOLDER.concat('svg/'),
  APLICATION_CONTEXT_PATH: '/'+nameApp,
  XSRF_TOKEN: 'XSRF-TOKEN',
  ADMIN_PROFILE_NAME: 'ADMIN'
});*/

// Puerto 8080 o 8081 (ARCHIVOS TRANSPILADOS) PROD
export const GlobalVariable = Object.freeze({
  BASE_URL_API: CONTEXT_PATH,
  MAIN_LOGO_INTEGRA: IMAGE_COMPILED_BASE_URL_API.concat('Logo.png'),
  BACKGROUND_IMG_APVIDA: IMAGE_COMPILED_BASE_URL_API.concat('familia.png'),
  I18_FOLDER: I18_COMPILED_FOLDER_APP,
  URL_SERVICES: URL_SERVICE_COMPILED,
  SVG_ICONS: ASSETS_COMPILED_FOLDER.concat('svg/'),
  APLICATION_CONTEXT_PATH: '/'+nameApp,
  XSRF_TOKEN: 'XSRF-TOKEN',
  ADMIN_PROFILE_NAME: 'ADMIN'
});


const PARENT_URL_PDF = 'PDF';
export const PdfVariable = Object.freeze({
  GENERATE_PDF: PARENT_URL_PDF.concat('/generatePDF'),
  DOWNLOAD_PDF: PARENT_URL_PDF.concat('/download'),
});

const PARENT_URL_USUARIO_ACCESO = 'usuario-acceso';
export const UsuarioAcceso = Object.freeze({
  USUARIO_NOMBRE_AP: PARENT_URL_USUARIO_ACCESO.concat('/usuarioNombre'),
  GET_REPORTE_AP: PARENT_URL_USUARIO_ACCESO.concat('/getReporte'),
  GET_LIST_EMPLEADOS_SEARCH: PARENT_URL_USUARIO_ACCESO.concat('/getBusquedaEmpleadosAP'),
});


export const BUTTON_EDIT = 'BTN-EDIT';
export const LINK_ACTION = 'L';
export const OBJ_PROPERTY = 'action';
export const DETALLE_ACTION = 'D';

/**
 * URIS PARA REALIZAR PETICIONES HTTP HACIA Empleados
 */
const PARENT_URL_EMPLEADO = 'usuario-acceso';
export const EmpleadoVariable = Object.freeze({
  INSERT_EMPLEADO: PARENT_URL_EMPLEADO.concat('/createUsuario'),
  LIST_EMPLEADOS: PARENT_URL_EMPLEADO.concat('/getEmpleados'),
  EMPLEADO_BY_ID: PARENT_URL_EMPLEADO.concat('/getEmpleadobyId'),
  UPDATE_EMPLEADO: PARENT_URL_EMPLEADO.concat('/updateEmpleado'),
  UPDATE_ESTATUS: PARENT_URL_EMPLEADO.concat('/updateEstatus'),
  CREATE_ANALISTA_AP: PARENT_URL_EMPLEADO.concat('/createAnalistaAP'),
  GET_EMPLEADOS_EXTERNOS: PARENT_URL_EMPLEADO.concat('/getEmpleadosExternos')


});

const PARENT_URL_EMAIL = 'email';
export const EmailVariable = Object.freeze({
  SEND_EMAIL: PARENT_URL_EMAIL.concat('/sendEmail'),
  RESET_PASSWORD: PARENT_URL_EMAIL.concat('/reset_password'),
  LIST_EMAIL: PARENT_URL_EMAIL.concat('/getEmailList'),
  REENVIAR_EMAIL : PARENT_URL_EMAIL.concat('/reenvioCorreo')
});

const PARENT_URL_SOLICITUD = 'solicitud';
export const SolicitudVariable = Object.freeze({
  CREAR_SOLICITUD: PARENT_URL_SOLICITUD.concat('/crearSolicitud'),
  DOCUMENTO_SOLICITUD: PARENT_URL_SOLICITUD.concat('/documentoSolicitud'),
  GET_SOLICITUDES_BY_EMPLEADO: PARENT_URL_SOLICITUD.concat('/getSolicitudesByEmpleado'),
  UPDATE_STATUS_SOLICITUD: PARENT_URL_SOLICITUD.concat('/updateEstatusSolicitud'),
  UPDATE_SOLICITUD: PARENT_URL_SOLICITUD.concat('/actualizaSolicitud'),
  GET_SOLICITUD: PARENT_URL_SOLICITUD.concat('/getSolicitud'),
  GET_DOCUMENTO: PARENT_URL_SOLICITUD.concat('/getDocumento'),
  GET_SOLIITUDES_ANALISTAS: PARENT_URL_SOLICITUD.concat('/getSolicitudesAnalistas'),
  GET_EVENTOS_SOLIITUDES: PARENT_URL_SOLICITUD.concat('/getEventosSolicitud'),

  UPDATE_STATUS_SOLICITUD_ANALISTAS: PARENT_URL_SOLICITUD.concat('/updateEstatusSolicitudAnalistas'),
  CREAR_OBSERVACION: PARENT_URL_SOLICITUD.concat('/crearObservacion'),
  VALIDAR_IMPORTES: PARENT_URL_SOLICITUD.concat('/validarImportes'),
  UPDATE_FECHA_ORDEN_PAGO: PARENT_URL_SOLICITUD.concat('/updateFechaOrdenPagoSolicitud'),
  INFORMACION_PAGO: PARENT_URL_SOLICITUD.concat('/informacionPago'),
  GET_CAT_ASEGURADOS: PARENT_URL_SOLICITUD.concat('/getCatAsegurados'),
  GET_ORDEN_PAGO_LAYOUT: PARENT_URL_SOLICITUD.concat('/getOrdenPagoLayout'),
  CREAR_LAYOUT_CALCULO_ACTUARIA: PARENT_URL_SOLICITUD.concat('/crearLayoutCalculoActuaria'),
  GET_DATA_CALCULO_ACTUARIA: PARENT_URL_SOLICITUD.concat('/getDataCalculoActuaria'),
  UPDATE_IMPORTES_DATA_LAYOUT: PARENT_URL_SOLICITUD.concat('/updateImportesDataLayout'),
  GET_LIST_CALCULO: PARENT_URL_SOLICITUD.concat('/getListCalculo'),
});

const PARENT_URL_ACLARACION = 'aclaraciones';
export const AclaracionVariable = Object.freeze({
  GET_ACLARACION: PARENT_URL_ACLARACION.concat('/getAclaraciones'),
  GET_ACLARACION_UNIQUE: PARENT_URL_ACLARACION.concat('/getAclaracion'),
  GET_TIPO_ACLARACION: PARENT_URL_ACLARACION.concat('/getTipoAclaracion'),
  GET_CATALOGO_ACLARACION: PARENT_URL_ACLARACION.concat('/getCatalogoDocumento'),
  UPDATE_STATUS_ACLARACION: PARENT_URL_ACLARACION.concat('/updateAclaracionStatus'),
  POST_CATALOGO_ACLARACION: PARENT_URL_ACLARACION.concat('/postinsertCatalogoDocumentos'),
  POST_TIPO_ACLARACION: PARENT_URL_ACLARACION.concat('/postInsertTipoAclaracion'),
  POST_ACLARACION: PARENT_URL_ACLARACION.concat('/postCrearAclaracion'),
});


const PARENT_URL_CARGABATCH = 'batch';
export const CargaBatchVariable = Object.freeze({
  POST_BATCH: PARENT_URL_CARGABATCH.concat('/postBatch'),
  RESUMEN_BATCH: PARENT_URL_CARGABATCH.concat('/resumenBatch'),
  GET_COMBO_MES_ANIO: PARENT_URL_CARGABATCH.concat('/getComboMesAnio'),
  GET_MOVIMIENTOS: PARENT_URL_CARGABATCH.concat('/getMovimientos'),
});

export const Dependencias = 'getDependencias';

const PARENT_URL_CATALOGOS = 'catalogos';
export const ObtencionCatalogos = Object.freeze ({
  GET_CATALOGO_DEPENDENCIAS: PARENT_URL_CATALOGOS.concat('/getDpendenciasAP'),
  GET_CATALOGO_UNIDADES_ADMINISTRATIVAS :PARENT_URL_CATALOGOS.concat('/getUnidades'),
  GET_CONCEPTOS: PARENT_URL_CATALOGOS.concat('/getConceptos'),
  GET_DEPENDENCIAS_USUARIO: PARENT_URL_CATALOGOS.concat('/getDependenciaUsuario'),
  POST_STATUS_DEPENDENCIA: PARENT_URL_CATALOGOS.concat('/postActualizaDependenciaStatus'),
});


