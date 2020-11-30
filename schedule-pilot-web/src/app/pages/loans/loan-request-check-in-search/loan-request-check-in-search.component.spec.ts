import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanRequestCheckInSearchComponent } from './loan-request-check-in-search.component';

describe('LoanRequestCheckInSearchComponent', () => {
  let component: LoanRequestCheckInSearchComponent;
  let fixture: ComponentFixture<LoanRequestCheckInSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanRequestCheckInSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanRequestCheckInSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
