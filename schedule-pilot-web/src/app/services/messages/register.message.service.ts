import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class RegisterMessageService {
  constructor(
    private messageService: MessagesService,
    private translate: TranslateService
  ) {}

  generalErrorRegister(message: string) {
    let titleMessage = this.translate.instant(
      'messages.services.authentication.error.title'
    );
    this.messageService.generateErrorMessage(titleMessage, message);
  }
}