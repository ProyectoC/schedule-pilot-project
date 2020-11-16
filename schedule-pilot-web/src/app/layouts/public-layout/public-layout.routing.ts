import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationComponent } from '../../pages/authentication/authentication.component';
import { RegisterComponent } from '../../pages/register/register.component';
import { RoutingConstants } from '@constants/routing-constants';
import { ForgotPasswordComponent } from 'app/pages/forgot-password/forgot-password.component';

const routers: Routes = [
  {
    path: '',
    redirectTo: RoutingConstants.ROUTING_URL_AUTHENTICATION,
    pathMatch: 'full',
  },
  {
    path: RoutingConstants.ROUTING_URL_AUTHENTICATION,
    component: AuthenticationComponent,
    data: { title: 'Auth | SchedulePilot' },
  },
  {
    path: RoutingConstants.ROUTING_URL_REGISTER,
    component: RegisterComponent,
    data: { title: 'Register | SchedulePilot' },
  },
  {
    path: RoutingConstants.ROUTING_URL_FORGOT_PASSWORD,
    component: ForgotPasswordComponent,
    data: { title: 'Forgot Password | SchedulePilot' },
  },
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routers)],
})
export class PublicLayoutRouting {}
