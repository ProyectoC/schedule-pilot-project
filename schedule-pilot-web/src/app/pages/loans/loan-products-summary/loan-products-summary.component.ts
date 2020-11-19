import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ProductRequest } from '@models/product/request/product-request';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { ProductCheckIn } from '@models/request-check-in/product-check-in';
import { Validations } from '@utils/forms/validations';

@Component({
  selector: 'app-loan-products-summary',
  templateUrl: './loan-products-summary.component.html',
  styleUrls: ['./loan-products-summary.component.scss']
})
export class LoanProductsSummaryComponent extends BaseFormComponent implements OnInit {

  public isSelected: boolean;
  public _productRequest: ProductRequest;

  constructor(private formBuilder: FormBuilder) { 
    super();
  }

  @Input()
  set productRequest(productRequest: ProductRequest) {
    this._productRequest = productRequest;
  }

  ngOnInit(): void {
    this.buildForm();
  }

  public buildForm(): void {
    this.formGroup = this.formBuilder.group({
      dateRequest: ['', [Validators.required]],
      timeRequest: ['', [Validators.required]],
    });
    this.isSelected = true;
  }

  public changeStatus(): void {
    this.isSelected = !this.isSelected;
    if (this.isSelected) {
      this.formGroup.controls.dateRequest.setValidators(Validators.compose([Validators.required]));
      this.formGroup.controls.timeRequest.setValidators(Validators.compose([Validators.required]));
    } else {
      this.formGroup.controls.dateRequest.setValidators(null);
      this.formGroup.controls.timeRequest.setValidators(null);
    }
    this.formGroup.controls.dateRequest.updateValueAndValidity();
    this.formGroup.controls.timeRequest.updateValueAndValidity();
  }

  public submit(): ProductCheckIn {
    let productCheckIn: ProductCheckIn = new ProductCheckIn();
    productCheckIn.productId = this._productRequest.id;
    productCheckIn.loanDate =  this.formGroup.get('dateRequest').value + ' ' + this.formGroup.get('timeRequest').value + ":00";
    return productCheckIn;
  }

  public isFormValid(): boolean {
    return Validations.validateFormData(this.formGroup);
  }

}
