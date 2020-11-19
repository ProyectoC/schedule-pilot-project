import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute } from '@angular/router';
import { ItemRequest } from '@models/item/request/item-request';
import { ItemResponse } from '@models/item/response/item-response';
import { ParametersQuery } from '@models/parameters-query';
import { ItemsService } from '@services/items/items.service';
import { MessagesService } from '@services/messages/message.service';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { merge, of } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { ItemPaginatorConfiguration } from '../../../config/pagination/item-paginator-configuration';
import { ItemFormComponent } from '../item-form/item-form.component';
import { ItemModalComponent } from '../item-modal/item-modal.component';

@Component({
  selector: 'app-items-container',
  templateUrl: './items-container.component.html',
  styleUrls: ['./items-container.component.scss'],
  providers: [
    {
      provide: MatPaginatorIntl,
      useValue: new ItemPaginatorConfiguration().getPaginatorIntl(),
    },
  ],
})
export class ItemsContainerComponent implements OnInit, AfterViewInit {

  public displayedColumns: string[] = [
    'number',
    'name',
    'serial',
    'status',
    'product',
    'update'
  ];

  public data: ItemResponse[] = [];
  public resultsLength = 0;
  public isLoadingResults = true;
  public isRateLimitReached = false;
  public productId: number;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(ItemModalComponent, { static: true })
  itemModalComponent: ItemModalComponent;

  constructor(private scrollTop: ScrollTopService, public itemService: ItemsService,
    private route: ActivatedRoute, public messageService: MessagesService) {
    this.route.params.subscribe(params => {
      this.productId = Number(params['id-product']);
    });
  }

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    this.refreshItems();
  }

  refreshItems() {
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
          return this.itemService!.getItems(parametersQuery, this.productId);
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

  public openModalCreateItem() {
    this.itemModalComponent.productId = this.productId;
    this.itemModalComponent.initModalToCreate();
    this.itemModalComponent.openModal();
  }

  public openModalUpdateItem(itemResponse: ItemResponse) {
    this.itemModalComponent.initModalToUpdate(itemResponse);
    this.itemModalComponent.openModal();
  }

  public createItem(itemRequest: ItemRequest) {
    this.messageService.generateConfirmMessage(null, 'Se creará un nuevo articulo.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.itemModalComponent.isLoadingModal = true;
          this.itemService.createItem(itemRequest).subscribe((bodyResponse: any) => {
            this.messageService.generateSuccessMessage('Crear Articulo', 'El articulo se ha creado correctamente.');
            this.itemModalComponent.closeModal();
            this.refreshItems();
          });
        }
      });
  }

  public updateItem(itemRequest: ItemRequest) {
    this.messageService.generateConfirmMessage(null, 'Se actualizará el articulo.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.itemModalComponent.isLoadingModal = true;
          this.itemService.updateItem(itemRequest).subscribe((bodyResponse: any) => {
            this.messageService.generateSuccessMessage('Actualizar Articulo', 'El articulo se ha actualizado correctamente.');
            this.itemModalComponent.closeModal();
            this.refreshItems();
          });
        }
      });
  }

}
