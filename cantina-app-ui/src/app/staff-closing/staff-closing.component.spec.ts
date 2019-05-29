import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffClosingComponent } from './staff-closing.component';

describe('StaffClosingComponent', () => {
  let component: StaffClosingComponent;
  let fixture: ComponentFixture<StaffClosingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StaffClosingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaffClosingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
