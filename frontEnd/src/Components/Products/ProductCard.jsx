import {FaShoppingCart} from "react-icons/fa";
import ProductViewModal from "./ProductViewModal.jsx";
import {useState} from "react";
import {truncateText} from "../../Utils/truncateText.js";
import {useDispatch} from "react-redux";
import {addToCart} from "../../Store/Actions/index.js";
import toast from "react-hot-toast";

const ProductCard = ({
                         productImage,
                         productName,
                         description,
                         discount,
                         specialPrice,
                         price,
                         productId,
                         quantity,
                         about = false,
                     }) => {
    // product  = {
    //    ...
    // categoryId;
    // seller;
    // }
    const isAvailable = quantity && (quantity > 0);
    let [open, setOpen] = useState(false)
    const [selectedViewProduct, setSelectedViewProduct] = useState("");

    const btnLoader = false;

    const handleProductView = (product) => {
        if (!about) {
            setSelectedViewProduct(product);
            setOpen(true);
        }
    };

    const dispatch = useDispatch();


    const addToCartHandler = (cartItems) => {
        dispatch(addToCart(cartItems, 1, toast));
    };

    return(

        <div className="border rounded-lg shadow-xl transition-shadow duration-300 overflow-hidden">
            <div onClick={() => {
                handleProductView({
                    productId,
                    productName,
                    productImage,
                    description,
                    quantity,
                    price,
                    discount,
                    specialPrice,
                })
            }}
                 className="w-full overflow-hidden aspect-[1]">

                <img className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-110"
                     src={productImage}
                     alt={productName}>

                </img>
            </div>

            <div className="p-4">
                <h2 onClick={() => {
                    handleProductView({
                        productId,
                        productName,
                        productImage,
                        description,
                        quantity,
                        price,
                        discount,
                        specialPrice,
                    })
                }}
                    className="text-lg font-semibold text-gray-700 cursor-pointer" >
                    {truncateText(productName,40)}
                </h2>
                <div className="min-h-20 max-h-20">
                    <p className="text-gray-500 text-sm">{truncateText(description)}</p>
                </div>

                <div className="flex justify-between">
                    {specialPrice ? (
                            <div className="flex flex-col">
                                <span className="text-gray-500 text-sm line-through"> ${Number(price).toFixed(2)}</span>

                                <span className="text-gray-700 font-semibold text-xl"> ${Number(specialPrice).toFixed(2)}</span>
                            </div>
                        )
                        : (
                            <div>
                                <span className="text-gray-700 text-xl font-semibold"> ${Number(price).toFixed(2)}</span>
                            </div>
                        )}
                    <button

                        disabled={!isAvailable || btnLoader}
                        onClick={() => addToCartHandler({
                            productImage,
                            productName,
                            description,
                            specialPrice,
                            price,
                            productId,
                            quantity,
                        })}

                        className={`bg-blue-500 ${isAvailable ? "opacity-100 hover:bg-blue-700" : "opacity-70"} flex justify-center items-center transition-colors duration-300 w-40 text-white py-1 px-3 rounded-lg font-semibold`} >

                        <FaShoppingCart className="mr-2" />
                        {isAvailable ? ( "Add to cart" ) : ("Stock Out")}
                    </button>

                </div>



            </div>
            <ProductViewModal open={open} setOpen={setOpen} isAvailable={isAvailable} product={selectedViewProduct}/>

        </div>

    )
}
export default ProductCard;