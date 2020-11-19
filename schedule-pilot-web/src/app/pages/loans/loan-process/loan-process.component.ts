import { Component, EventEmitter, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { ProductRequest } from '@models/product/request/product-request';
import { ProductResponse } from '@models/product/response/product-response';
import { LoanProductsSummaryComponent } from '../loan-products-summary/loan-products-summary.component';
import { RequestCheckIn } from '@models/request-check-in/request-check-in';
import { ProductCheckIn } from '@models/request-check-in/product-check-in';
import { AuthenticationService } from '@services/authentication/authentication.service';

@Component({
  selector: 'app-loan-process',
  templateUrl: './loan-process.component.html',
  styleUrls: ['./loan-process.component.scss']
})
export class LoanProcessComponent implements OnInit {

  @ViewChildren(LoanProductsSummaryComponent)
  public loanProductsSummaryComponents: QueryList<LoanProductsSummaryComponent>;

  public productRequests: ProductRequest[];
  public requestCheckIn: RequestCheckIn;
  public isLoading: boolean;

  @Output() onSubmitForm = new EventEmitter<RequestCheckIn>();

  constructor(public authService: AuthenticationService) { }

  ngOnInit(): void {
  }

  addProductToLoad(productResponse: ProductResponse) {
    this.productRequests.push(productResponse);
  }

  public submit(): void {
    let productCheckIns: ProductCheckIn[] = [];
    for (let i = 0; i < this.loanProductsSummaryComponents.toArray().length; i++) {
      let loanProductsSummaryComponent = this.loanProductsSummaryComponents.toArray()[i];
      if (loanProductsSummaryComponent.isSelected) {
        if (!loanProductsSummaryComponent.isFormValid()) {
          return;
        }
        let productCheckIn: ProductCheckIn = loanProductsSummaryComponent.submit();
        productCheckIns.push(productCheckIn);
      }
    }
    this.requestCheckIn = new RequestCheckIn();
    this.requestCheckIn.userAccountId = this.authService.userSession.user.id;
    this.requestCheckIn.checkInProductRequest = productCheckIns;
    this.onSubmitForm.emit(this.requestCheckIn);
  }

}
