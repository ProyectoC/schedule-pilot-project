import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HeaderMenuModule } from './shared/header-menu/header-menu.module';
import { FooterModule } from './shared/footer/footer.module';

import { PublicLayoutComponent } from './layouts/public-layout/public-layout.component';
import { PrivateLayoutComponent } from './layouts/private-layout/private-layout.component';

//Services
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';

// Translate
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import {
  HttpClient,
  HttpClientModule,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

// Guards
import { AuthGuard } from './guards/auth.guard';
import { NoAuthGuard } from './guards/no-auth.guard';

// Global Error Handler
import { GlobalErrorHandler } from '@services/global-error/global-error.handler.service';

// HTTP Interceptor
import { AuthInterceptor } from '@services/http-interceptor/auth-interceptor.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [AppComponent, PublicLayoutComponent, PrivateLayoutComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (http: HttpClient) => {
          return new TranslateHttpLoader(http);
        },
        deps: [HttpClient],
      },
    }),
    AppRoutingModule,
    HeaderMenuModule,
    FooterModule,
    NgbModule,
  ],
  providers: [
    { provide: ErrorHandler, useClass: GlobalErrorHandler },
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    AuthGuard,
    NoAuthGuard, ScrollTopService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
