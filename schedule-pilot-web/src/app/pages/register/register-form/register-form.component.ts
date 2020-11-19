import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from '@models/user';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from '../../../shared/forms/components/base-form.component';
import { RoutingConstants } from '../../../constants/routing-constants';
import { RegisterPasswordFormComponent } from '../register-password-form/register-password-form.component';
import { Observable } from 'rxjs';
import { RolAccount } from '@models/rol/rol-account';
import { RolService } from '@services/rol/rol.service';
import { CollegeCareer } from '@models/college-career/college-career';
import { CollegeCareerService } from '@services/college-career/college-career.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent extends BaseFormComponent implements OnInit {
  
  @ViewChild(RegisterPasswordFormComponent, { static: true })
  registerPasswordForm: RegisterPasswordFormComponent;

  public payLoad: string;
  public roles$: Observable<RolAccount[]>;
  public collegeCareers$: Observable<CollegeCareer[]>;

  constructor(private formBuilder: FormBuilder, public rolService: RolService,
    public collegeCareerService: CollegeCareerService) {
    super();
    this.roles$ = this.rolService.getRoles();
    this.collegeCareers$ = this.collegeCareerService.getCollegeCareers();
  }

  ngOnInit(): void {
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

  onSubmit() {
    if (this.formGroup.valid) {
      const user: User = new User();
      user.password = this.formGroup.get('password').value[
        'password'
      ];
      user.firstName = this.formGroup.value['firstName'];
      user.lastName = this.formGroup.value['lastName'];
      user.identification = this.formGroup.value['identification'];
      user.identificationCode = this.formGroup.value['identificationCode'];
      user.email = this.formGroup.value['email'];
      user.emailBackup = this.formGroup.value['emailBackup'];
      user.rolAccount.id = Number(this.formGroup.value['rolAccount']);
      user.collegeCareer.id = Number(this.formGroup.value['collegeCareer']);
      this.onSubmitForm.emit(user);
    } else {
      Validations.validateAllFormFields(this.formGroup);
    }
  }

  trackByFn(index, item) {
    return item.id;
  }
}
