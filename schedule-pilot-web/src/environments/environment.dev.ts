import { environment as defaultEnvironment } from './environment.defaults';
export const environment = {
  ...defaultEnvironment,
  apis: {
    "schedule-api": {
      "end.point": "http://localhost:8080/api/v1"
    }
  },
};