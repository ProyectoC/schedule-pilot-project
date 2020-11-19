import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductRolFormComponent } from './product-rol-form.component';

describe('ProductRolFormComponent', () => {
  let component: ProductRolFormComponent;
  let fixture: ComponentFixture<ProductRolFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductRolFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductRolFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
