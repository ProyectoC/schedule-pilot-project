import { Component, OnInit } from '@angular/core';
import {
  AbstractControlOptions,
  FormBuilder,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { BaseSubFormComponent } from '../../../shared/forms/components/base-sub-form.component';
import { Password } from '../../../models/password';
import { Validations } from '@utils/forms/validations';

type FormGroupConfig<T> = {
  [P in keyof T]: [
    T[P] | { value: T[P]; disabled: boolean },
    (AbstractControlOptions | ValidatorFn | ValidatorFn[])?
  ];
};

@Component({
  selector: 'app-register-password-form',
  templateUrl: './register-password-form.component.html',
  styleUrls: ['./register-password-form.component.scss'],
})
export class RegisterPasswordFormComponent
  extends BaseSubFormComponent
  implements OnInit {
  constructor(private formBuilder: FormBuilder) {
    super();
  }

  ngOnInit(): void {}

  createGroup() {
    const config: FormGroupConfig<Password> = {
      password: [
        '',
        Validators.compose([
          Validators.pattern(
            '^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$'
          ),
          Validators.required,
        ]),
      ],
      confirmPassword: ['', Validators.required],
    };

    this.formGroup = this.formBuilder.group(config, {
      validator: Validations.validatePasswords('password', 'confirmPassword'),
    });

    return this.formGroup;
  }
}
