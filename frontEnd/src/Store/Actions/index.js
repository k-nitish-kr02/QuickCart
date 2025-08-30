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
        localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
    } else {
        // error
        toast.error("Out of stock");
    }
};