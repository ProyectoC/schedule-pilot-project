import { Item } from '../../item/item';
import { TicketCheckStatus } from '../ticket-check-status';

export class TicketCheckInResponse {
    id: number;
    trackIdTicket: string;
    trackIdRequestOrigin: string;
    deliveryDate: string;
    returnDate: string;
    item: Item;
    ticketCheckStatus: TicketCheckStatus;
}