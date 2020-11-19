import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanProductModalComponent } from './loan-product-modal.component';

describe('LoanProductModalComponent', () => {
  let component: LoanProductModalComponent;
  let fixture: ComponentFixture<LoanProductModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanProductModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanProductModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
