import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { RoutingConstants } from '@constants/routing-constants';
import { ParametersQuery } from '@models/parameters-query';
import { ProductRequest } from '@models/product/request/product-request';
import { ProductResponse } from '@models/product/response/product-response';
import { MessagesService } from '@services/messages/message.service';
import { ProductService } from '@services/products/product.service';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { ModalsService } from 'app/modals/services/modals.service';
import { merge, Observable, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { ProductPaginatorConfiguration } from '../../../config/pagination/product-paginator-configuration';
import { ProductModalComponent } from '../product-modal/product-modal.component';

@Component({
  selector: 'app-products-container',
  templateUrl: './products-container.component.html',
  styleUrls: ['./products-container.component.scss'],
  providers: [
    {
      provide: MatPaginatorIntl,
      useValue: new ProductPaginatorConfiguration().getPaginatorIntl(),
    },
  ],
})
export class ProductsContainerComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = [
    'number',
    'name',
    'description',
    'status',
    'observations',
    'update',
    'delete',
  ];
  data: ProductResponse[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  @ViewChild(MatSort) sort: MatSort;

  @ViewChild(ProductModalComponent, { static: true })
  productModalComponent: ProductModalComponent;

  constructor(
    public productsService: ProductService, public messageService: MessagesService,
    private scrollTop: ScrollTopService, public modalService: ModalsService
  ) { }

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    this.refreshProducts();
  }

  get routingConstants() {
    return RoutingConstants;
  }

  refreshProducts() {
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
          return this.productsService!.getProducts(parametersQuery);
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
          return observableOf([]);
        })
      )
      .subscribe((data) => (this.data = data));
  }

  openModalCreateProduct() {
    this.productModalComponent.initModalToCreate();
    this.productModalComponent.openModal();
  }

  openModalUpdateProduct(productResponse: ProductResponse) {
    this.productModalComponent.initModalToUpdate(productResponse);
    this.productModalComponent.openModal();
  }

  createProduct(productRequest: ProductRequest) {
    this.messageService.generateConfirmMessage(null, 'Se creará un nuevo producto.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.productModalComponent.isLoadingModal = true;
          this.productsService.createProduct(productRequest).subscribe((bodyResponse: any) => {
            this.messageService.generateSuccessMessage('Crear Producto', 'El producto se ha creado correctamente.');
            this.productModalComponent.closeModal();
            this.refreshProducts();
          });
        }
      });
  }

  updateProduct(productRequest: ProductRequest) {
    this.messageService.generateConfirmMessage(null, 'Se actualizará el producto.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.productModalComponent.isLoadingModal = true;
          this.productsService.updateProduct(productRequest).subscribe((bodyResponse: any) => {
            this.messageService.generateSuccessMessage('Actualizar Producto', 'El producto se ha actualizado correctamente.');
            this.productModalComponent.closeModal();
            this.refreshProducts();
          });
        }
      });
  }

  deleteProduct(productRequest: ProductRequest) {
    this.messageService.generateConfirmMessage(null, 'Se eliminará el producto.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.productsService.deleteProduct(productRequest).subscribe((bodyResponse: any) => {
            this.messageService.generateSuccessMessage('Eliminar Producto', 'El producto se ha eliminado correctamente.');
            this.refreshProducts();
          });
        }
      });
  }
}
