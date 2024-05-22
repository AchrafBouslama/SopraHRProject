import { TestBed } from '@angular/core/testing';

import { EtageService } from './etage.service';

describe('EtageService', () => {
  let service: EtageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EtageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
