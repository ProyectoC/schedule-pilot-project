import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { RequestCheckInParameters } from '@models/request-check-in/request/request-check-in-search-parameters';
import { FormBuilder } from '@angular/forms';
import { Validations } from '@utils/forms/validations';
import { StatusService } from '@services/status/status.service';
import { StatusRequestCheckIn } from '@models/status/status-request-check-in';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-loan-request-check-in-search',
  templateUrl: './loan-request-check-in-search.component.html',
  styleUrls: ['./loan-request-check-in-search.component.scss']
})
export class LoanRequestCheckInSearchComponent extends BaseFormComponent implements OnInit {

  public requestStatus$: Observable<StatusRequestCheckIn[]>;
  @Output() onSubmitForm = new EventEmitter<RequestCheckInParameters>();

  constructor(private formBuilder: FormBuilder, public statusService: StatusService) { 
    super();
  }

  ngOnInit(): void {
    this.buildForm();
  }

  public initDataSources(): void {
    this.requestStatus$ = null;
    this.requestStatus$ = this.statusService.getStatusRequestCheckIn();
  }

  public buildForm(): void {
    this.initDataSources();
    this.formGroup = this.formBuilder.group({
      productName: ['', []],
      trackId: ['', []],
      requestStatus: ['', []],
      loanDateStart: ['', []],
      loanDateEnd: ['', []],
      requestDateStart: ['', []],
      requestDateEnd: ['', []]
    });
  }

  public onSubmit(): void {
    
    let requestSearchParameters: RequestCheckInParameters = new RequestCheckInParameters();
    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      requestSearchParameters.productName = this.formGroup.get('productName').value;
      requestSearchParameters.trackId = this.formGroup.get('trackId').value;
      requestSearchParameters.status = this.formGroup.get('requestStatus').value;

      requestSearchParameters.loanDateStart = this.formGroup.get('loanDateStart').value
      requestSearchParameters.loanDateEnd = this.formGroup.get('loanDateEnd').value;
      requestSearchParameters.requestDateStart = this.formGroup.get('requestDateStart').value;
      requestSearchParameters.requestDateEnd = this.formGroup.get('requestDateEnd').value;
      
      //Converts
      requestSearchParameters.loanDateStart = requestSearchParameters.loanDateStart.slice(0, 10) + ' ' + requestSearchParameters.loanDateStart.slice(11);
      requestSearchParameters.loanDateEnd = requestSearchParameters.loanDateEnd.slice(0, 10) + ' ' + requestSearchParameters.loanDateEnd.slice(11);
      requestSearchParameters.requestDateStart = requestSearchParameters.requestDateStart.slice(0, 10) + ' ' + requestSearchParameters.requestDateStart.slice(11);
      requestSearchParameters.requestDateEnd = requestSearchParameters.requestDateEnd.slice(0, 10) + ' ' + requestSearchParameters.requestDateEnd.slice(11);
      
      // console.log('request search: ', requestSearchParameters);
      this.onSubmitForm.emit(requestSearchParameters);
    }
  }

}
