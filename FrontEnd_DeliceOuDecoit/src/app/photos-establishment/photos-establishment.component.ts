import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BonplanService } from 'src/service/bonplan.service';

@Component({
  selector: 'app-photos-establishment',
  templateUrl: './photos-establishment.component.html',
  styleUrls: ['./photos-establishment.component.css']
})
export class PhotosEstablishmentComponent implements OnInit{
  images: string[] = [];
  establishmentId = this.data.bonplan.id;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,private bonplanService:BonplanService) {}
  ngOnInit(): void {
    this.bonplanService.getEstablishmentImages(this.establishmentId).subscribe({
      next: (imageUrls) => {
        this.images = imageUrls;
      },
      error: (error) => {
        console.error('Error fetching images:', error);
      }
    });
  }

}
