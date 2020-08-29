import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { PrivateLayoutRouting } from './private-layout.routing';

import { HomeComponent } from '../../pages/home/home.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    NgbModule,
    FormsModule,
    PrivateLayoutRouting,
  ],
  declarations: [HomeComponent],
})
export class PrivateLayoutModule {}