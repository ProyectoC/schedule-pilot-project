import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoansContainerComponent } from './loans-container.component';

describe('LoansContainerComponent', () => {
  let component: LoansContainerComponent;
  let fixture: ComponentFixture<LoansContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoansContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoansContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
