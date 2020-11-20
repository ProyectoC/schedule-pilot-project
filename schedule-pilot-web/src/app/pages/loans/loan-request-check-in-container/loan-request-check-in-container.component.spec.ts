import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanRequestCheckInContainerComponent } from './loan-request-check-in-container.component';

describe('LoanRequestCheckInContainerComponent', () => {
  let component: LoanRequestCheckInContainerComponent;
  let fixture: ComponentFixture<LoanRequestCheckInContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanRequestCheckInContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanRequestCheckInContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
