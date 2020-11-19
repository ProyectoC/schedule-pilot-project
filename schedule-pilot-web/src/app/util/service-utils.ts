import { HttpParams } from '@angular/common/http';
import { ParametersQuery } from '../models/parameters-query';

export class ServiceUtils {

    public static getHttpParameters(parametersQuery: ParametersQuery): HttpParams {
        let params = new HttpParams();
        params = params.append('page', parametersQuery.page + '');
        params = params.append('per_page', parametersQuery.per_page + '');
        params = params.append('order_by', parametersQuery.order_by);
        return params;
    }
}