import { Component, OnInit } from '@angular/core';
import { of,Observable } from 'rxjs';
import { UserService } from 'src/service/user.service';
import { Location } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DialogContentComponent } from './DialogContentComponent.components';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user$: Observable<any>| null = null; // Adjust type as needed
  imageUrl: string | ArrayBuffer | null = '';
  user: any = {  // Initialize user object
    firstname: '',
    lastname: '',
    numerotelephone: '11111111',
    username: '',
    Genre: '',
    DateNaissance: '',
    country: '',
    state: ''
  }; 
  encodedProfileImage: string = '';
  

  constructor(private userService: UserService,private location: Location,private dialog: MatDialog) {}

  ngOnInit(): void {
    this.user$ = this.userService.getCurrentUser();
    this.userService.getCurrentUser().subscribe(
      (userData) => {
        this.user = userData;
        console.log('Fetched User Data:', this.user);  // Check if the user data is correctly fetched
        this.encodedProfileImage = this.encodeProfileImage(userData.profile_image);
      },
      (error) => {
        console.error('Error fetching user data', error);
      }
    );
  }
  private encodeProfileImage(profileImage: string): string {
    return encodeURIComponent(profileImage); // Encode the URL
  }

  saveProfile(): void {
    console.log('Updated User:', this.user);
    this.userService.updateProfile(this.user).subscribe(
      response => {
        console.log('Profile updated successfully', response);
        this.dialog.open(DialogContentComponent, {
          width: '300px',
          data: { message: 'Your profile has been updated successfully.' }
        });
      },
      error => {
        console.error('Error updating profile', error);
      }
    );
  }
  
  triggerFileInput() {
    document.getElementById('profileImageInput')?.click();
  }

  // This is called when the user selects a file
  onProfileImageSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append('file', file);

      // Call the service to upload the profile image
      this.userService.uploadProfileImage(this.user.username,formData).subscribe(
        (response: any) => {
          console.log('Profile image uploaded successfully', response);
          // Optionally, update the user profile image URL
          this.user.profile_image = response.newImageUrl; // Adjust based on the API response
          this.encodedProfileImage = this.encodeProfileImage(response.newImageUrl);
          window.location.reload();
        },
        
      );
      window.location.reload();
    }
  }
}
