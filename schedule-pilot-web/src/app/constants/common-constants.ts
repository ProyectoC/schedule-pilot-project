export class CommonConstants {
  static readonly IMAGE_JPEG_TYPE: string = 'image/jpeg';
  static readonly IMAGE_PNG_TYPE: string = 'image/png';
  static readonly IMAGE_CONVERT_BASE_64: string = 'data:image/jpeg;base64,';
  static readonly IMAGE_MAX_SIZE: number = 10000;
  static readonly SUCCESS_CODE: number = 0;
  static readonly NO_SESSION_ERROR_CODE: number = -1;
  static readonly ERROR_CODE: number = -2;
  static readonly CREATE_FORM: string = 'create';
  static readonly EDIT_FORM: string = 'edit';
  static readonly CLIENTS_SESSION: string = 'CLIENTS_SESSION';
  static readonly USERS_SESSION: string = 'USERS_SESSION';
  static readonly STATUS_SESSION: string = 'STATUS_SESSION';
  static readonly SKILLS_SESSION: string = 'SKILLS_SESSION';
  static readonly NOTIFICATIONS_SESSION: string = 'NOTIFICATIONS_SESSION';
  static readonly SPRINTS_SESSION: string = 'SPRINTS_SESSION';
  static readonly USER_TYPE_ADMIN: number = 1;
  static readonly USER_TYPE_LEADER: number = 2;
  static readonly USER_TYPE_PROGRAMMER: number = 3;
  static readonly USER_TYPE_PROJECT: number = 4;
  static readonly TOKEN_COMPANY: string =
    'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOiJ3ZWJfY2xpZW50IiwiaG9zdCI6IndlYl9ob3N0IiwiY3JlYXRlZF9hdCI6MTU4Mzk4MDcxOSwiaWRfdHlwZSI6InVzZXJfaG9zdF9jdXN0b21lciIsImV4cCI6MTYxNTUxNjcxOX0.2YasL9jwclDu-EAq-3aL8Te6GuDyDTvJwdvcIy5ok6k';
}
