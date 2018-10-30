import { TestBed, inject } from '@angular/core/testing';

import { CarsApiService } from './cars-api.service';

describe('CarsApiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CarsApiService]
    });
  });

  it('should be created', inject([CarsApiService], (service: CarsApiService) => {
    expect(service).toBeTruthy();
  }));
});
