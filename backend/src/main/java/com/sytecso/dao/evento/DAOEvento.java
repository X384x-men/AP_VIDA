package com.sytecso.dao.evento;

import java.sql.Connection;

import com.sytecso.dto.EventoDTO;

public interface DAOEvento {
	public EventoDTO crearEvento(EventoDTO evento,  Connection connection);

}
