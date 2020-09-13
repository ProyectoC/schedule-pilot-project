import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from '@models/user';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from '../../../shared/forms/components/base-form.component';
import { RoutingConstants } from '../../../constants/routing-constants';
import { RegisterPasswordFormComponent } from '../register-password-form/register-password-form.component';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent extends BaseFormComponent implements OnInit {
  @ViewChild(RegisterPasswordFormComponent, { static: true })
  registerPasswordForm: RegisterPasswordFormComponent;

  public payLoad: string;

  public rolAccounts = new Map([
    [2, 'Registro y Control'],
    [3, 'Docente de Planta'],
    [4, 'Docente de Catedra'],
    [5, 'Estudiante'],
  ]);
  public collegeCareers = new Map([
    [1, 'Administración de Empresas'],
    [2, 'Administración Ambiental'],
    [3, 'Arquitectura'],
    [4, 'Contaduria Publica'],
  ]);

  constructor(private formBuilder: FormBuilder, private spinner: NgxSpinnerService) {
    super();
  }

  ngOnInit(): void {
    this.spinner.show();
    this.buildForm();
  }

  get validations() {
    return Validations;
  }

  get routingConstants() {
    return RoutingConstants;
  }

  buildForm() {
    this.formGroup = this.formBuilder.group({
      firstName: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      ],
      lastName: [
        '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(100),
        ],
      ],
      identification: ['', [Validators.required, Validators.maxLength(20)]],
      identificationCode: ['', [Validators.required, Validators.maxLength(20)]],
      email: [
        '',
        Validators.compose([
          Validators.pattern(
            '^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$'
          ),
          Validators.required,
        ]),
      ],
      emailBackup: [
        '',
        Validators.compose([
          Validators.pattern(
            '^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$'
          ),
          Validators.required,
        ]),
      ],
      rolAccount: ['', Validators.required],
      collegeCareer: ['', Validators.required],
      password: this.registerPasswordForm.createGroup(),
      termAndConditions: [false, Validators.requiredTrue],
    });
  }

  // onSubmit() {
  //   if (this.registerForm.valid) {
  //     const user: User = new User();
  //     user.password = this.registerForm.get('passwordFormGroup').value[
  //       'password'
  //     ];
  //     user.firstName = this.registerForm.value['firstName'];
  //     user.lastName = this.registerForm.value['lastName'];
  //     user.identification = this.registerForm.value['identification'];
  //     user.identificationCode = this.registerForm.value['identificationCode'];
  //     user.email = this.registerForm.value['email'];
  //     user.emailBackup = this.registerForm.value['emailBackup'];
  //     user.rolAccount.id = Number(this.registerForm.value['rolAccount']);
  //     user.collegeCareer.id = Number(this.registerForm.value['collegeCareer']);
  //   } else {
  //     Validations.validateAllFormFields(this.registerForm);
  //   }
  // }

  trackByFn(index, item) {
    return item.id;
  }
}
