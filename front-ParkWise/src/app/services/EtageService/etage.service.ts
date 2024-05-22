import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Etage } from 'src/app/models/etage';

@Injectable({
  providedIn: 'root'
})
export class EtageService {
  private BASE_URL = 'http://localhost:8080/api/Parking/';

  constructor(private http: HttpClient) { }

  addEtageToParking(etage: Etage,id:number): Observable<any> {
    return this.http.post(this.BASE_URL + 'addEtageToParking/'+id ,etage);
  }

  displayEtage(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`+"displayEtage");
  }

  deleteEtage(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}deleteEtage/${id}`, { responseType: 'text' });
  }

  getEtageById(id: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}displayEtage/${id}`);

  }
  displayEtages(idParking: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}displayEtages/${idParking}`);
  }
  

  
  updateEtage(id: number, etage: Etage): Observable<any> {
    return this.http.put<any>(`${this.BASE_URL}updateEtage/${id}`, etage)
      .pipe(
        catchError(error => {
          // Gérer les erreurs ici, par exemple afficher un message d'erreur
          console.error('Erreur lors de la mise à jour de l\'étage : ', error);
          throw error; // Renvoyer l'erreur pour la gérer dans le composant
        })
      );
  }


  



}
