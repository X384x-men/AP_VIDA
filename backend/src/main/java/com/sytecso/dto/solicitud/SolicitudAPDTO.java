package com.sytecso.dto.solicitud;

import java.io.Serializable;
import java.util.List;

public class SolicitudAPDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6631666662234492160L;
	private long idSolicitud;
	private long numeroRegistro;
	private String fechaSolicitud;
	private String statusSolicitud;
	private String claveDependencia;
	private String dependencia;
	private String moduloAtencion;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String rfcAsegurado;
	private String rfcGEM;
	private String tipoTramite;
	private String telefono;
	private String email;
	private String fechaFinLaboral;
	private int diasTranscurridos;
	private String nombreBanco;
	private String cuenta;
	private String clabe;
	private String tipoPago;
	private Double foremexFroa;
	private Double aportacionFuncionario;
	private Double aportacionDependencia; 
	private Double aportacionVoluntaria;
	private Double aportacionAPVida;
	private Double intereses;
	private Double aportacionTotal;
	private Double retiroMaximo;
	private String importeSolicitado;
	private Double importeContable;
	private Double importeApagar;
	private String fechaImporteContable;
	private String fechaRegistrodeSiniestros;
	private long numOrdenPagoSise;
	private String numChequeTransf;
	private String fechadeTransferencia;
	private String estPagRechPen;
	private String estatus;
	private String observaciones;
	private String obsSiniestros;
	private long idBanco;
	private String fechadeSeguimiento;
	private String fechaSolicitudAPV;
	private String fechadeBaja;
	private String prioridad;
	private int validadoModulo;
	private int validadoSiniestros;
	private int validadoContabilidad;
	private String rfcEmpleado;
	private long idEmpleado;
	

	private String faltanteAPagar;
	private String valorQuincValidar;
	private String quincAgoFeb;
	private String montoCalculado;
	private String analistaComercialValida;
	private String quinM;
	
	private List<SolicitudHasDocumentoDTO> documentos;
	private String stringPdf;
	private byte[] pdf;
	
	private List<ObservacionDTO> listObs;
	
	private String sexo;
	private String fechaNac;
	
	private String fechaOrdenPago;
	private long idEmpleadoGeneraOrden;
	private String nombreEmpleadoGeneraOrden;
	private String rfcEmpleadoGeneraOrden;
	
	private boolean solicActiva;
	
	private long idOrdenPago;
	private String folioOrdenPago;
	
	private long idCalculoActuaria;
	private String fechaCreacionCalculo;
	private String numProcesoCalculo;
	private String nombreAnalistaComercialValida;
	private String fechaCarga;
	
	private String sueldo;
	private String pagoAnterior;
	private String totalPagado;
	private String fechaPago;
	private String observacionesContable;
	private String saldoFinal;
	private String valRetencion;
	private String fechaCalculo;
	private long idAsignacion;
	private String empleadoAsignacion;
	private String tipoSolicitud;
	private FonacotDTO fonacot;
	
	
	
	public FonacotDTO getFonacot() {
		return fonacot;
	}
	public void setFonacot(FonacotDTO fonacot) {
		this.fonacot = fonacot;
	}
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	public String getEmpleadoAsignacion() {
		return empleadoAsignacion;
	}
	public void setEmpleadoAsignacion(String empleadoAsignacion) {
		this.empleadoAsignacion = empleadoAsignacion;
	}
	public long getIdAsignacion() {
		return idAsignacion;
	}
	public void setIdAsignacion(long idAsignacion) {
		this.idAsignacion = idAsignacion;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public long getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(long numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getStatusSolicitud() {
		return statusSolicitud;
	}
	public void setStatusSolicitud(String statusSolicitud) {
		this.statusSolicitud = statusSolicitud;
	}
	public String getClaveDependencia() {
		return claveDependencia;
	}
	public void setClaveDependencia(String claveDependencia) {
		this.claveDependencia = claveDependencia;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	public String getModuloAtencion() {
		return moduloAtencion;
	}
	public void setModuloAtencion(String moduloAtencion) {
		this.moduloAtencion = moduloAtencion;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRfcAsegurado() {
		return rfcAsegurado;
	}
	public void setRfcAsegurado(String rfcAsegurado) {
		this.rfcAsegurado = rfcAsegurado;
	}
	public String getRfcGEM() {
		return rfcGEM;
	}
	public void setRfcGEM(String rfcGEM) {
		this.rfcGEM = rfcGEM;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFechaFinLaboral() {
		return fechaFinLaboral;
	}
	public void setFechaFinLaboral(String fechaFinLaboral) {
		this.fechaFinLaboral = fechaFinLaboral;
	}
	public int getDiasTranscurridos() {
		return diasTranscurridos;
	}
	public void setDiasTranscurridos(int diasTranscurridos) {
		this.diasTranscurridos = diasTranscurridos;
	}
	public String getNombreBanco() {
		return nombreBanco;
	}
	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getClabe() {
		return clabe;
	}
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public Double getForemexFroa() {
		return foremexFroa;
	}
	public void setForemexFroa(Double foremexFroa) {
		this.foremexFroa = foremexFroa;
	}
	public Double getAportacionFuncionario() {
		return aportacionFuncionario;
	}
	public void setAportacionFuncionario(Double aportacionFuncionario) {
		this.aportacionFuncionario = aportacionFuncionario;
	}
	public Double getAportacionDependencia() {
		return aportacionDependencia;
	}
	public void setAportacionDependencia(Double aportacionDependencia) {
		this.aportacionDependencia = aportacionDependencia;
	}
	public Double getAportacionVoluntaria() {
		return aportacionVoluntaria;
	}
	public void setAportacionVoluntaria(Double aportacionVoluntaria) {
		this.aportacionVoluntaria = aportacionVoluntaria;
	}
	public Double getAportacionAPVida() {
		return aportacionAPVida;
	}
	public void setAportacionAPVida(Double aportacionAPVida) {
		this.aportacionAPVida = aportacionAPVida;
	}
	public Double getIntereses() {
		return intereses;
	}
	public void setIntereses(Double intereses) {
		this.intereses = intereses;
	}
	public Double getAportacionTotal() {
		return aportacionTotal;
	}
	public void setAportacionTotal(Double aportacionTotal) {
		this.aportacionTotal = aportacionTotal;
	}
	public Double getRetiroMaximo() {
		return retiroMaximo;
	}
	public void setRetiroMaximo(Double retiroMaximo) {
		this.retiroMaximo = retiroMaximo;
	}
	public String getImporteSolicitado() {
		return importeSolicitado;
	}
	public void setImporteSolicitado(String importeSolicitado) {
		this.importeSolicitado = importeSolicitado;
	}
	public Double getImporteContable() {
		return importeContable;
	}
	public void setImporteContable(Double importeContable) {
		this.importeContable = importeContable;
	}
	public Double getImporteApagar() {
		return importeApagar;
	}
	public void setImporteApagar(Double importeApagar) {
		this.importeApagar = importeApagar;
	}
	public String getFechaImporteContable() {
		return fechaImporteContable;
	}
	public void setFechaImporteContable(String fechaImporteContable) {
		this.fechaImporteContable = fechaImporteContable;
	}
	public String getFechaRegistrodeSiniestros() {
		return fechaRegistrodeSiniestros;
	}
	public void setFechaRegistrodeSiniestros(String fechaRegistrodeSiniestros) {
		this.fechaRegistrodeSiniestros = fechaRegistrodeSiniestros;
	}
	public long getNumOrdenPagoSise() {
		return numOrdenPagoSise;
	}
	public void setNumOrdenPagoSise(int numOrdenPagoSise) {
		this.numOrdenPagoSise = numOrdenPagoSise;
	}
	public String getNumChequeTransf() {
		return numChequeTransf;
	}
	public void setNumChequeTransf(String numChequeTransf) {
		this.numChequeTransf = numChequeTransf;
	}
	public String getFechadeTransferencia() {
		return fechadeTransferencia;
	}
	public void setFechadeTransferencia(String fechadeTransferencia) {
		this.fechadeTransferencia = fechadeTransferencia;
	}
	public String getEstPagRechPen() {
		return estPagRechPen;
	}
	public void setEstPagRechPen(String estPagRechPen) {
		this.estPagRechPen = estPagRechPen;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getObsSiniestros() {
		return obsSiniestros;
	}
	public void setObsSiniestros(String obsSiniestros) {
		this.obsSiniestros = obsSiniestros;
	}
	public long getIdBanco() {
		return idBanco;
	}
	public void setIdBanco(long idBanco) {
		this.idBanco = idBanco;
	}
	public String getFechadeSeguimiento() {
		return fechadeSeguimiento;
	}
	public void setFechadeSeguimiento(String fechadeSeguimiento) {
		this.fechadeSeguimiento = fechadeSeguimiento;
	}
	public String getFechaSolicitudAPV() {
		return fechaSolicitudAPV;
	}
	public void setFechaSolicitudAPV(String fechaSolicitudAPV) {
		this.fechaSolicitudAPV = fechaSolicitudAPV;
	}
	public String getFechadeBaja() {
		return fechadeBaja;
	}
	public void setFechadeBaja(String fechadeBaja) {
		this.fechadeBaja = fechadeBaja;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public int getValidadoModulo() {
		return validadoModulo;
	}
	public void setValidadoModulo(int validadoModulo) {
		this.validadoModulo = validadoModulo;
	}
	public int getValidadoSiniestros() {
		return validadoSiniestros;
	}
	public void setValidadoSiniestros(int validadoSiniestros) {
		this.validadoSiniestros = validadoSiniestros;
	}
	public int getValidadoContabilidad() {
		return validadoContabilidad;
	}
	public void setValidadoContabilidad(int validadoContabilidad) {
		this.validadoContabilidad = validadoContabilidad;
	}
	public String getRfcEmpleado() {
		return rfcEmpleado;
	}
	public void setRfcEmpleado(String rfcEmpleado) {
		this.rfcEmpleado = rfcEmpleado;
	}
	public long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public List<SolicitudHasDocumentoDTO> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<SolicitudHasDocumentoDTO> documentos) {
		this.documentos = documentos;
	}
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	public String getStringPdf() {
		return stringPdf;
	}
	public void setStringPdf(String stringPdf) {
		this.stringPdf = stringPdf;
	}
	public List<ObservacionDTO> getListObs() {
		return listObs;
	}
	public void setListObs(List<ObservacionDTO> listObs) {
		this.listObs = listObs;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getFechaOrdenPago() {
		return fechaOrdenPago;
	}
	public void setFechaOrdenPago(String fechaOrdenPago) {
		this.fechaOrdenPago = fechaOrdenPago;
	}
	public long getIdEmpleadoGeneraOrden() {
		return idEmpleadoGeneraOrden;
	}
	public void setIdEmpleadoGeneraOrden(long idEmpleadoGeneraOrden) {
		this.idEmpleadoGeneraOrden = idEmpleadoGeneraOrden;
	}
	public String getNombreEmpleadoGeneraOrden() {
		return nombreEmpleadoGeneraOrden;
	}
	public void setNombreEmpleadoGeneraOrden(String nombreEmpleadoGeneraOrden) {
		this.nombreEmpleadoGeneraOrden = nombreEmpleadoGeneraOrden;
	}
	public String getRfcEmpleadoGeneraOrden() {
		return rfcEmpleadoGeneraOrden;
	}
	public void setRfcEmpleadoGeneraOrden(String rfcEmpleadoGeneraOrden) {
		this.rfcEmpleadoGeneraOrden = rfcEmpleadoGeneraOrden;
	}
	public boolean isSolicActiva() {
		return solicActiva;
	}
	public void setSolicActiva(boolean solicActiva) {
		this.solicActiva = solicActiva;
	}
	public long getIdOrdenPago() {
		return idOrdenPago;
	}
	public void setIdOrdenPago(long idOrdenPago) {
		this.idOrdenPago = idOrdenPago;
	}
	public String getFolioOrdenPago() {
		return folioOrdenPago;
	}
	public void setFolioOrdenPago(String folioOrdenPago) {
		this.folioOrdenPago = folioOrdenPago;
	}
	public long getIdCalculoActuaria() {
		return idCalculoActuaria;
	}
	public void setIdCalculoActuaria(long idCalculoActuaria) {
		this.idCalculoActuaria = idCalculoActuaria;
	}
	public String getFaltanteAPagar() {
		return faltanteAPagar;
	}
	public void setFaltanteAPagar(String faltanteAPagar) {
		this.faltanteAPagar = faltanteAPagar;
	}
	public String getQuincAgoFeb() {
		return quincAgoFeb;
	}
	public void setQuincAgoFeb(String quincAgoFeb) {
		this.quincAgoFeb = quincAgoFeb;
	}
	public String getValorQuincValidar() {
		return valorQuincValidar;
	}
	public void setValorQuincValidar(String valorQuincValidar) {
		this.valorQuincValidar = valorQuincValidar;
	}
	public String getMontoCalculado() {
		return montoCalculado;
	}
	public void setMontoCalculado(String montoCalculado) {
		this.montoCalculado = montoCalculado;
	}
	public String getAnalistaComercialValida() {
		return analistaComercialValida;
	}
	public void setAnalistaComercialValida(String analistaComercialValida) {
		this.analistaComercialValida = analistaComercialValida;
	}
	public String getFechaCreacionCalculo() {
		return fechaCreacionCalculo;
	}
	public void setFechaCreacionCalculo(String fechaCreacionCalculo) {
		this.fechaCreacionCalculo = fechaCreacionCalculo;
	}
	public String getNumProcesoCalculo() {
		return numProcesoCalculo;
	}
	public void setNumProcesoCalculo(String numProcesoCalculo) {
		this.numProcesoCalculo = numProcesoCalculo;
	}
	public String getNombreAnalistaComercialValida() {
		return nombreAnalistaComercialValida;
	}
	public void setNombreAnalistaComercialValida(String nombreAnalistaComercialValida) {
		this.nombreAnalistaComercialValida = nombreAnalistaComercialValida;
	}
	public String getQuinM() {
		return quinM;
	}
	public void setQuinM(String quinM) {
		this.quinM = quinM;
	}
	public String getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	public String getSueldo() {
		return sueldo;
	}
	public void setSueldo(String sueldo) {
		this.sueldo = sueldo;
	}
	public String getPagoAnterior() {
		return pagoAnterior;
	}
	public void setPagoAnterior(String pagoAnterior) {
		this.pagoAnterior = pagoAnterior;
	}
	public String getTotalPagado() {
		return totalPagado;
	}
	public void setTotalPagado(String totalPagado) {
		this.totalPagado = totalPagado;
	}
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getObservacionesContable() {
		return observacionesContable;
	}
	public void setObservacionesContable(String observacionesContable) {
		this.observacionesContable = observacionesContable;
	}
	public String getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(String saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	public String getValRetencion() {
		return valRetencion;
	}
	public void setValRetencion(String valRetencion) {
		this.valRetencion = valRetencion;
	}
	public String getFechaCalculo() {
		return fechaCalculo;
	}
	public void setFechaCalculo(String fechaCalculo) {
		this.fechaCalculo = fechaCalculo;
	}
	
	
}
