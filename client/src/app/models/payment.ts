class Payment {
    amount: number;
    currency: string;
    username: string | undefined;

    constructor(amount: number, currency: string, username: string | undefined) {
        this.amount = amount;
        this.currency = currency;
        this.username = username;
    }
}

export default Payment;