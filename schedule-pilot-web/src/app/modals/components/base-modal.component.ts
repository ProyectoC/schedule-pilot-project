import { Component, Input, Output, ElementRef, OnInit, OnDestroy, EventEmitter } from "@angular/core";
import { ModalsService } from '../services/modals.service';
import { Modal } from '../models/modal';

declare var $: any;

@Component({
    selector: 'app-base-modal',
    template: ``
})
export class BaseModalComponent implements OnInit, OnDestroy, Modal {

    // Inputs

    @Input() modalId: string;

    // Outputs

    @Output() onCloseModal = new EventEmitter();

    public element: any;
    public isLoadingModal: boolean;

    constructor(public modalService: ModalsService,
        public elementRef: ElementRef) {
        this.element = elementRef.nativeElement;
    }

    ngOnInit(): void {
        if (!this.modalId) {
            return;
        }
        this.modalService.add(this);
    }

    ngOnDestroy(): void {
        this.modalService.remove(this.modalId);
        this.element.remove();
    }

    openModal(): void {
        this.isLoadingModal = false;
        $('#' + this.modalId).modal('show');
    }

    closeModal(): void {
        this.isLoadingModal = false;
        $('#' + this.modalId).modal('hide');
        this.onCloseModal.emit({ closed: true });
    }
}