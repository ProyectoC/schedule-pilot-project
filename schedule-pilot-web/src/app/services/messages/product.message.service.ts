import { Injectable } from '@angular/core';
import { MessagesService } from './message.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class ProductMessageService {
  constructor(
    private messageService: MessagesService,
    private translate: TranslateService
  ) {}

  generateMessageErrorGetProducts(message: string) {
    this.messageService.generateErrorMessage('Obtener Productos', message);
  }

  generateMessageErrorCreateProducts(message: string) {
    this.messageService.generateErrorMessage('Crear Producto', message);
  }

  generateMessageErrorUpdateProducts(message: string) {
    this.messageService.generateErrorMessage('Actualizar Producto', message);
  }

  generateMessageErrorDeleteProducts(message: string) {
    this.messageService.generateErrorMessage('Eliminar Producto', message);
  }

  generateMessageProductsDefault(message: string) {
    this.messageService.generateErrorMessage('Productos', message);
  }
}
