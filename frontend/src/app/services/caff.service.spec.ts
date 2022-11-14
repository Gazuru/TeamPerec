import { TestBed } from '@angular/core/testing';

import { CaffService } from './caff.service';

describe('CaffService', () => {
  let service: CaffService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CaffService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
