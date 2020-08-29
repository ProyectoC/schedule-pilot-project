import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationComponent } from '../../pages/authentication/authentication.component';
import { RegisterComponent } from '../../pages/register/register.component';
import { RoutingConstants } from '@constants/routing-constants';

const routers: Routes = [
  {
    path: '',
    redirectTo: RoutingConstants.ROUTING_URL_AUTHENTICATION,
    pathMatch: 'full',
  },
  { path: RoutingConstants.ROUTING_URL_AUTHENTICATION, component: AuthenticationComponent, data: { title: 'menu.name | translate' } },
  { path: RoutingConstants.ROUTING_URL_REGISTER, component: RegisterComponent, data: { title: 'Register | SchedulePilot' } },
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routers)],
})
export class PublicLayoutRouting {}