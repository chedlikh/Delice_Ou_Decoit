import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthUserService } from 'src/service/auth-user.service';
import { UserService } from 'src/service/user.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css',
    './bootstrap.min.css',
    './font-awesome.css',
    './style.css'
    
    
  ]
})
export class NavbarComponent implements OnInit{
  
  constructor(private userAuthService:AuthUserService,private router: Router,public userService: UserService){}
  ngOnInit(): void {}

  isLoggedIn(): boolean {
    return this.userAuthService.getRoles().length > 0 && !!this.userAuthService.getToken();
  }
  logOut():void{
    this.userAuthService.clear();
    this.router.navigate(['/home'])
  }
  


}
