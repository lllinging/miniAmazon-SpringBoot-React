import { SetStateAction, useState, Dispatch } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useAppDispatch } from "../../app/store/configureStore";
import AddressForm from "./AddressForm";
import PaymentForm from "./PaymentForm";
import Review from "./Review";
import { ValidationRules } from "./validationRules";
import { yupResolver } from "@hookform/resolvers/yup";
import agent from "../../app/api/agent";
import { setBasket } from "../basket/basketSlice";
import { toast } from "react-toastify";
import { BasketItem } from "../../app/models/basket";
import { Paper, Typography, Stepper, Step, StepLabel, Box, Button } from "@mui/material";

const steps = ["Shipping Address", "Review Your Order", "Payment Details"];

type setActiveStepType = Dispatch<SetStateAction<number>>;

function getStepContent(step: number, total: number, setActiveStep: setActiveStepType) {
    switch (step) {
        case 0:
            return <AddressForm />
        case 1:
            return <Review />
        case 2:
            return <PaymentForm total={total} setActiveStep={setActiveStep} />
        default:
            throw new Error("Unknown step")
    }
}

export default function CheckoutPage() {
    const [activeStep, setActiveStep] = useState<number>(0);
    const [orderNumber, setOrderNumber] = useState(0);
    const [loading, setLoading] = useState(false);
    const currentValidationRule = ValidationRules[activeStep];
    const [total, setTotal] = useState(0);

    

    const methods = useForm({
        mode: "all",
        resolver: yupResolver(currentValidationRule),
    })

    const dispatch = useAppDispatch();

    const handleNext = async () => {
        //Trigger form validation
        const isValid = await methods.trigger();
        if (isValid) {
            const data: any = methods.getValues();
            if (activeStep === (steps.length - 2)) {
                //If it's last step then submit the form
                const basket = await agent.Basket.get();
                console.log("Basket:", basket);
                if (basket) {
                    const subTotal = calculateSubTotal(basket.items);
                    //Add logic to calculate delivery fee
                    const deliveryFee = 10;
                    setTotal(subTotal + deliveryFee);

                    try {
                        setLoading(true);
                        //Construct the order dto to send to backend
                        const orderDto = {
                            basketId: basket.id,
                            shippingAddress: {
                                name: data.firstName + " " + data.lastName,
                                address1: data.address1,
                                address2: data.address2,
                                city: data.city,
                                state: data.state,
                                zipCode: data.zip,
                                country: data.country,
                            },
                            subTotal: subTotal,
                            deliveryFee: deliveryFee,
                        };
                        //Call the api to create the order
                        const orderId = await agent.Orders.create(orderDto);
                        //Order created successfully
                        setOrderNumber(orderId);
                        setActiveStep(activeStep + 1);
                        //Clear the basket
                        agent.Basket.deleteBasket(basket.id);
                        dispatch(setBasket(null));
                        //Clear the basket in local storage
                        localStorage.removeItem("basket");
                        localStorage.removeItem("basket_id");
                    } catch (error) {
                        console.error("Error submitting order: ", error);
                        toast.error("Error submitting order");
                    } finally {
                        setLoading(false);

                    }
                } else {
                    toast.error("Basket not found in local storage");
                }

            } else {
                //Move to the next step
                setActiveStep(activeStep + 1);
            }
        }
    }

    const handleBack = () => {
        setActiveStep(activeStep - 1);
    }

    const calculateSubTotal = (items: BasketItem[]): number => {
        return items.reduce((total, item) => total + item.price * item.quantity, 0);
    };

    
     
    return (
        <FormProvider {...methods}>
            <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
                <Typography component="h1" variant="h4" align="center">
                    Checkout
                </Typography>
                <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5 }}>
                    {steps.map((label) => (
                        <Step key={label}>
                            <StepLabel>{label}</StepLabel>
                        </Step>
                    ))}
                </Stepper>
                <>
                    {activeStep === steps.length ? (
                        <>
                            <Typography variant="h5" gutterBottom>
                                Thank you for your order.
                            </Typography>
                            <Typography variant="subtitle1">
                                Your order number is #{orderNumber}. We have emailed your order confirmation, and will send you an update when your order has shipped.
                            </Typography>
                        </>
                    ) : (
                        <>
                            {getStepContent(activeStep, total, setActiveStep)}
                            <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
                                {activeStep !== 0 && (
                                    <Button onClick={handleBack} sx={{ mt: 3, ml: 1 }}>
                                        Back
                                    </Button>
                                )}
                                {activeStep !== steps.length - 1 && (
                                    <Button
                                        variant="contained"
                                        onClick={handleNext}
                                        sx={{ mt: 3, ml: 1 }}
                                    >
                                        Next
                                    </Button>
                                )}
                                
                            </Box>
                        </>
                    )}
                </>
            </Paper>
        </FormProvider>
    );


}

