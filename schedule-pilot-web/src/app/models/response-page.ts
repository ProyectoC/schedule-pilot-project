export class ResponsePage<T> {
    totalRows: number;
    totalRowsCurrentPage: number;
    totalPages: number;
    pageNumber: number;
    content: Array<T>;
}