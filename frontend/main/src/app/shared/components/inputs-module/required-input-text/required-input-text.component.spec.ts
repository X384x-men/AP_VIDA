import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RequiredInputTextComponent } from './required-input-text.component';

describe('RequiredInputTextComponent', () => {
  let component: RequiredInputTextComponent;
  let fixture: ComponentFixture<RequiredInputTextComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ RequiredInputTextComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequiredInputTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
