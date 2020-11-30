import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanTicketCheckInSearchComponent } from './loan-ticket-check-in-search.component';

describe('LoanTicketCheckInSearchComponent', () => {
  let component: LoanTicketCheckInSearchComponent;
  let fixture: ComponentFixture<LoanTicketCheckInSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanTicketCheckInSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanTicketCheckInSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
