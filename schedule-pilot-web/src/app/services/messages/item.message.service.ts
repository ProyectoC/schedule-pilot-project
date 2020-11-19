import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class ItemMessageService {

    constructor(
        private messageService: MessagesService,
        private translate: TranslateService
    ) { }

    generateItemDefaultError(message: string) {
        let titleMessage = this.translate.instant(
            'messages.services.item.error.title'
        );
        this.messageService.generateErrorMessage(titleMessage, message);
    }

    generateMessageErrorCreateItem(message: string) {
        this.messageService.generateErrorMessage('Crear Articulo', message);
    }

    generateMessageErrorUpdateItem(message: string) {
        this.messageService.generateErrorMessage('Actualizar Articulo', message);
    }
}