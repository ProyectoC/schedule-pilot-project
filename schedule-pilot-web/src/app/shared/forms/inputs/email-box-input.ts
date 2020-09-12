import { FormConstants } from '../constants/form-constants';
import { BaseInput } from '../model/base-input';

export class EmailBoxInput extends BaseInput<string> {
    controlType = FormConstants.PASSWORD_BOX_CONTROL_TYPE;
}