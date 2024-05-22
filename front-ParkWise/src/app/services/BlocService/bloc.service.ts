import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { Bloc } from 'src/app/models/Bloc';
import { Etage } from 'src/app/models/etage';

@Injectable({
  providedIn: 'root'
})
export class BlocService {

  private BASE_URL = 'http://localhost:8080/api/Parking/';

  constructor(private http: HttpClient) { }

  addBlocToEtage(bloc: Bloc,id:number): Observable<any> {
    return this.http.post(this.BASE_URL + 'addBlocToEtage/'+id ,bloc);
  }

  displayBloc(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`+"displayBloc");
  }

  deleteBloc(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}deleteBloc/${id}`, { responseType: 'text' });
  }

  getBlocById(id: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}displayBloc/${id}`);

  }
  displayBlocs(idEtage: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}displayBlocs/${idEtage}`);
  }

  
  updateBloc(id: number, bloc: Bloc): Observable<any> {
    return this.http.put<any>(`${this.BASE_URL}updateBloc/${id}`, bloc, { responseType: 'json' })
      .pipe(
        catchError(error => {
          console.error('Erreur lors de la mise à jour de l\'étage : ', error);
          throw error; 
        })
      );
  }

 getOccupancyRateByBloc(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`+"occupancyRateForAllBlocs");
  }

}
