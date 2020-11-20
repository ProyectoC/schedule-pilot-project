import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnTicketCheckOutContainerComponent } from './return-ticket-check-out-container.component';

describe('ReturnTicketCheckOutContainerComponent', () => {
  let component: ReturnTicketCheckOutContainerComponent;
  let fixture: ComponentFixture<ReturnTicketCheckOutContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReturnTicketCheckOutContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnTicketCheckOutContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
