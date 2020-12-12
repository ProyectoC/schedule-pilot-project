import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class CountryMessageService {

    constructor(
        private messageService: MessagesService,
        private translate: TranslateService
    ) { }

    generateCountryDefaultError(message: string) {
        let titleMessage = this.translate.instant(
            'messages.services.country.error.title'
        );
        this.messageService.generateErrorMessage(titleMessage, message);
    }
}