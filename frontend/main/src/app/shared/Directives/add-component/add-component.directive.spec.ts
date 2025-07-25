/* tslint:disable:no-unused-variable */

import { TestBed, waitForAsync } from '@angular/core/testing';
import { AddComponentDirective } from './add-component.directive';
import { ElementRef, ViewContainerRef } from '@angular/core';

describe('Directive: AddComponent', () => {
  it('should create an instance', () => {
    let viewContainerRef: ViewContainerRef;
    const directive = new AddComponentDirective(viewContainerRef);
    expect(directive).toBeTruthy();
  });
});
