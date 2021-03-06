import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrivateLayoutRouting } from './private-layout.routing';

// Forms
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// HOME
import { HomeComponent } from '../../pages/home/home.component';

// PRODUCTS
import { ProductsContainerComponent } from '../../pages/products/products-container/products-container.component';
import { ProductModalComponent } from '../../pages/products/product-modal/product-modal.component';
import { CreateProductFormComponent } from '../../pages/products/create-product-form/create-product-form.component';
import { ProductRolFormComponent } from '../../pages/products/product-rol-form/product-rol-form.component';
import { ProductSearchComponent } from '../../pages/products/product-search/product-search.component';

// ITEMS
import { ItemsContainerComponent } from '../../pages/items/items-container/items-container.component';
import { ItemFormComponent } from '../../pages/items/item-form/item-form.component';
import { ItemDetailFormComponent } from '../../pages/items/item-detail-form/item-detail-form.component';
import { ItemModalComponent } from '../../pages/items/item-modal/item-modal.component';
import { ItemSearchComponent } from '../../pages/items/item-search/item-search.component';

// LOANS
import { LoansContainerComponent } from '../../pages/loans/loans-container/loans-container.component';
import { LoanProcessComponent } from '../../pages/loans/loan-process/loan-process.component';
import { LoanProductsComponent } from '../../pages/loans/loan-products/loan-products.component';
import { LoanProductModalComponent } from '../../pages/loans/loan-product-modal/loan-product-modal.component';
import { LoanProductsSummaryComponent } from '../../pages/loans/loan-products-summary/loan-products-summary.component';
import { LoanRequestCheckInContainerComponent } from '../../pages/loans/loan-request-check-in-container/loan-request-check-in-container.component';
import { LoanTicketCheckInContainerComponent } from '../../pages/loans/loan-ticket-check-in-container/loan-ticket-check-in-container.component';
import { LoanRequestCheckInSearchComponent } from '../../pages/loans/loan-request-check-in-search/loan-request-check-in-search.component';
import { LoanTicketCheckInSearchComponent } from '../../pages/loans/loan-ticket-check-in-search/loan-ticket-check-in-search.component';

// RETURNS
import { ReturnsContainerComponent } from '../../pages/returns/returns-container/returns-container.component';
import { ReturnTicketCheckOutContainerComponent } from '../../pages/returns/return-ticket-check-out-container/return-ticket-check-out-container.component';
import { ReturnTicketCheckOutSearchComponent } from '../../pages/returns/return-ticket-check-out-search/return-ticket-check-out-search.component';


// USERS
import { UserContainerComponent } from '../../pages/users/user-container/user-container.component';

// REPORTS
import { ReportFilterComponent } from 'app/pages/reports/report-filter/report-filter.component';
import { ReportContainerComponent } from 'app/pages/reports/report-container/report-container.component';
import { ReportChartsComponent } from 'app/pages/reports/report-charts/report-charts.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatTabsModule } from '@angular/material/tabs';

@NgModule({
  imports: [
    CommonModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    PrivateLayoutRouting,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatCardModule,
    MatMenuModule,
    MatTabsModule
  ],
  declarations: [HomeComponent, ProductsContainerComponent, ProductModalComponent, CreateProductFormComponent, ProductRolFormComponent, ProductSearchComponent,
    ItemsContainerComponent, ItemFormComponent, ItemDetailFormComponent, ItemModalComponent, ItemSearchComponent,
    LoansContainerComponent, LoanProcessComponent, LoanProductsComponent, LoanProductModalComponent, LoanProductsSummaryComponent,
    LoanRequestCheckInContainerComponent, LoanTicketCheckInContainerComponent, LoanRequestCheckInSearchComponent,
    ReturnsContainerComponent, ReturnTicketCheckOutContainerComponent, LoanTicketCheckInSearchComponent, ReturnTicketCheckOutSearchComponent,
    UserContainerComponent, ReportFilterComponent, ReportContainerComponent, ReportChartsComponent],
})
export class PrivateLayoutModule { }
