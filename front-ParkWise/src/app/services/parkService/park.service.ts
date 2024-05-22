import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { Parking } from 'src/app/models/parking';

@Injectable({
  providedIn: 'root'
})
export class ParkService {

  private BASE_URL = 'http://localhost:8080/api/Parking/';

  constructor(private http: HttpClient) { }

  displayParking(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`+"displayParking");
  }

  addParking(parking: Parking): Observable<any> {
    return this.http.post(`${this.BASE_URL}`+"addParking", parking);
  }

  deleteParking(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}delete/${id}`, { responseType: 'text' });
  }

  /*updateParking(parking: Parking): Observable<any> {
    return this.http.put(this.BASE_URL + 'updateParking', parking);
  }*/
  update(id: number , formData: FormData) {
    
    return this.http.put<Parking>(`${this.BASE_URL}updateParking/${id}`, formData, {});  }  
  
  getParkingById(id: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}displayParking/${id}`);

  }

  


}
