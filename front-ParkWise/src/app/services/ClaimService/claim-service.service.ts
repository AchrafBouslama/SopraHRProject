import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Claim } from 'src/app/models/claim';


import { AuthService } from '../AuthService/auth.service';
import { ResponseClaim } from 'src/app/models/responseClaim';

@Injectable({
  providedIn: 'root'
})
export class ClaimService {
  private BASE_URL = 'http://localhost:8080/api/claims/';

  constructor(private http: HttpClient, private authService: AuthService) { } // Injecter le service AuthService

  addClaimToUser(claim: Claim): Observable<any> {
    const userId = this.authService.getCurrentUserId(); // Obtenir l'ID de l'utilisateur connecté depuis le service AuthService
    if (!userId) {
      throw new Error('User ID not found in local storage');
    }
    return this.http.post(this.BASE_URL + 'claims/' + userId, claim);
  }

  displayClaim(): Observable<any> {
    return this.http.get(`${this.BASE_URL}` + "displayClaims");
  }

  addResponseToClaim(responseClaim:ResponseClaim,id:number):Observable<any>{
    return this.http.post(this.BASE_URL + 'response/'+id ,responseClaim);
  }

  getClaimsByCurrentUser(): Observable<Claim[]> {
    const userId = this.authService.getCurrentUserId(); // Obtenir l'ID de l'utilisateur connecté depuis le service AuthService
    if (!userId) {
      throw new Error('User ID not found in local storage');
    }
    return this.http.get<Claim[]>(`${this.BASE_URL}user/${userId}`);
  }
  
  getResponsesByClaimId(claimId: number): Observable<ResponseClaim[]> {
    return this.http.get<ResponseClaim[]>(`${this.BASE_URL}responses/${claimId}`);
  }

}


