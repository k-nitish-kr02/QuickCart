import {Button, Dialog, DialogBackdrop, DialogPanel, DialogTitle} from '@headlessui/react'

export default function ProductViewModal({open,setOpen,product}) {

    return (
        <>

            <Dialog open={open} as="div" className="relative z-10 focus:outline-none" onClose={()=> {setOpen(false)}}>

                <DialogBackdrop className="fixed inset-0 bg-black/60" />

                <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                    <div className="flex min-h-full items-center justify-center p-4">
                        <DialogPanel
                            transition
                            className="w-full max-w-md rounded-xl bg-blue-100/70 p-6 backdrop-blur-2xl duration-300 ease-out data-closed:transform-[scale(95%)] data-closed:opacity-0 pointer-events-none "
                        >
                            <DialogTitle as="h3" className="text-base/7 font-semibold text-gray-900">
                                {product.productName}
                            </DialogTitle>
                            <p className="mt-2 text-sm/3 text-gray-700">
                                {product.description}
                            </p>
                            <div className="mt-4">
                                <Button
                                    className="inline-flex items-center gap-2 rounded-md bg-gray-700 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-inner shadow-white/10 focus:not-data-focus:outline-none data-focus:outline data-focus:outline-white data-hover:bg-gray-600 data-open:bg-gray-800"
                                    onClick={() => setOpen(false)}
                                >
                                    Close
                                </Button>
                            </div>
                        </DialogPanel>
                    </div>
                </div>
            </Dialog>
        </>
    )
}