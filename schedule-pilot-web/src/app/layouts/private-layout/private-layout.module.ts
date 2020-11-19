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

// ITEMS
import { ItemsContainerComponent } from '../../pages/items/items-container/items-container.component';
import { ItemFormComponent } from '../../pages/items/item-form/item-form.component';
import { ItemDetailFormComponent } from '../../pages/items/item-detail-form/item-detail-form.component';
import { ItemModalComponent } from '../../pages/items/item-modal/item-modal.component';

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
  ],
  declarations: [HomeComponent, ProductsContainerComponent, ProductModalComponent, CreateProductFormComponent, ProductRolFormComponent,
    ItemsContainerComponent, ItemFormComponent, ItemDetailFormComponent, ItemModalComponent],
})
export class PrivateLayoutModule {}
