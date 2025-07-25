/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CreateOrUpdateUserFormTemplateComponent } from './create-or-update-user-form-template.component';

describe('CreateOrUpdateUserFormTemplateComponent', () => {
  let component: CreateOrUpdateUserFormTemplateComponent;
  let fixture: ComponentFixture<CreateOrUpdateUserFormTemplateComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateOrUpdateUserFormTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateOrUpdateUserFormTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
