import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
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
    console.log(isMatch,allowedRoles,userRoles);
    return isMatch;
  }
 
  
}
