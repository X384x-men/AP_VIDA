import { TestBed } from '@angular/core/testing';

import { UsuarioAccesoService } from './usuario-acceso.service';

describe('UsuarioAccesoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UsuarioAccesoService = TestBed.get(UsuarioAccesoService);
    expect(service).toBeTruthy();
  });
});
