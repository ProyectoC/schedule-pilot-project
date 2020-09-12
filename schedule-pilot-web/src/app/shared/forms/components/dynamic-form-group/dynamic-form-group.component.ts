import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BaseInput } from '../../model/base-input';

@Component({
  selector: 'app-dynamic-form-group',
  templateUrl: './dynamic-form-group.component.html',
  styleUrls: ['./dynamic-form-group.component.scss']
})
export class DynamicFormGroupComponent implements OnInit {

  @Input() baseInputs: BaseInput<string>[] = [];
  @Input() formGroup: FormGroup;
  
  constructor() { }

  ngOnInit(): void {
  }

}
