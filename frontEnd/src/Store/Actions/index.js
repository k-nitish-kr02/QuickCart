export const addToCart = (data, qty = 1, toast) =>
    (dispatch, getState) => {
    // Find the product
    const { products } = getState().products;
    const getProduct = products.find(
        (item) => item.productId === data.productId
    );

    // Check for stocks
    const isQuantityExist = getProduct.quantity >= qty;

    // If in stock -> add
    if (isQuantityExist) {
        dispatch({ type: "ADD_CART", payload: {...data, quantity: qty}});
        toast.success(`${data?.productName} added to the cart`);
        localStorage.setItem("cartItems", JSON.stringify(getState().carts));
    } else {
        // error
        toast.error("Out of stock");
    }
}
export const increaseCartQuantity =
    (data, toast, currentQuantity, setCurrentQuantity) =>
        (dispatch, getState) => {
            // Find the product
            const { products } = getState().products;

            const getProduct = products.find(
                (item) => item.productId === data.productId
            );

            const isQuantityExist = getProduct.quantity >= currentQuantity + 1;

            if (isQuantityExist) {
                const newQuantity = currentQuantity + 1;
                setCurrentQuantity(newQuantity);

                dispatch({
                    type: "ADD_CART",
                    payload: {...data, quantity: newQuantity + 1 },
                });
                localStorage.setItem("cartItems", JSON.stringify(getState().carts));
            } else {
                toast.error("Quantity Reached to Limit");
            }

        };



export const decreaseCartQuantity =
    (data, newQuantity) => (dispatch, getState) => {
        dispatch({
            type: "ADD_CART",
            payload: {...data, quantity: newQuantity},
        });
        localStorage.setItem("cartItems", JSON.stringify(getState().carts));
    }

export const removeFromCart =  (data, toast) => (dispatch, getState) => {
    dispatch({type: "REMOVE_CART", payload: data });
    toast.success(`${data.productName} removed from cart`);
    localStorage.setItem("cartItems", JSON.stringify(getState().carts));
}