import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BaseInput } from '../../model/base-input';
import { Validations } from '../../utils/validations';
import { FormConstants } from '../../constants/form-constants';
import { ValidationConstants } from '../../constants/validation-constants';

@Component({
  selector: 'app-dynamic-form-input',
  templateUrl: './dynamic-form-input.component.html',
  styleUrls: ['./dynamic-form-input.component.scss'],
})
export class DynamicFormInputComponent implements OnInit {

  @Input() baseInput: BaseInput<string>;
  @Input() formGroup: FormGroup;

  constructor() {}

  ngOnInit(): void {}

  get isValid() {
    return this.formGroup.controls[this.baseInput.key].valid;
  }

  get validations() {
    return Validations;
  }

  get formConstants() {
    return FormConstants;
  }

  get validationConstants() {
    return ValidationConstants
  }
}
