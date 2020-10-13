import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HeaderMenuComponent } from './header-menu.component';
import { SidebarMenuComponent } from '../sidebar-menu/sidebar-menu.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  imports: [
    RouterModule,
    CommonModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
      isolate: false,
    }),
  ],
  declarations: [HeaderMenuComponent, SidebarMenuComponent],
  exports: [HeaderMenuComponent, SidebarMenuComponent],
})
export class HeaderMenuModule {
  static forRoot(): any {
    return {
      ngModule: HeaderMenuModule,
      providers: [],
    };
  }
}
