import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanProductsSummaryComponent } from './loan-products-summary.component';

describe('LoanProductsSummaryComponent', () => {
  let component: LoanProductsSummaryComponent;
  let fixture: ComponentFixture<LoanProductsSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanProductsSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanProductsSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
