package com.sytecso.service.solicitud;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sytecso.dto.EventoSolicitudDTO;
import com.sytecso.dto.solicitud.CalculoActuariaDTO;
import com.sytecso.dto.solicitud.ObservacionDTO;
import com.sytecso.dto.solicitud.SolicitudAPDTO;
import com.sytecso.dto.usuarioacceso.CatalogoAseguradosDTO;

public interface ServiceSolicitud {

	public SolicitudAPDTO crearSolicitud(SolicitudAPDTO solicitud) throws Exception;
	
	public long subirDocumento(MultipartFile file, String fechaCreacion, int tipoDocumento, long idSolicitud, int tipoAccion, long idDocumento, int tipoArchivo,String categoriaDocumento);
	
	public List<SolicitudAPDTO> getSolicitudesByIdEmpleado(String rfc, String categorriaSolicitud) throws Exception;
	
	public boolean updateEstatusSolicitud(SolicitudAPDTO solicitud) throws Exception;
	
	public SolicitudAPDTO getSolicitud(long idSolicitud, String categoriaSolicitud) throws Exception;
	
	public SolicitudAPDTO findDocumentoSolicitud(Long id);
	
	public List<SolicitudAPDTO> getSolicitudesAnalistas(String nombre,String RFC, String tramite, String status, String fechaIni, String fechaFin, String categoriaSolicitud) throws Exception;
	
	public boolean updateEstatusSolicitudAnalistas(SolicitudAPDTO solicitud) throws Exception;
	
	public long crearObservacion(ObservacionDTO obs, String categoriaSolicitud) throws Exception;
	
	public boolean validarImportes(SolicitudAPDTO solicitud) throws Exception;
	
	public long updateFechaOrdenPagoSolicitud(List<SolicitudAPDTO> solicitudes) throws Exception;
	
	public boolean informacionPago(SolicitudAPDTO solicitud) throws Exception;

	public List<CatalogoAseguradosDTO> getCatAsegurados();
	
	public List<SolicitudAPDTO> getValidarSolicitudRFC(String rfc, String categoriaSolicitud) throws Exception;
	
	public List<SolicitudAPDTO> getOrdenPagoLayout(long idOrdenPago, String categoriaSolicitud) throws Exception;
	
	public long createCalculoActuaria(List<SolicitudAPDTO> solicitudes) throws Exception;
	
	public List<SolicitudAPDTO> getDataCalculoActuaria(long idCalculo, String categoriaSolicitud) throws Exception;
	
	public boolean updateImportesSolicitudActuaria(List<SolicitudAPDTO> solicitudes) throws Exception;
	
	public List<CalculoActuariaDTO> getListCalculoActuaria() throws Exception;
	
	public List<EventoSolicitudDTO> getEventosSolicitud(long solicitud);
	
	public boolean updateSOlicitudAsignacion(long idSolicitud, String RFC, String categoriaSolicitud) throws SQLException ;
	
	public SolicitudAPDTO updateSolicitud(SolicitudAPDTO solicitud) throws Exception;
	

	
	
}
