import { ProductStatus } from "./product-status";

export class Product {
    id: number;
    name: string;
    description: string;
    observations: string;
    productRoles = [];
    productStatus: ProductStatus;
    
}