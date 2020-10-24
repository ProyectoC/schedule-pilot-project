import { AbstractControlOptions, ValidatorFn } from '@angular/forms';

export type FormGroupConfig<T> = {
  [P in keyof T]: [
    T[P] | { value: T[P]; disabled: boolean },
    (AbstractControlOptions | ValidatorFn | ValidatorFn[])?
  ];
};