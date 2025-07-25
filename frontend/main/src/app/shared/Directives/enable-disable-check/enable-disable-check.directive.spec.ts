import { EnableDisableCheckDirective } from './enable-disable-check.directive';
import { ElementRef, Renderer2 } from '@angular/core';

describe('EnableDisableCheckDirective', () => {
  it('should create an instance', () => {
    const directive = new EnableDisableCheckDirective(null, null);
    expect(directive).toBeTruthy();
  });
});
