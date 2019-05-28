import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericIncidentComponent } from './generic-incident.component';

describe('GenericIncidentComponent', () => {
  let component: GenericIncidentComponent;
  let fixture: ComponentFixture<GenericIncidentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenericIncidentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericIncidentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
