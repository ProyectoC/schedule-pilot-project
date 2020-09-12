import { BaseValidation } from './base-validation';

export class BaseInput<T> {

    isGroup: boolean;
    key: string;
    controlType: string;
    label: string;
    value: T;
    order: number;
    type: string;
    options: { key: string, value: string }[];
    inputs: BaseInput<T>[];
    validations: BaseValidation[];

    constructor(options: {
        isGroup?: boolean;
        value?: T;
        key?: string;
        label?: string;
        order?: number;
        controlType?: string;
        type?: string;
        options?: { key: string, value: string }[];
        inputs?: BaseInput<T>[];
        validations?: BaseValidation[];
    } = {}) {
        this.isGroup = options.isGroup ? true : false;
        this.value = options.value;
        this.key = options.key || '';
        this.label = options.label || '';
        this.order = options.order === undefined ? 1 : options.order;
        this.controlType = options.controlType || '';
        this.type = options.type || '';
        this.options = options.options || [];
        this.inputs = options.inputs || [];
        this.validations = options.validations || [];
    }
}