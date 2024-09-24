import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';  
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthUserService {
  private readonly PATH_OF_API = 'http://localhost:8089';

  constructor(private httpClient: HttpClient) { }

  public setRoles(roles: string[]) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles(): string[] {
    const roles = localStorage.getItem('roles');
    if (roles) {
        const parsedRoles = JSON.parse(roles);
        // Ensure parsedRoles is an array
        
        return Array.isArray(parsedRoles) ? parsedRoles : [parsedRoles];
    } else {
        return []; // Return empty array if no roles are found
    }
  }

  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

  public getToken(): string {
    const token = localStorage.getItem('jwtToken');
    if (token) {
     console.log(token)
        return token;
    } else {
      
        return 'empty token'; // Return an empty string or handle the absence of a token as needed
    }
  }

  public clear() {
    localStorage.clear();
  }

  public isLoggedIn(): boolean {
    return !!this.getRoles().length && !!this.getToken();
  }

  public register(user: any): Observable<any> {
    console.log('Registering user:', user); // Log the request payload
    return this.httpClient.post<any>(`${this.PATH_OF_API}/register`, user)
      .pipe(
        tap(response => console.log('Registration response:', response)), // Log the response
        catchError(error => {
          console.error('Registration error:', error);
          return throwError(error);
        })
      );
  }
  
}
