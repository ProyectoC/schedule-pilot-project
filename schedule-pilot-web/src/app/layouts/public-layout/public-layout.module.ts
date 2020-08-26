import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { PublicLayoutRouting } from './public-layout.routing';

import { AuthenticationComponent } from '../../pages/authentication/authentication.component';
import { RegisterComponent } from '../../pages/register/register.component';

// import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    // NgbModule
    FormsModule,
    PublicLayoutRouting,
  ],
  declarations: [AuthenticationComponent, RegisterComponent],
})
export class PublicLayoutModule {}
