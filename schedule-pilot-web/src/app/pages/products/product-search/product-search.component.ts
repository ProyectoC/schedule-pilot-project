import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ProductStatus } from '@models/product/product-status';
import { ProductStatusService } from '@services/product-status/product-status.service';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { Observable } from 'rxjs';
import { ProductSearchParameters } from '@models/product/request/product-search-parameters';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.scss']
})
export class ProductSearchComponent extends BaseFormComponent implements OnInit {

  public productStatus$: Observable<ProductStatus[]>;
  @Output() onSubmitForm = new EventEmitter<ProductSearchParameters>();
  
  constructor(private formBuilder: FormBuilder, public productStatusService: ProductStatusService) { 
    super();
  }

  ngOnInit(): void {
    this.buildForm();
  }

  public initDataSources(): void {
    this.productStatus$ = null;
    this.productStatus$ = this.productStatusService.getProductStatus();
  }

  public buildForm(): void {
    this.initDataSources();
    this.formGroup = this.formBuilder.group({
      name: ['', []],
      description: ['', []],
      productStatus: ['', []]
    });
  }

  public onSubmit(): void {
    
    let productSearchParameters: ProductSearchParameters = new ProductSearchParameters();
    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      productSearchParameters.name = this.formGroup.get('name').value;
      productSearchParameters.description = this.formGroup.get('description').value;
      productSearchParameters.status = this.formGroup.get('productStatus').value;
      this.onSubmitForm.emit(productSearchParameters);
    }
  }

}
