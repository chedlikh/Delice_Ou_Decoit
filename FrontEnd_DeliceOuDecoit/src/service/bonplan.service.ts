import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BonplanService {
  private apiUrl = 'http://localhost:8089/establishment';

  constructor(private httpClient: HttpClient) { }
  getAllBonplans(): Observable<any> {
    return this.httpClient.get(`${this.apiUrl}/all`);
  }
  getImageByEstablishmentAndImageId(establishmentId: number, imageId: number): Observable<any> {
    return this.httpClient.get(`${this.apiUrl}/${establishmentId}/images/${imageId}`);
  }
  getImage(filename: string): Observable<Blob> {
    
    return this.httpClient.get(`${this.apiUrl}/images/${filename}`, { responseType: 'blob' });
  }
  getEstablishmentImages(establishmentId: number): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.apiUrl}/${establishmentId}/images`);
  }
}
