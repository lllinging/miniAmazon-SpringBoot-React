import { Navigate, createBrowserRouter } from "react-router-dom";
import App from "../layout/App";
import Catalog  from "../../features/catalog/Catalog";
import ContactPage  from "../../features/contact/ContactPage";
import HomePage  from "../../features/home/HomePage";
import ProductDetails  from "../../features/catalog/ProductDetails";
import NotFound from "../errors/NotFoundError"
import ServerError from "../errors/ServerError"
import BasketPage from "../../features/basket/BasketPage";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
        children: [
            {path: "home", element: <HomePage/>},
            {path: "store", element: <Catalog/>},
            {path: "store/:id", element: <ProductDetails/>},
            {path: "contact", element: <ContactPage/>},
            {path: "basket", element: <BasketPage/>},
            {path: "not-found", element: <NotFound/>},
            {path: "server-error", element: <ServerError/>},
            {path: "*", element: <Navigate replace to="/not-found"/>}

        ]
    }
]);