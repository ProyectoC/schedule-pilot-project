import { ChangeDetectorRef, Component, ElementRef, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { ProductRequest } from '@models/product/request/product-request';
import { BaseModalComponent } from 'app/modals/components/base-modal.component';
import { ModalsService } from 'app/modals/services/modals.service';

@Component({
  selector: 'app-loan-product-modal',
  templateUrl: './loan-product-modal.component.html',
  styleUrls: ['./loan-product-modal.component.scss']
})
export class LoanProductModalComponent extends BaseModalComponent implements OnInit, OnDestroy {

  @Output() onSelectedProductToLoan = new EventEmitter<ProductRequest>();

  public modalTitle: string;
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

  public selectProductToLoan(): void {
    this.onSelectedProductToLoan.emit(this.productRequest);
  }

}
