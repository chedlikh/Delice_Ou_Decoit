import { TestBed } from '@angular/core/testing';

import { BonplanService } from './bonplan.service';

describe('BonplanService', () => {
  let service: BonplanService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BonplanService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
