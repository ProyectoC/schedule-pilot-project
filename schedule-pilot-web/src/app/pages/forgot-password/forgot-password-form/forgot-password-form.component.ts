import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ForgotPassword } from '@models/forgot-password';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';

@Component({
  selector: 'app-forgot-password-form',
  templateUrl: './forgot-password-form.component.html',
  styleUrls: ['./forgot-password-form.component.scss']
})
export class ForgotPasswordFormComponent extends BaseFormComponent implements OnInit {

  constructor(private formBuilder: FormBuilder) { 
    super();
  }

  ngOnInit(): void {
    this.buildForm();
  }

  public buildForm(): void {
    this.formGroup = this.formBuilder.group({
      user: ['', [Validators.required]],
    });
  }

  get validations() {
    return Validations;
  }
  
  public onSubmit() {
    if (this.formGroup.valid) {
      const forgotPasswordRequest: ForgotPassword = new ForgotPassword();
      forgotPasswordRequest.username = this.formGroup.value['user'];
      this.onSubmitForm.emit(forgotPasswordRequest);
    } else {
      Validations.validateAllFormFields(this.formGroup);
    }
  }


}
