import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecificIncidentComponent } from './specific-incident.component';

describe('SpecificIncidentComponent', () => {
  let component: SpecificIncidentComponent;
  let fixture: ComponentFixture<SpecificIncidentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpecificIncidentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecificIncidentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
