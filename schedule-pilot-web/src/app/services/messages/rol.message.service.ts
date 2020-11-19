import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class RolMessageService {

    constructor(
        private messageService: MessagesService,
        private translate: TranslateService
    ) { }

    generateDefaultErrorRol(message: string) {
        let titleMessage = this.translate.instant(
            'messages.services.rol.error.title'
        );
        this.messageService.generateErrorMessage(titleMessage, message);
    }
}