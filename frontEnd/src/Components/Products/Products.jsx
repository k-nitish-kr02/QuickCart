import ProductCard from "./ProductCard.jsx";
import {useSelector} from "react-redux";
import {FaExclamationTriangle} from "react-icons/fa";
import Filter from "./Filter.jsx";
import useProductFilter from "../../CustomHooks/useProductFilter.js";
import useCategory from "../../CustomHooks/useCategory.js";
import {Loader} from "../SharedComponents/Loader.jsx";
import Paginations from "../SharedComponents/Paginations.jsx";


const Products = () => {

    const isLoading = useSelector(state => state.errors.isLoading);
    const errorMessage = useSelector(state => state.errors.errorMessage);
    const products = useSelector(state => state.products.products);
    const categories = useSelector(state => state.categories.categories);
    const pagination = useSelector(state => state.products.pagination);


    useProductFilter();
    useCategory();

    console.log(categories);

    return (
        <div className="lg:px - 14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
            <Filter categories={categories}/>

            {isLoading ? (
                <Loader />
            ): errorMessage ? (
                <div className="flex justify-center items-center h-[200px]">
                    <FaExclamationTriangle className="text-slate-800 text-3xl mr-2"/>
                    <span className="text-slate-800 text-lg font-medium">
                        {String(errorMessage)}
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
            <div className="flex justify-center pt-10">
                <Paginations pagination={pagination} />
            </div>
        </div>

    )
}
export default Products;