package com.sytecso.dao.solicitud.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sytecso.component.CustomContext;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.dao.evento.DAOEvento;
import com.sytecso.dao.solicitud.DAOSolicitud;
import com.sytecso.dao.usuario.DAOUsuarioAcceso;
import com.sytecso.dto.solicitud.CalculoActuariaDTO;
import com.sytecso.dto.solicitud.CalculoActuariaHasSolicDTO;
import com.sytecso.dto.solicitud.ObservacionDTO;
import com.sytecso.dto.solicitud.OrdenPagoDTO;
import com.sytecso.dto.solicitud.OrdenPagoHasSolicitudDTO;
import com.sytecso.dto.solicitud.SolicitudAPDTO;
import com.sytecso.dto.solicitud.SolicitudHasDocumentoDTO;
import com.sytecso.dto.usuarioacceso.CatalogoAseguradosDTO;
import com.sytecso.dto.EventoDTO;
import com.sytecso.dto.EventoSolicitudDTO;
import com.sytecso.component.utility.UtileriaValidaPatrones;

@Repository
@Transactional
public class DAOSolicitudImpl implements DAOSolicitud {

	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private DAOEvento daoEvento;
	
	@Autowired
    CustomContext context;
	
	@Autowired
	private DAOUsuarioAcceso usuarioAcceso;
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public long crearSolicitud(SolicitudAPDTO solicitud) throws Exception {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        boolean status = false;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("INSERT INTO solicitud (fechaSolicitud, tipoTramite, rfcAsegurado, nombredelServidor, aPaternodelServidor, aMaternodelServidor, dependencia, telefono, email, fechaFinLaboral, " + 
            		"fechaSolicitudAPV, diasTranscurridos, importeSolicitado, nombreBanco, clabe, idBanco, observaciones,Empleado_idEmpleado, statusSolicitud, validadoModulo, validadoSiniestros, validadoContabilidad, TipoPago, rfcGEM, sueldo, fechaPago, pagoAnterior,usuariosacceso_idusuariosAcceso) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 'Nueva', 0, 0, 0, ?, ?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, solicitud.getFechaSolicitud());
            pst.setString(2, solicitud.getTipoTramite());
            pst.setString(3, solicitud.getRfcAsegurado());
            pst.setString(4, solicitud.getNombre());
            pst.setString(5, solicitud.getApellidoPaterno());
            pst.setString(6, solicitud.getApellidoMaterno());
            pst.setString(7, solicitud.getDependencia());
            pst.setString(8, solicitud.getTelefono());
            pst.setString(9, solicitud.getEmail());
            if(solicitud.getFechaFinLaboral().equals("")) {
            	pst.setNull(10, Types.NULL);
            }else {
            	pst.setString(10, solicitud.getFechaFinLaboral());            	
            }
            pst.setString(11, solicitud.getFechaSolicitudAPV());
            pst.setInt(12, solicitud.getDiasTranscurridos());
            pst.setString(13, solicitud.getImporteSolicitado());
            pst.setString(14, solicitud.getNombreBanco());
            pst.setString(15, solicitud.getClabe());
            pst.setLong(16, solicitud.getIdBanco());
            pst.setString(17, solicitud.getObservaciones());
            if(solicitud.getIdEmpleado() > 0) {
            	pst.setLong(18, solicitud.getIdEmpleado());
            }else {
            	pst.setNull(18, Types.NULL);        	
            }
            
            pst.setString(19, solicitud.getTipoPago());
            pst.setString(20, solicitud.getRfcGEM());
            pst.setString(21, solicitud.getSueldo());
            if(solicitud.getFechaPago().equals("")) {
            	pst.setNull(22, Types.NULL);
            }else {
            	pst.setString(22, solicitud.getFechaPago());            	
            }
            if(solicitud.getPagoAnterior().equals("")) {
            	pst.setNull(23, Types.NULL);
            }else {
            	pst.setString(23, solicitud.getPagoAnterior());  
            	
            }
            if(solicitud.getEmpleadoAsignacion().equals("")) {
            	solicitud.setIdAsignacion(getAsignable(connection));
            	solicitud.setEmpleadoAsignacion(usuarioAcceso.getRfcUsuarioByIdC(solicitud.getIdAsignacion(), connection));
            	pst.setLong(24, solicitud.getIdAsignacion());
            }else {
            	pst.setLong(24,usuarioAcceso.getUsuarioByRFC(solicitud.getEmpleadoAsignacion(),connection));
            }

            pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				
				solicitud.setIdSolicitud(rs.getLong(1));
				status=manejaEventos(solicitud,"Creacion","Creacion de solicitud", connection);
			}
			connection.commit();
			
        } catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			connection.setAutoCommit(true);
			UtileriaSql.closeConnectionAndCommit(connection, pst, rs, status);
		}
        
        return solicitud.getIdSolicitud();
    }
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long subirDocumento(MultipartFile file, String fechaCreacion) throws SQLException {
		
		String sql = "INSERT INTO documentos (pdf, fechaCreacion) VALUES (?, ?)";
		long idTransaccion = 0L;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setBytes(1, file.getBytes());
			ps.setString(2, fechaCreacion);
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
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
			 idTransaccion = 0L;
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return idTransaccion;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long actualizarDocumento(MultipartFile file, long idDocumento) throws SQLException {
		
		String sql = "UPDATE documentos SET pdf = ? WHERE idDocumento = ?";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setBytes(1, file.getBytes());
			ps.setLong(2, idDocumento);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idDocumento = rs.getLong(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
			idDocumento = 0L;
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return idDocumento;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long crearSolicitudHasDocumento(long idDocumento, long idSolicitud, int tipoDocumento, int tipoArchivo) throws SQLException {
		
		String sql = "INSERT INTO solicitud_has_documentos (Solicitud_idSolicitud, Documentos_idDocumentos, tipoDocumento, tipoArchivo) VALUES (?, ?, ?, ?)";
		long idTransaccion = 0L;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		SolicitudAPDTO solicitud =  new SolicitudAPDTO();
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, idSolicitud);
			ps.setLong(2, idDocumento);
			ps.setInt(3, tipoDocumento);
			ps.setInt(4, tipoArchivo);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					con.commit();
					solicitud.setIdSolicitud(idSolicitud);
					manejaEventos(solicitud,"Creacion","Creacion de documento en solicitud", con);
					
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}
				
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
			 idTransaccion = 0L;
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return idTransaccion;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean actualizarSolicitudHasDocumento(long idDocumento, long idSolicitud, int tipoArchivo) throws SQLException {
		
		String sql = "UPDATE solicitud_has_documentos SET tipoArchivo = ? WHERE Solicitud_idSolicitud = ? and Documentos_idDocumentos = ?  ";
		boolean status = true;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		SolicitudAPDTO solicitud =  new SolicitudAPDTO();
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, tipoArchivo);
			ps.setLong(2, idSolicitud);
			ps.setLong(3, idDocumento);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			solicitud.setIdSolicitud(idSolicitud);
			manejaEventos(solicitud,"Actualicación","Actualicación de documento en solicitud", con);
			
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
			 status = false;
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}
	
	@Override
	public List<SolicitudAPDTO> getSolicitudesByIdEmpleado(long idEmpleado) {
		List<SolicitudAPDTO> solicitudes= new ArrayList<SolicitudAPDTO>();
		SolicitudAPDTO solic = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT idSolicitud, fechaSolicitud, tipoTramite, rfcAsegurado, nombredelServidor, aPaternodelServidor, aMaternodelServidor, dependencia, telefono, email, fechaFinLaboral, " + 
				"fechaSolicitudAPV, diasTranscurridos, importeSolicitado, nombreBanco, clabe, idBanco, observaciones,Empleado_idEmpleado, statusSolicitud, numeroRegistro, validadoModulo, validadoSiniestros, "
				+ "validadoContabilidad, TipoPago, rfcGEM, aportacionTotal, retiroMaximo, importeApagar, importeContable, fechaOrdenPago, idEmpleadoGeneraOrden,"
				+ "fechaImporteContable, fechadeTransferencia, estPagRechPen, estatus, numChequeTransf, obsSiniestros, intereses, montoCalculado, pagoAnterior, usuariosacceso_idUsuariosAcceso FROM solicitud WHERE Empleado_idEmpleado = ? and statusSolicitud <> 'Cancelada' ";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, idEmpleado);
			rs = pst.executeQuery();
			while (rs.next()) {
				solic=new SolicitudAPDTO();
				solic.setIdSolicitud(rs.getLong("idSolicitud"));
				solic.setFechaSolicitud(rs.getString("fechaSolicitud"));
				solic.setTipoTramite(rs.getString("tipoTramite"));
				solic.setRfcAsegurado(rs.getString("rfcAsegurado"));
				solic.setNombre(rs.getString("nombredelServidor"));
				solic.setApellidoPaterno(rs.getString("aPaternodelServidor"));
				solic.setApellidoMaterno(rs.getString("aMaternodelServidor"));
				solic.setDependencia(rs.getString("dependencia"));
				solic.setTelefono(rs.getString("telefono"));
				solic.setEmail(rs.getString("email"));
				solic.setFechaFinLaboral(rs.getString("fechaFinLaboral"));
				solic.setFechaSolicitudAPV(rs.getString("fechaSolicitudAPV"));
				solic.setDiasTranscurridos(rs.getInt("diasTranscurridos"));
				solic.setImporteSolicitado(rs.getString("importeSolicitado"));
				solic.setNombreBanco(rs.getString("nombreBanco"));
				solic.setClabe(rs.getString("clabe"));
				solic.setIdBanco(rs.getLong("idBanco"));
				solic.setObservaciones(rs.getString("observaciones"));
				solic.setIdEmpleado(rs.getLong("Empleado_idEmpleado"));
				solic.setStatusSolicitud(rs.getString("statusSolicitud"));
				solic.setNumeroRegistro(rs.getInt("numeroRegistro"));
				solic.setValidadoModulo(rs.getInt("validadoModulo"));
				solic.setValidadoSiniestros(rs.getInt("validadoSiniestros"));
				solic.setValidadoContabilidad(rs.getInt("validadoContabilidad"));
				solic.setTipoPago(rs.getString("TipoPago"));
				solic.setRfcGEM(rs.getString("rfcGEM"));
				solic.setAportacionTotal(rs.getDouble("aportacionTotal"));
				solic.setRetiroMaximo(rs.getDouble("retiroMaximo"));
				solic.setImporteApagar(rs.getDouble("importeApagar"));
				solic.setImporteContable(rs.getDouble("importeContable"));
				solic.setFechaOrdenPago(rs.getString("fechaOrdenPago"));
				solic.setIdEmpleadoGeneraOrden(rs.getLong("idEmpleadoGeneraOrden"));
				solic.setFechaImporteContable(rs.getString("fechaImporteContable"));
				solic.setFechadeTransferencia(rs.getString("fechadeTransferencia"));
				solic.setEstPagRechPen(rs.getString("estPagRechPen"));
				solic.setEstatus(rs.getString("estatus"));
				solic.setNumChequeTransf(rs.getString("numChequeTransf"));
				solic.setObsSiniestros(rs.getString("obsSiniestros"));
				solic.setIntereses(rs.getDouble("intereses"));
				solic.setMontoCalculado(rs.getString("montoCalculado"));
				solic.setPagoAnterior(rs.getString("pagoAnterior"));
				solic.setEmpleadoAsignacion(usuarioAcceso.getRfcUsuarioByIdC(rs.getLong("usuariosacceso_idUsuariosAcceso"), connection));
				solicitudes.add(solic);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return solicitudes;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateEstatusSolicitud(SolicitudAPDTO solicitud) {
		boolean status = true;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		String sql = "UPDATE solicitud SET statusSolicitud = ? WHERE rfc = ?";
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, solicitud.getStatusSolicitud());
			pst.setString(2, solicitud.getRfcAsegurado());
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			
			if (rs.next()) {
				solicitud.setIdSolicitud(rs.getLong(1));
				status=manejaEventos(solicitud,"Actualización de status","Actualización de solicitud a "+solicitud.getStatusSolicitud(), con);
			}
			
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}
	
	
	@Override
	public SolicitudAPDTO getSolicitud(long idSolicitud) {
		SolicitudAPDTO solic= null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT idSolicitud, fechaSolicitud, tipoTramite, rfcAsegurado, nombredelServidor, aPaternodelServidor, aMaternodelServidor, dependencia, telefono, email, fechaFinLaboral, " + 
				"fechaSolicitudAPV, diasTranscurridos, importeSolicitado, nombreBanco, clabe, idBanco, observaciones,Empleado_idEmpleado, statusSolicitud, numeroRegistro, validadoModulo, validadoSiniestros,validadoContabilidad, TipoPago, "
				+ "rfcGEM, aportacionTotal, retiroMaximo, importeApagar, importeContable, fechaOrdenPago, idEmpleadoGeneraOrden, fechaImporteContable, fechadeTransferencia, estPagRechPen, estatus, numChequeTransf, obsSiniestros, intereses, montoCalculado, analistaComercialValida, "
				+ "pagoAnterior, sueldo, saldoFinal, valRetencion, fechaCalculo, fechaPago, usuariosacceso_idUsuariosAcceso "
				+ " FROM solicitud WHERE idSolicitud = ?  ";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, idSolicitud);
			rs = pst.executeQuery();
			while (rs.next()) {
				solic=new SolicitudAPDTO();
				solic.setIdSolicitud(rs.getLong("idSolicitud"));
				solic.setFechaSolicitud(rs.getString("fechaSolicitud"));
				solic.setTipoTramite(rs.getString("tipoTramite"));
				solic.setRfcAsegurado(rs.getString("rfcAsegurado"));
				solic.setNombre(rs.getString("nombredelServidor"));
				solic.setApellidoPaterno(rs.getString("aPaternodelServidor"));
				solic.setApellidoMaterno(rs.getString("aMaternodelServidor"));
				solic.setDependencia(rs.getString("dependencia"));
				solic.setTelefono(rs.getString("telefono"));
				solic.setEmail(rs.getString("email"));
				solic.setFechaFinLaboral(rs.getString("fechaFinLaboral"));
				solic.setFechaSolicitudAPV(rs.getString("fechaSolicitudAPV"));
				solic.setDiasTranscurridos(rs.getInt("diasTranscurridos"));
				solic.setImporteSolicitado(rs.getString("importeSolicitado"));
				solic.setNombreBanco(rs.getString("nombreBanco"));
				solic.setClabe(rs.getString("clabe"));
				solic.setIdBanco(rs.getLong("idBanco"));
				solic.setObservaciones(rs.getString("observaciones"));
				solic.setIdEmpleado(rs.getLong("Empleado_idEmpleado"));
				solic.setStatusSolicitud(rs.getString("statusSolicitud"));
				solic.setNumeroRegistro(rs.getInt("numeroRegistro"));
				solic.setValidadoModulo(rs.getInt("validadoModulo"));
				solic.setValidadoSiniestros(rs.getInt("validadoSiniestros"));
				solic.setValidadoContabilidad(rs.getInt("validadoContabilidad"));
				solic.setTipoPago(rs.getString("TipoPago"));
				solic.setRfcGEM(rs.getString("rfcGEM"));
				solic.setAportacionTotal(rs.getDouble("aportacionTotal"));
				solic.setRetiroMaximo(rs.getDouble("retiroMaximo"));
				solic.setImporteApagar(rs.getDouble("importeApagar"));
				solic.setImporteContable(rs.getDouble("importeContable"));
				solic.setFechaOrdenPago(rs.getString("fechaOrdenPago"));
				solic.setIdEmpleadoGeneraOrden(rs.getLong("idEmpleadoGeneraOrden"));
				solic.setFechaImporteContable(rs.getString("fechaImporteContable"));
				solic.setFechadeTransferencia(rs.getString("fechadeTransferencia"));
				solic.setEstPagRechPen(rs.getString("estPagRechPen"));
				solic.setEstatus(rs.getString("estatus"));
				solic.setNumChequeTransf(rs.getString("numChequeTransf"));
				solic.setObsSiniestros(rs.getString("obsSiniestros"));
				solic.setIntereses(rs.getDouble("intereses"));
				solic.setMontoCalculado(rs.getString("montoCalculado"));
				solic.setAnalistaComercialValida(rs.getString("analistaComercialValida"));
				solic.setPagoAnterior(rs.getString("pagoAnterior"));
				solic.setSueldo(rs.getString("sueldo"));
				solic.setSaldoFinal(rs.getString("saldoFinal"));
				solic.setFechaCalculo(rs.getString("fechaCalculo"));
				solic.setValRetencion(rs.getString("valRetencion"));
				solic.setFechaPago(rs.getString("fechaPago"));
				solic.setDocumentos(getUrlArray(idSolicitud));
				solic.setListObs(getObservacionesSolicitud(idSolicitud));	
				solic.setEmpleadoAsignacion(usuarioAcceso.getRfcUsuarioByIdC(rs.getLong("usuariosacceso_idUsuariosAcceso"), connection));
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return solic;
	}
	
	
	public List<SolicitudHasDocumentoDTO> getUrlArray(long idSolicitud) {
		List<SolicitudHasDocumentoDTO> doc = new ArrayList<SolicitudHasDocumentoDTO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String query = "SELECT Documentos_idDocumentos, tipoDocumento, tipoArchivo FROM solicitud_has_documentos WHERE Solicitud_idSolicitud = ?";
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);
			ps.setLong(1, idSolicitud);
			rs = ps.executeQuery();
			while (rs.next()) {
				SolicitudHasDocumentoDTO pdf = new SolicitudHasDocumentoDTO();
				pdf.setIdDocumento(rs.getLong("Documentos_idDocumentos"));
				pdf.setTipoDocumento(rs.getInt("tipoDocumento"));
				pdf.setTipoArchivo(rs.getLong("tipoArchivo"));
				doc.add(pdf);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, ps, rs);
		}
		return doc;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public SolicitudAPDTO findDocumentoSolicitud(Long id) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		SolicitudAPDTO solic = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement("SELECT pdf FROM documentos WHERE idDocumento=?");
			pst.setLong(1, id);
			r = pst.executeQuery();
			if (r.next()) {
				solic = new SolicitudAPDTO();
				solic.setPdf(r.getBytes(1));
				solic.setStringPdf(r.getString(1));
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, r);
		}
		return solic;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean actualizarSolicitud(SolicitudAPDTO solicitud) throws SQLException {
		
		String sql = "UPDATE solicitud SET tipoTramite = ?, rfcAsegurado = ?, nombredelServidor = ?, aPaternodelServidor = ?, aMaternodelServidor = ?, dependencia = ?, telefono = ?, email = ?, fechaFinLaboral = ?, " + 
				"fechaSolicitudAPV = ?, diasTranscurridos = ?, importeSolicitado = ?, nombreBanco = ?, clabe = ?, idBanco = ?, observaciones = ?, statusSolicitud = ?, TipoPago = ?, rfcGEM = ?, sueldo = ?, fechaPago = ?, pagoAnterior = ? WHERE idSolicitud = ?";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean status = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, solicitud.getTipoTramite());
			ps.setString(2, solicitud.getRfcAsegurado());
			ps.setString(3, solicitud.getNombre());
			ps.setString(4, solicitud.getApellidoPaterno());
			ps.setString(5, solicitud.getApellidoMaterno());
			ps.setString(6, solicitud.getDependencia());
			ps.setString(7, solicitud.getTelefono());
			ps.setString(8, solicitud.getEmail());
			if(solicitud.getFechaFinLaboral() != null) {
				ps.setString(9, solicitud.getFechaFinLaboral());            	
            }else {
            	ps.setNull(9, Types.NULL);
            }
			ps.setString(10, solicitud.getFechaSolicitudAPV());
			ps.setDouble(11, solicitud.getDiasTranscurridos());
			ps.setString(12, solicitud.getImporteSolicitado());
			ps.setString(13, solicitud.getNombreBanco());
			ps.setString(14, solicitud.getClabe());
			ps.setLong(15, solicitud.getIdBanco());
			ps.setString(16, solicitud.getObservaciones());
			ps.setString(17, solicitud.getStatusSolicitud());
			ps.setString(18, solicitud.getTipoPago());
			ps.setString(19, solicitud.getRfcGEM());
			ps.setString(20, solicitud.getSueldo());
			if(solicitud.getFechaPago() != null) {
				ps.setString(21, solicitud.getFechaPago());            	
            }else {
            	ps.setNull(21, Types.NULL);
            }
			if(solicitud.getPagoAnterior() != null) {
				ps.setString(22, solicitud.getPagoAnterior());            	
            }else {
            	ps.setNull(22, Types.NULL);
            }
			ps.setLong(23, solicitud.getIdSolicitud());
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					con.commit();
					solicitud.setIdSolicitud(idTransaccion);
					status=manejaEventos(solicitud,"Actualización","Actualización de datos e solicitud", con);
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}
				
			}
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}
	
	
	@Override
	public List<SolicitudAPDTO> getSolicitudesAnalistas(String params, String paramsSol, boolean flag) {
		List<SolicitudAPDTO> solicitudes= new ArrayList<SolicitudAPDTO>();
		SolicitudAPDTO solic = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		/*String sql = "SELECT idSolicitud, fechaSolicitud, tipoTramite, rfcAsegurado, nombredelServidor, aPaternodelServidor, aMaternodelServidor, dependencia, telefono, email, fechaFinLaboral, " + 
				"fechaSolicitudAPV, diasTranscurridos, importeSolicitado, nombreBanco, clabe, idBanco, observaciones,Empleado_idEmpleado, statusSolicitud, numeroRegistro, validadoModulo, validadoSiniestros, "
				+ "validadoContabilidad, TipoPago, rfcGEM, aportacionTotal, retiroMaximo, importeApagar, importeContable, fechaOrdenPago, idEmpleadoGeneraOrden, "
				+ "fechaImporteContable, fechadeTransferencia, estPagRechPen, estatus, numChequeTransf, obsSiniestros, intereses, montoCalculado, pagoAnterior FROM solicitud WHERE statusSolicitud <> 'Cancelada' ";

		*/
		String sql ="SELECT sol.idSolicitud, sol.fechaSolicitud, sol.tipoTramite, sol.rfcAsegurado, sol.nombredelServidor, sol.aPaternodelServidor, sol.aMaternodelServidor, sol.dependencia, sol.telefono, sol.email, sol.fechaFinLaboral,   "
				+ "sol.fechaSolicitudAPV, sol.diasTranscurridos, sol.importeSolicitado, sol.nombreBanco, sol.clabe, sol.idBanco, sol.observaciones,sol.Empleado_idEmpleado, sol.statusSolicitud, sol.numeroRegistro, sol.validadoModulo, sol.validadoSiniestros,  "
				+ "sol.validadoContabilidad, sol.TipoPago, sol.rfcGEM, sol.aportacionTotal, sol.retiroMaximo, sol.importeApagar, sol.importeContable, sol.fechaOrdenPago, sol.idEmpleadoGeneraOrden,  "
				+ "sol.fechaImporteContable, sol.fechadeTransferencia, sol.estPagRechPen, sol.estatus, sol.numChequeTransf, sol.obsSiniestros, sol.intereses, sol.montoCalculado, sol.pagoAnterior, "
				+ "replace(replace(epap.sexo,'HOMBRE','M'),'MUJER','F'), epap.fechaNacimiento, EmpleadoRegistra(sol.rfcGEM), getOrdenesPago(sol.idSolicitud),getcalculoActuaria(sol.idSolicitud), sol.analistaComercialValida, sol.fechaCalculo, sol.saldoFinal, sol.sueldo, sol.valRetencion, sol.usuariosacceso_idUsuariosAcceso "
				+ "FROM solicitud sol, empleado_ap epap "
				+ "where epap.idEmpleadoAP=sol.empleado_idEmpleado "
				+ "and  statusSolicitud <> 'Cancelada' ";
		if(!(params.equals(""))) {
			sql=sql+params;
		}
		//System.out.println(sql);
		if(!flag) {
			sql=sql+"union "
					+"SELECT sol.idSolicitud, sol.fechaSolicitud, sol.tipoTramite, sol.rfcAsegurado, sol.nombredelServidor, sol.aPaternodelServidor, sol.aMaternodelServidor, sol.dependencia, sol.telefono, sol.email, sol.fechaFinLaboral,   "
					+"sol.fechaSolicitudAPV, sol.diasTranscurridos, sol.importeSolicitado, sol.nombreBanco, sol.clabe, sol.idBanco, sol.observaciones,sol.Empleado_idEmpleado, sol.statusSolicitud, sol.numeroRegistro, sol.validadoModulo, sol.validadoSiniestros, " 
					+"sol.validadoContabilidad, sol.TipoPago, sol.rfcGEM, sol.aportacionTotal, sol.retiroMaximo, sol.importeApagar, sol.importeContable, sol.fechaOrdenPago, sol.idEmpleadoGeneraOrden,  "
					+"sol.fechaImporteContable, sol.fechadeTransferencia, sol.estPagRechPen, sol.estatus, sol.numChequeTransf, sol.obsSiniestros, sol.intereses, sol.montoCalculado, sol.pagoAnterior, "
					+"'' as sexo, '' as fechaNacimiento , EmpleadoRegistra(sol.rfcGEM), getOrdenesPago(sol.idSolicitud),getcalculoActuaria(sol.idSolicitud), sol.analistaComercialValida, sol.fechaCalculo, sol.saldoFinal, sol.sueldo, sol.valRetencion, sol.usuariosacceso_idUsuariosAcceso  "
					+"FROM solicitud sol "
					+"where sol.empleado_idEmpleado  is null "
					+"and  statusSolicitud <> 'Cancelada' ";
			if(!params.equals("")) {
				sql=sql+paramsSol;
			}
		}
		//System.out.println(sql);
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				solic=new SolicitudAPDTO();
				solic.setIdSolicitud(rs.getLong("idSolicitud"));
				solic.setFechaSolicitud(rs.getString("fechaSolicitud"));
				solic.setTipoTramite(rs.getString("tipoTramite"));
				solic.setRfcAsegurado(rs.getString("rfcAsegurado"));
				solic.setNombre(rs.getString("nombredelServidor"));
				solic.setApellidoPaterno(rs.getString("aPaternodelServidor"));
				solic.setApellidoMaterno(rs.getString("aMaternodelServidor"));
				solic.setDependencia(rs.getString("dependencia"));
				solic.setTelefono(rs.getString("telefono"));
				solic.setEmail(rs.getString("email"));
				solic.setFechaFinLaboral(rs.getString("fechaFinLaboral"));
				solic.setFechaSolicitudAPV(rs.getString("fechaSolicitudAPV"));
				solic.setDiasTranscurridos(rs.getInt("diasTranscurridos"));
				solic.setImporteSolicitado(rs.getString("importeSolicitado"));
				solic.setNombreBanco(rs.getString("nombreBanco"));
				solic.setClabe(rs.getString("clabe"));
				solic.setIdBanco(rs.getLong("idBanco"));
				solic.setObservaciones(rs.getString("observaciones"));
				solic.setIdEmpleado(rs.getLong("Empleado_idEmpleado"));
				solic.setStatusSolicitud(rs.getString("statusSolicitud"));
				solic.setNumeroRegistro(rs.getInt("numeroRegistro"));
				solic.setValidadoModulo(rs.getInt("validadoModulo"));
				solic.setValidadoSiniestros(rs.getInt("validadoSiniestros"));
				solic.setValidadoContabilidad(rs.getInt("validadoContabilidad"));
				solic.setTipoPago(rs.getString("TipoPago"));
				solic.setRfcGEM(rs.getString("rfcGEM"));
				solic.setAportacionTotal(rs.getDouble("aportacionTotal"));
				solic.setRetiroMaximo(rs.getDouble("retiroMaximo"));
				solic.setImporteApagar(rs.getDouble("importeApagar"));
				solic.setImporteContable(rs.getDouble("importeContable"));
				solic.setFechaOrdenPago(rs.getString("fechaOrdenPago"));
				solic.setIdEmpleadoGeneraOrden(rs.getLong("idEmpleadoGeneraOrden"));
				solic.setFechaImporteContable(rs.getString("fechaImporteContable"));
				solic.setFechadeTransferencia(rs.getString("fechadeTransferencia"));
				solic.setEstPagRechPen(rs.getString("estPagRechPen"));
				solic.setEstatus(rs.getString("estatus"));
				solic.setNumChequeTransf(rs.getString("numChequeTransf"));
				solic.setObsSiniestros(rs.getString("obsSiniestros"));
				solic.setIntereses(rs.getDouble("intereses"));
				solic.setMontoCalculado(rs.getString("montoCalculado"));
				solic.setPagoAnterior(rs.getString("pagoAnterior"));
				solic.setSexo(rs.getString(42));
				solic.setFechaNac(rs.getString(43));
				solic.setNombreEmpleadoGeneraOrden(rs.getString(44));
				solic.setIdOrdenPago(rs.getLong(45));
				solic.setIdCalculoActuaria(rs.getLong(46));
				solic.setAnalistaComercialValida(rs.getString(47));
				solic.setFechaCalculo(rs.getString(48));
				solic.setSaldoFinal(rs.getString(49));
				solic.setSueldo(rs.getString(50));
				solic.setValRetencion(rs.getString(51));
				solic.setEmpleadoAsignacion(usuarioAcceso.getRfcUsuarioByIdC(rs.getLong("usuariosacceso_idUsuariosAcceso"), connection));
				solicitudes.add(solic);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return solicitudes;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateEstatusSolicitudAnalistas(SolicitudAPDTO solicitud) {
		boolean status = true;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		String sql = "UPDATE solicitud SET statusSolicitud = ?, validadoModulo = ?, validadoSiniestros = ?, validadoContabilidad = ?, rfcGEM = ?, analistaComercialValida = ? WHERE idSolicitud = ?";

		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, solicitud.getStatusSolicitud());
			pst.setInt(2,  solicitud.getValidadoModulo());
			pst.setInt(3,  solicitud.getValidadoSiniestros());
			pst.setInt(4,  solicitud.getValidadoContabilidad());
			pst.setString(5, solicitud.getRfcGEM());
			pst.setString(6, solicitud.getAnalistaComercialValida());
			pst.setLong(7, solicitud.getIdSolicitud());
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				solicitud.setIdSolicitud(rs.getLong(1));
				status=manejaEventos(solicitud,"Actualización de status","Actualiación de status de solicitud analistas a"+solicitud.getStatusSolicitud(), con);
			}
			
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}
	
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public long crearObservacionSolicitud(ObservacionDTO obs) throws Exception {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        long idObs = 0L;
        boolean status = false;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("INSERT INTO observaciones (observacion, fechaCreacion) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, obs.getObservacion());
            pst.setString(2, obs.getFechaCreacion());
            pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				idObs = rs.getLong(1);
				status = true;
			}
        } catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnectionAndCommit(connection, pst, rs, status);
		}
        
        return idObs;
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long crearSolicitudHasObservacion(long idObs, long idSolicitud) throws SQLException {
		
		String sql = "INSERT INTO solicitud_has_observaciones (Observaciones_idObservaciones, Solicitud_idSolicitud) VALUES (?, ?)";
		long idTransaccion = 0L;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		SolicitudAPDTO solicitud = new SolicitudAPDTO();
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, idObs);
			ps.setLong(2, idSolicitud);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					con.commit();
					solicitud.setIdSolicitud(idSolicitud);
					manejaEventos(solicitud,"Creacion","Creación de observación en solicitud ", con);
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}
				
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
			 idTransaccion = 0L;
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return idTransaccion;
	}
	
	
	@Override
	public List<ObservacionDTO> getObservacionesSolicitud(long idSolicitud) {
		List<ObservacionDTO> observaciones= new ArrayList<ObservacionDTO>();
		ObservacionDTO obs = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT obs.idObservacion, obs.fechaCreacion, obs.observacion FROM solicitud_has_observaciones sho, observaciones obs WHERE sho.Observaciones_idObservaciones = obs.idObservacion and Solicitud_idSolicitud = ?";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, idSolicitud);
			rs = pst.executeQuery();
			while (rs.next()) {
				obs=new ObservacionDTO();
				obs.setFechaCreacion(rs.getString("fechaCreacion"));
				obs.setIdObservacion(rs.getLong("idObservacion"));
				obs.setObservacion(rs.getString("observacion"));
				observaciones.add(obs);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return observaciones;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean validarImportes(SolicitudAPDTO solicitud) throws SQLException {
		
		String sql = "UPDATE solicitud SET aportacionTotal = ?, retiroMaximo = ?, importeApagar = ?, importeContable = ?, statusSolicitud = ?, intereses = ?, montoCalculado = ?, "
				+ "pagoAnterior = ?, sueldo = ?, saldoFinal = ?, valRetencion = ?, fechaCalculo = ? WHERE idSolicitud = ?";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean status = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, solicitud.getAportacionTotal());
			ps.setDouble(2, solicitud.getRetiroMaximo());
			ps.setDouble(3, solicitud.getImporteApagar());
			ps.setDouble(4, solicitud.getImporteContable());
			ps.setString(5, solicitud.getStatusSolicitud());
			ps.setDouble(6, solicitud.getIntereses());
			ps.setString(7, solicitud.getMontoCalculado());
			ps.setString(8, solicitud.getPagoAnterior());
			ps.setString(9, solicitud.getSueldo());
			ps.setString(10, solicitud.getSaldoFinal());
			ps.setString(11, solicitud.getValRetencion());
			ps.setString(12, solicitud.getFechaCalculo());
			ps.setLong(13, solicitud.getIdSolicitud());
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					
					con.commit();
					solicitud.setIdSolicitud(solicitud.getIdSolicitud());
					manejaEventos(solicitud,"Actualización","Actualización  de importes solicitud ", con);
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}
				
			}
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateFechaOrdenPagoSolicitud(SolicitudAPDTO solicitud) throws SQLException {
		
		String sql = "UPDATE solicitud SET fechaOrdenPago = ?, idEmpleadoGeneraOrden = ? WHERE idSolicitud = ?";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean status = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, solicitud.getFechaOrdenPago());
			ps.setLong(2, solicitud.getIdEmpleadoGeneraOrden());
			ps.setLong(3, solicitud.getIdSolicitud());
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					
					con.commit();
					solicitud.setIdSolicitud(solicitud.getIdSolicitud());
					manejaEventos(solicitud,"Actualización","Actualización de fecha orden de pago de solicitud ", con);
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}
				
			}
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean informacionPago(SolicitudAPDTO solicitud) throws SQLException {
		
		String sql = "UPDATE solicitud SET importeApagar = ?, fechaImporteContable = ?, fechadeTransferencia = ?, estPagRechPen = ?, estatus = ?, numChequeTransf = ?, obsSiniestros = ?, statusSolicitud = ? WHERE idSolicitud = ?";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean status = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, solicitud.getImporteApagar());
			ps.setString(2, solicitud.getFechaImporteContable());
			ps.setString(3, solicitud.getFechadeTransferencia());
			ps.setString(4, solicitud.getEstPagRechPen());
			ps.setString(5, solicitud.getEstPagRechPen());
			ps.setString(6, solicitud.getNumChequeTransf());
			ps.setString(7, solicitud.getObsSiniestros());
			ps.setString(8, solicitud.getStatusSolicitud());
			ps.setLong(9, solicitud.getIdSolicitud());
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					
					con.commit();
					solicitud.setIdSolicitud(solicitud.getIdSolicitud());
					manejaEventos(solicitud,"Actualización","Actualización de información de pago solicitud ", con);
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}
				
			}
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}


	@Override
	public List<CatalogoAseguradosDTO> getCatAsegurados() {
		List<CatalogoAseguradosDTO> cat= new ArrayList<CatalogoAseguradosDTO>();
		CatalogoAseguradosDTO c = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "select idcat_Asegurados, rfc, curp, apellido_paterno, apellido_materno, nombre from cat_asegurados";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				c=new CatalogoAseguradosDTO();
				c.setIdcat_Asegurados(rs.getLong(1));
				c.setRfc(rs.getString(2));
				c.setCurp(rs.getString(3));
				c.setApellidoPaterno(rs.getString(4));
				c.setApellidoMaterno(rs.getString(5));
				c.setNombre(rs.getString(6));
				cat.add(c);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return cat;
	}
	
	@Override
	public List<SolicitudAPDTO> getValidarSolicitudRFC(String rfc) {
		List<SolicitudAPDTO> solicitudes= new ArrayList<SolicitudAPDTO>();
		SolicitudAPDTO solic = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT numeroRegistro, fechaSolicitud, idSolicitud, statusSolicitud, rfcGEM FROM solicitud WHERE statusSolicitud <> 'Cancelada' " + 
				"and statusSolicitud <> 'Terminada' and statusSolicitud <> 'Rechazada' and rfcGEM = ? order by fechaSolicitud DESC";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, rfc);
			rs = pst.executeQuery();
			while (rs.next()) {
				solic=new SolicitudAPDTO();
				solic.setIdSolicitud(rs.getLong("idSolicitud"));
				solic.setFechaSolicitud(rs.getString("fechaSolicitud"));
				solic.setNumeroRegistro(rs.getInt("numeroRegistro"));
				solic.setStatusSolicitud(rs.getString("statusSolicitud"));
				solic.setRfcGEM(rs.getString("rfcGEM"));
				solic.setSolicActiva(true);
				solicitudes.add(solic);
			}

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return solicitudes;
	}
	
	
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public long crearOrdenPago(OrdenPagoDTO orden) throws Exception {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        long idOrden = 0L;
        boolean status = false;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("INSERT INTO ordenes_pago (fechaCreacion, idEmpleadoGeneraOrden) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, orden.getFechaCreacion());
            pst.setLong(2, orden.getIdEmpleadoGenera());
            pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				idOrden = rs.getLong(1);
				status = true;
			}
        } catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnectionAndCommit(connection, pst, rs, status);
		}
        
        return idOrden;
    }
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public long crearOrdenPagoSolicitud(OrdenPagoHasSolicitudDTO orden) throws Exception {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        long idOrden = 0L;
        boolean status = false;
        SolicitudAPDTO solicitud = new SolicitudAPDTO();
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("INSERT INTO ordenes_pago_has_solicitud (OrdenesPago_idOrdenPago, Solicitud_idSolicitud) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setLong(1, orden.getIdOrdenPago());
            pst.setLong(2, orden.getIdSolicitud());
            pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				idOrden = rs.getLong(1);
				solicitud.setIdSolicitud(orden.getIdSolicitud());
				status=manejaEventos(solicitud,"Creación","Creación de  orden de pago ", connection);
			}
        } catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnectionAndCommit(connection, pst, rs, status);
		}
        
        return idOrden;
    }
	
	
	@Override
	public OrdenPagoDTO getOrdenPago(long idSolicitud) {
		OrdenPagoDTO orden = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT op.idOrdenesPago, op.fechaCreacion FROM ordenes_pago op, ordenes_pago_has_solicitud ophs, solicitud s " + 
				"WHERE op.idOrdenesPago = ophs.OrdenesPago_idOrdenPago and s.idSolicitud = ophs.Solicitud_idSolicitud " + 
				"and s.idSolicitud = ? ";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, idSolicitud);
			rs = pst.executeQuery();
			while (rs.next()) {
				orden=new OrdenPagoDTO();
				orden.setIdOrdenPago(rs.getLong("idOrdenesPago"));
				orden.setFechaCreacion(rs.getString("fechaCreacion"));
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return orden;
	}
	
	
	@Override
	public List<SolicitudAPDTO> getDataReport(long idOrdenPago) {
		List<SolicitudAPDTO> solicitudes= new ArrayList<SolicitudAPDTO>();
		SolicitudAPDTO solic = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT s.idSolicitud, s.fechaSolicitud, s.tipoTramite, s.rfcAsegurado, s.nombredelServidor, s.aPaternodelServidor, s.aMaternodelServidor, s.dependencia, s.telefono, s.email, s.fechaFinLaboral,  " + 
				"s.fechaSolicitudAPV, s.diasTranscurridos, s.importeSolicitado, s.nombreBanco, s.clabe, s.idBanco, s.observaciones,s.Empleado_idEmpleado, s.statusSolicitud, s.numeroRegistro, s.validadoModulo, s.validadoSiniestros, " + 
				"s.validadoContabilidad, s.TipoPago, s.rfcGEM, s.aportacionTotal, s.retiroMaximo, s.importeApagar, s.importeContable, s.fechaOrdenPago, s.idEmpleadoGeneraOrden, " + 
				"s.fechaImporteContable, s.fechadeTransferencia, s.estPagRechPen, s.estatus, s.numChequeTransf, s.obsSiniestros, op.folioOrden, op.fechaCreacion as fechaCreacionOrdenPago " + 
				"FROM solicitud s, ordenes_pago_has_solicitud ophs, ordenes_pago op WHERE s.idSolicitud = ophs.Solicitud_idSolicitud and op.idOrdenesPago = ophs.OrdenesPago_idOrdenPago and ophs.OrdenesPago_idOrdenPago = ? ";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, idOrdenPago);
			rs = pst.executeQuery();
			while (rs.next()) {
				solic=new SolicitudAPDTO();
				solic.setIdSolicitud(rs.getLong("idSolicitud"));
				solic.setFechaSolicitud(rs.getString("fechaSolicitud"));
				solic.setTipoTramite(rs.getString("tipoTramite"));
				solic.setRfcAsegurado(rs.getString("rfcAsegurado"));
				solic.setNombre(rs.getString("nombredelServidor"));
				solic.setApellidoPaterno(rs.getString("aPaternodelServidor"));
				solic.setApellidoMaterno(rs.getString("aMaternodelServidor"));
				solic.setDependencia(rs.getString("dependencia"));
				solic.setTelefono(rs.getString("telefono"));
				solic.setEmail(rs.getString("email"));
				solic.setFechaFinLaboral(rs.getString("fechaFinLaboral"));
				solic.setFechaSolicitudAPV(rs.getString("fechaSolicitudAPV"));
				solic.setDiasTranscurridos(rs.getInt("diasTranscurridos"));
				solic.setImporteSolicitado(rs.getString("importeSolicitado"));
				solic.setNombreBanco(rs.getString("nombreBanco"));
				solic.setClabe(rs.getString("clabe"));
				solic.setIdBanco(rs.getLong("idBanco"));
				solic.setObservaciones(rs.getString("observaciones"));
				solic.setIdEmpleado(rs.getLong("Empleado_idEmpleado"));
				solic.setStatusSolicitud(rs.getString("statusSolicitud"));
				solic.setNumeroRegistro(rs.getInt("numeroRegistro"));
				solic.setValidadoModulo(rs.getInt("validadoModulo"));
				solic.setValidadoSiniestros(rs.getInt("validadoSiniestros"));
				solic.setValidadoContabilidad(rs.getInt("validadoContabilidad"));
				solic.setTipoPago(rs.getString("TipoPago"));
				solic.setRfcGEM(rs.getString("rfcGEM"));
				solic.setAportacionTotal(rs.getDouble("aportacionTotal"));
				solic.setRetiroMaximo(rs.getDouble("retiroMaximo"));
				solic.setImporteApagar(rs.getDouble("importeApagar"));
				solic.setImporteContable(rs.getDouble("importeContable"));
				solic.setFechaOrdenPago(rs.getString("fechaCreacionOrdenPago"));
				solic.setIdEmpleadoGeneraOrden(rs.getLong("idEmpleadoGeneraOrden"));
				solic.setFechaImporteContable(rs.getString("fechaImporteContable"));
				solic.setFechadeTransferencia(rs.getString("fechadeTransferencia"));
				solic.setEstPagRechPen(rs.getString("estPagRechPen"));
				solic.setEstatus(rs.getString("estatus"));
				solic.setNumChequeTransf(rs.getString("numChequeTransf"));
				solic.setObsSiniestros(rs.getString("obsSiniestros"));
				solic.setFolioOrdenPago(rs.getString("folioOrden"));
				solicitudes.add(solic);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return solicitudes;
	}
	
	
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public long crearCalculoActuaria(CalculoActuariaDTO cal) throws Exception {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        long idCalcula = 0L;
        boolean status = false;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("INSERT INTO calculo_actuaria (fechaCreacion, idEmpleadoGenera, numRegistros) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cal.getFechaCreacion());
            pst.setLong(2, cal.getIdEmpleadoGenera());
            pst.setLong(3, cal.getNumRegistros());
            pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				idCalcula = rs.getLong(1);
				status = true;
			}
        } catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnectionAndCommit(connection, pst, rs, status);
		}
        
        return idCalcula;
    }
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public long crearCalculoActuariaSolicitud(CalculoActuariaHasSolicDTO calculoSol) throws Exception {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        long idCal = 0L;
        boolean status = false;
        SolicitudAPDTO solicitud = new SolicitudAPDTO();
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("INSERT INTO calculo_actuaria_has_solicitud (CalculoActuaria_idCalculo, Solicitud_idSolicitud) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setLong(1, calculoSol.getIdCalculoActuaria());
            pst.setLong(2, calculoSol.getIdSolicitud());
            pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				idCal = rs.getLong(1);
				solicitud.setIdSolicitud(calculoSol.getIdSolicitud());
				status=manejaEventos(solicitud,"Creación","Creación cálculo actuaría ", connection);
			}
        } catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnectionAndCommit(connection, pst, rs, status);
		}
        
        return idCal;
    }
	
	
	@Override
	public CalculoActuariaDTO getCalculoActuaria(long idSolicitud) {
		CalculoActuariaDTO calculo = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT ca.idCalculoActuaria, ca.fechaCreacion, ca.registrosCargados FROM calculo_actuaria ca, calculo_actuaria_has_solicitud cahs, solicitud s " + 
				"WHERE ca.idCalculoActuaria = cahs.CalculoActuaria_idCalculo and s.idSolicitud = cahs.Solicitud_idSolicitud " + 
				"and s.idSolicitud = ? ";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, idSolicitud);
			rs = pst.executeQuery();
			while (rs.next()) {
				calculo=new CalculoActuariaDTO();
				calculo.setIdCalculo(rs.getLong("idCalculoActuaria"));
				calculo.setFechaCreacion(rs.getString("fechaCreacion"));
				calculo.setRegistrosCargados(rs.getLong("registrosCargados"));
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return calculo;
	}
	
	@Override
	public CalculoActuariaDTO getCalculoActuariaByFolioSolicitud(long folio) {
		CalculoActuariaDTO calculo = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT ca.idCalculoActuaria, ca.fechaCreacion, ca.registrosCargados, ca.numRegistros FROM calculo_actuaria ca, calculo_actuaria_has_solicitud cahs, solicitud s " + 
				"WHERE ca.idCalculoActuaria = cahs.CalculoActuaria_idCalculo and s.idSolicitud = cahs.Solicitud_idSolicitud " + 
				"and s.numeroRegistro = ? ";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, folio);
			rs = pst.executeQuery();
			while (rs.next()) {
				calculo=new CalculoActuariaDTO();
				calculo.setIdCalculo(rs.getLong("idCalculoActuaria"));
				calculo.setFechaCreacion(rs.getString("fechaCreacion"));
				calculo.setRegistrosCargados(rs.getLong("registrosCargados"));
				calculo.setNumRegistros(rs.getLong("numRegistros"));
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return calculo;
	}
	
	@Override
	public List<SolicitudAPDTO> getDataCalculoActuaria(long idCalculo) {
		List<SolicitudAPDTO> solicitudes= new ArrayList<SolicitudAPDTO>();
		SolicitudAPDTO solic = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT ca.fechaCreacion, ca.numProceso, s.idSolicitud, s.fechaSolicitud, s.tipoTramite, s.rfcAsegurado, s.nombredelServidor, s.aPaternodelServidor, s.aMaternodelServidor, s.dependencia, s.email, s.fechaFinLaboral,  " + 
				"s.fechaSolicitudAPV, s.statusSolicitud, s.numeroRegistro, s.TipoPago, s.rfcGEM, s.analistaComercialValida, s.importeSolicitado, s.sueldo, s.fechaPago, s.pagoAnterior FROM calculo_actuaria ca, calculo_actuaria_has_solicitud cahs, solicitud s  " + 
				"WHERE ca.idCalculoActuaria = cahs.CalculoActuaria_idCalculo  " + 
				"and cahs.Solicitud_idSolicitud = s.idSolicitud and ca.idCalculoActuaria = ? ";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, idCalculo);
			rs = pst.executeQuery();
			while (rs.next()) {
				solic=new SolicitudAPDTO();
				solic.setFechaCreacionCalculo(rs.getString("fechaCreacion"));
				solic.setNumProcesoCalculo(rs.getString("numProceso"));
				solic.setIdSolicitud(rs.getLong("idSolicitud"));
				solic.setFechaSolicitud(rs.getString("fechaSolicitud"));
				solic.setTipoTramite(rs.getString("tipoTramite"));
				solic.setRfcAsegurado(rs.getString("rfcAsegurado"));
				solic.setNombre(rs.getString("nombredelServidor"));
				solic.setApellidoPaterno(rs.getString("aPaternodelServidor"));
				solic.setApellidoMaterno(rs.getString("aMaternodelServidor"));
				solic.setDependencia(rs.getString("dependencia"));
				solic.setEmail(rs.getString("email"));
				solic.setFechaFinLaboral(rs.getString("fechaFinLaboral"));
				solic.setFechaSolicitudAPV(rs.getString("fechaSolicitudAPV"));
				solic.setStatusSolicitud(rs.getString("statusSolicitud"));
				solic.setNumeroRegistro(rs.getInt("numeroRegistro"));
				solic.setTipoPago(rs.getString("TipoPago"));
				solic.setRfcGEM(rs.getString("rfcGEM"));
				solic.setAnalistaComercialValida(rs.getString("analistaComercialValida"));
				solic.setImporteSolicitado(rs.getString("importeSolicitado"));
				solic.setSueldo(rs.getString("sueldo"));
				solic.setFechaPago(rs.getString("fechaPago"));
				solic.setPagoAnterior(rs.getString("pagoAnterior"));
				solicitudes.add(solic);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return solicitudes;
	}
	
	@Override
	public SolicitudAPDTO getStatusSolicitudByFolio(long folio) {
		SolicitudAPDTO solic = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT statusSolicitud FROM solicitud WHERE numeroRegistro = ?";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, folio);
			rs = pst.executeQuery();
			while (rs.next()) {
				solic=new SolicitudAPDTO();
				solic.setStatusSolicitud(rs.getString("statusSolicitud"));
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return solic;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateImportesSolicitudLayout(SolicitudAPDTO solicitud) throws SQLException {
		
		String sql = "UPDATE solicitud SET aportacionTotal = ?, quin74M = ?, quincAgoFeb = ?, intereses = ?, importeApagar = ?, montoCalculado = ?, " + 
				"faltanteAPagar = ?, valorQuincValidar = ?, statusSolicitud = 'Importes validados', sueldo = ?,  pagoAnterior = ?, totalPagado = ?, "
				+ "fechaPago = ?, observacionesContable = ?, saldoFinal = ?, valRetencion = ?, fechaCalculo = ?, estatus = ?, retiroMaximo = ?, importeContable = ? " + 
				"WHERE numeroRegistro = ?";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean status = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, solicitud.getAportacionTotal());
			ps.setString(2, solicitud.getQuinM());
			ps.setString(3, solicitud.getQuincAgoFeb());
			ps.setDouble(4, solicitud.getIntereses());
			ps.setDouble(5, solicitud.getImporteApagar());
			ps.setString(6, solicitud.getMontoCalculado());
			ps.setString(7, solicitud.getFaltanteAPagar());
			ps.setString(8, solicitud.getValorQuincValidar());
			ps.setString(9, solicitud.getSueldo());
			ps.setString(10, solicitud.getPagoAnterior());
			ps.setString(11, solicitud.getTotalPagado());
			ps.setString(12, solicitud.getFechaPago());
			ps.setString(13, solicitud.getObservacionesContable());
			ps.setString(14, solicitud.getSaldoFinal());
			ps.setString(15, solicitud.getValRetencion());
			ps.setString(16, solicitud.getFechaCalculo());
			ps.setString(17, solicitud.getEstatus());
			ps.setDouble(18, solicitud.getRetiroMaximo());
			ps.setDouble(19, solicitud.getImporteContable());
			ps.setLong(20, solicitud.getNumeroRegistro());
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idTransaccion = rs.getLong(1);
				if (idTransaccion > 0) {
					
					con.commit();
					
					solicitud.setIdSolicitud(solicitud.getIdSolicitud());
					status=manejaEventos(solicitud,"Actulización","Actualización de importes solicitud ", con);
				}
				else {
					con.rollback();
					 idTransaccion = 0L;
				}
				
			}
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateNumeroImportesValidados(CalculoActuariaDTO cal) throws SQLException {
		
		String sql = "UPDATE calculo_actuaria SET registrosCargados = ?, fechaCarga = ? WHERE idCalculoActuaria = ?";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean status = true;
		long idTransaccion = 0L;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, cal.getRegistrosCargados());
			if(cal.getFechaCarga().equals("")) {
				ps.setNull(2, Types.NULL);
			}else {
				ps.setString(2, cal.getFechaCarga());
			}
			
			ps.setLong(3, cal.getIdCalculo());
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
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}
	
	
	@Override
	public List<CalculoActuariaDTO> getListCalculoActuaria() {
		List<CalculoActuariaDTO> list = new ArrayList<CalculoActuariaDTO>();
		CalculoActuariaDTO calculo = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "SELECT idCalculoActuaria, fechaCreacion, numProceso, idEmpleadoGenera, numRegistros, registrosCargados, fechaCarga FROM calculo_actuaria";

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				calculo=new CalculoActuariaDTO();
				calculo.setIdCalculo(rs.getLong("idCalculoActuaria"));
				calculo.setFechaCreacion(rs.getString("fechaCreacion"));
				calculo.setNumeroProceso(rs.getString("numProceso"));
				calculo.setIdEmpleadoGenera(rs.getLong("idEmpleadoGenera"));
				calculo.setNumRegistros(rs.getLong("numRegistros"));
				calculo.setRegistrosCargados(rs.getLong("registrosCargados"));
				calculo.setFechaCarga(rs.getString("fechaCarga"));
				list.add(calculo);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return list;
	}
	
	
	private boolean crearEventoSolicitud(EventoSolicitudDTO eventoSolicitud, Connection connection) {
	        PreparedStatement pst = null;
	        boolean status = false;
	        try {
	            pst = connection.prepareStatement("INSERT INTO evento_has_solicitud (evento_idevento, solicitud_idSolicitud, tipo, RFCAsegurado,RFCEmpleado ) VALUES (?, ?,?,?, ?) ", Statement.RETURN_GENERATED_KEYS);
	            pst.setLong(1, eventoSolicitud.getIdEvento());
	            pst.setLong(2,eventoSolicitud.getIdSolicitud());
	            pst.setString(3, eventoSolicitud.getTipo());
	            pst.setString(4, eventoSolicitud.getEmpleadoAP());
	            pst.setString(5, eventoSolicitud.getEmpleadoSolicitad());
	            pst.executeUpdate();
				status = true;
	        } catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			} finally {
				UtileriaSql.closePreparedStatement( pst);
			}
	        return status;
	}
	
	private boolean manejaEventos(SolicitudAPDTO solicitud, String eventoString, String eventoSolicitudString, Connection connection) {
		EventoDTO evento = new EventoDTO();
        EventoSolicitudDTO  eventoSolicitud= new EventoSolicitudDTO();
        boolean status=false;
		evento.setTipo(eventoString);
		evento.setDescripcion(eventoSolicitudString);
		eventoSolicitud.setIdEvento(daoEvento.crearEvento(evento,connection).getIdEvento());
		eventoSolicitud.setIdSolicitud(solicitud.getIdSolicitud());
		eventoSolicitud.setEmpleadoAP(solicitud.getRfcAsegurado());
		eventoSolicitud.setTipo(eventoString);
		eventoSolicitud.setEmpleadoSolicitad(solicitud.getEmpleadoAsignacion());
		if(crearEventoSolicitud(eventoSolicitud, connection))
			status = true;
		return status;
		
	}


	@Override
	public List<EventoSolicitudDTO> getEventosSolicitud(long idSolicitud) {
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = " select ev.idEvento, evhs.tipo,evhs.RFCAsegurado,evhs.RFCEmpleado, evhs.solicitud_idSolicitud,  ev.fecha, ev.descripcion "
				+ "    from evento_has_solicitud evhs, evento ev where evhs.solicitud_idSolicitud="+idSolicitud+" and ev.idevento=evhs.evento_idevento ";
	    List<EventoSolicitudDTO> eventosSolicitud = new ArrayList<EventoSolicitudDTO>();
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				EventoSolicitudDTO  eventoSolicitud= new EventoSolicitudDTO();
				eventoSolicitud.setTipo(rs.getString(2));
				eventoSolicitud.setEmpleadoAP(rs.getString(3));
				eventoSolicitud.setEmpleadoSolicitad(rs.getString(4));
				eventoSolicitud.setIdSolicitud(rs.getLong(5));
				eventoSolicitud.setFecha(UtileriaValidaPatrones.formatTimestampJava8(rs.getTimestamp(6)));
				eventoSolicitud.setDescripcion(rs.getString(7));
				eventosSolicitud.add(eventoSolicitud);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return eventosSolicitud;
	}

	private  long getAsignable(Connection connection) {
		long asignacionRFC=-1L;
		ResultSet rs = null;
		PreparedStatement pst = null;
		 
		String sql = " select idusua from ( "
				+ "(SELECT idusuariosAcceso as idusua,  0 AS solic "
				+ "FROM usuariosacceso u "
				+ "WHERE NOT EXISTS ( "
				+ "    SELECT 1 "
				+ "    FROM solicitud s "
				+ "    WHERE s.usuariosacceso_idusuariosAcceso = u.idusuariosAcceso "
				+ ") "
				+ "AND rolesAcceso_idrolesAcceso <> 2 and rolesAcceso_idrolesAcceso <>1) "
				+ "union "
				+ "(select usua.idusuariosAcceso as idusua ,count(sol.idSolicitud) as solic  "
				+ "  from solicitud sol, usuariosacceso usua "
				+ "  where statusSolicitud='Nueva' "
				+ "  and sol.usuariosacceso_idusuariosAcceso=usua.idusuariosAcceso "
				+ "  and usua.rolesAcceso_idrolesAcceso<>2 "
				+ "  group by usuariosacceso_idusuariosAcceso "
				+ "  order by solic asc) ) as result   order by solic asc limit 1 ";
		try {
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				asignacionRFC=rs.getLong(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closePreparedStatemetAndResultSet( pst, rs);
		}
		return asignacionRFC;
	}


	@Override
	public boolean updateSOlicitudAsignacion(long idSolicitud, String RFC) throws SQLException {
		String sql="";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		boolean status = true;
		SolicitudAPDTO solicitud =  new SolicitudAPDTO();
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			solicitud.setIdAsignacion(usuarioAcceso.getUsuarioByRFC(RFC,con));
			solicitud.setEmpleadoAsignacion(RFC);
			sql=sql+ "UPDATE solicitud SET usuariosacceso_idusuariosAcceso = '"+solicitud.getIdAsignacion()+"' WHERE (idSolicitud = '"+idSolicitud+"') ";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			rs = ps.getGeneratedKeys();
			solicitud.setIdSolicitud(idSolicitud);
			

			status=manejaEventos(solicitud,"Actualización","Asignación de solcitud a  usuario"+solicitud.getEmpleadoAsignacion(), con);
			con.commit();
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}
}