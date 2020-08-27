import { Component, OnInit } from '@angular/core';
import { environment } from '@env/environment';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import * as HeaderConst from '@constants/header-menu';

@Component({
  selector: 'app-header-menu',
  templateUrl: './header-menu.component.html',
  styleUrls: ['./header-menu.component.scss'],
})
export class HeaderMenuComponent implements OnInit {

  public properties: any;
  public isExpanded = false;
  public activeLang = 'es';
  public isLoggedIn$: Observable<boolean>;

  public publicMenu = [
    { id: 0, name: 'header.home', url: '/home-carousel' },
    { id: 1, name: 'header.register', url: '/public/register' },
    { id: 2, name: 'header.forgot-password', url: '/public/forgot-password' },
    { id: 3, name: 'header.login', url: '/public/auth' },
  ];
  
  constructor(
    private translate: TranslateService,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
    this.properties = environment.components.header;
  }

  ngOnInit(): void {
    this.isLoggedIn$ = this.authenticationService.isLoggedIn;
  }

  changeMenu(lang: string) {
    this.activeLang = lang;
    this.translate.use(lang);
  }

  collapse() {
    this.isExpanded = false;
  }

  toggle() {
    this.isExpanded = !this.isExpanded;
  }

  trackByFn(index, item) {
    return item.id;
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate([HeaderConst.URL_LOGIN]).then(() => {
    });
  }
}
