import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffIncidentsComponent } from './staff-incidents.component';

describe('StaffIncidentsComponent', () => {
  let component: StaffIncidentsComponent;
  let fixture: ComponentFixture<StaffIncidentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StaffIncidentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaffIncidentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
