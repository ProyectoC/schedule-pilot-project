import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from '../../../shared/forms/components/base-form.component';
import { RoutingConstants } from '../../../constants/routing-constants';

@Component({
  selector: 'app-create-product-form',
  templateUrl: './create-product-form.component.html',
  styleUrls: ['./create-product-form.component.scss'],
})
export class CreateProductFormComponent extends BaseFormComponent implements OnInit {
    
  public productStatus = new Map([
    [1, 'Agotado'],
    [2, 'Disponible'],
    [3, 'En prestamo'],
  ]);

  public productTypes = new Map([[1, 'Tecnologia']]);

  constructor(private formBuilder: FormBuilder) {
    super();
  }

  ngOnInit(): void {
    this.buildForm();
  }

  get validations() {
    return Validations;
  }

  get routingConstants() {
    return RoutingConstants;
  }

  buildForm() {
    this.formGroup = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      observations: ['', [Validators.required]],
      productStatus: ['', [Validators.required]]
    });
  }
}
