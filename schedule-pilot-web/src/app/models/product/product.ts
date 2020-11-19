import { ProductStatus } from "./product-status";
import { ProductRol } from './product-rol';

export class Product {
    id: number;
    name: string;
    description: string;
    observations: string;
    productRoles: ProductRol[];
    productStatus: ProductStatus;
    itemsCount: number;
}