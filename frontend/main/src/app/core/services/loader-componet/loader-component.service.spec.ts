import { TestBed } from '@angular/core/testing';

import { LoaderComponentService } from './loader-component.service';

describe('LoaderComponentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LoaderComponentService = TestBed.get(LoaderComponentService);
    expect(service).toBeTruthy();
  });
});
