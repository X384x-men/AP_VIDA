package com.sytecso.dao.email.impl;

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

import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.dao.email.DAOEmail;
import com.sytecso.dao.evento.DAOEvento;
import com.sytecso.dto.EventoDTO;
import com.sytecso.dto.email.EmailDTO;

@Repository
public class DAOEmailImpl implements DAOEmail {

	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private DAOEvento daoEvento;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean creacionEnvioEmail(EmailDTO email) throws SQLException {
		String sql= "INSERT INTO email (rfc, correo, nombre, estatus, numeroRegistro, evento_idevento,Aclaracion_idAclaracion, solicitud_idSolicitud,tipo,fechaSucess) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?) ";
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		boolean status= false;
		try {
			con= dataSource.getConnection();
			con.setAutoCommit(false);
			email.setIdEvento( manejaEventos("Envío Correo","Envío de correo del usuario "+email.getNombre()+" del tipo"+email.getTipo(),con));
			ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, email.getRfc());
			ps.setString(2,email.getCorreo());
			ps.setString(3, email.getNombre());
			ps.setBoolean(4, email.isStatus());
			ps.setString(5,email.getNumerosRegistro());
			if(email.getIdEvento()>0) {
				ps.setLong(6, email.getIdEvento());
			}else {
				ps.setNull(6, Types.BIGINT);
			}
			if(email.getIdAclaracion()>0) {
				ps.setLong(7, email.getIdAclaracion());
			}else {
				ps.setNull(7, Types.BIGINT);
			}
			if(email.getIdSolicitud()>0) {
				ps.setLong(8, email.getIdSolicitud());
			}else {
				ps.setNull(8, Types.BIGINT);
			}
			ps.setString(9, email.getTipo());
			ps.setString(10, email.getFechaExito());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				status=true;
			}	
			con.commit();
		}catch(Exception e) {
			con.rollback();
			System.out.println(e);
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		
		return status;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateEnvioEmail(EmailDTO email) throws SQLException {
		String sql="UPDATE email SET estatus = ?, fechaSucess=?, correo=? WHERE idemail = ?";
		Connection con =null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		boolean status= false;
		try {
			con= dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1,true);
			ps.setString(2, email.getFechaExito());
			ps.setString(3, email.getCorreo());
			ps.setLong(4, email.getIdEmail());
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				status=true;
			}
			con.commit();
		}catch(Exception e) {
			con.rollback();
			System.out.println(e);
			SytecsoController.logClassAndMethodWithException(e);
		}finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConection(con, ps, rs);
		}
		return status;
	}

	@Override
	public List<EmailDTO> getEmailList(String parametros) throws SQLException {
		String sql="select idEmail,rfc, correo,nombre,estatus,fechaCorreo,numeroRegistro,"
				+ "evento_idEvento,aclaracion_idAclaracion,solicitud_idSolicitud,tipo,fechaSucess from email";
		if(!parametros.equals(""))
			sql=sql+parametros;
		Connection con =null;
		List<EmailDTO> emailList= new ArrayList<EmailDTO>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con= dataSource.getConnection();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = ps.executeQuery();
			while (rs.next()) {
				EmailDTO email= new EmailDTO();
				email.setIdEmail(rs.getLong(1));
				email.setRfc(rs.getString(2));
				email.setCorreo(rs.getString(3));
				email.setNombre(rs.getString(4));
				email.setStatus(rs.getBoolean(5));
				email.setFechaEmail(rs.getString(6));
				email.setNumerosRegistro(rs.getString(7));
				email.setIdEvento(rs.getLong(8));
				email.setIdAclaracion(rs.getLong(9));
				email.setIdSolicitud(rs.getLong(10));
				email.setTipo(rs.getString(11));
				email.setFechaExito(rs.getString(12));
				emailList.add(email);
			}
			
		}catch(Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}finally {
			UtileriaSql.closeConection(con, ps, rs);
		}
		return emailList;
	}
	
	private long manejaEventos( String eventoString, String eventoSolicitudString, Connection connection) {
		EventoDTO evento = new EventoDTO();
        long idEvento=-1L;
		evento.setTipo(eventoString);
		evento.setDescripcion(eventoSolicitudString);
		idEvento=daoEvento.crearEvento(evento,connection).getIdEvento();
		return idEvento;
		
	}
	

}
