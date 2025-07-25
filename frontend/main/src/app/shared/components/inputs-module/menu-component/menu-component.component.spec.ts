import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { MenuComponentComponent } from './menu-component.component';

describe('SchedulerMenuComponentComponent', () => {
  let component: MenuComponentComponent;
  let fixture: ComponentFixture<MenuComponentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
