import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ValidationConstants } from '../../constants/validation-constants';
import { BaseInput } from '../../model/base-input';
import { Validations } from '../../utils/validations';

@Injectable({
  providedIn: 'root',
})
export class DynamicFormService {
  constructor() {}

  getFormGroup(baseInputs: BaseInput<string>[]) {
    const group: any = {};

    let passwordValidation = false;

    for (let i = 0; i < baseInputs.length; i++) {
      let baseInput = baseInputs[i];

      if (baseInput.isGroup) {
        group[baseInput.key] = this.getFormGroup(baseInput.inputs);
      } else {
        let listValidations: any = [];
        let validations = baseInput.validations;

        for (let i = 0; i < validations.length; i++) {
          let validation = validations[i];

          if (validation.name === ValidationConstants.REQUIRED) {
            listValidations.push(Validators.required);
          }

          if (validation.name === ValidationConstants.MIN_LENGTH) {
            listValidations.push(Validators.minLength(validation.value));
          }

          if (validation.name === ValidationConstants.MAX_LENGTH) {
            listValidations.push(Validators.maxLength(validation.value));
          }

          if (validation.name === ValidationConstants.REGULAR_EXPRESSION) {
            listValidations.push(Validators.pattern(validation.value));
          }

          if (validation.name === ValidationConstants.PASSWORD_VALIDATION) {
            passwordValidation = true;
          }
        }

        group[baseInput.key] = new FormControl(
          baseInput.value || '',
          listValidations
        );
      }
    }

    if (passwordValidation) {
      return new FormGroup(group, {
        validators: [Validations.validatePasswordInput.bind(this)],
      });
    } else {
      return new FormGroup(group);
    }
  }
}
