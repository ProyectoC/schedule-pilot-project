import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { StatusTicketCheck } from '@models/status/status-ticket-check';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { Observable } from 'rxjs';
import { RequestTicketCheckInParameters } from '@models/ticket-check-in/request/request-ticket-check-in-parameters';
import { StatusService } from '@services/status/status.service';
import { FormBuilder } from '@angular/forms';
import { Validations } from '@utils/forms/validations';

@Component({
  selector: 'app-loan-ticket-check-in-search',
  templateUrl: './loan-ticket-check-in-search.component.html',
  styleUrls: ['./loan-ticket-check-in-search.component.scss']
})
export class LoanTicketCheckInSearchComponent extends BaseFormComponent implements OnInit {

  public ticketStatus$: Observable<StatusTicketCheck[]>;
  @Output() onSubmitForm = new EventEmitter<RequestTicketCheckInParameters>();

  constructor(private formBuilder: FormBuilder, public statusService: StatusService) { 
    super();
  }

  ngOnInit(): void {
    this.buildForm();
  }

  public initDataSources(): void {
    this.ticketStatus$ = null;
    this.ticketStatus$ = this.statusService.getStatusTicketCheck();
  }

  public buildForm(): void {
    this.initDataSources();
    this.formGroup = this.formBuilder.group({
      trackIdTicket: ['', []],
      trackIdRequest: ['', []],
      deliveryDateStart: ['', []],
      deliveryDateEnd: ['', []],
      returnDateStart: ['', []],
      returnDateEnd: ['', []],
      itemName: ['', []],
      status: ['', []]
    });
  }

  public onSubmit(): void {
    
    let requestSearchParameters: RequestTicketCheckInParameters = new RequestTicketCheckInParameters();
    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      requestSearchParameters.trackIdTicket = this.formGroup.get('trackIdTicket').value;
      requestSearchParameters.trackIdRequest = this.formGroup.get('trackIdRequest').value;
      requestSearchParameters.status = this.formGroup.get('status').value;
      requestSearchParameters.itemName = this.formGroup.get('itemName').value;

      requestSearchParameters.deliveryDateStart = this.formGroup.get('deliveryDateStart').value
      requestSearchParameters.deliveryDateEnd = this.formGroup.get('deliveryDateEnd').value;
      requestSearchParameters.returnDateStart = this.formGroup.get('returnDateStart').value;
      requestSearchParameters.returnDateEnd = this.formGroup.get('returnDateEnd').value;
      
      //Converts
      requestSearchParameters.deliveryDateStart = requestSearchParameters.deliveryDateStart.slice(0, 10) + ' ' + requestSearchParameters.deliveryDateStart.slice(11);
      requestSearchParameters.deliveryDateEnd = requestSearchParameters.deliveryDateEnd.slice(0, 10) + ' ' + requestSearchParameters.deliveryDateEnd.slice(11);
      requestSearchParameters.returnDateStart = requestSearchParameters.returnDateStart.slice(0, 10) + ' ' + requestSearchParameters.returnDateStart.slice(11);
      requestSearchParameters.returnDateEnd = requestSearchParameters.returnDateEnd.slice(0, 10) + ' ' + requestSearchParameters.returnDateEnd.slice(11);
      
      // console.log('request search: ', requestSearchParameters); 
      this.onSubmitForm.emit(requestSearchParameters);
    }
  }

}
