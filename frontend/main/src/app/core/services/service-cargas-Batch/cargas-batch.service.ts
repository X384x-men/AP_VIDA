import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { URLUtilities } from 'src/app/core/static/variables/url/URLUtilities';
import { GlobalVariable } from '../../static/variables/url/URLImages';

@Injectable({
  providedIn: 'root'
})
export class CargasBatchService {

  private url = GlobalVariable.URL_SERVICES + '/batch/';
  public serviceURL = URLUtilities.postFileBatch() + '/';

  constructor(private http: HttpClient) { }

  postFile(formData: FormData, fileName: string): Observable<any> {

    return this.http.post(this.serviceURL + fileName, formData, { reportProgress: true });

  }
  postOrdenes(formData: FormData, fileName: string, numCuenta: string): Observable<any> {
    return this.http.post(this.serviceURL + fileName+'?cuenta='+numCuenta, formData)
  }

  postUserAct = ( file : any ) : Observable<any> => {
    return this.http.post<FormData>(this.url + 'postInactivateUsers', file)
  }

  postCargaUser = ( file : any ) : Observable<any> => {
    return this.http.post<FormData>(this.url + 'carga-empleados', file)
  }
}
