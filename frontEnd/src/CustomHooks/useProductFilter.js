import {useSearchParams} from "react-router";
import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {fetchProducts} from "../Store/Actions/fetchProducts.js";


const useProductFilter = () => {
    const [searchParams] = useSearchParams();
    const dispatch = useDispatch();

    useEffect(() => {
        const params = new URLSearchParams();
        const currentPage = Number(searchParams.get("pageNumber") || 1);
        params.set("pageNumber",currentPage-1);

        const sortOrder = searchParams.get("sortOrder") || "asc";
        const keyword = searchParams.get("keyword") || null;
        const category = searchParams.get("category") || null;

        params.set("sortBy", "price");
        params.set("sortOrder",sortOrder);

        if (keyword) {
            params.set("keyword", keyword);
        }


        if (category && category!=="all") {
            params.set("category", category);
            dispatch(fetchProducts(params.toString(),true));
        } else {
            dispatch(fetchProducts(params.toString(),false));
        }




    }, [dispatch, searchParams]);
}
export default useProductFilter;