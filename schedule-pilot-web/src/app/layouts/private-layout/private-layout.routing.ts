import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../../pages/home/home.component';
import { RoutingConstants } from '@constants/routing-constants';
import { ProductsContainerComponent } from '../../pages/products/products-container/products-container.component';

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
  {
    path: RoutingConstants.ROUTING_URL_PRODUCTS,
    component: ProductsContainerComponent,
    data: { title: 'Products | SchedulePilot' },
  },
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routers)],
})
export class PrivateLayoutRouting {}
