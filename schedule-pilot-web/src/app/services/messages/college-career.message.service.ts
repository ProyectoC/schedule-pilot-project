import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class CollegeCareerMessageService {

    constructor(
        private messageService: MessagesService,
        private translate: TranslateService
    ) { }

    generateCollegeCareerDefaultError(message: string) {
        let titleMessage = this.translate.instant(
            'messages.services.college-career.error.title'
        );
        this.messageService.generateErrorMessage(titleMessage, message);
    }
}