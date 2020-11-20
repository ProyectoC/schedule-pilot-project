import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanTicketCheckInContainerComponent } from './loan-ticket-check-in-container.component';

describe('LoanTicketCheckInContainerComponent', () => {
  let component: LoanTicketCheckInContainerComponent;
  let fixture: ComponentFixture<LoanTicketCheckInContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanTicketCheckInContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanTicketCheckInContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
