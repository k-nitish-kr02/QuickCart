import {configureStore} from "@reduxjs/toolkit";
import {productReducer} from "./productReducer.js";
import {errorReducer} from "./errorReducer.js";
import {categoryReducer} from "./categoryReducer.js";

export const store = configureStore(
    {
        reducer: {
            products : productReducer,
            categories : categoryReducer,
            errors : errorReducer,
        },
        preloadedState : {

        }

    }
);
export default store;