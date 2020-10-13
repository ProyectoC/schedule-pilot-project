import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoutingConstants } from '@constants/routing-constants';
import { AuthenticationService } from '@services/authentication/authentication.service';

declare var $: any;

@Component({
  selector: 'app-sidebar-menu',
  templateUrl: './sidebar-menu.component.html',
  styleUrls: ['./sidebar-menu.component.scss'],
})
export class SidebarMenuComponent implements OnInit {

  sidebar: boolean;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    $(document).ready(function () {
      $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
      });
    });
  }

  sidebarCollapse() {
    this.sidebar = !this.sidebar;
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate([RoutingConstants.URL_AUTHENTICATION]).then(() => {});
  }
}
