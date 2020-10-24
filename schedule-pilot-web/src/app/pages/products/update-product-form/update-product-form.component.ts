import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { RoutingConstants } from '@constants/routing-constants';
import { ProductRequest } from '@models/product/request/product-request';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';

@Component({
  selector: 'app-update-product-form',
  templateUrl: './update-product-form.component.html',
  styleUrls: ['./update-product-form.component.scss']
})
export class UpdateProductFormComponent extends BaseFormComponent implements OnInit {

  public productStatus = new Map([
    [1, 'Agotado'],
    [51, 'Disponible'],
    [101, 'En prestamo'],
  ]);

  public productTypes = new Map([[1, 'Tecnologia']]);

  constructor(private formBuilder: FormBuilder) {
    super();
  }

  ngOnInit(): void {
  }

  get validations() {
    return Validations;
  }

  get routingConstants() {
    return RoutingConstants;
  }

  buildForm(productRequest: ProductRequest) {
    this.formGroup = this.formBuilder.group({
      name: [productRequest.name, [Validators.required]],
      description: [productRequest.description, [Validators.required]],
      serial1: [productRequest.serial1, [Validators.required]],
      observations: [productRequest.observations, [Validators.required]],
      productStatus: [productRequest.productStatus.id, [Validators.required]],
      productType: [productRequest.productType.id, [Validators.required]],
    });
  }

}
