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

  /**
   * Administration - Private
   */
  static readonly SERVICE_GET_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/products';
  static readonly SERVICE_CREATE_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/products/create';
  static readonly SERVICE_UPDATE_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/products/update';
  static readonly SERVICE_DELETE_PRODUCTS = EndPointsHttpConstants.COMPONENT_WS + '/products/delete';
  
  static readonly PUBLIC_SERVICES: string[] = [
    EndPointsHttpConstants.SERVICE_AUTHENTICATION,
    EndPointsHttpConstants.SERVICE_REGISTER,
    EndPointsHttpConstants.SERVICE_FORGOT_PASSWORD
  ];
}
