import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ProductRol } from '@models/product/product-rol';
import { RolAccount } from '@models/rol/rol-account';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { CommonUtils } from '@utils/common-utils'

@Component({
  selector: 'app-product-rol-form',
  templateUrl: './product-rol-form.component.html',
  styleUrls: ['./product-rol-form.component.scss']
})
export class ProductRolFormComponent extends BaseFormComponent implements OnInit {

  public isSelected: boolean;
  public textTime: string;

  public _rolAccount: RolAccount;
  public _productRol: ProductRol;

  constructor(private formBuilder: FormBuilder) {
    super();
  }

  @Input()
  set rolAccount(rolAccount: RolAccount) {
    this._rolAccount = rolAccount;
  }

  @Input()
  set productRol(productRol: ProductRol) {
    this._productRol = productRol;
  }

  ngOnInit(): void {
    this.buildForm();
    this.initChangeListeners();
  }

  public buildForm(): void {
    this.formGroup = this.formBuilder.group({
      loanTime: [(this._productRol ? this._productRol.loanTime : 0), this._productRol ? [Validators.required] : []],
    });

    if (this._productRol) {
      this.isSelected = true;
      this.updateLoanTimeText(this._productRol.loanTime);
    }
  }

  public initChangeListeners(): void {
    this.formGroup.controls.loanTime.valueChanges.subscribe((value) => {
      this.updateLoanTimeText(value);
    });
  }

  public updateLoanTimeText(loanTime: number) {
    this.textTime = CommonUtils.countdown(loanTime);
  }

  public changeStatus(): void {
    this.isSelected = !this.isSelected;
    if (this.isSelected) {
      this.formGroup.controls.loanTime.setValidators(Validators.compose([Validators.required]));
    } else {
      this.formGroup.controls.loanTime.setValidators(null);
    }
    this.formGroup.controls.loanTime.updateValueAndValidity();
  }

  public submit(): ProductRol {
    let productRol: ProductRol = new ProductRol();
    productRol.loanTime = this.formGroup.get('loanTime').value;
    productRol.rol = this._rolAccount.id;
    return productRol;
  }

  public isFormValid(): boolean {
    return Validations.validateFormData(this.formGroup);
  }

}
