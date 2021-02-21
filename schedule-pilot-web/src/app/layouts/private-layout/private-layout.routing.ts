import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../../pages/home/home.component';
import { RoutingConstants } from '@constants/routing-constants';
import { ProductsContainerComponent } from '../../pages/products/products-container/products-container.component';
import { ItemsContainerComponent } from 'app/pages/items/items-container/items-container.component';
import { LoansContainerComponent } from 'app/pages/loans/loans-container/loans-container.component';
import { ReturnsContainerComponent } from 'app/pages/returns/returns-container/returns-container.component';
import { UserContainerComponent } from 'app/pages/users/user-container/user-container.component';
import { ReportContainerComponent } from 'app/pages/reports/report-container/report-container.component';

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
  {
    path: RoutingConstants.ROUTING_URL_ITEMS + '/:id-product',
    component: ItemsContainerComponent,
    data: { title: 'Items | SchedulePilot' },
  },
  {
    path: RoutingConstants.ROUTING_URL_LOANS,
    component: LoansContainerComponent,
    data: { title: 'Loans | SchedulePilot' },
  },
  {
    path: RoutingConstants.ROUTING_URL_RETURNS,
    component: ReturnsContainerComponent,
    data: { title: 'Returns | SchedulePilot' },
  },
  {
    path: RoutingConstants.ROUTING_URL_USERS,
    component: UserContainerComponent,
    data: { title: 'Users | SchedulePilot' },
  },
  {
    path: RoutingConstants.ROUTING_URL_REPORTS,
    component: ReportContainerComponent,
    data: { title: 'Reports | SchedulePilot' },
  },
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routers)],
})
export class PrivateLayoutRouting {}
