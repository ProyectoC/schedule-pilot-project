import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ItemDetail } from '@models/item/item-detail';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';

@Component({
  selector: 'app-item-detail-form',
  templateUrl: './item-detail-form.component.html',
  styleUrls: ['./item-detail-form.component.scss']
})
export class ItemDetailFormComponent extends BaseFormComponent implements OnInit {

  public _itemDetail: ItemDetail;

  constructor(private formBuilder: FormBuilder) { 
    super();
  }

  @Input()
  set itemDetail(itemDetail: ItemDetail) {
    this._itemDetail = itemDetail;
  }

  ngOnInit(): void {
    this.buildForm();
  }

  public buildForm(): void {
    this.formGroup = this.formBuilder.group({
      key: [(this._itemDetail ? this._itemDetail.key : ''), this._itemDetail ? [Validators.required] : []],
      value: [(this._itemDetail ? this._itemDetail.value : ''), this._itemDetail ? [Validators.required] : []]
    });
  }

  public submit(): ItemDetail {
    let itemDetail: ItemDetail = new ItemDetail();
    itemDetail.key = this.formGroup.get('key').value;
    itemDetail.value = this.formGroup.get('value').value;
    return itemDetail;
  }

  public isFormValid(): boolean {
    return Validations.validateFormData(this.formGroup);
  }

}
