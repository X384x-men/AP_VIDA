
export interface UserAp {
    nombre?: string;
    apellidoPaterno?: string;
    apellidoMaterno?: string;
    calle?: string;
    colonia?: string;
    noInt?: number;
    noExt?: number;
    cp?: string;
    rfc?: string;
    curp?: string;
    sexo?: string;
    telCasa?: number;
    telMovil?: number;
    mail?: string;
    mail2?: string;
    noEmpleado?: string;
    cuenta?: string;
    fechaNacimiento?: string;
    dependencia?: string;
    unidadAdministrativa?: string;
    fechaIngresoSeguro?: string;
    banco?: string;
    psw?: string;
    confirmpsw?: string;
    isValid?: boolean;
    messageError?: string;
    idUsuarioAcceso?: number,
    fechaCreacion?: string;
    estado?: string;
    tipoCuenta?: string;
    errorPass?: boolean;
    pswValidate?: string;
    estatus?: number;
    fechaCambioEstatus?: string;
    tipoAnalista?: number;
  }

  export interface EmailAP {
    email?: string;
    rfc?: string;
	  content?: string;
    subject?: string;
    psw?: string;
    confirmpsw?: string;
    isValid?: boolean;
    messageError?: string;
  }

  export interface solicitudAP {
    idSolicitud?: number;
    numeroRegistro?: number;
    fechaSolicitud?: string;
    statusSolicitud?: string;
    claveDependencia?: string;
    dependencia?: string;
    moduloAtencion?: string;
    apellidoPaterno?: string;
    apellidoMaterno?: string;
    nombre?: string;
    rfcAsegurado?: string;
    rfcGEM?: string;
    tipoTramite?: string;
    telefono?: string;
    email?: string;
    fechaFinLaboral?: string;
    diasTranscurridos?: string;
    nombreBanco?: string;
    cuenta?: string;
    clabe?: string;
    tipoPago?: string;
    foremexFroa?: number;
    aportacionFuncionario?: number;
    aportacionDependencia?: number;
    aportacionVoluntaria?: number;
    aportacionAPVida?: number;
    intereses?: number;
    aportacionTotal?: number;
    retiroMaximo?: number;
    importeSolicitado?: string;
    importeContable?: number;
    importeApagar?: number;
    fechaImporteContable?: string;
    fechaRegistrodeSiniestros?: string;
    numOrdenPagoSise?: number;
    numChequeTransf?: number;
    fechadeTransferencia?: string;
    estPagRechPen?: string;
    estatus?: string;
    observaciones?: string;
    obsSiniestros?: string;
    idBanco?: number;
    fechadeSeguimiento?: string;
    fechaSolicitudAPV?: string;
    fechadeBaja?: string;
    prioridad?: string;
    validadoModulo?: number;
    validadoSiniestros?: number;
    validadoContabilidad?: number;
    rfcEmpleado?: string;
    listObs?: any;

    rfcConfirmar?: string;

    isValid?: boolean;
    messageError?: string;

    rfcEmpleadoGeneraOrden?: string;
    sexo?: string;
    fechaNac?: string;
    fechaOrdenPago?: string;
    idEmpleadoGeneraOrden?: number;
    nombreEmpleadoGeneraOrden?: string;
    faltanteAPagar?: string;
    valorQuincValidar?: string;
    quincAgoFeb?: string;
    montoCalculado?: string;
    analistaComercialValida?: string;
    pagoAnterior?: string;
    sueldo?: string;
    fechaPago?: string;
    empleadoAsignacion?: string
  }




