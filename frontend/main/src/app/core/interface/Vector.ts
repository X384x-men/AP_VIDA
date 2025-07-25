import { ResumenMovimiento } from 'src/app/core/interface/resumenMovimiento';
import { DetalleMovimiento } from 'src/app/core/interface/DetalleMovimiento';
import { PeriodosConsulta } from 'src/app/core/interface/PeriodosConsulta';


export class Vector {

    numCuenta:string;
	periodoConsulta: string;
	anioConsulta: string;
	mesConsulta: string;
	codigoRFC: string;
	numPoliza: string;
	retenedor: string;
	dependencia: string;
	tasaPeriodo: string;
	totalDetalleMovimiento: string;
	listResumenMovimiento = Array<ResumenMovimiento>();
	listDetalleMovimiento =Array<DetalleMovimiento>();
	listPeriodosConsulta =Array<PeriodosConsulta>();
}