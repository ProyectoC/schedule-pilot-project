import { NgModule } from '@angular/core';
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

// Services
import { FormsService } from '@services/forms/forms.service';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  imports: [
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
  ],
})
export class PublicLayoutModule {
  static forRoot(): any {
    return {
      ngModule: PublicLayoutModule,
      providers: [FormsService],
    };
  }
}
