import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { BaseInput } from '../../model/base-input';
import { TextBoxInput } from '../../inputs/text-box-input';
import { GroupBoxInput } from '../../inputs/group-box-input';
import { EmailBoxInput } from '../../inputs/email-box-input';

@Injectable()
export class TestDynamicFormService {
  getControls() {
    const controls: BaseInput<string>[] = [
      new TextBoxInput({
        key: 'firstName',
        label: 'Nombres',
        type: 'text',
        value: '',
        order: 1,
        validations: [
          {
            name: 'required',
            value: '',
            errorMessage: 'Los nombres son necesarios',
          },
          {
            name: 'minLength',
            value: 5,
            errorMessage: 'El nombre debe contener al menos 5 caracteres',
          },
          {
            name: 'maxLength',
            value: 30,
            errorMessage: 'El nombre debe contener hasta 30 caracteres maximo.',
          },
        ],
      }),

      new GroupBoxInput({
        key: 'auth',
        order: 2,
        inputs: [
          new TextBoxInput({
            key: 'password',
            label: 'Contraseña',
            type: 'password',
            value: '',
            order: 1,
            validations: [
              {
                name: 'required',
                value: '',
                errorMessage: 'La contraseña es obligatoria',
              },
            ],
          }),

          new TextBoxInput({
            key: 'confirmPassword',
            label: 'Confirmar Contraseña',
            type: 'password',
            value: '',
            order: 2,
            validations: [
              {
                name: 'required',
                value: '',
                errorMessage: 'La confirmacion no puede estar vacia',
              },
              {
                name: 'passwordValidation',
                value: '',
                errorMessage: 'La confirmacion no es la misma',
              },
            ],
          }),
        ],
      }),

      new TextBoxInput({
        key: 'lastName',
        label: 'Apellidos',
        type: 'text',
        value: '',
        order: 3,
        validations: [
          {
            name: 'required',
            value: '',
            errorMessage: 'Los apellidos son necesarios',
          },
        ],
      }),

      new EmailBoxInput({
        key: 'email',
        label: 'Correo Electronico',
        type: 'email',
        value: '',
        order: 4,
        validations: [
          {
            name: 'required',
            value: '',
            errorMessage: 'El correo es requerido',
          },
          {
            name: 'regularExpression',
            value:
              '^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$',
            errorMessage: 'El correo no tiene la estructura adecuada',
          },
        ],
      }),

      new GroupBoxInput({
        key: 'address',
        order: 5,
        inputs: [
          new TextBoxInput({
            key: 'house',
            label: 'Casa',
            type: 'text',
            value: '',
            order: 1,
          }),
          new TextBoxInput({
            key: 'city',
            label: 'Ciudad',
            type: 'text',
            value: '',
            order: 2,
          }),
        ],
      }),
    ];

    return of(controls.sort((a, b) => a.order - b.order));
  }
}
