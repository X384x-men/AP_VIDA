package com.sytecso.service.batchManaging;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.web.multipart.MultipartFile;

import com.sytecso.dto.batchmodel.DTOCargaBatchControl;
import com.sytecso.dto.batchmodel.DTOCargo;
import com.sytecso.dto.usuario.UserAp;
import com.sytecso.dto.empleado.EmpleadoAPDTO;

public interface HandleBatch {
	
	public DTOCargaBatchControl administraCargas(DTOCargaBatchControl batchControl) throws SQLException;
	
	public DTOCargaBatchControl convertMultipartToFile(MultipartFile  mpFile) throws IOException, SQLException;
	
	public DTOCargaBatchControl getBatchResumenCarga(long idCarga);
	
	public DTOCargo getCargoResumen(String fecha, String rfc);
	
	public List<String> getFechaAnio(String rfc);
	
	public DTOCargaBatchControl updateStatusUser(DTOCargaBatchControl batchControl,List<UserAp> userAp, String filename) throws SQLException;
	
	public List<EmpleadoAPDTO> getEmpleadosListFromExcel(MultipartFile file) throws EncryptedDocumentException, IOException;
	
	public DTOCargaBatchControl insertEmpleadosAPMasivo(List<EmpleadoAPDTO> listaEmpleados,String fileName) throws SQLException;
	
	 public List<List<String>> getEnrichedCsvData(String from, String to, String rfc);
	
	
	
	
}
