import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ButtonBackAndCreateComponent } from './button-back-and-create.component';

describe('ButtonBackAndCreateComponent', () => {
  let component: ButtonBackAndCreateComponent;
  let fixture: ComponentFixture<ButtonBackAndCreateComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ButtonBackAndCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ButtonBackAndCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
