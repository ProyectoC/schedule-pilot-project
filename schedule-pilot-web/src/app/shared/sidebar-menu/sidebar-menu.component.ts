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
  showSidebar: boolean;
  public actualYear: string;

  constructor(
    public authenticationService: AuthenticationService,
    private router: Router
  ) {
    this.actualYear = new Date().getFullYear().toString();
  }

  ngOnInit(): void {
    $(document).ready(function () {
      $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
      });
    });
  }

  changeSidebar() {
    this.showSidebar = !this.showSidebar;
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate([RoutingConstants.URL_AUTHENTICATION]).then(() => {});
  }
}
