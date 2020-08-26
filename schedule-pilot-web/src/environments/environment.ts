// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  components: {
    header: {
      'logo': '../../assets/images/common/kentaurus-logo.png',
      'logo.text': 'Kentaurus Logo',
      'change.language.spanish': ' Español',
      'change.language.english': ' English',

      'label.menu.home': 'Home',
      'label.menu.register': 'Registrarse',
      'label.menu.price': 'Precios',
      'label.menu.clients': 'Nuestros Clientes',
      'label.menu.about': 'Acerca de',

      'label.menu.establishment': 'Mi Establecimiento',
      'label.menu.solicitudes.propietario': 'Solicitudes',
      'label.menu.suministros.propietario': 'Suministros',
      'label.menu.estadisticas': 'Estadisticas',

      'label.menu.mascotas.usuario': 'Mis Mascotas',
      'label.menu.agenda.usuario': 'Mi Agenda',
      'label.menu.suministros.usuario': 'Suministros',
      'label.menu.exit': 'Salir'
    },
  },
  services: {
    'end.point': 'http://localhost:8080/api/v1',
    'current.url.app': 'http://localhost:4200/#',
    'content.type': 'application/json',
    // 'token.company': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOiJ3ZWJfY2xpZW50IiwiaG9zdCI6IndlYl9ob3N0IiwiY3JlYXRlZF9hdCI6MTU4Mzk4MDcxOSwiaWRfdHlwZSI6InVzZXJfaG9zdF9jdXN0b21lciIsImV4cCI6MTYxNTUxNjcxOX0.2YasL9jwclDu-EAq-3aL8Te6GuDyDTvJwdvcIy5ok6k'
    'token.company': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOiJ3ZWJfY2xpZW50IiwiaG9zdCI6IndlYl9ob3N0IiwiY3JlYXRlZF9hdCI6MTU4NzQ5MjQ3OCwiaWRfdHlwZSI6InVzZXJfaG9zdF9jdXN0b21lciIsImV4cCI6MTYxOTAyODQ3OH0.smK9RAtTGOebkdxOHpCyHfCDmxvrncel8tGd34HuyiE'
  },
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
