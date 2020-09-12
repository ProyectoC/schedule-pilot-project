import { FormConstants } from '../constants/form-constants';
import { BaseInput } from '../model/base-input';

export class GroupBoxInput extends BaseInput<string> {
  controlType = FormConstants.GROUP_BOX_CONTROL_TYPE;
  isGroup = true;
}
