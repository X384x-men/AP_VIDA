import { ChangeButtonDirective } from './change-button.directive';
import { ElementRef, Renderer2 } from '@angular/core';

describe('ChangeButtonDirective', () => {
  it('should create an instance', () => {
    let elementRef: ElementRef;
    let render: Renderer2;
    const directive = new ChangeButtonDirective(elementRef, render);
    expect(directive).toBeTruthy();
  });
});
