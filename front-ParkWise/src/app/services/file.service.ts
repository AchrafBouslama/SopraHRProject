import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private BASE_URL = 'http://localhost:8080/api/file';
  constructor(private http:HttpClient) { }
  uploadFile(file:File): Observable<any>{
    const formData:FormData=new FormData();
    formData.append('file',file,file.name);
    return this.http.post(`${this.BASE_URL}/upload`,formData,{
      reportProgress:true,
      responseType: 'text'
    });

  }
}
