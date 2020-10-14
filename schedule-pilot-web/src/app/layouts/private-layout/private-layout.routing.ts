import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../../pages/home/home.component';
import { RoutingConstants } from '@constants/routing-constants';

const routers: Routes = [
  {
    path: '',
    redirectTo: RoutingConstants.ROUTING_URL_HOME,
    pathMatch: 'full',
  },
  {
    path: RoutingConstants.ROUTING_URL_HOME,
    component: HomeComponent,
    data: { title: 'DashBoard | SchedulePilot' },
  },
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routers)],
})
export class PrivateLayoutRouting {}
