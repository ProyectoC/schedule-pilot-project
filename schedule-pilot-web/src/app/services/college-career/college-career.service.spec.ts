import { TestBed } from '@angular/core/testing';

import { CollegeCareerService } from './college-career.service';

describe('CollegeCareerService', () => {
  let service: CollegeCareerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollegeCareerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
