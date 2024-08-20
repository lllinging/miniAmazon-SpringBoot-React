import { Remove, Add } from "@mui/icons-material";
import { TableContainer, Paper, Table, TableHead, TableRow, TableCell, TableBody, IconButton, Typography, Box, Button, Link } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import agent from "../../app/api/agent";
import { useAppDispatch, useAppSelector } from "../../app/store/configureStore";
import { Product } from "../../app/models/product";

export default function BasketPage() {
    const { basket } = useAppSelector(state => state.basket);
    const dispatch = useAppDispatch();
    const {Basket: BasketActions} = agent;

    const removeItem = (productId: number) => {
        BasketActions.removeItem(productId, dispatch);
    };

    const decrementItem = (productId: number, quantity: number = 1) => {
        BasketActions.decrementItemQuantity(productId, quantity, dispatch);
    };

    const incrementItem = (productId: number, quantity: number = 1) => {
        BasketActions.incrementItemQuantity(productId, quantity, dispatch);
    };

    //define the extractImageName function
    const extractImageName = (item: Product): string | null => {
        if (item && item.pictureUrl) {
            const parts = item.pictureUrl.split('/');
            if (parts.length > 0) {
                return parts[parts.length - 1];
            }
        }
        return null;
    }

    //function to format the price with USD currency symbol
    const formatPrice = (price: number): string => {
        const formatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 2
        });
        return formatter.format(price);
    }

    if (!basket || basket.items.length === 0) {
        return (
            <Typography variant="h3">
                Your basket is empty. Please add few items!
            </Typography>
        );
    }

    return (
        <>
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Product Image</TableCell>
                        <TableCell>Product</TableCell>
                        <TableCell>Price</TableCell>
                        <TableCell>Quantity</TableCell>
                        <TableCell>Subtotal</TableCell>
                        <TableCell>Remove</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {basket.items.map((item) => (
                        <TableRow key={item.id}>
                            <TableCell>
                                {item.pictureUrl && (
                                    <img src={"/images/products/"+extractImageName(item)} alt="Product" width="50" height="50" />
                                )}
                            </TableCell>
                            <TableCell>{item.name}</TableCell>
                            <TableCell>{formatPrice(item.price)}</TableCell>
                            <TableCell>
                                <IconButton color='error' onClick={() => decrementItem(item.id)}>
                                    <Remove />
                                </IconButton>
                                {item.quantity}
                                <IconButton color='error' onClick={() => incrementItem(item.id)}>
                                    <Add />
                                </IconButton>
                            </TableCell>
                            <TableCell>{formatPrice(item.price * item.quantity)}</TableCell>
                            <TableCell>
                                <IconButton onClick={() => removeItem(item.id)} aria-label="delete">
                                    <DeleteIcon />
                                </IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        {/* <Box mt={2} p={2} bgcolor="background.paper" borderRadius={4}>
            <BasketSummary/>
            <Button
                component={Link}
                to='/checkout'
                variant='contained'
                size='large'
                fullWidth
            >
                Checkout
            </Button>
        </Box> */}
        </>
    );

    // return (
    //     <TableContainer>
    //         <Table>
    //             <TableHead>
    //                 <TableRow>
    //                     <TableCell>Product Image</TableCell>
    //                     <TableCell>Product</TableCell>
    //                     <TableCell>Price</TableCell>
    //                     <TableCell>Quantity</TableCell>
    //                     <TableCell>Subtotal</TableCell>
    //                     <TableCell>Remove</TableCell>
    //                 </TableRow>
    //             </TableHead>
    //             <TableBody>
    //                     <TableRow >
    //                         <TableCell>
                                
    //                                 <img src="/images/defualt-product-image.png/"  alt="Product" width="50" height="50" />
                               
    //                         </TableCell>
    //                         <TableCell>Produc.Name</TableCell>
    //                         <TableCell>$0.00</TableCell>
    //                         <TableCell>
    //                             0
    //                         </TableCell>
    //                         <TableCell>$0.00</TableCell>
    //                         <TableCell>
    //                             <IconButton aria-label="delete">
    //                                 <DeleteIcon />
    //                             </IconButton>
    //                         </TableCell>
    //                     </TableRow>
                    
    //             </TableBody>
    //         </Table>
    //     </TableContainer>
    // );

}