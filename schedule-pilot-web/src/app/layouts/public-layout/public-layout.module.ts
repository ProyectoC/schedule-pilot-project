import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// Forms
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// Routing
import { PublicLayoutRouting } from './public-layout.routing';
// Modules
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// Components
import { AuthenticationComponent } from '../../pages/authentication/authentication.component';
import { RegisterComponent } from '../../pages/register/register.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    PublicLayoutRouting,
  ],
  declarations: [AuthenticationComponent, RegisterComponent],
})
export class PublicLayoutModule {}
