import { Component, OnInit } from '@angular/core';
import { Validations } from '@utils/forms/validations';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-base-sub-form',
  template: ``,
})
export class BaseSubFormComponent implements OnInit {
  public formGroup: FormGroup;

  constructor() {}

  ngOnInit(): void {}

  isValidCheck(field: string) {
    return Validations.isValidCheck(field, this.formGroup);
  }

  isValidInput(field: string) {
    return Validations.isValidInput(field, this.formGroup);
  }

  isCheckValid(field: string) {
    return Validations.isCheckValid(field, this.formGroup);
  }

  isCheckCaptcha(field: string) {
    return Validations.isCheckCaptcha(field, this.formGroup);
  }
}
