package com.sytecso.dao.cargasBatch.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.dao.cargasBatch.DAOCargasBatch;
import com.sytecso.dto.batchmodel.DTOAsegurado;
import com.sytecso.dto.batchmodel.DTOCargaBatchControl;
import com.sytecso.dto.batchmodel.DTOCriterios;
import com.sytecso.dto.batchmodel.DTODetalle;
import com.sytecso.dto.batchmodel.DTOResumen;
import com.sytecso.dto.catalogosAP.DTOCatalogoConceptos;
import com.sytecso.dto.solicitud.ShortSolicitudAPDTO;
import com.sytecso.dto.usuario.UserAp;
import java.util.HashSet;


@Repository
public class DAOCargasBatchImpl implements DAOCargasBatch {
	@Autowired
	private DataSource dataSource;
	
	@Override
	public boolean insertResumen(List<DTOResumen> resumenList,long registrobatch) throws SQLException {
		boolean status = true;
		ListIterator <DTOResumen> it = resumenList.listIterator();
		String sql = " insert into estadoCuentaResumen  (registrosBatch_idregistrosBatch,empleado_ap_idEmpleadoAP,catalogoConceptos_idConcepto,saldoInicial,primasAportadas,interesesGanados,retiros,saldoFinal,anio,mes,fecha,homonimia,nombre,catalogoDependencias_idcatalogoDependencias) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		Connection con = null;
		PreparedStatement pst = null;
		int contador=0;
		con = dataSource.getConnection();
		try {
			pst = con.prepareStatement(sql);
			while (it.hasNext()) {
				DTOResumen resumenTemp = it.next();
				pst.setLong(1,registrobatch);
				pst.setLong(2,  resumenTemp.getCriterios().getAsegurado().getId());
				pst.setLong(3, resumenTemp.getCriterios().getConcepto().getIdCatalogoConceptos());
				pst.setFloat(4,resumenTemp.getSaldoInicial());
				pst.setFloat(5, resumenTemp.getPrimasAportadas());
				pst.setFloat(6, resumenTemp.getInteresesGanados());
				pst.setFloat(7, resumenTemp.getRetiros());
				pst.setFloat(8, resumenTemp.getSaldoFinal());
				pst.setString(9, resumenTemp.getCriterios().getAnio());
				pst.setString(10, resumenTemp.getCriterios().getMes());
				pst.setString(11, resumenTemp.getCriterios().getFecha());
				pst.setString(12, resumenTemp.getCriterios().getHomoninima());
				pst.setString(13, resumenTemp.getCriterios().getNombre());
				pst.setLong(14, resumenTemp.getCriterios().getCatalogoDependencias().getIdCatalogo());
				pst.addBatch();
				contador++;
				if(contador==3000) {
					pst.executeBatch();
					contador=0;
				}
			}
			if(contador>0)
				pst.executeBatch();
		} catch (Exception e) {
			status=false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst);
		}
		return status;
	}
	
	@Override
	public boolean insertDetalle(List<DTODetalle> detalle, long registroBatch) throws SQLException {
		boolean status = true;
		ListIterator <DTODetalle> it = detalle.listIterator();
		String sql = " insert into estadoCuentaDetalle  (registrosBatch_idregistrosBatch,empleado_ap_idEmpleadoAP,catalogoConceptos_idConcepto,deposito,intereses,retiros,saldo,homonimia,nombre,anio,mes,fecha) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?) ";
		Connection con = null;
		PreparedStatement pst = null;
		int contador=0;
		con = dataSource.getConnection();
		try {
			pst = con.prepareStatement(sql);
			while (it.hasNext()) {
				DTODetalle detalleTemp = it.next();
				pst.setLong(1,registroBatch);
				pst.setLong(2,  detalleTemp.getCriterio().getAsegurado().getId());
				pst.setLong(3, detalleTemp.getCriterio().getConcepto().getIdCatalogoConceptos());
				pst.setFloat(4,detalleTemp.getDeposito());
				pst.setFloat(5, detalleTemp.getIntereses());
				pst.setFloat(6, detalleTemp.getRetiros());
				pst.setFloat(7, detalleTemp.getSaldo());
				pst.setString(8, detalleTemp.getCriterio().getHomoninima());
				pst.setString(9, detalleTemp.getCriterio().getNombre());
				pst.setString(10, detalleTemp.getCriterio().getAnio());
				pst.setString(11, detalleTemp.getCriterio().getMes());
				pst.setString(12, detalleTemp.getCriterio().getFecha());
				pst.addBatch();
				contador++;
				if(contador==3000) {
					pst.executeBatch();
					contador=0;
				}
			}
			if(contador>0)
				pst.executeBatch();
		} catch (Exception e) {
			status=false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst);
		}
		return status;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public long insertControlBatch(DTOCargaBatchControl control) throws SQLException {
		String sql = " insert into registrosBatch  (nombreArchivo,totalRegistros,rechazados,validos,tipoCarga,fecha) values(?,?,?,?,?,?) ";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		long idBatch=-1L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1,control.getNombreArchivo());
			pst.setInt(2, control.getTotalRegistros());
			pst.setInt(3, control.getRegistrosRechazados());
			pst.setInt(4, control.getRegristrosValidos());
			pst.setString(5, control.getTipo());
			pst.setString(6, control.getFechaCarga());
			pst.executeUpdate();
			con.commit();
			rs= pst.getGeneratedKeys();
			if (rs.next()) {
				idBatch = rs.getLong(1);
			}
		} catch (Exception e) {
			con.rollback();
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, pst, rs);
		}
		return idBatch;
	}

	@Override
	public DTOCargaBatchControl getResumenInsert(long  idCarga) {
		String sql = "  select nombreArchivo,TotalRegistros, rechazados,validos,tipocarga,fecha from registrosBatch where idregistrosBatch=? ";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		DTOCargaBatchControl control = new DTOCargaBatchControl();
		control.setId(idCarga);
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setLong(1,control.getId());
			rs=pst.executeQuery();
			while(rs.next()) {
				control.setNombreArchivo(rs.getString(1));
				control.setTotalRegistros(rs.getInt(2));
				control.setRegistrosRechazados(rs.getInt(3));
				control.setRegristrosValidos(rs.getInt(4));
				control.setTipo(rs.getString(5));
				control.setFechaCarga(rs.getString(6));
			}
			control.setMensaje("");
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			control.setMensaje("Operación fallida");
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return control;
	}

	@Override
	public List<DTODetalle> getDetalleLista(String anio, String mes, String rfc) {
		String sql = "  select det.fecha, cat.descripcion, det.deposito, det.intereses, det.retiros, det.saldo "
				+ "    from estadoCuentaDetalle det, catalogoConceptos cat, empleado_AP empAP "
				+ "    where det.empleado_ap_idEmpleadoAP=empAP.idEmpleadoAP "
				+ "    and det.catalogoConceptos_idConcepto=cat.idConcepto "
				+ "     and empAP.rfc=? "
				+ "     and anio=? "
				+ "     and mes=? "
				+"      and conv(validate,2,10)=0 ";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		List<DTODetalle> detalleList = new ArrayList<DTODetalle>();
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1,rfc);
			pst.setString(2, anio);
			pst.setString(3, mes);
			rs=pst.executeQuery();
			while(rs.next()) {
				DTODetalle detalleTemp = new DTODetalle();
				detalleTemp.setCriterio(new DTOCriterios());
				detalleTemp.getCriterio().setFecha(rs.getString(1));
				detalleTemp.getCriterio().setConcepto(new DTOCatalogoConceptos());
				detalleTemp.getCriterio().getConcepto().setDescripcion(rs.getString(2));
				detalleTemp.setDeposito(rs.getFloat(3));
				detalleTemp.setIntereses(rs.getFloat(4));
				detalleTemp.setRetiros(rs.getFloat(5));
				detalleTemp.setSaldo(rs.getFloat(6));
				detalleList.add(detalleTemp);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return detalleList;
	}

	@Override
	public List<DTOResumen> getResumenLista(String anio, String mes, String rfc) {
		String sql=" select cat.descripcion,sum(res.saldoinicial), sum(res.primasAportadas), sum(res.interesesGanados), sum(res.retiros), sum(res.saldoFinal) "
				+ " from estadoCuentaResumen res, empleado_AP empAP,catalogoconceptos cat "
				+ " where res.empleado_ap_idEmpleadoAP=empAP.idEmpleadoAP "
				+ " and res.catalogoConceptos_idConcepto= cat.idConcepto "
				+ " and empAp.rfc=? "
				+ " and res.anio=? "
				+ " and res.mes=? "
				+"  and conv(validate,2,10)=0 "
				+ " group by cat.descripcion ";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		List<DTOResumen> resumenList = new ArrayList<DTOResumen>();
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1,rfc);
			pst.setString(2, anio);
			pst.setString(3, mes);
			rs=pst.executeQuery();
			while(rs.next()) {
				DTOResumen resumenTemp = new DTOResumen();
				resumenTemp.setCriterios(new DTOCriterios());
				resumenTemp.getCriterios().setConcepto(new DTOCatalogoConceptos());
				resumenTemp.getCriterios().getConcepto().setDescripcion(rs.getString(1));
				resumenTemp.setSaldoInicial(rs.getFloat(2));
				resumenTemp.setPrimasAportadas(rs.getFloat(3));
				resumenTemp.setInteresesGanados(rs.getFloat(4));
				resumenTemp.setRetiros(rs.getFloat(5));
				resumenTemp.setSaldoFinal(rs.getFloat(6));
				resumenList.add(resumenTemp);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return resumenList;
		
	}

	@Override
	public DTOResumen getResumenSuma(String anio, String mes, String rfc) {
		String sql=" select sum(res.saldoinicial), sum(res.primasAportadas), sum(res.interesesGanados), sum(res.retiros), sum(res.saldoFinal) "
				+ " from estadoCuentaResumen res, empleado_AP empAP,catalogoDependencias cat "
				+ " where res.empleado_ap_idEmpleadoAP=empAP.idEmpleadoAP "
				+ " and res.catalogoDependencias_idcatalogoDependencias= cat.idcatalogoDependencias "
				+ " and empAP.rfc=? "
				+ " and res.anio=? "
				+ " and res.mes=? "
				+ " and conv(validate,2,10)=0 ";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		DTOResumen resumenSuma = new DTOResumen();
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1,rfc);
			pst.setString(2, anio);
			pst.setString(3, mes);
			rs=pst.executeQuery();
			while(rs.next()) {
				resumenSuma.setSaldoInicial(rs.getFloat(1));
				resumenSuma.setPrimasAportadas(rs.getFloat(2));
				resumenSuma.setInteresesGanados(rs.getFloat(3));
				resumenSuma.setRetiros(rs.getFloat(4));
				resumenSuma.setSaldoFinal(rs.getFloat(5));
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return resumenSuma;
	}


	@Override
	public long getIdAsegurado(String rfc,Statement st) throws SQLException {
		String query = "select idEmpleadoAP from empleado_ap  where rfc='"+rfc+"' ";
		long idAsegurado=-1L;
		ResultSet rs=null;
		try {
			rs=st.executeQuery(query);
			while (rs.next()) {
				idAsegurado=rs.getLong(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			rs.close();
		}
		return idAsegurado;
	}
	
	
	@Override
	public Set<DTOAsegurado> getRFCValidos(List<String> rfcList){
		Connection con = null;
		Set<DTOAsegurado> rfcValidos= new HashSet<DTOAsegurado>();
		try {
			con = dataSource.getConnection();
			ListIterator<String> parametros = rfcList.listIterator();
			while(parametros.hasNext()) {
				ResultSet rs=null;
				PreparedStatement pst = null;
				pst = con.prepareStatement("select idEmpleadoAP, rfc from empleado_ap "
						+ "           where rfc in  "+parametros.next());
			//	System.out.println(pst.toString());
				rs=pst.executeQuery();
				while (rs.next()) {
					DTOAsegurado asegurado = new DTOAsegurado();
					asegurado.setId(rs.getLong(1));
					asegurado.setRfc(rs.getString(2).toUpperCase());
					rfcValidos.add(asegurado);
				}
				UtileriaSql.closePreparedStatemetAndResultSet(pst, rs);
			}
			
			
	
			
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnection(con);
		}
		return rfcValidos;
	}
	

	@Override
	public int validateFile(String nameFile) {
		String query="select count(idRegistrosBatch)  from registrosBatch where nombreArchivo=? ";
		int file=0;
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.setString(1,nameFile);
			rs=pst.executeQuery();
			while (rs.next()) {
				file=rs.getInt(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return file;
	}

	@Override
	public List<String> getMesAnioList(String rfc) {
		String query="select mes_new, anio_new from( "
				+ "  select CAST(res.mes as SIGNED ) as mes_new , cast(res.anio as SIGNED ) as anio_new  "
				+ "  from estadoCuentaResumen res, empleado_ap empAp "
				+ "  where  res.empleado_ap_idEmpleadoAP= empAp.idEmpleadoAP "
				+ "  and empAp.rfc=? "
				+ "  ) as new_date "
				+ " group by mes_new, anio_new "
				+ "  order by anio_new asc, mes_new asc ";
		List<String> fechas= new ArrayList<String>();
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.setString(1,rfc);
			rs=pst.executeQuery();
			while(rs.next()) {
				 String fecha=rs.getString(1)+"-"+rs.getString(2);
				 fechas.add(fecha);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return fechas;
	}

	@Override
	public String getDependencia(String rfc) {
		String query=" select dependencia from empleado_ap  where rfc =? ";
		String  dependencia="";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.setString(1,rfc);
			rs=pst.executeQuery();
			while (rs.next()) {
				dependencia=rs.getString(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return dependencia;
	}

	@Override
	public List<Long> getIdFromFileName(String fileName) {
		String query="select idregistrosBatch from registrosbatch "
				+ "      where nombreArchivo like ? ";
		long idFile=-1L;
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		List<Long> fileList = new ArrayList<Long>();
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.setString(1,"%"+fileName+"%");
			rs=pst.executeQuery();
			while (rs.next()) {
				idFile=rs.getLong(1);
				if(idFile>0L)
					fileList.add(idFile);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return fileList;
	}

	@Override
	public boolean updateSingleClient(long empleadoAP, long cargaBatch, String tableName) {
		String query="update "+tableName+" set validate=conv(1,10,2) "
				+ "where  registrosBatch_idregistrosBatch=? "
				+ "and empleado_ap_idEmpleadoAP=? ";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.setLong(1,cargaBatch);
			pst.setLong(2, empleadoAP);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean updateMassive(long cargaBatch, String tableName) {
		String query="update "+tableName+" set validate=conv(1,10,2) "
				+ "where  registrosBatch_idregistrosBatch=? ";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.setLong(1,cargaBatch);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean updateEmpleadoApBatch(List<UserAp> empleadoApList) throws SQLException{
		boolean status = true;
		ListIterator <UserAp> it = empleadoApList.listIterator();
		String sql = "  update empleado_ap set estatus=? where  rfc =? ";
		Connection con = null;
		PreparedStatement pst = null;
		int contador=0;
		con = dataSource.getConnection();
		try {
			pst = con.prepareStatement(sql);
			while (it.hasNext()) {
				UserAp userAp= it.next();
				pst.setInt(1, userAp.getStatus());
				pst.setString(2,userAp.getRfc());
				pst.addBatch();
				contador++;
				if(contador==3000) {
					pst.executeBatch();
					contador=0;
				}
			}
			if(contador>0)
				pst.executeBatch();
		} catch (Exception e) {
			status=false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst);
		}
		return status;
	}

	@Override
	public List<ShortSolicitudAPDTO> getSolicitudesFiltered(String params) {
		String sql = "  select   DATE_FORMAT(fechaSolicitud,'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(fechaFinLaboral,'%Y-%m-%d %H:%i:%s'), rfcAsegurado, nombredelServidor,"
				+ "    aPaternodelServidor, "
				+ "    aMaternodelServidor, "
				+ "    dependencia, email, telefono, sueldo, pagoAnterior, tipoPago, nombreBanco, observaciones "
				+ "	 from solicitud ";
		
		if(!params.isEmpty()) {
			if(params.startsWith("and"))
				params=params.substring(3);
			sql =sql+"where "+params;
		}
		List<ShortSolicitudAPDTO> solicitudes= new ArrayList<ShortSolicitudAPDTO>();
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			rs=pst.executeQuery();
			while(rs.next()) {
				ShortSolicitudAPDTO solicitud = new ShortSolicitudAPDTO();
				solicitud.setFechaSolicitud(rs.getString(1));
				solicitud.setFechaFinLaboral(rs.getString(2));
				solicitud.setRfcAsegurado(rs.getString(3));
				solicitud.setNombre(rs.getString(4));
				solicitud.setApellidoPaterno(rs.getString(5));
				solicitud.setDependencia(rs.getString(6));
				solicitud.setEmail(rs.getString(7));
				solicitud.setTelefono(rs.getString(8));
				solicitud.setSueldo(rs.getString(9));
				solicitud.setPagoAnterior(rs.getString(10));
				solicitud.setTipoPago(rs.getString(11));
				solicitud.setNombreBanco(rs.getString(12));
				solicitud.setObservaciones(rs.getString(13));
				solicitudes.add(solicitud);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return solicitudes;
	}

	

	
	
}