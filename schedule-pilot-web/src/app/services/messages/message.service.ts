import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { environment } from '@env/environment';

@Injectable({
  providedIn: 'root',
})
export class MessagesService {
  constructor() {}

  generateSuccessMessage(title: string, text: string) {
    Swal.fire({
      title:
        title != null
          ? title.toString()
          : environment.common.messages.correct['title.common'],
      text:
        text != null
          ? text.toString()
          : environment.common.messages.correct['message.common'],
      icon: 'success',
    });
  }

  generateWarningMessage(title: string, text: string) {
    Swal.fire({
      title:
        title != null
          ? title.toString()
          : environment.common.messages.warning['title.common'],
      text:
        text != null
          ? text.toString()
          : environment.common.messages.warning['message.common'],
      icon: 'warning',
    });
  }

  generateErrorMessage(title: string, text: string) {
    Swal.fire({
      icon: 'error',
      title:
        title != null
          ? title.toString()
          : environment.common.messages.error['title.common'],
      text:
        text != null
          ? text.toString()
          : environment.common.messages.error['message.common'],
      footer:
        '<a href="https://www.instagram.com/?hl=es-la" target="_blank">¿Necesitas ayuda?</a>',
    });
  }

  public generateConfirmMessage(title: string, text: string) {
    return Swal.fire({
      // title: title != null ? title.toString() : '¿Esta Seguro?',
      text: text != null ? text.toString() : 'No se podria revertir esta acción.',
      // type: 'warning',
      html: `<i class="	fa fa-warning" style="font-size:100px;color:#ffc107;"></i><br><br>
      <h3>${title != null ? title : '¿Esta Seguro?'}</h3><br>
      <p>${text}</p>`,
      showCancelButton: true,
      confirmButtonText: 'Aceptar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#28a745',
      cancelButtonColor: '#dc3545',
    }).then((result) => {
      if (result.value) {
        return true;
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        return false;
      }
    });
  }
}
