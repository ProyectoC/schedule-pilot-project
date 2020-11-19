import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  QueryList,
  ViewChild,
  ViewChildren,
} from '@angular/core';
import { ModalsService } from 'app/modals/services/modals.service';
import { BaseModalComponent } from '../../../modals/components/base-modal.component';
import { ProductRequest } from '@models/product/request/product-request';
import { CreateProductFormComponent } from '../create-product-form/create-product-form.component';

@Component({
  selector: 'app-product-modal',
  templateUrl: './product-modal.component.html',
  styleUrls: ['./product-modal.component.scss'],
})
export class ProductModalComponent extends BaseModalComponent implements OnInit, OnDestroy {

  @Output() onCreateProduct = new EventEmitter<ProductRequest>();
  @Output() onUpdateProduct = new EventEmitter<ProductRequest>();

  createProductFormComponent: CreateProductFormComponent;
  @ViewChild(CreateProductFormComponent, { static: false }) set contentCreateProduct(content: CreateProductFormComponent) {
    if (content) {
      this.createProductFormComponent = content;
    }
  }

  public modalTitle: string;
  public isCreate: boolean;
  public isUpdate: boolean;
  public productRequest: ProductRequest;

  constructor(public modalService: ModalsService, public elementRef: ElementRef, private changeDetectorRef: ChangeDetectorRef) {
    super(modalService, elementRef);
  }

  ngOnInit(): void {
    super.ngOnInit();
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
  }

  initModalToCreate() {
    this.isCreate = true;
    this.isUpdate = false;
    this.modalTitle = 'Crear Producto';
    this.changeDetectorRef.detectChanges();
    this.createProductFormComponent.buildForm();
  }

  initModalToUpdate(productRequest: ProductRequest) {
    this.productRequest = productRequest;
    this.isCreate = false;
    this.isUpdate = true;
    this.modalTitle = 'Actualizar Producto';
    this.changeDetectorRef.detectChanges();
    this.createProductFormComponent.buildFormWithValues(productRequest);
  }

  submitModal(event: ProductRequest): void {
    if (this.isCreate) {
      this.onCreateProduct.emit(event);
    } else if (this.isUpdate) {
      this.onUpdateProduct.emit(event);
    }
  }
}
