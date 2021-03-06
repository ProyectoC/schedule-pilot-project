export class EndPointsHttpConstants {
  /**
   * Components
   */
  static readonly COMPONENT_SECURITY = '/public/security';
  static readonly COMPONENT_CORE = '/public/core';
  static readonly COMPONENT_WS = '/public/ws';

  /**
   * Administration - Public
   */
  static readonly SERVICE_AUTHENTICATION = EndPointsHttpConstants.COMPONENT_SECURITY + '/users/auth';
  static readonly SERVICE_REGISTER = EndPointsHttpConstants.COMPONENT_SECURITY + '/users/create';
  static readonly SERVICE_FORGOT_PASSWORD = EndPointsHttpConstants.COMPONENT_SECURITY + '/users/forgot-password';
  static readonly SERVICE_CHANGE_PASSWORD = EndPointsHttpConstants.COMPONENT_SECURITY + '/users/change-password';
  static readonly SERVICE_ROL = EndPointsHttpConstants.COMPONENT_SECURITY + '/roles';
  static readonly SERVICE_COLLEGE_CAREER = EndPointsHttpConstants.COMPONENT_SECURITY + '/college-careers';

  /**
   * Administration - Private
   */

  // Products
  static readonly SERVICE_GET_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/products';
  static readonly SERVICE_GET_PRODUCT_STATUS = EndPointsHttpConstants.COMPONENT_WS + '/products/status';
  static readonly SERVICE_CREATE_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/products/create';
  static readonly SERVICE_UPDATE_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/products/update';
  static readonly SERVICE_DELETE_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/products/delete';

  // Items
  static readonly SERVICE_GET_ITEMS = EndPointsHttpConstants.COMPONENT_WS + '/items';
  static readonly SERVICE_GET_ITEM_STATUS = EndPointsHttpConstants.COMPONENT_WS + '/items/status';
  static readonly SERVICE_CREATE_ITEMS = EndPointsHttpConstants.COMPONENT_WS + '/items/create';
  static readonly SERVICE_UPDATE_ITEMS = EndPointsHttpConstants.COMPONENT_WS + '/items/update';


  // Countries
  static readonly SERVICE_GET_COUNTRIES = EndPointsHttpConstants.COMPONENT_WS + '/countries';

  // DashBoards
  static readonly SERVICE_DASHBOARD_GET_STATUS_OPERATIONS = EndPointsHttpConstants.COMPONENT_WS + '/dashboard/status-operations';
  static readonly SERVICE_DASHBOARD_GET_STATUS_LOAN_MADE = EndPointsHttpConstants.COMPONENT_WS + '/dashboard/status-loan-made';
  static readonly SERVICE_DASHBOARD_GET_STATUS_RETURN_MADE = EndPointsHttpConstants.COMPONENT_WS + '/dashboard/status-return-made';
  static readonly SERVICE_DASHBOARD_GET_PRINCIPAL = EndPointsHttpConstants.COMPONENT_WS + '/dashboard/principal';

  static readonly SERVICE_DASHBOARD_GET_LOAN_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/dashboard/loan-products';
  static readonly SERVICE_DASHBOARD_GET_REQUEST_VS_LOANS = EndPointsHttpConstants.COMPONENT_WS + '/dashboard/request-vs-loans';
  static readonly SERVICE_DASHBOARD_GET_PENALTY_SUMMARY = EndPointsHttpConstants.COMPONENT_WS + '/dashboard/penalty-summary';

  // Reports
  static readonly SERVICE_REPORTS_GET_LOAN_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/report/loan-products';
  static readonly SERVICE_REPORTS_GET_PENALTY_SUMMARY = EndPointsHttpConstants.COMPONENT_WS + '/report/penalty-summary';


  // Loan
  static readonly SERVICE_CREATE_REQUEST_CHECK_IN = EndPointsHttpConstants.COMPONENT_WS + '/loan/create/request-check-in';
  static readonly SERVICE_GET_REQUEST_CHECK_IN = EndPointsHttpConstants.COMPONENT_WS + '/loan/users';
  static readonly SERVICE_REQUEST_CHECK_IN_STATUS = EndPointsHttpConstants.COMPONENT_WS + '/status/request/check-in';

  static readonly SERVICE_TICKET_CHECK_STATUS = EndPointsHttpConstants.COMPONENT_WS + '/status/ticket';
  static readonly SERVICE_GET_TICKET_CHECK_IN = EndPointsHttpConstants.COMPONENT_WS + '/loan/users';

  static readonly SERVICE_CREATE_TICKET_CHECK_OUT = EndPointsHttpConstants.COMPONENT_WS + '/loan/create/request-check-out';
  static readonly SERVICE_GET_TICKET_CHECK_OUT = EndPointsHttpConstants.COMPONENT_WS + '/loan/users';

  static readonly SERVICE_CREATE_REQUEST_CHECK_LOG = EndPointsHttpConstants.COMPONENT_WS + '/loan/create/request-check-log';

  static readonly PUBLIC_SERVICES: string[] = [
    EndPointsHttpConstants.SERVICE_AUTHENTICATION,
    EndPointsHttpConstants.SERVICE_REGISTER,
    EndPointsHttpConstants.SERVICE_FORGOT_PASSWORD,
    EndPointsHttpConstants.SERVICE_ROL,
    EndPointsHttpConstants.SERVICE_COLLEGE_CAREER,
    EndPointsHttpConstants.SERVICE_GET_COUNTRIES 
  ];
}
