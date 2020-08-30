export class EndPointsHttpConstants {
  /**
   * Components
   */
  static readonly COMPONENT_SECURITY = '/public/security';
  static readonly COMPONENT_CORE = '/public/core';

  /**
   * Administration
   */
  static readonly SERVICE_AUTHENTICATION = EndPointsHttpConstants.COMPONENT_SECURITY + '/users/auth';
}
