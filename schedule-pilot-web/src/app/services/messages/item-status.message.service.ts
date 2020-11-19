import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class ItemStatusMessageService {

    constructor(
        private messageService: MessagesService,
        private translate: TranslateService
    ) { }

    generateItemStatusDefaultError(message: string) {
        let titleMessage = this.translate.instant(
            'messages.services.item-status.error.title'
        );
        this.messageService.generateErrorMessage(titleMessage, message);
    }
}