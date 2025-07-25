package com.sytecso.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(path = "/angular")
public class MainController {
	private static final String INDEX_VIEW = "index";
	

	@GetMapping(value = { "" })
	public String getHome(HttpServletResponse response) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "" })
	public ResponseEntity<Void> postHome(HttpServletResponse response) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-ordenes", "/inventario/proyecto", "solicitudes/list" })
	public String getAdministracionOrdenes() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-ordenes", "/inventario/proyecto", "solicitudes/list" })
	public ResponseEntity<Void> postAdministracionOrdenes() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/scheduler" })
	public String scheduler() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/scheduler" })
	public ResponseEntity<Void> postScheduler() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-ordenes/listado-ordenes" })
	public String listadoOrdenes(@RequestParam(name = "cuenta") String cuenta) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-ordenes/listado-ordenes" })
	public ResponseEntity<Void> postListadoOrdenes(@RequestParam(name = "cuenta") String cuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-ordenes/registro-ordenes" })
	public String getRegistroOrdenes() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-ordenes/registro-ordenes" })
	public ResponseEntity<Void> registroOrdenes() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-ordenes/resumen-ordenes" })
	public String getResumenOrdenes() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-ordenes/resumen-ordenes" })
	public ResponseEntity<Void> postResumenOrdenes() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-ordenes/registro" })
	public String getResumenOrdenes(@RequestParam(name = "cuenta") String cuenta) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-ordenes/registro" })
	public ResponseEntity<Void> postResumenOrdenes(@RequestParam(name = "cuenta") String cuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch" })
	public String getCargasBatch() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch" })
	public ResponseEntity<Void> postCargasBatch() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch/cuenta" })
	public String getCargasBatchCuentas() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch/cuenta" })
	public ResponseEntity<Void> postCargasBatchCuentas() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch/geoCerca" })
	public String getCargasBatchGeoCerca() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch/geoCerca" })
	public ResponseEntity<Void> postCargasBatchGeoCerca() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch/insumoEmpresa" })
	public String getCargasBatchInsumoEmpresa() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch/insumoEmpresa" })
	public ResponseEntity<Void> postCargasBatchInsumoEmpresa() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch/insumoCliente" })
	public String getCargasBatchInsumoCliente() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch/insumoCliente" })
	public ResponseEntity<Void> postCargasBatchInsumoCliente() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch/tipoOrden" })
	public String getCargasBatchTipoOrden() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch/tipoOrden" })
	public ResponseEntity<Void> postCargasBatchTipoOrden() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch/ocupaInsumo" })
	public String cargasBatchOcupaInsumo() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch/ocupaInsumo" })
	public ResponseEntity<Void> postCargasBatchOcupaInsumo() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch/batchProyectos" })
	public String getCargasBatchProyectos() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch/batchProyectos" })
	public ResponseEntity<Void> postCargasBatchProyectos() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/cargasBatch/batchCuadrilla" })
	public String getCargasBatchCuadrilla() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/cargasBatch/batchCuadrilla" })
	public ResponseEntity<Void> postCargasBatchCuadrilla() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/reportes" })
	public String getReportes() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/reportes" })
	public ResponseEntity<Void> postReportes() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/contratos" })
	public String getContratos() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/contratos" })
	public ResponseEntity<Void> postContratos() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/reportes/descargarReporte" })
	public String getReportesDescargarReportes() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/reportes/descargarReporte" })
	public ResponseEntity<Void> postReportesDescargarReportes() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "reportes/reporteCanceladas" })
	public String getReportesCanceladas() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "reportes/reporteCanceladas" })
	public ResponseEntity<Void> postReportesCanceladas() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "reportes/reporteMaterialOrden" })
	public String getReportMaterilByOrden() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "reportes/reporteMaterialOrden" })
	public ResponseEntity<Void> postReportMaterilByOrden() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "reportes/reporteMaterialCliente" })
	public String getReportMaterialByClient() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "reportes/reporteMaterialCliente" })
	public ResponseEntity<Void> postReportMaterialByClient() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-cuadrillas/cuadrilla" })
	public String getCuadrilla(@RequestParam(name = "numero") String numero) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-cuadrillas/cuadrilla" })
	public ResponseEntity<Void> postCuadrilla(@RequestParam(name = "numero") String numero) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-cuadrillas/listado" })
	public String getCuadrillaListado() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-cuadrillas/listado" })
	public ResponseEntity<Void> postCuadrillaListado() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-cuadrillas" })
	public String getAdministracionCuadrillas() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-cuadrillas" })
	public ResponseEntity<Void> postAdministracionCuadrillas() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-cuadrillas/alta-cuadrilla" })
	public String getAltaCuadrilla() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-cuadrillas/alta-cuadrilla" })
	public ResponseEntity<Void> postAltaCuadrilla() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/scheduler/position" })
	public String getLocationOrdenes(@RequestParam(name = "orden") Long orden) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/scheduler/position" })
	public ResponseEntity<Void> postLocationOrdenes(@RequestParam(name = "orden") Long orden) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "contratos/usuario-contratos" })
	public String getFindContratos() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "contratos/usuario-contratos" })
	public ResponseEntity<Void> postFindContratos() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "ordenes/seguimientoOrden" })
	public String getSeguimientoOrdenes() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "ordenes/seguimientoOrden" })
	public ResponseEntity<Void> seguimientoOrdenes() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "ordenes/datos-orden" })
	public String getDatosOrdenes() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "ordenes/datos-orden" })
	public ResponseEntity<Void> postDatosOrdenes() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "registro-ordenes" })
	public String getRegistroOrdenesInfo() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "registro-ordenes" })
	public ResponseEntity<Void> postRegistroOrdenesInfo() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "registro-ordenes/orden" })
	public String getRegistroOrdenesInfoOrden(@RequestParam(name = "numero") String numero) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "registro-ordenes/orden" })
	public ResponseEntity<Void> postRegistroOrdenesInfoOrden(@RequestParam(name = "numero") String numero) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion-perfiles" })
	public String getAdministracionPerfilesMain() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion-perfiles" })
	public ResponseEntity<Void> administracionPerfilesMain() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion-perfiles/seccion" })
	public String getAdministracionPerfilesSeccion(@RequestParam(name = "modulo") String seccion,
			@RequestParam(name = "rol") String rol) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion-perfiles/seccion" })
	public ResponseEntity<Void> postAdministracionPerfilesSeccion(@RequestParam(name = "modulo") String seccion,
			@RequestParam(name = "rol") String rol) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion-web/administracion-perfiles" })
	public String getAdministracionPerfiles() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion-web/administracion-perfiles" })
	public ResponseEntity<Void> postAdministracionPerfiles() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion-web/alta-usuario" })
	public String getAltaUsuarioWeb() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion-web/alta-usuario" })
	public ResponseEntity<Void> altaUsuarioWeb() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion-web/actualiza-usuario" })
	public String getListadoUsuarios(@RequestParam(name = "usuario", required = true) String usuario) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion-web/actualiza-usuario" })
	public ResponseEntity<Void> postListadoUsuarios(@RequestParam(name = "usuario", required = true) String usuario) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion-rol" })
	public String getAdministracionRol() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion-rol" })
	public ResponseEntity<Void> postAdministracionRol() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion-rol/alta" })
	public String getAdministracionRolAlta() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion-rol/alta" })
	public ResponseEntity<Void> postAdministracionRolAlta() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion-rol/update" })
	public String getAdministracionRolUpdate(@RequestParam(name = "value") String value) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion-rol/update" })
	public ResponseEntity<Void> postAdministracionRolUpdate(@RequestParam(name = "value") String value) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-web" })
	public String getAdministracionWeb() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-web" })
	public ResponseEntity<Void> postAdministracionWeb() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * added by Jose Trejo
	 */

	@GetMapping(value = { "/organizaciones", "/inventario" })
	public String getListaCuentas(@RequestParam(name = "opt") int opt) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/organizaciones", "/inventario" })
	public ResponseEntity<Void> postListaCuentas(@RequestParam(name = "opt") int opt) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/*
	 * LISTADO DE ORGANIZACIONES Y CUENTAS INICIO
	 * 
	 **/
	@GetMapping(value = { "/organizaciones/cuentas" })
	public String getOrganizacionCuenta(@RequestParam(name = "org") int org,
			@RequestParam(name = "opt", required = false) String opt) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/organizaciones/cuentas" })
	public ResponseEntity<Void> postOrganizacionCuenta(@RequestParam(name = "org") int org,
			@RequestParam(name = "opt", required = false) String opt) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * LISTADO DE ORGANIZACIONES Y CUENTAS FIN
	 * 
	 **/

	/*
	 * LISTADO DE ORGANIZACIONES Y CUENTAS MODIFICAR INICIO
	 * 
	 **/
	@GetMapping(value = { "/organizaciones/modificar-cuentas" })
	public String getOrganizacionModificarCuenta() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/organizaciones/modificar-cuentas" })
	public ResponseEntity<Void> postOrganizacionModificarCuenta() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * LISTADO DE ORGANIZACIONES Y CUENTAS MODIFICAR FIN
	 * 
	 **/

	/*
	 * CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta" })
	public String getCuenta(@RequestParam(name = "numCuenta") String numCuenta) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta" })
	public ResponseEntity<Void> postCuenta(@RequestParam(name = "numCuenta") String numCuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/*
	 * CUENTA FIN
	 * 
	 **/

	/*
	 * ALTA CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/alta-cuenta" })
	public String getAltaCuenta() {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/alta-cuenta" })
	public ResponseEntity<Void> postAltaCuenta() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * ALTA CUENTA FIN
	 * 
	 **/

	/*
	 * MODIFICAR CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/modificar-cuenta" })
	public String getModificarCuenta(@RequestParam(name = "numCuenta") String numCuenta) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/modificar-cuenta" })
	public ResponseEntity<Void> postModificarCuenta(@RequestParam(name = "numCuenta") String numCuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * MODIFICAR CUENTA FIN
	 * 
	 **/

	/*
	 * MODIFICAR CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/datos-fiscales" })
	public String getCuentaDatosFiscales(@RequestParam(name = "numCuenta") String numCuenta) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/datos-fiscales" })
	public ResponseEntity<Void> postCuentaDatosFiscales(@RequestParam(name = "numCuenta") String numCuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * MODIFICAR CUENTA FIN
	 * 
	 **/

	/*
	 * DIRECCION CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/direccion" })
	public String getCuentaDireccion(@RequestParam(name = "numCuenta") String numCuenta) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/direccion" })
	public ResponseEntity<Void> postCuentaDireccion(@RequestParam(name = "numCuenta") String numCuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * DIRECCION CUENTA FIN
	 * 
	 **/

	/*
	 * PROYECTOS CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/proyectos" })
	public String getCuentaProyectos(@RequestParam(name = "numCuenta") String numCuenta) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/proyectos" })
	public ResponseEntity<Void> postCuentaProyectos(@RequestParam(name = "numCuenta") String numCuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * PROYECTOS CUENTA FIN
	 * 
	 **/

	/*
	 * PROYECTOS ALTA CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/alta-proyecto" })
	public String getCuentaAltaProyectos(@RequestParam(name = "numCuenta") String numCuenta) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/alta-proyecto" })
	public ResponseEntity<Void> postCuentaAltaProyectos(@RequestParam(name = "numCuenta") String numCuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * PROYECTOS ALTA CUENTA FIN
	 * 
	 **/

	/*
	 * PROYECTOS UPDATE CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/modificar-proyectos" })
	public String getCuentaUpdateProyectos(@RequestParam(name = "proyecto") int proyecto) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/modificar-proyectos" })
	public ResponseEntity<Void> postCuentaUpdateProyectos(@RequestParam(name = "proyecto") int proyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * PROYECTOS UPDATE CUENTA FIN
	 * 
	 **/

	/*
	 * CONTACTOS CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/contactos" })
	public String getCuentaContatos(@RequestParam(name = "numCuenta") String numCuenta) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/contactos" })
	public ResponseEntity<Void> postCuentaContactos(@RequestParam(name = "numCuenta") String numCuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * CONTACTOS CUENTA FIN
	 * 
	 **/

	/*
	 * CONTACTOS CUENTA INICIO
	 * 
	 **/
	@GetMapping(value = { "/cuenta/almacenes" })
	public String getCuentaAlmacen(@RequestParam(name = "numCuenta") String numCuenta) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/cuenta/almacenes" })
	public ResponseEntity<Void> postCuentaAlmacen(@RequestParam(name = "numCuenta") String numCuenta) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/*
	 * CONTACTOS CUENTA FIN
	 * 
	 **/

	/**
	 * ROUTES Bodega inicio
	 **/

	@GetMapping(value = { "/bodegas" })
	public String getManejoBodega(@RequestParam(name = "numCuenta") String numCuenta,
			@RequestParam(name = "opt", required = false) String opt) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/bodegas" })
	public ResponseEntity<Void> postManejoBodegas(@RequestParam(name = "numCuenta") String numCuenta,
			@RequestParam(name = "opt", required = false) String opt) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/bodegas/proyecto", "cuenta/bodega/alta" })
	public String getManejoBodegaProyecto(@RequestParam(name = "proyecto") String proyecto) {
		return INDEX_VIEW;
	}

	@GetMapping(value = { "bodega/bodega-new" })
	public String getManejoBodega() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/bodegas/proyecto", "cuenta/bodega/alta", })
	public ResponseEntity<Void> postManejoBodegasProyecto(@RequestParam(name = "proyecto") String proyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = { "bodega/bodega-new" })
	public ResponseEntity<Void> postManejoBodega() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/bodega", "/bodega/alta", "/bodega/catalogo", "/bodega/almacenes", "bodega/detalle" })
	public String getMainBodega(@RequestParam(name = "numero") String numero) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/bodega", "/bodega/alta", "/bodega/catalogo", "/bodega/almacenes", "bodega/detalle" })
	public ResponseEntity<Void> postMainBodegas(@RequestParam(name = "numero") String numero) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/inventario/bodega/inventario" })
	public String getBodegaInventario(@RequestParam(name = "numero") String numero,
			@RequestParam(name = "isBodega") int isBodega) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/inventario/bodega/inventario" })
	public ResponseEntity<Void> postBodegaInventario(@RequestParam(name = "numero") String numero,
			@RequestParam(name = "isBodega") int isBodega) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/bodega/ingresoMaterialBodega" })
	public String getIngresoMaterialBodega() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/bodega/ingresoMaterialBodega" })
	public ResponseEntity<Void> postIngresoMaterialBodega() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 
	 * */
	@GetMapping(value = { "/bodega/alta-inventario" })
	public String getBodegaAlmacen(@RequestParam(name = "numero") String numero,
			@RequestParam(name = "almacen") String almancen) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/bodega/alta-inventario" })
	public ResponseEntity<Void> postBodegaAlmacen(@RequestParam(name = "numero") String numero,
			@RequestParam(name = "almacen") String almancen) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/bodega/proyecto", "/bodega/proyecto/paquetes", "/bodega/proyecto/items" })
	public String getBodegaProyecto(@RequestParam(name = "numero") String numero,
			@RequestParam(name = "proyecto") String proyecto) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/bodega/proyecto", "/bodega/proyecto/paquetes", "/bodega/proyecto/items" })
	public ResponseEntity<Void> postBodegaProyecto(@RequestParam(name = "numero") String numero,
			@RequestParam(name = "proyecto") String proyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/bodega/alta-bodega", })
	public String getBodegaAlta(@RequestParam(name = "numero") String numero,
			@RequestParam(name = "almacen") String almancen) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/bodega/alta-bodega" })
	public ResponseEntity<Void> postBodegaAlta(@RequestParam(name = "numero") String numero,
			@RequestParam(name = "almacen") String almancen) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * ROUTES Bodega fin
	 **/

	@PostMapping(value = { "/dashboard" })
	public ResponseEntity<Void> postDashboard(@RequestParam(name = "opt") String opt) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard" })
	public String getDashboard(@RequestParam(name = "opt") String opt) {
		return INDEX_VIEW;

	}

	@PostMapping(value = {"/dashboard/cuadrillasAsignadas",
			"/dashboard/agregarCuadrillas", "/dashboard/checkin", "/dashboard/solicitudes", "/dashboard/incidencias",
			"/dashboard/materialesConsumidos", "/dashboard/agregarRama", "/dashboard/personalAsignadoProyecto",
			"/dashboard/agregarPersonalAsig", "/dashboard/agregarIncidencia", "/dashboard/reporteAsistencia","/dashboard/gantt-ramas", "/dashboard/gantt-cuadrillas", "/dashboard/getSupervisores"})
	public ResponseEntity<Void> postActividadesProyecto(@RequestParam(name = "proyecto") String proyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = {"/dashboard/cuadrillasAsignadas",
			"/dashboard/agregarCuadrillas", "/dashboard/checkin", "/dashboard/solicitudes", "/dashboard/incidencias",
			"/dashboard/materialesConsumidos", "/dashboard/agregarRama", "/dashboard/personalAsignadoProyecto",
			"/dashboard/agregarPersonalAsig", "/dashboard/agregarIncidencia", "/dashboard/reporteAsistencia", "/dashboard/gantt-ramas", "/dashboard/gantt-cuadrillas", "/dashboard/getSupervisores" })
	public String getActividadesProyecto(@RequestParam(name = "proyecto") String proyecto) {
		return INDEX_VIEW;

	}
	
	@PostMapping(value = { "/dashboard/actividadesProyecto" })
	public ResponseEntity<Void> postActividadesProyecto(@RequestParam(name = "proyecto") String proyecto,
			@RequestParam(name = "opt") String opt) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/actividadesProyecto" })
	public String getActividadesProyecto(@RequestParam(name = "proyecto") String proyecto,
			@RequestParam(name = "opt") String opt) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/geocerca" })
	public ResponseEntity<Void> postGeoCerca(@RequestParam(name = "proyecto", required = false) Long proyecto,
			@RequestParam(name = "cuadrilla", required = false) Long cuadrilla) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/geocerca" })
	public String getGeoCerca(@RequestParam(name = "proyecto", required = false) Long proyecto,
			@RequestParam(name = "cuadrilla", required = false) Long cuadrilla) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/microactividad" })
	public ResponseEntity<Void> postMicroactividades(@RequestParam(name = "actividad") String actividad,
			@RequestParam(name = "nombre") String nombre, @RequestParam(name = "proyecto", required = false) String proyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/microactividad" })
	public String getMicroactividades(@RequestParam(name = "actividad") String actividad,
			@RequestParam(name = "nombre") String nombre, @RequestParam(name = "proyecto", required = false) String proyecto) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/agregarEspecialidad", "/dashboard/agregarSupervisor" })
	public ResponseEntity<Void> postAgregarEspecialidad(@RequestParam(name = "cuadrilla") String cuadrilla,
			@RequestParam(name = "proyecto") String proyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/agregarEspecialidad", "/dashboard/agregarSupervisor" })
	public String getAgregarEspecialidad(@RequestParam(name = "cuadrilla") String cuadrilla,
			@RequestParam(name = "proyecto") String proyecto) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/asistenciaCuadrillas" })
	public ResponseEntity<Void> postAsistenciaCuadrillas(@RequestParam(name = "cuadrilla") String cuadrilla) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/asistenciaCuadrillas" })
	public String getAsistenciaCuadrillas(@RequestParam(name = "cuadrilla") String cuadrilla) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/agregarEmpleadoCuadrilla" })
	public ResponseEntity<Void> postAgregarEmpleadoCuadrilla(@RequestParam(name = "idCuadrilla") String idCuadrilla,
			@RequestParam(name = "cuadrilla") String cuadrilla) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/agregarEmpleadoCuadrilla" })
	public String getAgregarEmpleadoCuadrilla(@RequestParam(name = "idCuadrilla") String idCuadrilla,
			@RequestParam(name = "cuadrilla") String cuadrilla) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/agregarActividad" })
	public ResponseEntity<Void> postAgregarActividad(@RequestParam(name = "tarea") String tarea,
			@RequestParam(name = "proyecto") String proyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/agregarActividad" })
	public String getAgregarActividad(@RequestParam(name = "tarea") String tarea,
			@RequestParam(name = "proyecto") String proyecto) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/agregarTarea" })
	public ResponseEntity<Void> postAgregarTarea(@RequestParam(name = "rama") String rama,
			@RequestParam(name = "proyecto") String proyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/agregarTarea" })
	public String getAgregarTarea(@RequestParam(name = "rama") String rama,
			@RequestParam(name = "proyecto") String proyecto) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/detalleVehiculo" })
	public ResponseEntity<Void> postDetalleVehiculo(@RequestParam(name = "vehiculo") String vehiculo) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/detalleVehiculo" })
	public String getDetalleVehiculo(@RequestParam(name = "vehiculo") String vehiculo) {
		return INDEX_VIEW;

	}

	@PostMapping(value = { "/dashboard/detalleIncidencia", "/dashboard/lista-cargasBatch" })
	public ResponseEntity<Void> postDetalleIncidencia(@RequestParam(name = "numero") String numero) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/detalleIncidencia", "/dashboard/lista-cargasBatch" })
	public String getDetalleIncidencia(@RequestParam(name = "numero") String numero) {
		return INDEX_VIEW;

	}
	
	@PostMapping(value = { "/dashboard/editarSolicitud" })
	public ResponseEntity<Void> postEditSolicitud(@RequestParam(name = "solicitud", required = false) Long solicitud,
			@RequestParam(name = "usr", required = false) String usr, @RequestParam(name = "destino", required = false) Long destino) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/editarSolicitud" })
	public String getEditSolicitud(@RequestParam(name = "solicitud", required = false) Long proyecto,
			@RequestParam(name = "usr", required = false) String usr, @RequestParam(name = "destino", required = false) Long destino) {
		return INDEX_VIEW;

	}
	

	@GetMapping(value = { "/administracion/organizacion/alta" })
	public String getAdministracion() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion/organizacion/alta" })
	public ResponseEntity<Void> postAdministracion() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion/organizacion/update",
			"/administracion/organizacion/update/form/datos-fiscales",
			"/administracion/organizacion/update/form/direccion-fiscal",
			"/administracion/organizacion/update/form/contacto-fiscal",
			"/administracion/organizacion/update/jornada-laboral" })
	public String getAdministracionUpdate(@RequestParam(name = "org") int org, @RequestParam(name = "aso") int aso) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion/organizacion/update", "/administracion/organizacion/update/datos-fiscales",
			"/administracion/organizacion/update/direccion-fiscal",
			"/administracion/organizacion/update/contacto-fiscal",
			"/administracion/organizacion/update/jornada-laboral"})
	public ResponseEntity<Void> postAdministracionUpdate(@RequestParam(name = "org") int org, @RequestParam(name = "aso") int aso) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/dashboard/material-alta" })
	public String getAltaMaterial(@RequestParam(name = "proyecto") int proyecto,
			@RequestParam(name = "rama") int rama , @RequestParam(name = "almRama") int almRama, @RequestParam(name = "opt") int opt ) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/dashboard/material-alta" })
	public ResponseEntity<Void> postAltaMaterial(@RequestParam(name = "proyecto") int proyecto,
			@RequestParam(name = "rama") int rama, @RequestParam(name = "almRama") int almRama, @RequestParam(name = "opt") int opt ) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-act" })
	public ResponseEntity<Void> postCatalogoActividades() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-tareas" })
	public String getCatalogoTareas() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-tareas" })
	public ResponseEntity<Void> postCatalogoTareas() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-microActividades" })
	public String getCatalogoMicroActividades() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-microActividades" })
	public ResponseEntity<Void> postCatalogoMicroActividades() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-incidencias" })
	public String getCatalogoIncidencias() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-incidencias" })
	public ResponseEntity<Void> postCatalogoIncidencias() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-items" })
	public String getCatalogoItems() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion-altaCatalogo/catalogos/alta-catalogo-items" })
	public ResponseEntity<Void> postCatalogoItems() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/solicitudes/edit", "/solicitudes/detalle" })
	public String getSolicitud(@RequestParam(name = "solicitud") long solicitud) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/solicitudes/edit", "/solicitudes/detalle" })
	public ResponseEntity<Void> postSolicitud(@RequestParam(name = "solicitud") long solicitud) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/administracion/personal/alta", "administracion/personal/admin",
			"/administracion/personal/alta-asociado", "administracion/personal/admin-asociado" })
	public String getAdministracionPersonal() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/administracion/personal/alta", "administracion/personal/admin", 
			"/administracion/personal/alta-asociado", "administracion/personal/admin-asociado" })
	public ResponseEntity<Void> postAdministracionPersonal() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "administracion/personal/actualizar" })
	public String getActualizarEmpleado(@RequestParam(name = "empleado") int empleado,
			@RequestParam(name = "org") int org) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "administracion/personal/actualizar" })
	public ResponseEntity<Void> postActualizarEmpleado(@RequestParam(name = "empleado") int empleado,
			@RequestParam(name = "org") int org) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/almacenes/listaAlmacenes" })
	public String getManejoAlmacenes() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/almacenes/listaAlmacenes" })
	public ResponseEntity<Void> postManejoAlmacenes() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/almacenes/list-almacen" })
	public String getListAlmacen(@RequestParam(name = "proyecto") int idProyecto) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/almacenes/list-almacen" })
	public ResponseEntity<Void> postListAlmacen(@RequestParam(name = "proyecto") int idProyecto) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = { "/almacenes/crear-almacen" })
	public String getListAlmacen(@RequestParam(name = "proyecto") int idProyecto,
			@RequestParam(name = "almacen") int idAlmacen) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/almacenes/crear-almacen" })
	public ResponseEntity<Void> postListAlmacen(@RequestParam(name = "proyecto") int idProyecto,
			@RequestParam(name = "almacen") int idAlmacen) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	
	@GetMapping(value = { "/dashboardAP", "/register", "/update" })
	public String getChangePswd() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/dashboardAP", "/register", "/update" })
	public ResponseEntity<Void> postChangePswd() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@GetMapping(value = { "/changePswd" })
	public String getChangePswd2(@RequestParam(name = "code") String code) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/changePswd"})
	public ResponseEntity<Void> postChangePswd2(@RequestParam(name = "code") String code) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = { "/reporte" })
	public String getReporte() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/reporte"})
	public ResponseEntity<Void> postReporte() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping(value = { "/dashboard-admin" })
	public String getDashboardAdmin() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/dashboard-admin"})
	public ResponseEntity<Void> postDashboardAdmin() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = { "/reporte-admin" })
	public String getReporteAdmin() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/reporte-admin"})
	public ResponseEntity<Void> postReporteAdmin() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = { "/register-analista", "/dashboard-analista", "/list-solicitudes", "/dashboard-solicitudes" })
	public String getAltaAnalista() {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/register-analista",  "/dashboard-analista", "/list-solicitudes", "/dashboard-solicitudes"  })
	public ResponseEntity<Void> postAltaAnalista() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = { "/form-edit-solicitudes" })
	public String getEditSolicitudes(@RequestParam(name = "solicitud") int solicitud, @RequestParam(name = "opt") int opt) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/form-edit-solicitudes" })
	public ResponseEntity<Void> postEditSolicitudes(@RequestParam(name = "solicitud") int solicitud, @RequestParam(name = "opt") int opt) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = { "/form-solicitudes", "/update-analista" })
	public String getAltaSolicitudes(@RequestParam(name = "opt") int opt) {
		return INDEX_VIEW;
	}

	@PostMapping(value = { "/form-solicitudes", "/update-analista" })
	public ResponseEntity<Void> postAltaSolicitudes(@RequestParam(name = "opt") int opt) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
