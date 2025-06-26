import {FaShoppingCart} from "react-icons/fa";
import ProductViewModal from "./ProductViewModal.jsx";
import {useState} from "react";

const ProductCard = ({product}) => {
    const isAvailable = product.quantity && (product.quantity > 0);
    let [open, setOpen] = useState(false)

    return(

        <div className="border rounded-lg shadow-xl transition-shadow duration-300 overflow-hidden">
            <div className="w-full overflow-hidden aspect-[3/2]" onClick={()=> {setOpen(true)}} >

                <img className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-110"
                     src={product.productImage}
                      alt={product.productName}>

                </img>
            </div>

            <div className="p-4">
                <h2 onClick={()=> {setOpen(true)}} className="text-lg font-semibold text-gray-700 cursor-pointer" >
                    {product.productName}
                </h2>
                <div className="min-h-20 max-h-20">
                    <p className="text-gray-500 text-sm">{product.description}</p>
                </div>

                <div className="flex justify-between">
                {product.specialPrice ? (
                        <div className="flex flex-col">
                                <span className="text-gray-500 text-sm line-through"> ${Number(product.price).toFixed(2)}</span>

                                <span className="text-gray-700 font-semibold text-xl"> ${Number(product.specialPrice).toFixed(2)}</span>
                            </div>
                        )
                    : (
                            <div>
                                <span className="text-gray-700 text-xl font-semibold"> ${Number(product.price).toFixed(2)}</span>
                            </div>
                        )}
                    <button className={`bg-blue-500 ${isAvailable ? "opacity-100 hover:bg-blue-700" : "opacity-70"} flex justify-center items-center transition-colors duration-300 w-40 text-white py-1 px-3 rounded-lg font-semibold`} >
                        <FaShoppingCart className="mr-2" />
                        {isAvailable ? ( "Add to cart" ) : ("Stock Out")}
                    </button>

                </div>



            </div>
            <ProductViewModal open={open} setOpen={setOpen} product={product}/>

        </div>

    )
}
export default ProductCard;