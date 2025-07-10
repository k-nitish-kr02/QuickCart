import { Dialog, DialogBackdrop, DialogPanel, DialogTitle} from '@headlessui/react'
import {Divider} from "@mui/material";
import Status from "./Status.jsx";
import {MdClose, MdDone} from "react-icons/md";

export default function ProductViewModal({open,setOpen,isAvailable,product}) {


	return (
	<>

		<Dialog open={open} as="div" className="relative z-10 focus:outline-none" onClose={()=> {setOpen(false)}}>

			<DialogBackdrop className="fixed inset-0 bg-black/60" />

			<div className="fixed inset-0 z-10 w-screen overflow-y-auto">
				<div className="flex min-h-full items-center justify-center p-4">
					<DialogPanel transition className="w-full max-w-md rounded-xl bg-white   overflow-hidden">
						{product.productImage && (
						<div className="flex justify-center aspect-[3/2] ">
							<img  src={product.productImage} alt="Product Image" />
						</div>
						)}

						<div className="px-6 pt-6 pb-2">
							<DialogTitle as="h1" className=" font-semibold lg:text-3xl sm:text-2xl text-xl leading-6 text-gray-700 mb-4">
							{product.productName}
							</DialogTitle>

							<div className="space-y-2 text-gray-700 pb-4">

								<div className="flex justify-between gap-2">
								{product.specialPrice ? (
									<div className="flex flex-row gap-2 items-baseline">
										<span className="text-gray-500 text-sm line-through"> ${Number(product.price).toFixed(2)}</span>

										<span className="text-gray-700 font-semibold text-xl"> ${Number(product.specialPrice).toFixed(2)}</span>
										</div>
										)
										: (
										<div>
										<span className="text-gray-700 text-xl font-semibold"> ${Number(product.price).toFixed(2)}</span>
										</div>
										)}

									{isAvailable ? (
										<Status
											text = "In-Stock"
											icon = {MdDone}
											bg ="bg-teal-200"
											color = "text-teal-900"
										/>
									):(
										<Status
											text = "Out-of-Stock"
											icon = {MdClose}
											bg ="bg-rose-200"
											color = "text-rose-700"
										/>
									)}

									</div>

								<Divider />
								<p className=" text-sm leading-6 text-gray-700 mb-4">{product.description}</p>
							</div>
						</div>

						<div className="px-6 py-4 flex justify-end gap-4">
							<button
								onClick={() => setOpen(false)}
								type="button"
								className="px-4 py-2 text-sm font-semibold text-gray-700 border border-slate-400 hover:text-gray-900 hover:border-slate-900 rounded-lg"
							>
								Close
							</button>
						</div>
					</DialogPanel>
				</div>
			</div>
		</Dialog>
	</>
)
}