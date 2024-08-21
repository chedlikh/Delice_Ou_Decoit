import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoginComponent } from '../login/login.component';
import { AuthUserService } from 'src/service/auth-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css',
    './bootstrap.min.css',
    './font-awesome.css',
    './style.css'
  ]
})
export class HomeComponent {
  
  constructor(public dialog: MatDialog, private userAuthService:AuthUserService,private router:Router) {}

  openLoginDialog(): void {
    this.dialog.open(LoginComponent, {
      width: '50%',
      height: '70%',
      // Autres options si nécessaire
    });
  }
  isLoggedIn(): boolean {
    return this.userAuthService.getRoles().length > 0 && !!this.userAuthService.getToken();
  }
  logOut():void{
    this.userAuthService.clear();
    this.router.navigate(['/home'])
  }
  

}

