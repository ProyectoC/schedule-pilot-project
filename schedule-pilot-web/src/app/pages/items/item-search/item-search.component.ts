import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ItemStatus } from '@models/item/item-status';
import { ItemStatusService } from '@services/item-status/item-status.service';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { Observable } from 'rxjs';
import { ItemSearchParameters } from '@models/item/request/item-search-parameters';
import { Validations } from '@utils/forms/validations';

@Component({
  selector: 'app-item-search',
  templateUrl: './item-search.component.html',
  styleUrls: ['./item-search.component.scss']
})
export class ItemSearchComponent extends BaseFormComponent implements OnInit {

  public itemStatus$: Observable<ItemStatus[]>;
  @Output() onSubmitForm = new EventEmitter<ItemSearchParameters>();

  constructor(private formBuilder: FormBuilder, public itemStatusService: ItemStatusService) {
    super();
  }

  ngOnInit(): void {
    this.buildForm();
  }

  public initDataSources(): void {
    this.itemStatus$ = null;
    this.itemStatus$ = this.itemStatusService.getItemsStatus();
  }

  public buildForm(): void {
    this.initDataSources();
    this.formGroup = this.formBuilder.group({
      name: ['', []],
      serial1: ['', []],
      itemStatus: ['', []],
    });
  }

  public onSubmit(): void {
    let itemSearchParameters: ItemSearchParameters = new ItemSearchParameters();
    if (!Validations.validateFormData(this.formGroup)) {
      return;
    } else {
      itemSearchParameters.name = this.formGroup.get('name').value;
      itemSearchParameters.serial = this.formGroup.get('serial1').value;
      itemSearchParameters.status = this.formGroup.get('itemStatus').value;
      this.onSubmitForm.emit(itemSearchParameters);
    }
  }

}
