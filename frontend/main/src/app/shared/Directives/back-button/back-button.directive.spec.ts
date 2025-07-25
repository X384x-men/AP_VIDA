/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { BackButtonDirective } from './back-button.directive';
import { Location } from '@angular/common';

describe('Directive: BackButton', () => {
  it('Service injected via inject(...) and TestBed.get(...) should be the same instance',
    inject([Location], (location: Location) => {
      const directive = new BackButtonDirective(location);
      expect(directive).toBeTruthy();
    })
  );
});
