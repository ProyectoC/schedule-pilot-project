import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Response } from '@models/response';
import * as CommonConst from '@constants/common';
import * as HeaderConst from '@constants/header-menu';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorResponse } from '@models/error-response';
import { Login } from '@models/login';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss']
})
export class AuthenticationComponent implements OnInit {

  constructor(private router: Router, public authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }

  validateData() {
    // if (this.loginForm.valid) {
    //   const login: Login = new Login();
    //   login.userOrEmail = this.loginForm.value['user'];
    //   login.password = this.loginForm.value['password'];
    //   this.loginUser(login);
    // } else {
    //   Validations.validateAllFormFields(this.loginForm);
    // }

    const login: Login = new Login();
      login.username = 'cristhian117';
      login.password = 'Nirvana2020*';
      this.loginUser(login);
  }

  loginUser(user: Login) {
    // this.loadingForm(true);
    this.authenticationService.loginUser(user).subscribe(
      (bodyResponse: Response) => {
        // this.loadingForm(false);
        if (bodyResponse.code === CommonConst.SUCCESS_CODE) {
          this.router.navigate([HeaderConst.URL_HOME]).then(() => { });
        } else {
          // this.messageService.generateErrorMessage('Error Autenticación Normal', bodyResponse.description);
        }
      }, (error) => {
        console.log(error);
        // this.loadingForm(false);
        const errorResponse: HttpErrorResponse = error;
        switch (errorResponse.status) {
          case 0:
            // this.messageService.generateErrorMessage('Error Autenticación', null);
            break;
          case 400:
            const errorResponse: ErrorResponse = error.error;
            // this.messageService.generateErrorMessage('Error Autenticación', errorResponse.result.message);
            break;
        }
      }
    );
  }

}
