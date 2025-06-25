const Products = () => {
    const isLoading = false;
    const errorMsg = "something went wrong";
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
                        <p>Products</p>
                    </div>
                </div>
            )

            }
        </div>
    )
}
export default Products;