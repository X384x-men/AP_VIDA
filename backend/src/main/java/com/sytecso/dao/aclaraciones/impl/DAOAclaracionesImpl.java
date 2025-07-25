package com.sytecso.dao.aclaraciones.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ListIterator;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.dao.aclaraciones.DAOAclaraciones;
import com.sytecso.dao.evento.DAOEvento;
import com.sytecso.dto.AclaracionDTO;
import com.sytecso.dto.CatalogoDocumentoDTO;
import com.sytecso.dto.DocumentoDTO;
import com.sytecso.dto.EventoDTO;
import com.sytecso.dto.EventoSolicitudDTO;
import com.sytecso.dto.TipoAclaracionDTO;
import java.sql.Types;


@Repository
public class DAOAclaracionesImpl implements DAOAclaraciones {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private DAOEvento daoEvento;
	
	
	@Override
	public long crearAclaracion(AclaracionDTO aclaracion) throws SQLException {
		String sql= "    INSERT INTO aclaracion (tipoAclaracion_idtipoAclaracion, CatalogoDocumento_idCatalogoDocumento,descripcion, empleado_ap_idEmpleadoAP,fechaReal, fechaNueva,categoriaAclaracion,emailAclaracion,nombreAclaracion,rfcAclaracion ) "
					+ "      VALUES (?,?, ?, ?, ? ,?,?,?,?,?)  ";

		Connection con = null;
		PreparedStatement pst = null;
		con = dataSource.getConnection();
		con.setAutoCommit(false);
		ResultSet rs = null;
		long idAclaracion =-1L;
		ListIterator<DocumentoDTO> documentoIterador= null;
		if( (aclaracion.getDocumentoList()!= null) && (!aclaracion.getDocumentoList().isEmpty()))
			documentoIterador=aclaracion.getDocumentoList().listIterator();
		try {
				
			pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1,aclaracion.getTipoAclaracion());
			pst.setLong(2, aclaracion.getDocumentoTipo());
			pst.setString(3, aclaracion.getComentarios());
			if(aclaracion.getIdEmpleadoAP()>0) {
				pst.setLong(4, aclaracion.getIdEmpleadoAP());
			}else {
				pst.setNull(4, Types.BIGINT);
			}
			pst.setString(5, aclaracion.getFechaReal());
			pst.setString(6, aclaracion.getFechaAclaracion());
			if(!aclaracion.isCategoriaAclaracion()) {
				pst.setBoolean(7, false);
			}else {
				pst.setBoolean(7, true);
			}
			pst.setString(8,aclaracion.getEmailAclaracion());
			pst.setString(9,aclaracion.getNombreAclaracion());
			if(aclaracion.isCategoriaAclaracion()) 
				pst.setString(10, aclaracion.getRfc());
			else
				pst.setString(10, "");
			//System.out.println(pst.toString());
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				idAclaracion = rs.getLong(1);
				if( (aclaracion.getDocumentoList()!= null) && (!aclaracion.getDocumentoList().isEmpty())) {
					while(documentoIterador.hasNext()) {
						DocumentoDTO documento = documentoIterador.next();
						documento.setIdAclaracion(idAclaracion);
						setDocument(documento,con);
						
					}
				}
				
				manejaEventos(idAclaracion,"Creacion","Creación de aclaración", con);
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			System.out.println(e);
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, pst, rs);
		}
		return idAclaracion;
	}
	
	
	
	private boolean manejaEventos(long idAclaracion, String eventoString, String eventoSolicitudString, Connection connection) {
		EventoDTO evento = new EventoDTO();
        EventoSolicitudDTO  eventoSolicitud= new EventoSolicitudDTO();
        boolean status=false;
		evento.setTipo(eventoString);
		evento.setDescripcion(eventoSolicitudString);
		eventoSolicitud.setIdEvento(daoEvento.crearEvento(evento,connection).getIdEvento());
		if(crearEventoAclaracion(idAclaracion,eventoSolicitud.getIdEvento(), connection))
			status = true;
		return status;
		
	}
	
	private boolean crearEventoAclaracion(long idAclaracion, long evento , Connection connection) {
        PreparedStatement pst = null;
        boolean status = false;
        try {
            pst = connection.prepareStatement("INSERT INTO aclaracion_has_evento (`Aclaracion_idAclaracion`, `evento_idevento`) VALUES (?, ?) " );
            pst.setLong(1, idAclaracion);
            pst.setLong(2,evento);
            pst.executeUpdate();
			status = true;
        } catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closePreparedStatement( pst);
		}
        return status;
	}
	
	@Override
	public boolean insertCatalogoDocumento(String tipoDocumento) throws SQLException {
		String sql = "    INSERT INTO catalogoDocumento (`tipoDocumento`) VALUES (?) ";
		Connection con = null;
		PreparedStatement pst = null;
		con = dataSource.getConnection();
		con.setAutoCommit(false);
		ResultSet rs = null;
		boolean status = false;
		try {
			pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
				pst.setString(1,tipoDocumento);
				pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				if (rs.next()) {
					status=true;
				}
				con.commit();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}
	
	@Override
	public boolean insertCatalogoTipoAclaracion(String tipoAclaracion, String descripcion) throws SQLException  {
		String sql = " INSERT INTO `ap`.`tipoAclaracion` (`tipoAclaracion`, `DescripcionAclaracion`) VALUES (?, ?) ";
		Connection con = null;
		PreparedStatement pst = null;
		con = dataSource.getConnection();
		con.setAutoCommit(false);
		ResultSet rs = null;
		boolean status = false;
		try {
			pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
				pst.setString(1,tipoAclaracion);
				pst.setString(2,descripcion);
				pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				if (rs.next()) {
					status=true;
				}
				con.commit();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}



	@Override
	public List<AclaracionDTO> getAclaraciones(String filtros, int banderaFuncionamiento,String filtros2) {
		List<AclaracionDTO> aclaraciones= new ArrayList<AclaracionDTO>();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "select ac.idAclaracion, ac.descripcion as descripcionAclaracion,ac.fecha as fechaAclaracion,tia.tipoAclaracion as tipoAclaracion, "
				+ "          catdoc.tipoDocumento as tipoDocumento,eap.rfc as rfc, concat( eap.nombre, ' ', eap.apellidoP, ' ' ,eap.apellidoM) as nombre , "
				+ "          catdep.Descripcion as dependencia, eap.fechaCreacion as fechaCreacionPortal, ac.catalogodocumento_idCatalogoDocumento as idCatalogoDocumento, ac.tipoAclaracion_idtipoAclaracion as tipoDocumento, "
				+ "          eap.email as email, eap.telefonoMovil as telefono, ac.status , ac.fechaReal, ac.fechaNueva, ac.emailAclaracion as emailac, conv(categoriaAclaracion,2,10) as categoriaAclaracion  "
				+ "          from aclaracion ac, tipoAclaracion tia, CatalogoDocumento catDoc, empleado_ap eap, catalogodependencias catdep "
				+ "          where  ac.tipoAclaracion_idtipoAclaracion=tia.idTipoAclaracion  "
				+ "          and  ac.catalogodocumento_idCatalogoDocumento= catDoc.idCatalogoDocumento "
				+ "          and ac.empleado_ap_idEmpleadoAP= eap.idEmpleadoAP "
				+ "          and eap.dependencia_fk= catdep.idcatalogoDependencias "
				+"           and ac.categoriaAclaracion=conv(0,10,2) ";
		
		
		String queryNoEAP = "select ac.idAclaracion, ac.descripcion as descripcionAclaracion,ac.fecha as fechaAclaracion,tia.tipoAclaracion as tipoAclaracion, "
				+ "          catdoc.tipoDocumento as tipoDocumento,ac.rfcAclaracion as rfc, ac.nombreAclaracion as nombre , "
				+ "          '' as dependencia, ac.fecha as fechaCreacionPortal, ac.catalogodocumento_idCatalogoDocumento as idCatalogoDocumento, ac.tipoAclaracion_idtipoAclaracion as tipoDocumento, "
				+ "          ac.emailAclaracion as email, ' ' as telefono, ac.status , ac.fechaReal, ac.fechaNueva, ac.emailAclaracion as emailac, conv(categoriaAclaracion,2,10) as categoriaAclaracion   "
				+ "          from aclaracion ac, tipoAclaracion tia, CatalogoDocumento catDoc "
				+ "          where  ac.tipoAclaracion_idtipoAclaracion=tia.idTipoAclaracion  "
				+ "          and  ac.catalogodocumento_idCatalogoDocumento= catDoc.idCatalogoDocumento "
				+ "          and ac.categoriaAclaracion=conv(1,10,2) ";
		
		String queryFinal="";
		if (!filtros.equals(""))
			sql=sql+filtros;
		if (!filtros2.equals(""))
			queryNoEAP=queryNoEAP+filtros2;
		if( banderaFuncionamiento==0) 	
			queryFinal=sql+" union "+queryNoEAP;
		if(banderaFuncionamiento==1) 
			queryFinal=sql;
		if(banderaFuncionamiento==2) 
			queryFinal=queryNoEAP;
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(queryFinal, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				AclaracionDTO aclaracion =new AclaracionDTO();
				aclaracion.setIdAclaracion(rs.getLong(1));
				aclaracion.setComentarios(rs.getString(2));
				aclaracion.setTipoAclaracionString(rs.getString(4));
				aclaracion.setTipoDocumentoString(rs.getString(5));
				String rfc=rs.getString(6);
				if(rfc!=null)
					aclaracion.setRfc(rs.getString(6));
				else
					aclaracion.setRfc(" ");
				aclaracion.setNombre(rs.getString(7));
				String dependencia=rs.getString(8);
				if(dependencia!=null)
					aclaracion.setDependencia(rs.getString(8));
				else
					aclaracion.setDependencia(" ");
				aclaracion.setFechaRegistroPortal(rs.getString(9));
				aclaracion.setDocumentoTipo(rs.getLong(10));
				aclaracion.setTipoAclaracion(rs.getLong(11));
				aclaracion.setEmail(rs.getString(12));
				aclaracion.setTelefono(rs.getString(13));
				aclaracion.setStatus(rs.getInt(14));
				aclaracion.setFechaReal(rs.getString(15));
				aclaracion.setFechaAclaracion(rs.getString(16));
				aclaracion.setEmailAclaracion(rs.getString(17));
				if(rs.getInt(18)>0)
					aclaracion.setCategoriaAclaracion(true);
				else
					aclaracion.setCategoriaAclaracion(false);
				aclaraciones.add(aclaracion);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return aclaraciones;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateAclaracionStatus(long idAclaracion, int status) throws SQLException {
		
		String sql = "update aclaracion set status="+status+"  where idAclaracion="+idAclaracion+" ";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean estatus = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					
					manejaEventos(idAclaracion,"Actualización de estatu","Actualización de estatus", con);
					con.commit();
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}	
			}
		} catch (Exception e) {
			estatus = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return estatus;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateTipoAclaracionesCatalogo(long idTipoDesc,String desc) throws SQLException {
		
		String sql = "UPDATE tipoAclaracion SET tipoAclaracion = '"+desc+"' WHERE idtipoAclaracion = "+idTipoDesc+" ";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean estatus = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					con.commit();
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}	
			}
		} catch (Exception e) {
			estatus = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return estatus;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateCatalogoDocumentos(long idTipoDocumento,String desc) throws SQLException {
		
		String sql = "  UPDATE tipoAclaracion SET tipoAclaracion = '"+desc+"' WHERE (idtipoAclaracion = "+idTipoDocumento+") ";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean estatus = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					con.commit();
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}	
			}
		} catch (Exception e) {
			estatus = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return estatus;
	}



	@Override
	public List<TipoAclaracionDTO> getTipoAclaracion() {
		List<TipoAclaracionDTO> tipoAclaracionList= new ArrayList<TipoAclaracionDTO>();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "select idTipoAclaracion,tipoAclaracion, DescripcionAclaracion "
				+ "       from tipoAclaracion ";
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				TipoAclaracionDTO tipoAclaracion =new TipoAclaracionDTO();
				tipoAclaracion.setIdTipoAclaracion(rs.getLong(1));
				tipoAclaracion.setTipoAclaracion(rs.getString(2));
				tipoAclaracion.setDescripcion(rs.getString(3));
				tipoAclaracionList.add(tipoAclaracion);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return tipoAclaracionList;
	}



	@Override
	public List<CatalogoDocumentoDTO> getCatalogoDocumento() {
		List<CatalogoDocumentoDTO> catalogoDocumentosList= new ArrayList<CatalogoDocumentoDTO>();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "select idCatalogoDocumento, tipoDocumento "
				+ "        from catalogoDocumento ";
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				CatalogoDocumentoDTO catalogoDocumento =new CatalogoDocumentoDTO();
				catalogoDocumento.setIdCatalogoDocumento(rs.getLong(1));
				catalogoDocumento.setTipoDocumento(rs.getString(2));
				catalogoDocumentosList.add(catalogoDocumento);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return catalogoDocumentosList;
	}



	@Override
	public boolean updateAclaracion(AclaracionDTO aclaracion) throws SQLException {
		String sql = "UPDATE aclaracion SET tipoAclaracion_idtipoAclaracion = "+aclaracion.getTipoAclaracion()+", "
				+ "  CatalogoDocumento_idCatalogoDocumento = "+aclaracion.getDocumentoTipo()+ ", "
				+ "  descripcion = '"+aclaracion.getComentarios()+"', "
				+ "   fechaReal= '"+aclaracion.getFechaReal()+"', "
				+"    fechaNueva='"+aclaracion.getFechaAclaracion()+" "
				+ " WHERE idAclaracion = "+aclaracion.getIdAclaracion()+" ";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean estatus = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					
					manejaEventos(aclaracion.getIdAclaracion(),"Actualización de aclaración ","Actualización de aclaración", con);
					con.commit();
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}	
			}
		} catch (Exception e) {
			estatus = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return estatus;
	}



	@Override
	public AclaracionDTO getAclaracion(long id,int tipoAclaracion) {
		
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		AclaracionDTO aclaracion =new AclaracionDTO();
		String sql="";
		if (tipoAclaracion==1) {
			sql = "select ac.idAclaracion, ac.descripcion as descripcionAclaracion,ac.fecha as fechaAclaracion,tia.tipoAclaracion as tipoAclaracion, "
				+ "          catdoc.tipoDocumento as tipoDocumento,eap.rfc as rfc, concat( eap.nombre, ' ', eap.apellidoP, ' ' ,eap.apellidoM) as nombre , "
				+ "          catdep.Descripcion as dependencia, eap.fechaCreacion as fechaCreacionPortal, ac.catalogodocumento_idCatalogoDocumento as idCatalogoDocumento, ac.tipoAclaracion_idtipoAclaracion as tipoDocumento, "
				+ "          eap.email as email, eap.telefonoMovil as telefono, ac.status, ac.descripcionEmpleado, ac.fechaReal , ac.fechaNueva, ac.emailAclaracion as emailac "
				+ "          from aclaracion ac, tipoAclaracion tia, CatalogoDocumento catDoc, empleado_ap eap, catalogodependencias catdep "
				+ "          where  ac.tipoAclaracion_idtipoAclaracion=tia.idTipoAclaracion  "
				+ "          and  ac.catalogodocumento_idCatalogoDocumento= catDoc.idCatalogoDocumento "
				+ "          and ac.empleado_ap_idEmpleadoAP= eap.idEmpleadoAP "
				+ "          and eap.dependencia_fk= catdep.idcatalogoDependencias "
				+"           and idAclaracion="+id+" ";
		}
		if (tipoAclaracion==2) {
			sql = "select ac.idAclaracion, ac.descripcion as descripcionAclaracion,ac.fecha as fechaAclaracion,tia.tipoAclaracion as tipoAclaracion, "
				+ "          catdoc.tipoDocumento as tipoDocumento,ac.rfcAclaracion  as rfc, ac.nombreAclaracion as nombre , "
				+ "          ' ' as dependencia, ac.fecha as fechaCreacionPortal, ac.catalogodocumento_idCatalogoDocumento as idCatalogoDocumento, ac.tipoAclaracion_idtipoAclaracion as tipoDocumento, "
				+ "          ac.emailAclaracion as email, ' ' as telefono, ac.status, ac.descripcionEmpleado, ac.fechaReal , ac.fechaNueva, ac.emailAclaracion as emailac "
				+ "          from aclaracion ac, tipoAclaracion tia, CatalogoDocumento catDoc "
				+ "          where  ac.tipoAclaracion_idtipoAclaracion=tia.idTipoAclaracion  "
				+ "          and  ac.catalogodocumento_idCatalogoDocumento= catDoc.idCatalogoDocumento "
				+"           and idAclaracion="+id+" ";
		}
		
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				aclaracion.setIdAclaracion(rs.getLong(1));
				aclaracion.setComentarios(rs.getString(2));
				aclaracion.setTipoAclaracionString(rs.getString(4));
				aclaracion.setTipoDocumentoString(rs.getString(5));
				String rfc=rs.getString(6);
				if(rfc!=null)
					aclaracion.setRfc(rs.getString(6));
				else
					aclaracion.setRfc(" ");
				aclaracion.setNombre(rs.getString(7));
				String dependencia=rs.getString(8);
				if(dependencia!=null)
					aclaracion.setDependencia(rs.getString(8));
				else
					aclaracion.setDependencia(" ");
				aclaracion.setFechaRegistroPortal(rs.getString(9));
				aclaracion.setDocumentoTipo(rs.getLong(10));
				aclaracion.setTipoAclaracion(rs.getLong(11));
				aclaracion.setEmail(rs.getString(12));
				if(rs.getString(13)==null)
					aclaracion.setTelefono("");
				else
					aclaracion.setTelefono(rs.getString(13));
				aclaracion.setStatus(rs.getInt(14));
				aclaracion.setDescripcionEmpleado(rs.getString(15));
				aclaracion.setFechaReal(rs.getString(16));
				aclaracion.setFechaAclaracion(rs.getString(17));
				aclaracion.setEmailAclaracion(rs.getString(18));
				aclaracion.setDocumentoList(getDocumentosList(aclaracion.getIdAclaracion()));
				
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return aclaracion;
	}
	

	@Transactional(rollbackFor = Exception.class)
	private boolean setDocument(DocumentoDTO documento, Connection con) throws SQLException {
		
		String sql = "INSERT INTO documentos (pdf, fechaCreacion, Aclaracion_idAclaracion) VALUES (?, now(),?)";
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean status=true;
		long idTransaccion =-1L;
		try {
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setBytes(1, Base64Utils.decodeFromString(documento.getDocumentoString()));
			ps.setLong(2, documento.getIdAclaracion());
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					con.commit();
				}
				else {
					con.rollback();
					 status=false;
				}
				
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
			 idTransaccion = 0L;
			 status = false;
		} finally {
			UtileriaSql.closePreparedStatemetAndResultSet(ps, rs);
		}
		return status;
	}



	@Override
	public boolean updateAclaracionEmpleado(AclaracionDTO aclaracion) throws SQLException {
		String sql = "UPDATE aclaracion SET  descripcionEmpleado = '"+aclaracion.getDescripcionEmpleado()+"' "
				+ " WHERE idAclaracion = "+aclaracion.getIdAclaracion()+" ";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean estatus = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					
					manejaEventos(aclaracion.getIdAclaracion(),"Actualización de aclaración ","Actualización de aclaración", con);
					con.commit();
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}	
			}
		} catch (Exception e) {
			estatus = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return estatus;
	}
	
	@Transactional(readOnly = true)
	private byte[] findDocumento(Long id, Connection con) {
		PreparedStatement pst = null;
		ResultSet r = null;
		byte[] oldDocumento = new byte[0];
		try {
			pst = con.prepareStatement("SELECT pdf FROM documentos WHERE idDocumento=?");
			pst.setLong(1, id);
			r = pst.executeQuery();
			if (r.next()) {
				 oldDocumento =r.getBytes(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closePreparedStatemetAndResultSet(pst, r);
		}
		return oldDocumento;
	}
	
	
	@Override
	public boolean updateAclaracionDocumento(AclaracionDTO aclaracion) throws SQLException {
		String sql = "";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean estatus = true;
		ListIterator<DocumentoDTO> documentoIterator= aclaracion.getDocumentoList().listIterator();
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);	
			while(documentoIterator.hasNext()) {
				DocumentoDTO documento = documentoIterator.next();
				sql  = " update documentos set  pdf=? where idDocumento=? ";
				ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setBytes(1,  Base64Utils.decodeFromString(documento.getDocumentoString()));
				ps.setLong(2, documento.getIdDocumento());
				ps.addBatch();
				ps.executeBatch();
				
			}
			manejaEventos(aclaracion.getIdAclaracion(),"Actualización de aclaración ","Actualización de aclaración", con);
			con.commit();
	
		} catch (Exception e) {
			estatus = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return estatus;
	}
	
	

	private  List<DocumentoDTO> getDocumentosList(long id) {
		
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		List<DocumentoDTO> documentos = new ArrayList<DocumentoDTO>();
		String sql = " select idDocumento, pdf, fechaCreacion, Aclaracion_idAclaracion  from documentos "
				+ "          where Aclaracion_idAclaracion="+id+" ";
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				DocumentoDTO documento = new DocumentoDTO();
				documento.setIdDocumento(rs.getLong(1));
				byte[] documentoBytes=rs.getBytes(2);
				if (!rs.wasNull()) {
					documento.setDocumentoString(Base64.getEncoder().encodeToString(documentoBytes));
				}
				documento.setFecha(rs.getString(3));
				documento.setIdAclaracion(rs.getLong(4));
				documentos.add(documento);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return documentos;
	}
	
	
	
}
