const initialState = {
    isLoading: false,
    errorMessage : "",
};
export  const errorReducer = (state = initialState, action) => {
    switch (action.type) {

        case "ERROR_OCCURRED":
            return {
                ...state,
                isLoading: false,
                errorMessage: action.payload,
            };
        case "IS_FETCHING":
            return {
                ...state,
                isLoading: true,
                errorMessage : null,
            }
        case "PRODUCTS_FETCHED" :
            return {
                ...state,
                isLoading: false,
                errorMessage: null,
            }
        default:
            return state;
    }
}