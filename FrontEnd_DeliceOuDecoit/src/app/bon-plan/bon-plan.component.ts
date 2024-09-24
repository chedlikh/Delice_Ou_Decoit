import { Component, OnInit } from '@angular/core';
import { BonplanService } from 'src/service/bonplan.service';
import { MatDialog } from '@angular/material/dialog';
import { InfoEstablishmentComponentComponent } from '../info-establishment-component/info-establishment-component.component';

@Component({
  selector: 'app-bon-plan',
  templateUrl: './bon-plan.component.html',
  styleUrls: ['./bon-plan.component.css']
})
export class BonPlanComponent implements OnInit{
  bonplans: any[] = [];
  imageData: any;
  encodedProfileImage: string = '';
  selectedBonplan: any = null;
  constructor(private bonplanService: BonplanService,private dialog: MatDialog) { }

  ngOnInit(): void {
    this.fetchBonplans();
  }
  fetchBonplans(): void {
    this.bonplanService.getAllBonplans().subscribe(
      (data: any) => {
        this.bonplans = data;
        console.log(this.bonplans); // Debugging output

        // Encode the image for each bonplan
        this.bonplans.forEach(bonplan => {
          bonplan.encodedImage = this.encodeProfileImage(bonplan.cardImage);
          console.log(bonplan.encodedImage);
        });
      },
      (error) => {
        console.error('Error fetching bon plans:', error);
      }
    );
  }

  private encodeProfileImage(profileImage: string): string {
    return encodeURIComponent(profileImage); // Encode the URL
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
  openDialog(bonplan: any): void {
    this.selectedBonplan = bonplan;
    console.log('Selected bonplan:', this.selectedBonplan);
    this.dialog.open(InfoEstablishmentComponentComponent, {
        data: { bonplan: bonplan },
        panelClass: 'custom-dialog-container'
    });
}

}
