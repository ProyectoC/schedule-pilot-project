import { ItemStatus } from "./item-status";
import { Product } from '../product/product';
import { ItemDetail } from "./item-detail";

export class Item {
    id: number;
    name: string;
    serial1: string;
    itemStatus: ItemStatus;
    product: Product;
    itemDetails: ItemDetail[];

    constructor() {
        this.itemStatus = new ItemStatus();
        this.itemDetails = [];
        this.product = new Product();
    }
}