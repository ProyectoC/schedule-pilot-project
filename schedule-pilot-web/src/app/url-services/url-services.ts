'use strict';

/**
 * Utils
 */
export const GET_ALL = '/get/all';
export const OFFSET = 'offset=';
export const LIMIT = 'limit=';
export const PROJECT = 'project=';
export const CLIENT = 'client='
export const SPRINT = 'sprint=';
export const IS_COMPLETE = 'isComplete=';
export const DEAD_LINE = 'deadline=';
export const STATUS = "status=";
export const ID_USER = "idUser=";

/**
 * Components
 */
export const COMPONENT_SECURITY = '/public/core';
export const COMPONENT_CORE = '/public/core'

/**
 * Administration
 */
export const SERVICE_LOGIN = COMPONENT_SECURITY + '/users/auth';
export const SERVICE_REGISTER = COMPONENT_SECURITY + '/users/create';
export const SERVICE_FORGOT_PASSWORD = COMPONENT_SECURITY + '/users/forgot-password';
export const SERVICE_CHANGE_PASSWORD = COMPONENT_SECURITY + '/users/change-password';
export const SERVICE_UPDATE_USER = COMPONENT_CORE + '/users/skills/update';

/**
 * Projects
 */
export const SERVICE_CREATE_PROJECT = COMPONENT_CORE + "/projects/create";
export const SERVICE_UPDATE_PROJECT = COMPONENT_CORE + "/projects/update";
export const SERVICE_GET_PROJECTS_BY_USER = COMPONENT_CORE + "/projects/users";
export const SERVICE_GET_PROJECT_BY_ID = COMPONENT_CORE + "/projects/get/id";

/**
 * Sprints
 */
export const SERVICE_CREATE_SPRINT = COMPONENT_CORE + '/sprints/create'
export const SERVICE_UPDATE_SPRINT = COMPONENT_CORE + "/sprints/update";
export const SERVICE_GET_SPRINTS_BY_PROJECT = COMPONENT_CORE + "/sprints/projects";
export const SERVICE_GET_SPRINT_BY_ID = COMPONENT_CORE + "/sprints/get/id";
export const SERVICE_GET_SPRINT_USER_STATUS = "/users/status";
export const SERVICE_GET_SPRINT_ACTIVITIES_STATUS = '/activities/status';
export const SERVICE_GET_SPRINT_ESTIMATE_ASSIGN_ACTIVITIES = "/estimate/assign/activities";
export const SERVICE_GET_SPRINT_ASSIGN_ACTIVITIES = "/assign/activities";
export const SERVICE_SPRINTS = COMPONENT_CORE + '/sprints';

/**
 * Requests
 */
export const SERVICE_CREATE_REQUEST = COMPONENT_CORE + '/requests/create';
export const SERVICE_UPDATE_REQUEST = COMPONENT_CORE + '/requests/update';
export const SERVICE_GET_REQUESTS_BY_SPRINT = COMPONENT_CORE + '/requests/sprints';
export const SERVICE_GET_REQUEST_BY_ID = COMPONENT_CORE + "/requests/get/id";

/**
 * Activities
 */
export const SERVICE_CREATE_ACTIVITY = COMPONENT_CORE + '/activities/create';
export const SERVICE_UPDATE_ACTIVITY = COMPONENT_CORE + '/activities/update';
export const SERVICE_UPDATE_STATUS_ACTIVITY = COMPONENT_CORE + '/activities/update/status';
export const SERVICE_SEND_ERROR_ACTIVITY = COMPONENT_CORE + '/activities/error/create';
export const SERVICE_GET_ACTIVITIES_BY_SPRINT = COMPONENT_CORE + '/activities/sprints';
export const SERVICE_GET_ACTIVITY_BY_ID = COMPONENT_CORE + '/activities/get/id';

/**
 * Board
 */
export const SERVICE_BOARD_INFORMATION = COMPONENT_CORE + '/board/sprints';

/**
 * DashBoards
 */
export const CHART_BURN_DOWN = '/burn-down';
export const CHART_GENERAL_PROJECTS = '/projects/general';
export const CHART_STATUS_ACTIVITIES = '/activities/status';
export const CHART_CLIENTS_ACTIVITIES = '/activities/clients';
export const SERVICE_GET_USER_CHART = COMPONENT_CORE + '/dashboards/user';
export const SERVICE_GET_SPRINT_CHART = COMPONENT_CORE + '/dashboards/sprint';

/**
 * Reports
 */
export const SERVICE_GET_REPORT_DASHBOARD = COMPONENT_CORE + '/reports/dashboard';
export const SERVICE_GET_REPORT_SPRINT = '/sprint';
export const SERVICE_GET_REPORT_USER = COMPONENT_CORE + '/reports/user';
export const SERVICE_GET_REPORT_HOURS_USER = COMPONENT_CORE + '/reports/hours/user';

/**
 * Clients
 */
export const SERVICE_GET_CLIENTS = COMPONENT_CORE + '/clients' + GET_ALL;
export const SERVICE_CREATE_CLIENTS = COMPONENT_CORE + '/clients/create';

/**
 * Users
 */
export const SERVICE_GET_USERS = COMPONENT_CORE + '/users' + GET_ALL;
export const SERVICE_GET_USERS_BY_SPRINT = COMPONENT_CORE + '/users/sprint';

/**
 * Status
 */
export const SERVICE_GET_STATUS = COMPONENT_CORE + '/status' + GET_ALL;

/**
 * Skills
 */
export const SERVICE_GET_SKILLS = COMPONENT_CORE + '/skills' + GET_ALL;

/**
 * Notifications
 */
export const SERVICE_GET_NOTIFICATIONS_NOT_RECEIVED_BY_USER = COMPONENT_CORE + '/notifications/not-received/users';
export const SERVICE_GET_NOTIFICATIONS_NOT_NOTIFIED_BY_USER = COMPONENT_CORE + '/notifications/not-notified/users';
export const SERVICE_UPDATE_STATUS_NOTIFICATIONS = COMPONENT_CORE + '/notifications/update';