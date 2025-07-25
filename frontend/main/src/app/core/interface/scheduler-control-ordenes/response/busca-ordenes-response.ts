export interface BuscaOrdenesResponse {
    idOrden: string;
    fechaInicio: string;
    fechaFin: string;
    numeroOrden: string;
    ordenPadre: string;
    descripcion: string;
    tiempoOrden: number;
    tstampInicio: number;
    tstampFin: number;
    idCuadrillaOrden: number;
    nombreOrden: string;
    tipoOrden: string;
    descripcionTipo: string;
    estatus: number;
    idTipoOrden: number;
}
