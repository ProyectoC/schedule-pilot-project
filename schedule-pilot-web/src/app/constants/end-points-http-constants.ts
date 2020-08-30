export class EndPointsHttpConstants {
  /**
   * Components
   */
  static readonly COMPONENT_SECURITY = '/public/security';
  static readonly COMPONENT_CORE = '/public/core';

  /**
   * Administration - Public
   */
  static readonly SERVICE_AUTHENTICATION = EndPointsHttpConstants.COMPONENT_SECURITY + '/users/auth';
  static readonly SERVICE_REGISTER = EndPointsHttpConstants.COMPONENT_SECURITY + '/users/create';
  static readonly SERVICE_FORGOT_PASSWORD = EndPointsHttpConstants.COMPONENT_SECURITY + '/users/forgot-password';
  static readonly PUBLIC_SERVICES: string[] = [
    EndPointsHttpConstants.SERVICE_AUTHENTICATION,
    EndPointsHttpConstants.SERVICE_REGISTER,
    EndPointsHttpConstants.SERVICE_FORGOT_PASSWORD
  ];
}
