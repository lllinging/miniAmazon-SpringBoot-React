import React from 'react';
import { SetStateAction, Dispatch } from 'react';
import { useFormContext } from 'react-hook-form';
import { CardElement, useStripe, useElements } from '@stripe/react-stripe-js';
import { Button, Grid, Typography } from '@mui/material';
import { store } from '../../app/store/configureStore';
import Spinner from '../../app/layout/Spinner';

type setActiveStepType = Dispatch<SetStateAction<number>>;

interface PaymentFormProps {
    total: number; 
    setActiveStep: setActiveStepType;
}

const PaymentForm: React.FC<PaymentFormProps> = ({ total, setActiveStep }) => {
    const { formState: { errors } } = useFormContext();
    const stripe = useStripe();
    const elements = useElements();

    const { user } = store.getState().account;

    if (!user) {
        return <Spinner />;
    }

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
    
        // Create PaymentIntent
        const response = await fetch('http://localhost:8081/api/payment/payment-intent', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                amount: total * 100, // because stripe expects the price in cents
                currency: 'usd', 
                username: user?.username,
            }),
        });

        if (!response.ok) {
            console.error('Failed to create PaymentIntent');
            return;
        }
    
        const { client_secret } = await response.json();
        if (!client_secret) {
            console.error('Missing client_secret');
            return;
        }
    
        // Confirm the PaymentIntent with the card element
        const cardElement = elements?.getElement(CardElement);
        if (!cardElement) {
            console.error('Card element not found');
            return;
        }

        try {
            const paymentIntentResult = await stripe?.confirmCardPayment(client_secret, {
                payment_method: {
                    card: cardElement,
                },
            });
        
            // ensure paymentIndent is returned
            if (!paymentIntentResult) {
                console.error('No result returned from confirmCardPayment');
                return;
            }
        
            if (paymentIntentResult.error) {
                console.error('Error confirming payment:', paymentIntentResult.error.message);
            } else {
                console.log('Payment successful!', paymentIntentResult);
                setActiveStep((prevStep: number) => prevStep + 1);
            }
        } catch (e) {
            console.error('Error during payment confirmation:', e);
        }
    };
    
    return (
        <>
            <Typography variant="h6" gutterBottom>
                Payment Form
            </Typography>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <CardElement options={{ hidePostalCode: true }} />
                    </Grid>
                    <Grid item xs={12}>
                        <Button type="submit" variant="contained" color="primary" disabled={!stripe}>
                            Pay ${total} 
                        </Button>
                    </Grid>
                </Grid>
            </form>
        </>
    );
}

export default PaymentForm;
