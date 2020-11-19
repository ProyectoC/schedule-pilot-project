import { AfterViewInit, Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ParametersQuery } from '@models/parameters-query';
import { ProductResponse } from '@models/product/response/product-response';
import { ProductService } from '@services/products/product.service';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { ProductPaginatorConfiguration } from 'app/config/pagination/product-paginator-configuration';
import { merge, of } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { LoanProductModalComponent } from '../loan-product-modal/loan-product-modal.component';

@Component({
  selector: 'app-loan-products',
  templateUrl: './loan-products.component.html',
  styleUrls: ['./loan-products.component.scss'],
  providers: [
    {
      provide: MatPaginatorIntl,
      useValue: new ProductPaginatorConfiguration().getPaginatorIntl(),
    },
  ],
})
export class LoanProductsComponent implements OnInit, AfterViewInit {

  public displayedColumns: string[] = [
    'number',
    'name',
    'description',
    'status',
    'select'
  ];
  public data: ProductResponse[] = [];
  public productSearch: string = '';
  public resultsLength = 0;
  public isLoadingResults = true;
  public isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(LoanProductModalComponent, { static: true })
  loanProductModalComponent: LoanProductModalComponent;
  
  @Output() onSelectProductToLoan = new EventEmitter<ProductResponse>();

  constructor(private scrollTop: ScrollTopService, public productsService: ProductService) { }

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    this.refreshProducts();
  }

  public refreshProducts() {
    this.paginator.pageIndex = 0;
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          let parametersQuery = new ParametersQuery();
          parametersQuery.page = this.paginator.pageIndex;
          parametersQuery.per_page = this.paginator.pageSize;
          parametersQuery.order_by = `${this.sort.direction}:${this.sort.active}:1`;
          return this.productsService!.getProductsWithFilter(parametersQuery, this.productSearch);
        }),
        map((data) => {
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.result.totalRows;
          return data.result.content;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return of([]);
        })
      )
      .subscribe((data) => (this.data = data));
  }

  openModalSelectProduct(productResponse: ProductResponse) {
    this.loanProductModalComponent.productRequest = productResponse;
    this.loanProductModalComponent.modalTitle = 'Detalles del Producto';
    this.loanProductModalComponent.openModal();
  }

  onSelectProduct(productResponse: ProductResponse) {
    // Validate execute event with the document Selected.
    this.loanProductModalComponent.closeModal();
    this.onSelectProductToLoan.emit(productResponse);
  }

}
