import { Component, EventEmitter, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ItemStatus } from '@models/item/item-status';
import { ItemRequest } from '@models/item/request/item-request';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { Observable } from 'rxjs';
import { ItemDetailFormComponent } from '../item-detail-form/item-detail-form.component';
import { ItemStatusService } from '@services/item-status/item-status.service';
import { ProductResponse } from '@models/product/response/product-response';
import { Product } from '@models/product/product';
import { ProductService } from '@services/products/product.service';
import { ItemDetail } from '@models/item/item-detail';
import { Validations } from '@utils/forms/validations';

@Component({
  selector: 'app-item-form',
  templateUrl: './item-form.component.html',
  styleUrls: ['./item-form.component.scss']
})
export class ItemFormComponent extends BaseFormComponent implements OnInit {

  @ViewChildren(ItemDetailFormComponent)
  public itemDetailFormComponents: QueryList<ItemDetailFormComponent>;

  public itemStatus$: Observable<ItemStatus[]>;
  public products$: Observable<ProductResponse[]>;

  public productId: number;
  public itemRequest: ItemRequest;
  public itemDetails: ItemDetail[] = [];

  @Output() onSubmitForm = new EventEmitter<ItemRequest>();

  constructor(private formBuilder: FormBuilder, public itemStatusService: ItemStatusService,
    public productService: ProductService) {
    super();
  }

  ngOnInit(): void {
  }

  public initDataSources(): void {
    this.itemStatus$ = null;
    this.products$ = null;
    this.itemStatus$ = this.itemStatusService.getItemsStatus();
    this.products$ = this.productService.getProductsList();
    this.itemDetails = [];
  }

  public buildForm(): void {
    this.initDataSources();
    this.itemRequest = new ItemRequest();
    this.formGroup = this.formBuilder.group({
      name: ['', [Validators.required]],
      serial1: ['', [Validators.required]],
      itemStatus: ['', [Validators.required]],
      product: [this.productId ? this.productId : '', [Validators.required]],
    });
  }

  public buildFormWithValues(itemRequest: ItemRequest): void {
    this.initDataSources();
    this.itemRequest = itemRequest;
    this.formGroup = this.formBuilder.group({
      name: [itemRequest.name, [Validators.required]],
      serial1: [itemRequest.serial1, [Validators.required]],
      itemStatus: [itemRequest.itemStatus.id, [Validators.required]],
      product: [itemRequest.product.id, [Validators.required]],
    });
    itemRequest.itemDetails.forEach((itemDetail) => {
      this.addItemDetailWithValue(itemDetail);
    });
  }

  public addItemDetail(): void {
    this.itemDetails.push(new ItemDetail());
  }

  public addItemDetailWithValue(itemDetail: ItemDetail): void {
    this.itemDetails.push(itemDetail);
  }

  public deleteItemDetail(itemDetailIdex: number): void {
    this.itemDetails.splice(itemDetailIdex, 1);
  }

  public submit(): void {
    let itemDetails: ItemDetail[] = [];
    for (let i = 0; i < this.itemDetailFormComponents.toArray().length; i++) {
      let itemDetailComponent = this.itemDetailFormComponents.toArray()[i];
      if (!itemDetailComponent.isFormValid()) {
        return;
      }
      let itemDetail: ItemDetail = itemDetailComponent.submit();
      itemDetails.push(itemDetail);
    }

    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      this.itemRequest.name = this.formGroup.get('name').value;
      this.itemRequest.serial1 = this.formGroup.get('serial1').value;
      this.itemRequest.itemStatus.id = this.formGroup.get('itemStatus').value;
      this.itemRequest.product.id = this.formGroup.get('product').value;
      this.itemRequest.itemDetails = itemDetails;
      this.onSubmitForm.emit(this.itemRequest);
    }
  }

}
