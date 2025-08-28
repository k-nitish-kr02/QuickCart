const initialState = {
    products: [],
    pagination :{}
}
export const productReducer = (state = initialState, action) => {
    switch (action.type) {
        case "FETCH_PRODUCTS":
            return {

                ...state,
                products: action.payload.products,
                pagination: {
                    ...initialState.pagination,
                    pageNumber:action.payload.pagination.pageNumber,
                    totalPages: action.payload.pagination.totalPages,
                    totalElements : action.payload.pagination.totalElements,
                    pageSize: action.payload.pagination.pageSize,
                    lastPage: action.payload.pagination.lastPage,
                }
            };

        default:
                return state;
    }
}