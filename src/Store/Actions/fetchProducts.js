import api from "../../APIs/api.js";
export const fetchProducts = () => async (dispatch) => {
    try {
        dispatch({type : "IS_FETCHING"});
        const {data} = await api.get(`/public/products`);
        dispatch({
            type :"FETCH_PRODUCTS",
            payload: {
                products: data.content,
                pagination: {
                    pageNumber: data.pageNumber,
                    totalElements: data.totalElements,
                    pageSize: data.pageSize,
                    totalPages: data.totalPages,
                    lastPage: data.lastPage
                }
            }
        })
        dispatch({type : "PRODUCTS_FETCHED"});
    }catch (e) {
        console.log(e);
        dispatch({type : "ERROR_OCCURRED",payload: e});
    }
}