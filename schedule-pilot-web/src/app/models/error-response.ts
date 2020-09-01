import { ErrorServer } from '@models/error-server';

export class ErrorResponse {
    public code: number;
    public description: string;
    public details: any;
    public result: ErrorServer;
}
