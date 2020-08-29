import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PublicLayoutComponent } from '../app/layouts/public-layout/public-layout.component';
import { PrivateLayoutComponent } from './layouts/private-layout/private-layout.component';

import { AuthGuard } from './guards/auth.guard';
import { NoAuthGuard } from './guards/no-auth.guard';

import { RoutingConstants } from '@constants/routing-constants';

const routes: Routes = [
  {
    path: '',
    redirectTo: RoutingConstants.ROUTING_URL_PUBLIC_LAYOUT,
    pathMatch: 'full',
  },
  {
    path: RoutingConstants.ROUTING_URL_PUBLIC_LAYOUT,
    component: PublicLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () => import('./layouts/public-layout/public-layout.module').then(m => m.PublicLayoutModule), canActivate: [NoAuthGuard]
      },
    ],
  },
  {
    path: RoutingConstants.ROUTING_URL_PRIVATE_LAYOUT,
    component: PrivateLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () => import('./layouts/private-layout/private-layout.module').then(m => m.PrivateLayoutModule), canActivate: [AuthGuard]
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
