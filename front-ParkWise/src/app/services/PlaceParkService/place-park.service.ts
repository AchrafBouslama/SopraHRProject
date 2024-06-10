import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { PlaceParking } from 'src/app/models/PlaceParking';
import { Parking } from 'src/app/models/parking';

@Injectable({
  providedIn: 'root'
})
export class PlaceParkService {

  private BASE_URL = 'http://localhost:8080/api/Parking/';

  constructor(private http: HttpClient) { }
  displayPlaceParking(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`+"displayPlaceParking");
  }

  addPlaceParkingToBloc(placeParking: PlaceParking,id:number): Observable<any> {
    return this.http.post(this.BASE_URL + 'addPlaceParkingToBloc/'+id ,placeParking);
  }


  deletePlaceParking(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}deletePlaceParking/${id}`, { responseType: 'text' });
  }

 

  updatePlaceParking(id: number, placeParking: PlaceParking): Observable<any> {
    return this.http.put<any>(`${this.BASE_URL}updatePlaceParking/${id}`, placeParking)
      .pipe(
        catchError(error => {
          // Gérer les erreurs ici, par exemple afficher un message d'erreur
          console.error('Erreur lors de la mise à jour de l\'étage : ', error);
          throw error; // Renvoyer l'erreur pour la gérer dans le composant
        })
      );
  }
  
  getPlaceParkingById(id: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}displayPlaceParking/${id}`);

  }
  getPlaceParkingsByIdBloc(idBloc: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}displayPlaceParkings/${idBloc}`);

  }
  getPlaceParkingsByEtageId(etageId: number): Observable<PlaceParking[]> {
    return this.http.get<PlaceParking[]>(`${this.BASE_URL}etage/${etageId}`);
  }


  

}