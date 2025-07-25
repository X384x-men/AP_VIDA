import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DivErrorMessageComponent } from './div-error-message.component';

describe('DivErrorMessageComponent', () => {
  let component: DivErrorMessageComponent;
  let fixture: ComponentFixture<DivErrorMessageComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DivErrorMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DivErrorMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
