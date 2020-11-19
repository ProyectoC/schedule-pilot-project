import { Component, EventEmitter, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from '../../../shared/forms/components/base-form.component';
import { RoutingConstants } from '../../../constants/routing-constants';
import { Observable } from 'rxjs';
import { ProductStatus } from '@models/product/product-status';
import { ProductStatusService } from '@services/product-status/product-status.service';
import { ProductRol } from '@models/product/product-rol';
import { RolService } from '@services/rol/rol.service';
import { RolAccount } from '@models/rol/rol-account';
import { ProductRolFormComponent } from '../product-rol-form/product-rol-form.component';
import { ProductRequest } from '@models/product/request/product-request';

@Component({
  selector: 'app-create-product-form',
  templateUrl: './create-product-form.component.html',
  styleUrls: ['./create-product-form.component.scss'],
})
export class CreateProductFormComponent extends BaseFormComponent implements OnInit {

  @ViewChildren(ProductRolFormComponent)
  public productRolFormComponents: QueryList<ProductRolFormComponent>;

  public productStatus$: Observable<ProductStatus[]>;
  public roles$: Observable<RolAccount[]>;

  public productRequest: ProductRequest;

  @Output() onSubmitForm = new EventEmitter<ProductRequest>();

  constructor(private formBuilder: FormBuilder, public productStatusService: ProductStatusService,
    public rolService: RolService) {
    super();
  }

  ngOnInit(): void {
  }

  public initDataSources(): void {
    this.productStatus$ = null;
    this.roles$ = null;
    this.productStatus$ = this.productStatusService.getProductStatus();
    this.roles$ = this.rolService.getRoles();
  }

  get validations() {
    return Validations;
  }

  get routingConstants() {
    return RoutingConstants;
  }

  public buildForm(): void {
    this.initDataSources();
    this.productRequest = new ProductRequest();
    this.formGroup = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      observations: ['', [Validators.required]],
      productStatus: ['', [Validators.required]]
    });
  }

  public buildFormWithValues(productRequest: ProductRequest): void {
    this.initDataSources();
    this.productRequest = productRequest;
    this.formGroup = this.formBuilder.group({
      name: [productRequest.name, [Validators.required]],
      description: [productRequest.description, [Validators.required]],
      observations: [productRequest.observations, [Validators.required]],
      productStatus: [productRequest.productStatus.id, [Validators.required]],
    });
  }

  public getProductRol(rolAccount: RolAccount): ProductRol {
    let productRolesCurrent = this.productRequest.productRoles;
    for (let i = 0; i < productRolesCurrent.length; i++) {
      let productRol = productRolesCurrent[i];
      if (productRol.rol === rolAccount.id) {
        return productRol;
      }
    }
    return null;
  }

  public submit(): void {
    let productRoles: ProductRol[] = [];
    for (let i = 0; i < this.productRolFormComponents.toArray().length; i++) {
      let productRolComponent = this.productRolFormComponents.toArray()[i];
      if (productRolComponent.isSelected) {
        if (!productRolComponent.isFormValid()) {
          return;
        }
        let productRol: ProductRol = productRolComponent.submit();
        productRoles.push(productRol);
      }
    }

    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      this.productRequest.name = this.formGroup.get('name').value;
      this.productRequest.description = this.formGroup.get('description').value;
      this.productRequest.observations = this.formGroup.get('observations').value;
      this.productRequest.productStatus.id = this.formGroup.get('productStatus').value;
      this.productRequest.productRoles = productRoles;
      this.onSubmitForm.emit(this.productRequest);
    }
  }
}
