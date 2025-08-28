import {useEffect, useState} from "react";
import {FiArrowDown, FiArrowUp, FiRefreshCw, FiSearch} from "react-icons/fi";
import {Button, FormControl, InputLabel, MenuItem, Select, Tooltip} from "@mui/material";
import {useLocation, useNavigate, useSearchParams} from "react-router";




const Filter = ({categories}) => {



    const [category,setCategory] = useState("all");
    const [search, setSearch] = useState("");
    const [sortOrder, setSortOrder] = useState("");


    const [searchParams] = useSearchParams();
    const params = new URLSearchParams(searchParams);
    const pathname = useLocation().pathname;
    const navigate = useNavigate();


    useEffect(() => {
        const currCategory = searchParams.get("category") || "all" ;
        setCategory(currCategory);

        const currSortOrder = searchParams.get("sortOrder") || "asc";
        setSortOrder(currSortOrder);
        const currSearch = searchParams.get("keyword") || "" ;
        setSearch(currSearch);

    //     to store the state from  copy pasted links
    },[searchParams])

    const handleCategoryChange = (e) => {
        const selectedCategory = e.target.value;
        if (selectedCategory === "all") {
            params.delete("category");

        }else{
            params.set("category", selectedCategory);

        }
        params.set("pageNumber","1");
        navigate(`${pathname}?${params}`);

        setCategory(e.target.value);

    }

    const toggleSortOrder = () =>{

        const newSortOrder = sortOrder ===  "asc" ? "desc" : "asc";
        setSortOrder(newSortOrder);
        params.set("sortOrder", newSortOrder);
        navigate(`${pathname}?${params}`);
    }

    const handleClearFilter = () => {
        navigate({pathname: window.location.pathname});
    }

    const handleSearch = (value) => {
        params.set("keyword", value);
        params.set("pageNumber","1");
        navigate(`${pathname}?${params}`);
    }



    return (
        <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4 ">
            <div className="relative flex items-center 2xl:w-[450px] sm:w-[420px] w-full">
                <input
                    type="text"
                    placeholder="Search Products"
                    value={search}
                    onChange={(e) => handleSearch(e.target.value)}
                    className="border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-4 w-full focus:outline-hidden focus:ring-2 focus:ring-[#1976d2]"/>
                <FiSearch className="absolute left-3 text-slate-800 size={20}"/>
            </div>

            {/* CATEGORY SELECTION */}
            <div className="flex sm:flex-row flex-col gap-4 items-center">
                <FormControl
                    className="text-slate-800 border-slate-700"
                    variant="outlined"
                    size="small">
                    <InputLabel id="category-select-label">Category</InputLabel>
                    <Select
                        labelId="category-select-label"
                        value={category}
                        onChange={handleCategoryChange}
                        label="Category"
                        className="min-w-[120px] text-slate-800 border-slate-700"
                        // variant={}
                        >
                        <MenuItem value="all">All</MenuItem>

                        {categories.map((category) => (
                            <MenuItem key={category.categoryId} value={category.categoryName}>
                                {category.categoryName}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                {/*SORT AND RESET*/}
                <Tooltip title={`Sorted By Price in ${sortOrder}`}>
                    <Button variant="contained" color="primary"
                            className="flex items-center gap-2 h-10 "
                    onClick={toggleSortOrder}
                    >
                        Sort By
                        {
                            (sortOrder === "asc") ? <FiArrowUp size={20}/> : <FiArrowDown size={20}/>
                        }

                    </Button>
                </Tooltip>

                <button
                    className="flex items-center gap-2 bg-rose-900 text-white px-3 py-2 rounded-md transition duration-300 ease-in shadow-md focus:outline-hidden"
                    onClick={() => handleClearFilter()}
                >
                    <FiRefreshCw className="font-semibold" size={16}/>
                    <span className="font-semibold">Clear Filter</span>
                </button>

            </div>

        </div>
    )
}
export default Filter;