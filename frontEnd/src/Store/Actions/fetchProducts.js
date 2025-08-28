import api from "../../APIs/api.js";
export const fetchProducts = (params,isCategory) => async (dispatch) => {


    try {
        dispatch({type : "IS_FETCHING"}); //making isloading = true

        const {data} = isCategory ? await api.get(`/public/products/category?${params}`)
                                : await api.get(`/public/products?${params}`);

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

        dispatch({type : "PRODUCTS_FETCHED"}); //making isloading = false
    }catch (e) {
        console.log(e);
        dispatch({type : "ERROR_OCCURRED",payload: e});
    }
}