import { Product } from '../product';
import { ProductStatus } from '../product-status';
import { ProductType } from '../product-type';

export class ProductRequest extends Product {

  constructor() {
    super();
    this.productStatus = new ProductStatus();
    this.productType = new ProductType()
  }

  static parseToCreate(json: string): ProductRequest {
    var data = JSON.parse(json);
    let productRequest = new ProductRequest();
    productRequest.name = data.name;
    productRequest.description = data.description;
    productRequest.serial1 = data.serial1;
    productRequest.observations = data.observations;
    productRequest.productStatus.id = data.productStatus;
    productRequest.productType.id = data.productType;
    return productRequest;
  }

  static parseToUpdate(json: string, productRequest: ProductRequest): ProductRequest {
    var data = JSON.parse(json);
    productRequest.name = data.name;
    productRequest.description = data.description;
    productRequest.serial1 = data.serial1;
    productRequest.observations = data.observations;
    productRequest.productStatus.id = data.productStatus;
    productRequest.productType.id = data.productType;
    return productRequest;
  }
}
