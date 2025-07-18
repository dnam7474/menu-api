export type Order = {
    id: number;
    userid: number;
    ordertime: string;
    pickuptime: string;
    area: string;
    location: string;
    tax: number;
    tip: number;
    pan: string;
    expiryMonth: number;
    expiryYear: number;
    status: string;
}