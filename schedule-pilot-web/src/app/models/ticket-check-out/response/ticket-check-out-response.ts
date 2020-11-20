import { Item } from '../../item/item';
import { TicketCheckStatus } from '../../ticket-check-in/ticket-check-status';

export class TicketCheckOutResponse {
    id: number;
    trackIdTicketOut: string;
    trackIdTicketIn: string;
    deliveryDate: string;
    returnDate: string;
    item: Item;
    ticketCheckStatus: TicketCheckStatus;
}