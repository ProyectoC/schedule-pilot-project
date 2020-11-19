import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Validations } from '@utils/forms/validations';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-base-form',
  template: ``,
})
export class BaseFormComponent implements OnInit {
  
  public formGroup: FormGroup;
  public payLoad: string;

  @Output() onSubmitForm = new EventEmitter();

  constructor() {}

  ngOnInit(): void {}

  onSubmit() {
    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      this.payLoad = JSON.stringify(this.formGroup.getRawValue());
      console.log(this.payLoad);
      this.onSubmitForm.emit(this.payLoad);
    }
  }

  cleanForm(): void {
    this.formGroup.reset();
  }

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
