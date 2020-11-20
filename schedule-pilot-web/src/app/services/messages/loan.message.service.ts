import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class LoanMessageService {

    constructor(
        private messageService: MessagesService,
        private translate: TranslateService
    ) { }

    generateLoanDefaultError(message: string) {
        let titleMessage = this.translate.instant(
            'messages.services.item.error.title'
        );
        this.messageService.generateErrorMessage(titleMessage, message);
    }

    generateCreateRequestCheckInError(message: string) {
        this.messageService.generateErrorMessage('Solicitud de prestamo', message);
    }

    generateCreateTicketCheckOutError(message: string) {
        this.messageService.generateErrorMessage('Validacion TicketCheckIn', message);
    }

    generateCreateTicketCheckLogError(message: string) {
        this.messageService.generateErrorMessage('Validacion TicketCheckOut', message);
    }
}