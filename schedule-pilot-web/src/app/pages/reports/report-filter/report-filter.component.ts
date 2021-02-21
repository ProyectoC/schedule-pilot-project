import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { FormBuilder, Validators } from '@angular/forms';
import { RequestReport } from '@models/reports/request-report';
import { Validations } from '@utils/forms/validations';

@Component({
  selector: 'app-report-filter',
  templateUrl: './report-filter.component.html',
  styleUrls: ['./report-filter.component.scss']
})
export class ReportFilterComponent extends BaseFormComponent implements OnInit {

  @Output() onSubmitForm = new EventEmitter<RequestReport>();
  
  constructor(private formBuilder: FormBuilder) {
    super();
  }

  public ngOnInit(): void {
    this.buildForm();
  }

  public buildForm(): void {
    this.formGroup = this.formBuilder.group({
      dateStart: ['', [Validators.required]],
      dateEnd: ['', [Validators.required]],
    });
  }

  public onSubmit(): void {
    
    let requestSearchParameters: RequestReport = new RequestReport();
    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      requestSearchParameters.startDate = this.formGroup.get('dateStart').value
      requestSearchParameters.endDate = this.formGroup.get('dateEnd').value;
      
      //Converts
      requestSearchParameters.startDate = requestSearchParameters.startDate.slice(0, 10) + ' ' + requestSearchParameters.startDate.slice(11);
      requestSearchParameters.endDate = requestSearchParameters.endDate.slice(0, 10) + ' ' + requestSearchParameters.endDate.slice(11);
      
      // console.log('request search: ', requestSearchParameters);
      this.onSubmitForm.emit(requestSearchParameters);
    }
  }

}
