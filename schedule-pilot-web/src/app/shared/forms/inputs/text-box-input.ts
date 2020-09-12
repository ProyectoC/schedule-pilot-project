import { FormConstants } from '../constants/form-constants';
import { BaseInput } from '../model/base-input';

export class TextBoxInput extends BaseInput<string> {
    controlType = FormConstants.TEXT_BOX_CONTROL_TYPE;
}