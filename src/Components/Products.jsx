import ProductCard from "./ProductCard.jsx";

const Products = () => {
    const isLoading = false;
    const errorMsg = "";
    const products = [
        {
            "productId": 1,
            "productName": "Product_8224",
            "productImage": "https://placehold.co/600x400",
            "description": "Description explaining the name Product_3427",
            "quantity": 49,
            "price": 484,
            "discount": 44,
            "specialPrice": 348,
            "categoryId": 2,
            "seller": "someone.."
        },
        {
            "productId": 2,
            "productName": "Product_2065",
            "productImage": "https://placehold.co/600x400",
            "description": "Description explaining the name Product_5916",
            "quantity": 56,
            "price": 917,
            "discount": 45,
            "specialPrice": 104,
            "categoryId": 1,
            "seller": "someone.."
        },
        {
            "productId": 3,
            "productName": "Product_9916",
            "productImage": "https://placehold.co/600x400",
            "description": "Description explaining the name Product_9885",
            "quantity": 42,
            "price": 995,
            "discount": 10,
            "specialPrice": 71,
            "categoryId": 2,
            "seller": "someone.."
        },
        {
            "productId": 4,
            "productName": "Product_6521",
            "productImage": "https://placehold.co/600x400",
            "description": "Description explaining the name Product_1926",
            "quantity": 0,
            "price": 411,
            "discount": 25,
            "specialPrice": 9,
            "categoryId": 2,
            "seller": "someone.."
        }
    ]
    return (
        <div className="lg:px - 14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
            {isLoading ? (
                <p>Loading...</p>
            ): errorMsg ? (
                <div>
                    <p>something went wrong</p>
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