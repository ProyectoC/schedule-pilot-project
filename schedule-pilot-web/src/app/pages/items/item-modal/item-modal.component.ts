import { ChangeDetectorRef, Component, ElementRef, EventEmitter, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { ItemRequest } from '@models/item/request/item-request';
import { BaseModalComponent } from 'app/modals/components/base-modal.component';
import { ModalsService } from 'app/modals/services/modals.service';
import { ItemFormComponent } from '../item-form/item-form.component';

@Component({
  selector: 'app-item-modal',
  templateUrl: './item-modal.component.html',
  styleUrls: ['./item-modal.component.scss']
})
export class ItemModalComponent extends BaseModalComponent implements OnInit, OnDestroy {

  @Output() onCreateItem = new EventEmitter<ItemRequest>();
  @Output() onUpdateItem = new EventEmitter<ItemRequest>();

  itemFormComponent: ItemFormComponent;
  @ViewChild(ItemFormComponent, { static: false }) set contentCreateProduct(content: ItemFormComponent) {
    if (content) {
      this.itemFormComponent = content;
    }
  }

  public modalTitle: string;
  public isCreate: boolean;
  public isUpdate: boolean;
  public productId: number;
  public itemRequest: ItemRequest;

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
    this.modalTitle = 'Crear Articulo';
    this.changeDetectorRef.detectChanges();
    this.itemFormComponent.productId = this.productId;
    this.itemFormComponent.buildForm();
  }

  initModalToUpdate(itemRequest: ItemRequest) {
    this.itemRequest = itemRequest;
    this.isCreate = false;
    this.isUpdate = true;
    this.modalTitle = 'Actualizar Articulo';
    this.changeDetectorRef.detectChanges();
    this.itemFormComponent.buildFormWithValues(itemRequest);
  }

  submitModal(event: ItemRequest): void {
    if (this.isCreate) {
      this.onCreateItem.emit(event);
    } else if (this.isUpdate) {
      this.onUpdateItem.emit(event);
    }
  }

}
