import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { FormTemplate } from '@models/form-template';

@Injectable({
  providedIn: 'root',
})
export class FormsService {
  constructor() {}

  getFormGroup(formTemplate: FormTemplate) {
    let authForm = new FormGroup({});
    formTemplate.listItems.forEach((itemForm) => {
      authForm.addControl(itemForm.label, new FormControl(itemForm.value, itemForm.required ? Validators.required : null));
    });
    return authForm;
  }
}
