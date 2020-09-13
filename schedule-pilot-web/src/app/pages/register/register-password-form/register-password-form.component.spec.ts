import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterPasswordFormComponent } from './register-password-form.component';

describe('RegisterPasswordFormComponent', () => {
  let component: RegisterPasswordFormComponent;
  let fixture: ComponentFixture<RegisterPasswordFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterPasswordFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterPasswordFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
