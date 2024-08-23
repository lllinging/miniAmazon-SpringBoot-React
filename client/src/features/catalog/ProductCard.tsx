import { Avatar, Button, Card, CardActions, CardContent, CardHeader, CardMedia, CircularProgress, Typography } from "@mui/material";
import { LoadingButton } from '@mui/lab';
import { Product } from "../../app/models/product";
import { Link } from "react-router-dom";
import { useState } from "react";
import agent from "../../app/api/agent";
import { useAppDispatch } from "../../app/store/configureStore";
import { setBasket } from "../basket/basketSlice";

interface Props {
    product: Product;
}

export default function ProductCard({ product }: Props) {
    const extractImageName = (item: Product): string | null => {
        if (item && item.pictureUrl) {
            const parts = item.pictureUrl.split('/');
            if (parts.length > 0) {
                return parts[parts.length - 1];
            }
        }
        return null;
    }
    const formatPrice = (price: number): string => {
        const formatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 2
        });
        return formatter.format(price);
    }
    const [loading, setLoading] = useState(false);
    const dispatch = useAppDispatch();
    function addItem() {
        setLoading(true);
        agent.Basket.addItem(product, dispatch)
            .then(response => {
                console.log('New Basket:', response.basket);
                dispatch(setBasket(response.basket));
            })
            .catch(error => console.error('Error adding item to basket:', error))
            .finally(() => setLoading(false));
    }
    return (
        <Card>
            <CardHeader
                avatar={
                    <Avatar sx={{ bgcolor: 'secondary.main' }} aria-label="recipe">
                        {product.name.charAt(0).toUpperCase()}
                    </Avatar>
                }

                title={product.name}
                titleTypographyProps={{ sx: { fontWeight: 'bold', color: 'primary.main' } }}
            />
            <CardMedia
                sx={{ height: 140, backgroundSize: 'contain' }}
                image={'/images/products/' + extractImageName(product)}
                title={product.name}
            />
            <CardContent>
                <Typography gutterBottom variant="h5" color="secondary">
                    {formatPrice(product.price)}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {product.productBrand} / {product.productType}
                </Typography>
            </CardContent>
            <CardActions>
                <LoadingButton
                    loading={loading}
                    onClick={addItem}
                    size="small"
                    startIcon={loading ? <CircularProgress size={20} color="inherit" /> : null}
                >
                    Add to cart
                </LoadingButton>
                <Button component={Link} to={`/store/${product.id}`} size="small">View</Button>
                {/* <Button size='small'> Add to cart </Button>
                <Button component={Link} to={`/store/${product.id}`} size='small'> View </Button> */}
            </CardActions>
        </Card>
    )
}