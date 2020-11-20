import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { ChangePasswordRequest } from '@models/authentication/request/change-password-request';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Response } from '@models/response';
import { CommonConstants } from '@constants/common-constants';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorResponse } from '@models/error-response';
import { MessagesService } from '@services/messages/message.service';
import { RoutingConstants } from '@constants/routing-constants';

@Component({
  selector: 'app-user-container',
  templateUrl: './user-container.component.html',
  styleUrls: ['./user-container.component.scss']
})
export class UserContainerComponent extends BaseFormComponent implements OnInit {

  public isLoadingForm: boolean;
  
  constructor(private formBuilder: FormBuilder, public authService: AuthenticationService,
    private router: Router, private messageService: MessagesService) {
    super();
   }

  ngOnInit(): void {
    this.buildForm();
  }

  public buildForm(): void {
    this.isLoadingForm = false;
    this.formGroup = this.formBuilder.group({
      user: ['', Validators.required],
      password: ['', Validators.required],
      passwordFormGroup: this.formBuilder.group({
        password: ['', Validators.compose([Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$'), Validators.required])],
        confirmPassword: ['', Validators.required],
      }, { validator: Validations.validatePasswords('password', 'confirmPassword') }),
    });
  }

  public onSubmit(): void {
    if (this.formGroup.valid) {
      const changePasswordRequest: ChangePasswordRequest = new ChangePasswordRequest();
      changePasswordRequest.username = this.formGroup.value['user'];
      changePasswordRequest.actualPassword = this.formGroup.value['password'];
      changePasswordRequest.newPassword = this.formGroup.get('passwordFormGroup').value['password'];
      this.changePassword(changePasswordRequest);
    } else {
      Validations.validateAllFormFields(this.formGroup);
    }
  }

  changePassword(changePasswordRequest: ChangePasswordRequest) {
    this.isLoadingForm = true;
    this.authService.changePasswordUser(changePasswordRequest).subscribe(
      (bodyResponse: Response<any>) => {
        this.isLoadingForm = false;
        if (bodyResponse.code === CommonConstants.SUCCESS_CODE) {
          this.messageService.generateSuccessMessage('Cambiar Contraseña', bodyResponse.description);
          this.logout();
        } else {
          this.messageService.generateErrorMessage('Cambiar Contraseña', bodyResponse.description);
        }
      }, (error) => {
        console.log(error);
        this.isLoadingForm = false;
        const errorResponse: HttpErrorResponse = error;
        switch (errorResponse.status) {
          case 0:
            this.messageService.generateErrorMessage('Cambiar Contraseña', error.error);
            break;
          case 400:
            const errorResponse: ErrorResponse = error.error;
            this.messageService.generateErrorMessage('Cambiar Contraseña', errorResponse.result.message);
            break;
        }
      }
    );
  }

  logout() {
    this.authService.logout();
    this.router.navigate([RoutingConstants.URL_AUTHENTICATION]).then(() => {
    });
  }

}
