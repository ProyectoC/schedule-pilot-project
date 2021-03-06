import { NgModule , CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import { CommonModule } from '@angular/common';
// Forms
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// Routing
import { PublicLayoutRouting } from './public-layout.routing';
// Modules
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// Components
import { AuthenticationComponent } from '../../pages/authentication/authentication.component';
import { RegisterComponent } from '../../pages/register/register.component';
import { ShowPasswordComponent } from '../../pages/authentication/show-password/show-password.component';

import { DynamicFormComponent } from '../../shared/forms/components/dynamic-form/dynamic-form.component';
import { DynamicFormGroupComponent } from '../../shared/forms/components/dynamic-form-group/dynamic-form-group.component';
import { DynamicFormInputComponent } from '../../shared/forms/components/dynamic-form-input/dynamic-form-input.component';

import { RegisterFormComponent } from '../../pages/register/register-form/register-form.component';
import { RegisterPasswordFormComponent } from '../../pages/register/register-password-form/register-password-form.component';

import { ForgotPasswordComponent } from '../../pages/forgot-password/forgot-password.component';
import { ForgotPasswordFormComponent } from '../../pages/forgot-password/forgot-password-form/forgot-password-form.component';

// Services
import { FormsService } from '@services/forms/forms.service';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';

import { NgxSpinnerModule } from "ngx-spinner";

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  imports: [
    NgxSpinnerModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    PublicLayoutRouting,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
      isolate: false,
    }),
  ],
  declarations: [
    ShowPasswordComponent,
    AuthenticationComponent,
    RegisterComponent,
    DynamicFormComponent,
    DynamicFormGroupComponent,
    DynamicFormInputComponent,
    RegisterFormComponent,
    RegisterPasswordFormComponent,
    ForgotPasswordComponent,
    ForgotPasswordFormComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PublicLayoutModule {
  static forRoot(): any {
    return {
      ngModule: PublicLayoutModule,
      providers: [FormsService],
    };
  }
}
