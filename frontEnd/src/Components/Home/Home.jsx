import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import HeroBanner from "./HeroBanner.jsx";
import {fetchProducts} from "../../Store/Actions/fetchProducts.js";
import ProductCard from "../Products/ProductCard.jsx";
import {Loader} from "../SharedComponents/Loader.jsx";
import {FaExclamationTriangle} from "react-icons/fa";

const Home =() =>{

    const dispatch = useDispatch();

    const products = useSelector((state)=>state.products.products);
    const isLoading = useSelector(state => state.errors.isLoading);
    const errorMessage = useSelector(state => state.errors.errorMessage);

    useEffect(()=>{
        dispatch(fetchProducts());
    },[dispatch])

    return (
        <div className="lg:px-14 sm:px-8 px-4 bg-gray-100">
            <div className="py-6">
                <HeroBanner />
            </div>

            <div className="py-5">
                <div className="flex flex-col items-center justify-center space-y-2">
                    <h1 className="text-slate-800 text-4xl font-bold">Products </h1>

                        <span className="text-slate-800">
                            Discover Our Handpicked Selection Just For You
                        </span>

                </div>
            </div>

            <div>

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
                        <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
                            {products && products?.slice(0, 4).map((product, index) => (
                                <ProductCard key={index} {...product} />
                            ))}
                        </div>
                )}
            </div>
        </div>

    )
}
export default Home;