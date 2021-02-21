import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class ReportMessageService {

    constructor(private messageService: MessagesService, private translate: TranslateService) { }

    public generateDefaultError(message: string): void {
        let titleMessage = "Reporte"
        this.messageService.generateErrorMessage(titleMessage, message);
    }
}