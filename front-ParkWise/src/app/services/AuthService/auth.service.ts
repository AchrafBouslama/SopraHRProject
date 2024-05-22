import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { AuthRequest } from 'src/app/models/auth-request';
import { AuthResponse } from 'src/app/models/auth-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
 private url = 'http://localhost:8080/api/vi/auth/';
public loggedIn=new BehaviorSubject<boolean>(this.tokenAvailabe());
  constructor(private http: HttpClient) { }
register (user : any ){  
  console.log(user);
  const headers = new HttpHeaders({'Content-Type': 'application/json'});
  return this.http.post<AuthResponse>(this.url +'register',JSON.stringify(user),{headers} )
  .pipe(
  map(response=>{
    localStorage.setItem('access_token',response.token);
    localStorage.setItem('id',response.id.toString());
    localStorage.setItem('role', response.role.toString()); // Stocker l'objet Role comme une chaîne JSON
    



    this.loggedIn.next(true);
    return true;
  })
  )
}
login(authRequest:AuthRequest):Observable<boolean>{
  console.log(authRequest);
  const headers = new HttpHeaders({'Content-Type': 'application/json'});
  return this.http.post<AuthResponse>(this.url +'authenticate',JSON.stringify(authRequest),{headers} )
  .pipe(
  map(response=>{
    localStorage.setItem('access_token',response.token);
    localStorage.setItem('id',response.id.toString());
    localStorage.setItem('role', response.role.toString()); // Stocker l'objet Role comme une chaîne JSON
    



    this.loggedIn.next(true);
    return true;
  })
  )
}
logout(){
  localStorage.removeItem('access_token');
  localStorage.removeItem('id');
  localStorage.removeItem('role');

  this.loggedIn.next(false);
}
get isLoggedIn(){
  return this.loggedIn.asObservable();
}
tokenAvailabe():boolean{
  return !!localStorage.getItem('access_token');
}
getToken():string|null{
  return localStorage.getItem('access_token');
}
getCurrentUserId():string|null{
  return localStorage.getItem('id');
}

getCurrentRole():string|null{
  return localStorage.getItem('role');
}
verifyUser(id:number,verificationCode:number){
  return this.http.post<string>(`${this.url}verify/${id}/${verificationCode}`,{},{
    responseType: 'text' as 'json'
  });
}
resendVerificationCode(id:number):Observable<string>{
  return this.http.get<string>(`${this.url}/resend-code/${id}`);
}

changePassword(userId: number, oldPassword: string, newPassword: string, confirmPassword: string): Observable<any> {

  return this.http.post(this.url + 'change-password', { userId, oldPassword, newPassword, confirmPassword }, { responseType: 'text' as 'json' });
}

forgetPassword(email:string):Observable<any>{
  return this.http.post(this.url+'reset-password',{email:email});
}

setNewPassword(token:string,newPassword:string):Observable<any>{
  return this.http.post(this.url+'set-new-password',newPassword,{
    params:{token}
  });

}

}
