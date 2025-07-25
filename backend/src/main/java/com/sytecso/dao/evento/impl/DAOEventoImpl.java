package com.sytecso.dao.evento.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.dao.evento.DAOEvento;
import com.sytecso.dto.EventoDTO;

@Repository
@Transactional
public class DAOEventoImpl implements DAOEvento {



	@Override
    @Transactional(rollbackFor = Exception.class)
	public EventoDTO crearEvento(EventoDTO evento, Connection connection) {
	        PreparedStatement pst = null;
	        ResultSet rs = null;
	        try {
	            pst = connection.prepareStatement("INSERT INTO `ap`.`evento` (`tipo`, `descripcion`) VALUES (?, ?) ", Statement.RETURN_GENERATED_KEYS);
	            pst.setString(1, evento.getTipo());
	            pst.setString(2,evento.getDescripcion());
	            pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				if (rs.next()) {
					evento.setIdEvento( rs.getLong(1));
				}
	        } catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			} finally {
				UtileriaSql.closePreparedStatemetAndResultSet( pst, rs);
			}
	        return evento;
	}

}
