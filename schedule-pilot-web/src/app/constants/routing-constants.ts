export class RoutingConstants {
  // Layouts
  static readonly ROUTING_URL_PUBLIC_LAYOUT: string = 'public';
  static readonly URL_PUBLIC_LAYOUT: string = '/' + RoutingConstants.ROUTING_URL_PUBLIC_LAYOUT;

  static readonly ROUTING_URL_PRIVATE_LAYOUT: string = 'private';
  static readonly URL_PRIVATE_LAYOUT: string = '/' + RoutingConstants.ROUTING_URL_PRIVATE_LAYOUT;

  static readonly ROUTING_URL_AUTHENTICATION: string = 'auth';
  static readonly URL_AUTHENTICATION: string = RoutingConstants.URL_PUBLIC_LAYOUT + '/' + RoutingConstants.ROUTING_URL_AUTHENTICATION;
  
  static readonly ROUTING_URL_REGISTER: string = 'register';
  static readonly URL_REGISTER: string = '/' + RoutingConstants.ROUTING_URL_REGISTER;
  
  static readonly ROUTING_URL_HOME: string = 'home';
  static readonly URL_HOME: string = RoutingConstants.URL_PRIVATE_LAYOUT + '/' + RoutingConstants.ROUTING_URL_HOME;
  

  static URL_MENU_HOME = '/home-carousel';
  static ROUTING_URL_MENU_HOME: string = 'home-carousel';

  

  static URL_FORGOT_PASSWORD = '/forgot-password';
  static ROUTING_URL_FORGOT_PASSWORD: string = 'forgot-password';

  static URL_UPDATE_USER = '/update-user';
  static ROUTING_URL_UPDATE_USER: string = 'update-user';

  static URL_CHANGE_PASSWORD = '/change-password';
  static ROUTING_URL_CHANGE_PASSWORD: string = 'change-password';

  static URL_PROJECTS = '/projects';
  static ROUTING_URL_PROJECTS: string = 'projects';

  static URL_SPRINTS: string = '/sprints';
  static ROUTING_URL_SPRINTS: string = 'sprints';

  static URL_BOARD: string = '/board';
  static ROUTING_URL_BOARD: string = 'board';

  static URL_REPORT_SPRINTS: string = '/report-sprints';
  static ROUTING_URL_REPORT_SPRINTS: string = 'report-sprints';

  static URL_REPORT_USERS: string = '/report-users';
  static ROUTING_URL_REPORT_USERS: string = 'report-users';

  static URL_REPORT_NOTIFICATIONS: string = '/notifications';
  static ROUTING_URL_NOTIFICATIONS: string = 'notifications';

  static URL_ERROR: string = '/error';
  static ROUTING_URL_ERROR: string = 'error';

  static URL_PAGE_NOT_FOUND = '**';

  static URL_DEFAULT: string = '#';
  static ROUTING_URL_DEFAULT: string = '#';

  static URL_PRIVACY_TERMS_CONDITIONS = '/legal/terms-and-conditions';
  static URL_PRIVACY_POLITY_PRIVACY = '/legal/polity-privacy';
  static ROUTING_URL_PRIVACY = 'legal';
}
