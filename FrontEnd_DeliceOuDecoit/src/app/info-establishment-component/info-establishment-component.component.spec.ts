import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoEstablishmentComponentComponent } from './info-establishment-component.component';

describe('InfoEstablishmentComponentComponent', () => {
  let component: InfoEstablishmentComponentComponent;
  let fixture: ComponentFixture<InfoEstablishmentComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfoEstablishmentComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoEstablishmentComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
