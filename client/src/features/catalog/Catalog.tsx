import { useEffect, useState } from 'react';
import { Product } from '../../app/models/product';
import ProductList from './ProductList';
import agent from '../../app/api/agent';
import Spinner from '../../app/layout/Spinner';

function Catalog() {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        agent.Store.list()
        .then((products) => setProducts(products.content))
        .catch((error) => console.error('Error Fetching Data:', error))
        .finally(() => setLoading(false));
    }, []);

    if (!products) return <h3>unable to load products</h3>;
    if (loading) return <Spinner message="Loading Products..." />;

    console.log("productslength......" + products.length);
    console.log("products......" + products[0]);

    return (
        <>
            <ProductList products={products} />
        </>
    );
}

export default Catalog;