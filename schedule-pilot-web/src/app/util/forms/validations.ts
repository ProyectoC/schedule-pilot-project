import { FormControl, FormGroup } from '@angular/forms';

export class Validations {
  static isValidCheck(field: string, formGroup: FormGroup) {
    if (!formGroup.get(field).errors) {
      return 'fa fa-check-circle check';
    } else if (formGroup.get(field).errors && formGroup.get(field).touched) {
      return 'fa fa-exclamation-circle uncheck';
    }
  }

  static isValidInput(field: string, formGroup: FormGroup) {
    if (!formGroup.get(field).errors) {
      return 'is-valid';
    } else if (formGroup.get(field).errors && formGroup.get(field).touched) {
      return 'is-invalid';
    }
  }

  static isCheckValid(field: string, formGroup: FormGroup) {
    if (formGroup.get(field).errors && formGroup.get(field).touched) {
      return true;
    } else {
      return false;
    }
  }

  static isCheckCaptcha(field: string, formGroup: FormGroup) {
    if (formGroup.get(field).errors && formGroup.get(field).touched) {
      return true;
    } else {
      return false;
    }
  }

  static validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach((field) => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  static validateFormData(formGroup: FormGroup) {
    if (!formGroup.valid) {
      Validations.validateAllFormFields(formGroup);
      return false;
    }
    return true;
  }

  static validatePasswords(
    passwordKey: string,
    passwordConfirmationKey: string
  ) {
    return (group: FormGroup) => {
      const passwordInput = group.controls[passwordKey];
      const passwordConfirmationInput = group.controls[passwordConfirmationKey];
      if (passwordInput.value !== passwordConfirmationInput.value) {
        return passwordConfirmationInput.setErrors({ notEquivalent: true });
      } else {
        return passwordConfirmationInput.setErrors(null);
      }
    };
  }
}
