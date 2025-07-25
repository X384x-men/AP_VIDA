package com.sytecso.service.aclaraciones;

import java.sql.SQLException;
import java.util.List;

import com.sytecso.dto.AclaracionDTO;
import com.sytecso.dto.CatalogoDocumentoDTO;
import com.sytecso.dto.TipoAclaracionDTO;

public interface ServiceAclaraciones {
	
	public long crearAclaracion(AclaracionDTO aclaracion) throws SQLException ;
	public boolean insertCatalogoDocumento(String tipoDocumento) throws SQLException;
	public boolean insertCatalogoTipoAclaracion(String tipoAclaracion, String descripcion) throws SQLException ;
	public List<AclaracionDTO> getAclaraciones(String rfc, String nombre,String dependencia , String fechaRegistroPortal, String telefono, String email,int aclaracionEmpleados);
	public boolean updateAclaracionStatus(long idAclaracion, int status) throws SQLException;
	public boolean updateTipoAclaracionesCatalogo(long idTipoDesc,String desc) throws SQLException;
	public boolean updateCatalogoDocumentos(long idTipoDocumento,String desc) throws SQLException;
	public List<TipoAclaracionDTO> getTipoAclaracion();
	public List<CatalogoDocumentoDTO> getCatalogoDocumento();
	public boolean updateAclaracion(AclaracionDTO aclaracion ) throws SQLException;
	public AclaracionDTO getAclaracion(long id,int funcionamiento);
	public boolean updateAclaracionEmpleado(AclaracionDTO aclaracion) throws SQLException ;
	public boolean updateAclaracionDocumento(AclaracionDTO aclaracion) throws SQLException ;


}
