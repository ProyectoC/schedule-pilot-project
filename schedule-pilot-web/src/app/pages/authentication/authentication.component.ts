import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Response } from '@models/response';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorResponse } from '@models/error-response';
import { Login } from '@models/login';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { MessagesService } from '@services/messages/message.service';
import { environment } from '@env/environment';
import { Validations } from '@utils/forms/validations';
import { CommonConstants } from '@constants/common-constants';
import { RoutingConstants } from '@constants/routing-constants';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss'],
})
export class AuthenticationComponent implements OnInit {
  public authForm: FormGroup;
  public properties: any;
  public variables: any;
  public isLoadingForm: boolean;
  public labelButtonLogin: string;

  public validations: any;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    public authenticationService: AuthenticationService,
    private scrollTop: ScrollTopService,
    private messageService: MessagesService
  ) {
    this.authForm = this.createForm();
    this.properties = environment.components.login;
    this.variables = environment;
    this.loadingForm(false);
    this.validations = Validations;
  }

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
  }

  get routingConstants() {
    return RoutingConstants
  }

  private createForm() {
    return this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      rememberPassword: false,
    });
  }

  

  trackByFn(index, item) {
    return item.id;
  }

  loadingForm(isLoading: boolean) {
    if (isLoading) {
      this.isLoadingForm = true;
    } else {
      this.isLoadingForm = false;
    }
  }

  validateData() {
    if (this.authForm.valid) {
      const login: Login = new Login();
      login.username = this.authForm.value['username'];
      login.password = this.authForm.value['password'];
      this.loginUser(login);
    } else {
      Validations.validateAllFormFields(this.authForm);
    }
  }

  loginUser(user: Login) {
    this.loadingForm(true);
    this.authenticationService.loginUser(user).subscribe(
      (bodyResponse: Response) => {
        this.loadingForm(false);
        if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
          this.router.navigate([RoutingConstants.URL_HOME]).then(() => {});
        } else {
          this.messageService.generateErrorMessage(
            'Error Autenticación Normal',
            bodyResponse.description
          );
        }
      },
      (error) => {
        console.log(error);
        this.loadingForm(false);
        const errorResponse: HttpErrorResponse = error;
        switch (errorResponse.status) {
          case 0:
            this.messageService.generateErrorMessage(
              'Error Autenticación',
              null
            );
            break;
          case 400:
            const errorResponse: ErrorResponse = error.error;
            this.messageService.generateErrorMessage(
              'Error Autenticación',
              errorResponse.result.message
            );
            break;
        }
      }
    );
  }

  showPassword() {
    var x: any = document.getElementById('password-user');
    if (x.type === 'password') {
      x.type = 'text';
      document.getElementById('icon-password').classList.add('fa-eye-slash');
      document.getElementById('icon-password').classList.remove('fa-eye');
    } else {
      x.type = 'password';
      document.getElementById('icon-password').classList.remove('fa-eye-slash');
      document.getElementById('icon-password').classList.add('fa-eye');
    }
  }
}
