import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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
import { JsonPipe } from '@angular/common';
import { CountryService } from '@services/countries/country.service';
import { CountryResponse } from '@models/country/country-response';

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
  public countries$: Observable<CountryResponse[]>

  constructor(public rolService: RolService,
    public collegeCareerService: CollegeCareerService, public countryService: CountryService) {
    super();
    this.roles$ = this.rolService.getRoles();
    this.collegeCareers$ = this.collegeCareerService.getCollegeCareers();
    this.countries$ = this.countryService.getCountries();
  }

  ngOnInit(): void {
    this.buildForm();
    this.initValueChanges();
  }

  get validations() {
    return Validations;
  }

  get routingConstants() {
    return RoutingConstants;
  }

  buildForm() {
    this.formGroup = new FormGroup({
      firstName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      identification: new FormControl('', [Validators.required, Validators.min(99999), Validators.max(99999999999999999999)]),
      identificationCode: new FormControl('', [Validators.required, Validators.maxLength(20)]),
      email: new FormControl('', [Validators.pattern(
        '^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$'
      ), Validators.required]),
      emailBackup: new FormControl('', [Validators.pattern(
        '^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$'
      ), Validators.required]),
      rolAccount: new FormControl(null, [Validators.required]),
      collegeCareer: new FormControl('', [Validators.required]),
      password: this.registerPasswordForm.createGroup(),
      countryCode: new FormControl('', [Validators.required]),
      phoneNumber: new FormControl('', [Validators.required]),
      termAndConditions: new FormControl(false, [Validators.requiredTrue]),
    });
  }

  public initValueChanges() {
    this.formGroup.controls.rolAccount.valueChanges.subscribe((value) => {
      if (value === '5') {
        this.formGroup.controls.collegeCareer.enable();
      } else {
        this.formGroup.controls.collegeCareer.disable();
      }
      this.formGroup.controls.collegeCareer.updateValueAndValidity();
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
      user.phoneNumber = this.formGroup.value['phoneNumber'];
      user.phoneCountryCode = this.formGroup.value['countryCode'];
      user.rolAccount.id = Number(this.formGroup.value['rolAccount']);

      if (user.rolAccount.id === 5) {
        user.collegeCareer.id = Number(this.formGroup.value['collegeCareer']);
      } else {
        user.collegeCareer = null;
      }
      console.log('User: ', user);
      this.onSubmitForm.emit(user);
    } else {
      Validations.validateAllFormFields(this.formGroup);
    }
  }

  trackByFn(index, item) {
    return item.id;
  }
}
