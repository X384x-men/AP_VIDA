package com.sytecso.dao.cargasBatch;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.sytecso.dto.batchmodel.DTOCargaBatchControl;
import com.sytecso.dto.batchmodel.DTODetalle;
import com.sytecso.dto.batchmodel.DTOResumen;
import com.sytecso.dto.solicitud.ShortSolicitudAPDTO;
import com.sytecso.dto.usuario.UserAp;

public interface DAOCargasBatch {
	public boolean  insertResumen( List<DTOResumen> resumenList,long registroBatch) throws SQLException;
	public boolean  insertDetalle(List<DTODetalle> detalle,long registroBatch ) throws SQLException;
	public long insertControlBatch(DTOCargaBatchControl control) throws SQLException;
	public DTOCargaBatchControl getResumenInsert(long  idCarga);
	public List<DTODetalle> getDetalleLista(String anio,String mes, String rfc);
	public List<DTOResumen> getResumenLista(String anio, String mes, String tfc);
	public DTOResumen getResumenSuma(String anio, String mes, String rfc);
	public long getIdAsegurado(String rfc,Statement st) throws SQLException;
	public int validateFile(String nameFile);
	public List<String> getMesAnioList(String rfc);
	public String getDependencia(String rfc);
	public List<Long> getIdFromFileName(String fileName);
	public boolean updateSingleClient(long empleadoAP, long cargaBatch, String tableName);
	public boolean updateMassive(long cargaBatch, String tableName);
	public boolean updateEmpleadoApBatch(List<UserAp> empleadoApList) throws SQLException;
	public List<ShortSolicitudAPDTO> getSolicitudesFiltered(String params);
	
}
