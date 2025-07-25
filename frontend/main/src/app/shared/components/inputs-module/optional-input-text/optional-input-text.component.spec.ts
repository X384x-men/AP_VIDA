import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OptionalInputTextComponent } from './optional-input-text.component';

describe('OptionalInputTextComponent', () => {
  let component: OptionalInputTextComponent;
  let fixture: ComponentFixture<OptionalInputTextComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OptionalInputTextComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OptionalInputTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
