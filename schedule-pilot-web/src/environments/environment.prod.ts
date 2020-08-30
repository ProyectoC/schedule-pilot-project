export const environment = {
  production: true,
  apis: {
    "schedule-api": {
      "end.point": "http://192.168.120.45:8080/api/v1"
    }
  },
  components: {
    login: {
      'title': 'Login | AcquaBoard',
      'label.another.accounts': 'Inicia sesión con alguna de las siguientes cuentas',
      'label.facebook': 'Facebook',
      'label.twitter': 'Twitter',
      'label.google': 'Google',
      'description': 'Por favor ingresa tus datos para entrar',
      'label.userOrEmail': 'Nombre Usuario o Correo Electronico',
      'label.help.userOrEmail': 'Ingresa tu usuario o correo electronico para iniciar sesión',
      'label.userOrEmail.invalid': 'Ingresa un usuario o email valido.',
      'label.password': 'Contraseña',
      'label.password.invalid': 'Ingresa una contraseña valida.',
      'label.remember.password': 'Recordar contraseña',
      'label.forgot.password': '¿Olvidaste tu nombre de usuario o contraseña?',
      'label.register': '¿No tienes cuenta? Regístrate',
      'login.message.incorrect': 'Usuario y/o contraseña no validos.'
    },
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
  services: {
    'end.point': 'http://localhost:8082/api/v1',
    'current.url.app': 'http://localhost:4200/#',
    'content.type': 'application/json',
    // 'token.company': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOiJ3ZWJfY2xpZW50IiwiaG9zdCI6IndlYl9ob3N0IiwiY3JlYXRlZF9hdCI6MTU4Mzk4MDcxOSwiaWRfdHlwZSI6InVzZXJfaG9zdF9jdXN0b21lciIsImV4cCI6MTYxNTUxNjcxOX0.2YasL9jwclDu-EAq-3aL8Te6GuDyDTvJwdvcIy5ok6k'
    'token.company': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOiJ3ZWJfY2xpZW50IiwiaG9zdCI6IndlYl9ob3N0IiwiY3JlYXRlZF9hdCI6MTU4NzQ5MjQ3OCwiaWRfdHlwZSI6InVzZXJfaG9zdF9jdXN0b21lciIsImV4cCI6MTYxOTAyODQ3OH0.smK9RAtTGOebkdxOHpCyHfCDmxvrncel8tGd34HuyiE'
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
