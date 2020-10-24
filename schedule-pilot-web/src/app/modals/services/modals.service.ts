import { Injectable } from '@angular/core';
import { BaseModalComponent } from '../components/base-modal.component';

@Injectable({
    providedIn: 'root',
})
export class ModalsService {

    private modals: BaseModalComponent[] = [];

    constructor() { }

    add(modal: BaseModalComponent) {
        this.modals.push(modal);
    }

    remove(modalId: string) {
        this.modals = this.modals.filter(x => x.modalId !== modalId);
    }

    open(modalId: string) {
        let modal: BaseModalComponent = this.modals.filter(x => x.modalId === modalId)[0];
        modal.openModal();
    }

    close(modalId: string) {
        let modal: BaseModalComponent = this.modals.filter(x => x.modalId === modalId)[0];
        modal.closeModal();
    }
}