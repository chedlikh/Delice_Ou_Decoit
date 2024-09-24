import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TabService } from 'src/service/tab.service';


@Component({
  selector: 'app-info-establishment-component',
  templateUrl: './info-establishment-component.component.html',
  styleUrls: ['./info-establishment-component.component.css']
})
export class InfoEstablishmentComponentComponent {
  currentTab: string;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,private tabService: TabService) {
    this.currentTab = this.tabService.getCurrentTab();
  }
  changeTab(tab: string) {
    this.tabService.setCurrentTab(tab);
    this.currentTab = tab; 
  }
  getStarClasses(rating: number): string[] {
    const stars = [];
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 !== 0;

    // Add full stars
    for (let i = 0; i < fullStars; i++) {
      stars.push('fa-star');
    }

    // Add half star if applicable
    if (hasHalfStar) {
      stars.push('fa-star-half-o');
    }

    // Add empty stars to make a total of 5
    for (let i = stars.length; i < 5; i++) {
      stars.push('fa-star-o');
    }

    return stars;
  }

}
