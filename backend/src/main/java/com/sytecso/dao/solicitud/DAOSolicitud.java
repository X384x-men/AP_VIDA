package com.sytecso.dao.solicitud;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sytecso.dto.EventoSolicitudDTO;
import com.sytecso.dto.solicitud.CalculoActuariaDTO;
import com.sytecso.dto.solicitud.CalculoActuariaHasSolicDTO;
import com.sytecso.dto.solicitud.ObservacionDTO;
import com.sytecso.dto.solicitud.OrdenPagoDTO;
import com.sytecso.dto.solicitud.OrdenPagoHasSolicitudDTO;
import com.sytecso.dto.solicitud.SolicitudAPDTO;
import com.sytecso.dto.usuarioacceso.CatalogoAseguradosDTO;

public interface DAOSolicitud {

	
	public long crearSolicitud(SolicitudAPDTO solicitud) throws Exception;
	
	public long subirDocumento(MultipartFile file, String fechaCreacion) throws SQLException;
	
	public long crearSolicitudHasDocumento(long idDocumento, long idSolicitud, int tipoDocumento, int tipoArchivo) throws SQLException;
	
	public boolean actualizarSolicitudHasDocumento(long idDocumento, long idSolicitud, int tipoArchivo) throws SQLException;
	
	public List<SolicitudAPDTO> getSolicitudesByIdEmpleado(long idEmpleado);
	
	public boolean updateEstatusSolicitud(SolicitudAPDTO solicitud);
	
	public SolicitudAPDTO getSolicitud(long idSolicitud);
	
	public SolicitudAPDTO findDocumentoSolicitud(Long id);
	
	public long actualizarDocumento(MultipartFile file, long idDocumento) throws SQLException;
	
	public boolean actualizarSolicitud(SolicitudAPDTO solicitud) throws SQLException;
	
	public List<SolicitudAPDTO> getSolicitudesAnalistas(String params, String paramsSol, boolean flag );
	
	public boolean updateEstatusSolicitudAnalistas(SolicitudAPDTO solicitud);
	
	public long crearObservacionSolicitud(ObservacionDTO obs) throws Exception;
	
	public long crearSolicitudHasObservacion(long idObs, long idSolicitud) throws SQLException;
	
	public List<ObservacionDTO> getObservacionesSolicitud(long idSolicitud);
	
	public boolean validarImportes(SolicitudAPDTO solicitud) throws SQLException;
	
	public boolean updateFechaOrdenPagoSolicitud(SolicitudAPDTO solicitud) throws SQLException;
	
	public boolean informacionPago(SolicitudAPDTO solicitud) throws SQLException;

	public List<CatalogoAseguradosDTO> getCatAsegurados();
	
	public List<SolicitudAPDTO> getValidarSolicitudRFC(String rfc);
	
	public long crearOrdenPago(OrdenPagoDTO orden) throws Exception;
	
	public long crearOrdenPagoSolicitud(OrdenPagoHasSolicitudDTO orden) throws Exception;
	
	public OrdenPagoDTO getOrdenPago(long idSolicitud);
	
	public List<SolicitudAPDTO> getDataReport(long idOrdenPago);
	
	public long crearCalculoActuaria(CalculoActuariaDTO cal) throws Exception;
	
	public long crearCalculoActuariaSolicitud(CalculoActuariaHasSolicDTO calculoSol) throws Exception;
	
	public CalculoActuariaDTO getCalculoActuaria(long idSolicitud);
	
	public CalculoActuariaDTO getCalculoActuariaByFolioSolicitud(long folio);
	
	public List<SolicitudAPDTO> getDataCalculoActuaria(long idCalculo);
	
	public SolicitudAPDTO getStatusSolicitudByFolio(long folio);
	
	public boolean updateImportesSolicitudLayout(SolicitudAPDTO solicitud) throws SQLException;
	
	public boolean updateNumeroImportesValidados(CalculoActuariaDTO cal) throws SQLException;
	
	public List<CalculoActuariaDTO> getListCalculoActuaria();
	
	public List<EventoSolicitudDTO> getEventosSolicitud(long solicitud);
	
	public boolean updateSOlicitudAsignacion(long id, String RFC) throws SQLException;
}
