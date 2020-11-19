import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class ProductStatusMessageService {

    constructor(
        private messageService: MessagesService,
        private translate: TranslateService
    ) { }

    generateProductStatusDefaultError(message: string) {
        let titleMessage = this.translate.instant(
            'messages.services.product-status.error.title'
        );
        this.messageService.generateErrorMessage(titleMessage, message);
    }
}