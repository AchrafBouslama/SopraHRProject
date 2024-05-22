import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private BASE_URL = 'http://localhost:8080/api/users/';

  constructor(private http: HttpClient) { }

  getUser(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`+"displayUser");
  }

  createUser(user: Object): Observable<any> {
    return this.http.post(`${this.BASE_URL}`+"Createprofile", user);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}deleteQuiz/${id}`, { responseType: 'text' });
  }

  updateUser(user: User): Observable<any> {
    return this.http.put(this.BASE_URL + 'updateUser', user);
  }
  
  getUserId(id: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}displayUser/${id}`);
  }
  




}
