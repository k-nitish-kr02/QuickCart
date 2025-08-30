import {configureStore} from "@reduxjs/toolkit";
import {productReducer} from "./Reducers/productReducer.js";
import {errorReducer} from "./Reducers/errorReducer.js";
import {categoryReducer} from "./Reducers/categoryReducer.js";
import {cartReducer} from "./Reducers/cartReducer.js";

const cartItems = localStorage.getItem("cartItems")
    ? JSON.parse(localStorage.getItem("cartItems")) : [];
const initialState = {
    carts: cartItems.cart ? cartItems : { cart: [], totalPrice: 0, cartId: null },
}

export const store = configureStore(
    {
        reducer: {
            products : productReducer,
            categories : categoryReducer,
            errors : errorReducer,
            carts : cartReducer,
        },
        preloadedState : initialState

    }
);
export default store;