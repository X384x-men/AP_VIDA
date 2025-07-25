import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SelectComponentScrollComponent } from './select-component-scroll.component';

describe('SelectComponentScrollComponent', () => {
  let component: SelectComponentScrollComponent;
  let fixture: ComponentFixture<SelectComponentScrollComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectComponentScrollComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectComponentScrollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
