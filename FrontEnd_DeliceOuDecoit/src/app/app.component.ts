import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  
  showNavbarFooter: boolean = true;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Routes where navbar and footer should not be displayed
        if (event.url === '/home') {
          this.showNavbarFooter = false;
        } else {
          this.showNavbarFooter = true;
        }
      }
    });
  }
}
