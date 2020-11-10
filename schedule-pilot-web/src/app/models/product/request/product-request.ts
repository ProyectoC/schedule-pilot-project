import { Product } from '../product';
import { ProductStatus } from '../product-status';

export class ProductRequest extends Product {

  constructor() {
    super();
    this.productStatus = new ProductStatus();
  }

  static parseToCreate(json: string): ProductRequest {
    var data = JSON.parse(json);
    let productRequest = new ProductRequest();
    productRequest.name = data.name;
    productRequest.description = data.description;
    productRequest.observations = data.observations;
    productRequest.productStatus.id = data.productStatus;
    return productRequest;
  }

  static parseToUpdate(json: string, productRequest: ProductRequest): ProductRequest {
    var data = JSON.parse(json);
    productRequest.name = data.name;
    productRequest.description = data.description;
    productRequest.observations = data.observations;
    productRequest.productStatus.id = data.productStatus;
    return productRequest;
  }
}
