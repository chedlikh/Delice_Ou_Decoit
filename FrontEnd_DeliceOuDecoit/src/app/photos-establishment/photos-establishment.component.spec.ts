import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotosEstablishmentComponent } from './photos-establishment.component';

describe('PhotosEstablishmentComponent', () => {
  let component: PhotosEstablishmentComponent;
  let fixture: ComponentFixture<PhotosEstablishmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PhotosEstablishmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PhotosEstablishmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
