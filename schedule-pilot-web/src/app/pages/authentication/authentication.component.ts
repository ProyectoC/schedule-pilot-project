import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Response } from '@models/response';
import { Router } from '@angular/router';
import { AuthUser } from '@models/auth-user';
import { FormGroup } from '@angular/forms';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { Validations } from '@utils/forms/validations';
import { RoutingConstants } from '@constants/routing-constants';
import { FormsService } from '@services/forms/forms.service';
import { FormTemplate } from '@models/form-template';
import { ItemFormTemplate } from '@models/item-form-template';
import { Observable } from 'rxjs';
import { BaseInput } from 'app/shared/forms/model/base-input';
import { DynamicFormComponent } from 'app/shared/forms/components/dynamic-form/dynamic-form.component';
import { TestDynamicFormService } from '../../shared/forms/services/dynamic-form/test-dynamic-form.service';
import { AuthResponse } from '@models/authentication/response/auth-response';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss'],
})
export class AuthenticationComponent implements OnInit {
  // Forms

  public controls$: Observable<BaseInput<any>[]>;

  @ViewChild(DynamicFormComponent, { static: false })
  private dynamicFormComponent: DynamicFormComponent;

  public authForm: FormGroup;

  constructor(
    private router: Router,
    public formsService: FormsService,
    public authenticationService: AuthenticationService,
    private scrollTopService: ScrollTopService,
    public testDynamicFormService: TestDynamicFormService
  ) {
    this.controls$ = testDynamicFormService.getControls();
    this.buildAuthForm();
  }

  submitFormDynamic(event: string) {
    console.log(event);
  }

  cancelProcess() {
    this.dynamicFormComponent.cleanForm();
  }

  ngOnInit(): void {
    this.scrollTopService.setScrollTop();
  }

  private buildAuthForm() {
    let formTemplate = new FormTemplate();
    formTemplate.addItem(new ItemFormTemplate('username', '', true));
    formTemplate.addItem(new ItemFormTemplate('password', '', true));
    formTemplate.addItem(new ItemFormTemplate('rememberPassword', true, false));
    this.authForm = this.formsService.getFormGroup(formTemplate);
  }

  get routingConstants() {
    return RoutingConstants;
  }

  get validations() {
    return Validations;
  }

  authenticateUser() {
    if (!Validations.validateFormData(this.authForm)) {
      return;
    }

    let authUser: AuthUser = new AuthUser();
    authUser.username = this.authForm.value['username'];
    authUser.password = this.authForm.value['password'];

    this.authenticationService.authenticateUser(authUser).subscribe(
      (bodyResponse: Response<AuthResponse>) => {
        this.router.navigate([RoutingConstants.URL_HOME]).then(() => {});
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
