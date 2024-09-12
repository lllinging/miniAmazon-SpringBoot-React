import { Navigate, createBrowserRouter } from "react-router-dom";
import App from "../layout/App";
import Catalog  from "../../features/catalog/Catalog";
import ContactPage  from "../../features/contact/ContactPage";
import HomePage  from "../../features/home/HomePage";
import ProductDetails  from "../../features/catalog/ProductDetails";
import NotFound from "../errors/NotFoundError"
import ServerError from "../errors/ServerError"
import BasketPage from "../../features/basket/BasketPage";
import SignInPage from "../../features/account/SignInPage";
import RegisterPage from "../../features/account/RegisterPage";
import CheckoutPage from "../../features/checkout/CheckoutPage";
import RequireAuth from "./RequireAuth";
import Order from "../../features/orders/Order";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
        children: [
            {
                element: <RequireAuth/>, children:[
                    {path:'checkout', element:<CheckoutPage/>},
                    {path:'orders', element:<Order/>},
                ]
            },
            {path: "", element: <HomePage/>},
            {path: "store", element: <Catalog/>},
            {path: "store/:id", element: <ProductDetails/>},
            {path: "contact", element: <ContactPage/>},
            {path: "login", element: <SignInPage/>},
            {path: "register", element: <RegisterPage/>},
            {path: "basket", element: <BasketPage/>},
            {path: "not-found", element: <NotFound/>},
            {path: "server-error", element: <ServerError/>},
            {path: "*", element: <Navigate replace to="/not-found"/>}

        ]
    }
]);