import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class StatusMessageService {

    constructor(
        private messageService: MessagesService,
        private translate: TranslateService
    ) { }

    generateDefaultErrorStatus(message: string) {
        let titleMessage = this.translate.instant(
            'messages.services.status.error.title'
        );
        this.messageService.generateErrorMessage(titleMessage, message);
    }
}