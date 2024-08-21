import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthUserService {

  constructor() { }

  public setRoles(roles: []) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles(): string[] {
    const roles = localStorage.getItem('roles');
    if (roles) {
        const parsedRoles = JSON.parse(roles);
        // Ensure parsedRoles is an array
        console.log('auth-userservice methode getroles role et parsedrole: ',roles,parsedRoles);
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
      console.log('getToken',token);
        return token;
    } else {
      console.log('notoken',token);
        return 'empty token'; // Return an empty string or handle the absence of a token as needed
    }
}

  public clear() {
    localStorage.clear();
  }

  public isLoggedIn() {
    
    return this.getRoles() && this.getToken();
  }

}
