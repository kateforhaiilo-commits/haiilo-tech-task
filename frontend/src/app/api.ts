import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  // Use the path as defined in the Spring Boot Controller
  private backendUrl = '/api/test/greeting';

  constructor(private http: HttpClient) { }

  public getBackendGreeting(): Observable<string> {
    // Angular will see this as http://localhost:4200/api/test/greeting
    // The proxy redirects it to http://localhost:8080/api/test/greeting
    return this.http.get(this.backendUrl, { responseType: 'text' });
  }
}