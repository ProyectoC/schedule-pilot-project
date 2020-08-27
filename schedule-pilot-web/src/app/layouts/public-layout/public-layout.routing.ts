import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationComponent } from '../../pages/authentication/authentication.component';
import { RegisterComponent } from '../../pages/register/register.component';

const routers: Routes = [
  {
    path: '',
    redirectTo: 'auth',
    pathMatch: 'full',
  },
  { path: 'auth', component: AuthenticationComponent, data: { title: 'Auth | SchedulePilot' } },
  { path: 'register', component: RegisterComponent, data: { title: 'Register | SchedulePilot' } },
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routers)],
})
export class PublicLayoutRouting {}
