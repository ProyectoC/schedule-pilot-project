import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnsContainerComponent } from './returns-container.component';

describe('ReturnsContainerComponent', () => {
  let component: ReturnsContainerComponent;
  let fixture: ComponentFixture<ReturnsContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReturnsContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnsContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
