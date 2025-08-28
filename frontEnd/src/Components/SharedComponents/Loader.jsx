import {Hourglass} from "react-loader-spinner";

export const Loader = () =>{
    return(

        <div className="flex justify-center items-center w-full h-[450px]">
            <div className="flex flex-col items-center gap-2  "></div>
            <Hourglass
                visible={true}
                height="80"
                width="80"
                ariaLabel="hourglass-loading"
                wrapperStyle={{}}
                wrapperClass=""
                colors={['#306cce', '#72a1ed']}
            />
        </div>
    );
}