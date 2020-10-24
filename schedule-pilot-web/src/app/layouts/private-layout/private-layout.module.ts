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
import { UpdateProductFormComponent } from '../../pages/products/update-product-form/update-product-form.component';

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
  declarations: [HomeComponent, ProductsContainerComponent, ProductModalComponent, CreateProductFormComponent, UpdateProductFormComponent],
})
export class PrivateLayoutModule {}
