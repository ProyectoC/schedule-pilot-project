import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnTicketCheckOutSearchComponent } from './return-ticket-check-out-search.component';

describe('ReturnTicketCheckOutSearchComponent', () => {
  let component: ReturnTicketCheckOutSearchComponent;
  let fixture: ComponentFixture<ReturnTicketCheckOutSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReturnTicketCheckOutSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnTicketCheckOutSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
