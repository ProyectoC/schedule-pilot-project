import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { StatusTicketCheck } from '@models/status/status-ticket-check';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { Observable } from 'rxjs';
import { RequestTicketCheckOutParameters } from '@models/ticket-check-out/request/request-ticket-check-out-parameters';
import { StatusService } from '@services/status/status.service';
import { FormBuilder } from '@angular/forms';
import { Validations } from '@utils/forms/validations';

@Component({
  selector: 'app-return-ticket-check-out-search',
  templateUrl: './return-ticket-check-out-search.component.html',
  styleUrls: ['./return-ticket-check-out-search.component.scss']
})
export class ReturnTicketCheckOutSearchComponent extends BaseFormComponent implements OnInit {

  public ticketStatus$: Observable<StatusTicketCheck[]>;
  @Output() onSubmitForm = new EventEmitter<RequestTicketCheckOutParameters>();

  constructor(private formBuilder: FormBuilder, public statusService: StatusService) { super(); }

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
      trackIdOut: ['', []],
      trackIdIn: ['', []],
      deliveryDateStart: ['', []],
      deliveryDateEnd: ['', []],
      returnDateStart: ['', []],
      returnDateEnd: ['', []],
      itemName: ['', []],
      status: ['', []]
    });
  }

  public onSubmit(): void {
    
    let requestSearchParameters: RequestTicketCheckOutParameters = new RequestTicketCheckOutParameters();
    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      requestSearchParameters.trackIdOut = this.formGroup.get('trackIdOut').value;
      requestSearchParameters.trackIdIn = this.formGroup.get('trackIdIn').value;
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
