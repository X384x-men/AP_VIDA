import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { FormChangePasswordComponent } from './form-change-password.component';

describe('FormChangePasswordComponent', () => {
  let component: FormChangePasswordComponent;
  let fixture: ComponentFixture<FormChangePasswordComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FormChangePasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormChangePasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
