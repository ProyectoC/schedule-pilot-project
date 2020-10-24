import { ProductStatus } from "./product-status";
import { ProductType } from "./product-type";

export class Product {
    id: number;
    name: string;
    description: string;
    serial1: string;
    observations: string;
    productStatus: ProductStatus;
    productType: ProductType;
    
}