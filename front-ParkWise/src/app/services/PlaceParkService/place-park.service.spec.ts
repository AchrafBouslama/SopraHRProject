import { TestBed } from '@angular/core/testing';

import { PlaceParkService } from './place-park.service';

describe('PlaceParkService', () => {
  let service: PlaceParkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlaceParkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
