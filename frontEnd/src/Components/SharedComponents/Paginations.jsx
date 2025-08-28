import {Pagination} from "@mui/material";
import {useLocation, useNavigate, useSearchParams} from "react-router";


const Paginations =({pagination}) =>{
    const [searchParams] = useSearchParams();
    const params = new URLSearchParams(searchParams);
    const pathname = useLocation().pathname;
    const navigate = useNavigate();


    const totalPages = parseInt(pagination?.totalPages) || 1;
    const pageNumber = parseInt(params.get("pageNumber") || 1);


    const handlePageChange = (e,val) =>{
        params.set("pageNumber", val.toString());
        navigate(`${pathname}?${params}`);
    }




    return (
        <>
            <Pagination 
                count={totalPages}
                page={pageNumber}
                onChange={handlePageChange}

            />
        </>
    )
}
export default Paginations;
