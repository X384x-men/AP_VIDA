/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { StateStorageService } from './state-storage.service';

describe('Service: StateStorage', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StateStorageService]
    });
  });

  it('should ...', inject([StateStorageService], (service: StateStorageService) => {
    expect(service).toBeTruthy();
  }));
});
