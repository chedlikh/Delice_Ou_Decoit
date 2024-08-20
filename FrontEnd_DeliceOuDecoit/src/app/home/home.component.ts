import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoginComponent } from '../login/login.component';

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
  constructor(public dialog: MatDialog) {}

  openLoginDialog(): void {
    this.dialog.open(LoginComponent, {
      width: '50%',
      height: '70%',
      // Autres options si nécessaire
    });
  }
  

}

