import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { MainChangePasswordComponent } from './main-change-password.component';

describe('MainChangePasswordComponent', () => {
  let component: MainChangePasswordComponent;
  let fixture: ComponentFixture<MainChangePasswordComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MainChangePasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainChangePasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
