import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RequiredOptionalInputTextComponent } from './required-optional-input-text.component';

describe('RequiredOptionalInputTextComponent', () => {
  let component: RequiredOptionalInputTextComponent;
  let fixture: ComponentFixture<RequiredOptionalInputTextComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ RequiredOptionalInputTextComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequiredOptionalInputTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
