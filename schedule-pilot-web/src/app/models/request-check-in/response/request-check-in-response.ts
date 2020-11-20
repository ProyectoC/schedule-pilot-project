import { ProductRequestStatusResponse } from "./product-request-status-response";

export class RequestCheckInResponse {
    productName: string;
    trackId: string;
    loanDate: string;
    createdDate: string;
    attempts: number;
    productRequestStatus: ProductRequestStatusResponse;
}