import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class DashboardMessageService {

    constructor(private messageService: MessagesService, private translate: TranslateService) { }

    public generateDefaultError(message: string): void {
        let titleMessage = this.translate.instant('messages.services.dashboard.error.title');
        this.messageService.generateErrorMessage(titleMessage, message);
    }
}