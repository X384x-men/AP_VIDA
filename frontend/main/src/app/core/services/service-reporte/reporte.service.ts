import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { URLUtilities } from '../../static/variables/url/URLUtilities';
import { Fechas } from '../../interface/date/locale/fechas';
import { PdfVariable } from 'src/app/core/static/variables/url/URLImages';

@Injectable({
  providedIn: 'root'
})
export class ReporteService {

  constructor(private http: HttpClient) { }

  getReporte(): Observable<any[]> {
    return this.http.get(URLUtilities.getReporteFull()).pipe(
      map((response) => response as any[])
    );
  }

  getPDF(name :string ): Observable<any> {
    return this.http.get(PdfVariable.DOWNLOAD_PDF).pipe(
      map((response) => response )
    );
  }
  
    public downloadFile(docFile: string, url:String): Observable < Blob > {  
      console.log(docFile);
      console.log(url);
        return this.http.get(url + docFile, {  
            responseType: 'blob'  
        });  
    }  

  getBuscarReporte(fechas: string): Observable<any[]> {
    return this.http.get(URLUtilities.getBuscarReporte() + '?fechas=' + fechas).pipe(
      map((response) => response as any[])
    );
  }
  getBuscarReporteporYear(year: string): Observable<any[]> {

    return this.http.get(URLUtilities.getBuscarReporteporYear() + '?year=' + year).pipe(
      map((response) => response as any[])
    );
  }

  getReporteCanceladas(): Observable<any[]> {
    return this.http.get(URLUtilities.getreporteCanceladas()).pipe(
      map((response) => response as any[])
    );
  }

  getBuscarReporteCanceladas(fechas: string): Observable<any[]> {
    return this.http.get(URLUtilities.getBuscarReporteCanceladas() + '?fechas=' + fechas).pipe(
      map((response) => response as any[])
    );
  }

  getBuscarReporteporAnioCanceladas(year: string): Observable<any[]> {
    return this.http.get(URLUtilities.getBuscarReporteporAnioCanceladas() + '?anio=' + year).pipe(
      map((response) => response as any[])
    );
  }

  getReporteMaterialOrden(): Observable<any[]> {
    return this.http.get(URLUtilities.getReporteMaterialOrden()).pipe(
      map((response) => response as any[])
    );
  }

  getBuscarReporteMaterial(fechas: string): Observable<any[]> {
    return this.http.get(URLUtilities.getBuscarReporteMaterial() + '?fechas=' + fechas).pipe(
      map((response) => response as any[])
    );
  }

  getBuscarReporteporAnioMaterial(year: string): Observable<any[]> {
    return this.http.get(URLUtilities.getBuscarReporteporAnioMaterial() + '?anio=' + year).pipe(
      map((response) => response as any[])
    );
  }



  getBuscarReporteporAnioMaterialCliente(year: string, cliente: string): Observable<any[]> {
    return this.http.get(URLUtilities.getBuscarReporteporAnioCliente() + '?anio=' + year + '&&cliente=' + cliente).pipe(
      map((response) => response as any[]));
  }


  getReporteMaterialCliente(): Observable<any[]> {
    return this.http.get(URLUtilities.getReporteMaterialCliente()).pipe(
      map((response) => response as any[])
    );
  }

  getYears(): Observable<Array<Fechas>> {

    return this.http.get<Array<Fechas>>(URLUtilities.getYearsReporte());
  }

  getClientesService(): Observable<Array<any>> {

    return this.http.get<Array<any>>(URLUtilities.getClientes());

  }

  getBuscarCliente(cliente: string, anio: string): Observable<any[]> {

    return this.http.get(URLUtilities.getBuscarReporteporAnioCliente() + '?cliente=' + cliente + '&&anio=' + anio).pipe(
      map((response) => response as any[])
    );
  }

  getBuscarReporteMaterialCliente(fechas: string, cliente: string): Observable<any[]> {
    return this.http.get(URLUtilities.getBuscarReporteMaterialCliente() + '?fechas=' + fechas + '&&cliente=' + cliente).pipe(
      map((response) => response as any[])
    );
  }

}
