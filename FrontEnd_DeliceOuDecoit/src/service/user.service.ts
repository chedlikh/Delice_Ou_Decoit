import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthUserService } from './auth-user.service';



@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly PATH_OF_API = 'http://localhost:8089';
  private readonly requestHeader = new HttpHeaders({ 'No-Auth': 'True' });

  constructor(private httpClient: HttpClient, private userAuthService:AuthUserService) { }

  public login(loginData: any) {
    return this.httpClient.post(`${this.PATH_OF_API}/login`, loginData, {
      headers: this.requestHeader,
    });
  }
  public roleMatch(allowedRoles: string[]): boolean {
    let isMatch = false;
    const userRoles: any[] = this.userAuthService.getRoles();
    if (userRoles != null && userRoles.length > 0) {
      for (let i = 0; i < userRoles.length; i++) {
        for (let j = 0; j < allowedRoles.length; j++) {
          if (userRoles[i] === allowedRoles[j]) {
            isMatch = true;
            break; // Exit the inner loop if a match is found
          }
        }
        if (isMatch) {

          break; // Exit the outer loop if a match is found
        }
      }
    }
    
    return isMatch;
  }
  getCurrentUser(): Observable<any> {
    return this.httpClient.get<any>(`${this.PATH_OF_API}/me`);
  }
  getImage(filename: string): Observable<Blob> {
    const apiUrl = 'http://localhost:8089/images/';
    return this.httpClient.get(`${this.PATH_OF_API}${filename}`, { responseType: 'blob' });
  }
  updateProfile(user: any): Observable<any> {
    
    return this.httpClient.put('http://localhost:8089/me', user);
  }
  uploadProfileImage(username: string, formData: FormData): Observable<any> {
    // Append username to formData
    formData.append('username', username);

    return this.httpClient.post(`${this.PATH_OF_API}/uploadProfileImage`, formData);
  }
}
