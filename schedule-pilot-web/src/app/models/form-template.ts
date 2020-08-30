import { ItemFormTemplate } from './item-form-template';

export class FormTemplate {
  public listItems: Array<ItemFormTemplate>;

  constructor() {
      this.listItems = [];
  }

  public addItem(item: ItemFormTemplate) {
    this.listItems.push(item);
  }
}
