import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { RegisterService } from '@services/register/register.service';
import { environment } from '@env/environment';
import { RoutingConstants } from '@constants/routing-constants';
import { Validations } from '@utils/forms/validations';
import { User } from '@models/user';
import { Response } from '@models/response';
import { MessagesService } from '@services/messages/message.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  public registerForm: FormGroup;
  public variables: any;
  public isLoadingForm: boolean;
  public labelButtonLogin: string;
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

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    public registerService: RegisterService,
    private scrollTop: ScrollTopService,
    private messageService: MessagesService
  ) {
    this.registerForm = this.createForm();
    this.variables = environment;
    this.loadingForm(false);
  }

  get routingConstants() {
    return RoutingConstants;
  }

  get validations() {
    return Validations;
  }

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
  }

  trackByFn(index, item) {
    return item.id;
  }

  isValidCheck(field: string) {
    return Validations.isValidCheck(field, this.registerForm);
  }

  isValidInput(field: string) {
    return Validations.isValidInput(field, this.registerForm);
  }

  isCheckValid(field: string) {
    return Validations.isCheckValid(field, this.registerForm);
  }

  isCheckCaptcha(field: string) {
    return Validations.isCheckCaptcha(field, this.registerForm);
  }

  handleReset() {}

  handleExpire() {}

  handleLoad() {}

  handleSuccess() {}

  loadingForm(isLoading: boolean) {
    if (isLoading) {
      this.isLoadingForm = true;
    } else {
      this.isLoadingForm = false;
    }
  }

  private createForm() {
    return this.formBuilder.group({
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
      passwordFormGroup: this.formBuilder.group(
        {
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
        },
        {
          validator: Validations.validatePasswords(
            'password',
            'confirmPassword'
          ),
        }
      ),
      termAndConditions: [false, Validators.requiredTrue]
    });
  }

  validateData() {
    if (this.registerForm.valid) {
      const user: User = new User();
      user.password = this.registerForm.get(
        'passwordFormGroup'
      ).value['password'];
      user.firstName = this.registerForm.value['firstName'];
      user.lastName = this.registerForm.value['lastName'];
      user.identification = this.registerForm.value['identification'];
      user.identificationCode = this.registerForm.value['identificationCode'];
      user.email = this.registerForm.value['email'];
      user.emailBackup = this.registerForm.value['emailBackup'];
      user.rolAccount.id = Number(this.registerForm.value['rolAccount']);
      user.collegeCareer.id = Number(this.registerForm.value['collegeCareer']);
      this.registerUser(user);
    } else {
      Validations.validateAllFormFields(this.registerForm);
    }
  }

  registerUser(user: User) {
    this.registerService.registerUser(user).subscribe(
      (bodyResponse: Response) => {
        this.messageService.generateSuccessMessage(
          'Registro',
          'Registro exitoso, por favor revisa tu email para activar tu cuenta'
        );
        this.router.navigate([RoutingConstants.URL_AUTHENTICATION]).then(() => {});
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
