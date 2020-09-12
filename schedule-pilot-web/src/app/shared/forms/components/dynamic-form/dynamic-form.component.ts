import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BaseInput } from '../../model/base-input';
import { DynamicFormService } from '../../services/dynamic-form/dynamic-form.service';
import { Validations } from '../../utils/validations';

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss']
})
export class DynamicFormComponent implements OnInit {

  // Inputs

  @Input() controls: BaseInput<string>[] = [];

  // Outputs

  @Output() onSubmitForm = new EventEmitter<string>();

  public formGroup: FormGroup;
  public payLoad = '';
  
  constructor(private dynamicFormService: DynamicFormService) { }

  ngOnInit(): void {
    this.formGroup = this.dynamicFormService.getFormGroup(this.controls);
  }

  onSubmit() {
    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      this.payLoad = JSON.stringify(this.formGroup.getRawValue());
      this.onSubmitForm.emit(this.payLoad);
    }
  }

  public cleanForm() {
    this.formGroup.reset();
  }

}
