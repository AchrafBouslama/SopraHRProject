import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PlaceParking } from 'src/app/models/PlaceParking';
import { Reservation } from 'src/app/models/reservation';
import { AuthService } from '../AuthService/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private BASE_URL = 'http://localhost:8080/api/Parking/';
 
  private userId = this.authService.getCurrentUserId(); // Obtenir l'ID de l'utilisateur connecté depuis le service AuthService

  constructor(private http: HttpClient,private authService: AuthService) { }

  displayReservations(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`+"displayReservation");
  }
  addReservationToPlaceParking(userId: any,placeParkingId: number, reservation: Reservation): Observable<any> {
    const url = `${this.BASE_URL}addReservation/${userId}/${placeParkingId}`;
    return this.http.post<any>(url, reservation);
  }  
}

