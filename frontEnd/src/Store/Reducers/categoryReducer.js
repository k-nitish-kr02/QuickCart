const initialState = {
    categories: [],
}
export const categoryReducer = (state = initialState,action) => {
    switch (action.type){
        case "FETCH_CATEGORIES":
            return {
                ...state,
                categories: action.payload.categories,
            }
        default:
            return state;
    }

}