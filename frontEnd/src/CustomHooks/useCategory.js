import {useEffect} from "react";
import {fetchCategories} from "../Store/Actions/fetchCategories.js";
import {useDispatch} from "react-redux";


const useCategory = () => {

    const dispatch = useDispatch();
    useEffect(() => {

        dispatch(fetchCategories());

    }, []);
}
export default useCategory;