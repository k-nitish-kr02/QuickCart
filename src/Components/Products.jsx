import ProductCard from "./ProductCard.jsx";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {fetchProducts} from "../Store/Actions/fetchProducts.js";
import {FaExclamationTriangle} from "react-icons/fa";

const Products = () => {

    const isLoading = useSelector(state => state.errors.isLoading);
    const errorMsg = useSelector(state => state.errors.errorMsg);
    const products = useSelector(state => state.products.products);

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(fetchProducts());
    },[dispatch]);

    return (
        <div className="lg:px - 14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
            {isLoading ? (
                <p>Loading...</p>
            ): errorMsg ? (
                <div className="flex justify-center items-center h-[200px]">
                    <FaExclamationTriangle className="text-slate-800 text-3xl mr-2"/>
                    <span className="text-slate-800 text-lg font-medium">
                        {String(errorMsg)}
                    </span>
                </div>
            ):(
                <div className="min-h-[700px]">
                    <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">

                        {products.map((product) => <ProductCard key={product.productId} product={product}/>)}

                    </div>
                </div>
            )

            }
        </div>
    )
}
export default Products;