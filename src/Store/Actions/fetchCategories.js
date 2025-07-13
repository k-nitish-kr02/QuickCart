import api from "../../APIs/api.js";
export const fetchCategories = () => async (dispatch) => {


    try {
        // dispatch({type : "IS_FETCHING"}); //making isloading = true

        const {data} = await api.get(`/public/categories`);

        dispatch({
            type :"FETCH_CATEGORIES",
            payload: {
                categories: data.content,
            }
        })

        // dispatch({type : "PRODUCTS_FETCHED"}); //making isloading = false
    }catch (e) {
        console.log(e);
        dispatch({type : "ERROR_OCCURRED",payload: e});
    }
}