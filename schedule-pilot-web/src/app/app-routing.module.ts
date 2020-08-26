import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PublicLayoutComponent } from '../app/layouts/public-layout/public-layout.component';
import { PrivateLayoutComponent } from './layouts/private-layout/private-layout.component';

import { AuthGuard } from './guards/auth.guard';
import { NoAuthGuard } from './guards/no-auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'public',
    pathMatch: 'full',
  },
  {
    path: 'public',
    component: PublicLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () => import('./layouts/public-layout/public-layout.module').then(m => m.PublicLayoutModule), canActivate: [NoAuthGuard]
      },
    ],
  },
  {
    path: 'private',
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
