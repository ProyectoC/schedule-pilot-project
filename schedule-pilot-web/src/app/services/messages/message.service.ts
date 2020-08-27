import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { environment } from '@env/environment';

@Injectable({
  providedIn: 'root'
})
export class MessagesService {

  constructor() {
  }

  generateSuccessMessage(title: string, text: string) {
    Swal.fire({
      title: title != null ? title.toString() : environment.common.messages.correct["title.common"],
      text: text != null ? text.toString() : environment.common.messages.correct["message.common"],
      icon: 'success'
    });
  }

  generateWarningMessage(title: string, text: string) {
    Swal.fire({
      title: title != null ? title.toString() : environment.common.messages.warning["title.common"],
      text: text != null ? text.toString() : environment.common.messages.warning["message.common"],
      icon: 'warning'
    });
  }

  generateErrorMessage(title: string, text: string) {
    Swal.fire({
      icon: 'error',
      title: title != null ? title.toString() : environment.common.messages.error["title.common"],
      text: text != null ? text.toString() : environment.common.messages.error["message.common"],
      footer: '<a href="https://www.instagram.com/?hl=es-la" target="_blank">¿Necesitas ayuda?</a>'
    });
  }
}
