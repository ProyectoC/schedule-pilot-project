export const environment = {
  production: false,
  apis: {
    "schedule-api": {
      // "end.point": "http://localhost:8080/api/v1"
      "end.point": "http://schedulepilot.tk:8080/api/v1"
    }
  },
  services: {
    'end.point': 'http://localhost:8080/api/v1',
    'current.url.app': 'http://localhost:4200/#',
    'content.type': 'application/json',
    // 'token.company': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOiJ3ZWJfY2xpZW50IiwiaG9zdCI6IndlYl9ob3N0IiwiY3JlYXRlZF9hdCI6MTU4Mzk4MDcxOSwiaWRfdHlwZSI6InVzZXJfaG9zdF9jdXN0b21lciIsImV4cCI6MTYxNTUxNjcxOX0.2YasL9jwclDu-EAq-3aL8Te6GuDyDTvJwdvcIy5ok6k'
    'token.company': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOiJ3ZWJfY2xpZW50IiwiaG9zdCI6IndlYl9ob3N0IiwiY3JlYXRlZF9hdCI6MTU4NzQ5MjQ3OCwiaWRfdHlwZSI6InVzZXJfaG9zdF9jdXN0b21lciIsImV4cCI6MTYxOTAyODQ3OH0.smK9RAtTGOebkdxOHpCyHfCDmxvrncel8tGd34HuyiE'
  },
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
    dashboard: {
      'chart.solicitudes.servicio': 'Solicitudes por servicio',
      'chart.calificaciones': 'Calificaciones',
      'random.letters': '0123456789ABCDEF',
      'message.no.dashboards': 'Aun no tienes calificaciones!',
      'url.image.no.dashboards': '../../assets/imgs/no-dashboards.png',
    },
  },
  common: {
    select: {
      'default.option': '-- Selecciona una opción'
    },
    buttons: {
      'button.loading': 'Cargando...',
      'button.login': 'Iniciar Sesión',
      'button.create': 'Crear',
      'button.create.count': 'Registrarse',
      'button.search': 'Consultar',
      'button.update': 'Actualizar',
      'button.delete': 'Eliminar',
      'button.ok': 'Aceptar',
      'button.cancel': 'Cancelar',
      'button.close': 'Cerrar',
      'button.view': 'Ver Detalles',
      'button.register': 'Registrar',
      'button.response': 'Responder',
      'button.send': 'Enviar'
    },
    messages: {
      'message.title.default': 'AcquaBoard',
      'message.body.default': 'AcquaBoard, el sitio ideal para gestionar las actividades de tu equipo de trabajo.',
      correct: {
        'title.common': 'Correcto',
        'message.common': 'Acción completada correctamente!'
      },
      error: {
        'title.common': 'Error',
        'message.common': 'AcquaBoard pide disculpas, en estos momentos su solicitud no podra ser atendida.'
      },
      information: {
        'title.common': 'Información',
        'message.common': 'Mensaje de información'
      },
      warning: {
        'title.common': 'Advertencia',
        'message.common': 'Advertencia.'
      }
    }
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
