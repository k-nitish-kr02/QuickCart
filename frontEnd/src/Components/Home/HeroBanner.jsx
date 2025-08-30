import {Autoplay, EffectFade, Navigation,Pagination} from "swiper/modules";
import {Swiper,SwiperSlide} from "swiper/react";

import 'swiper/css';
import 'swiper/css/autoplay';
import 'swiper/css/effect-fade';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';

import {Link} from "react-router-dom";
import {banner} from "../../Utils/index.js";



const HeroBanner = () => {

    const colors = ["bg-banner-color1","bg-banner-color2","bg-banner-color3"];
    return (
        <div className="py-2 rounded-lg">
            <Swiper
                grabCursor={true}
                autoplay={{
                    delay: 4000,
                    disableOnInteraction: false,
                }}
                navigation={true}
                modules={[Pagination,EffectFade,Navigation,Autoplay]}
                pagination={{clickable:true}}
                scrollbar={{draggable:true}}
                slidesPerView={1}
            >
                {banner.map((item,index) => (
                    <SwiperSlide key={item.id}>
                        <div className={`carousel-item rounded-md sm:h-[500px] h-96 ${colors[index]} `}>
                            <div className="flex items-center justify-center ">
                                <div className="hidden lg:flex justify-center items-center w-1/2 p-8">
                                    <div className="text-center">
                                        <h3 className="text-3xl  font-bold text-white">
                                            {item.title}
                                        </h3>
                                        <h1 className="text-5xl  font-bold text-white mt-2">
                                            {item.subtitle}
                                        </h1>
                                        <p className="text-xl font-bold text-white mt-4">
                                            {item.description}
                                        </p>
                                        <Link
                                            className="mt-6 inline-block text-xl font-semibold bg-black text-white py-2 px-4 rounded-lg "
                                            to={"/products"}>
                                            Shop Now
                                        </Link>
                                    </div>
                                </div>
                                <div className="w-full flex justify-center items-center lg:w-1/2 p-4">
                                    <img className=""
                                         src={item.image}
                                    />
                                </div>
                            </div>
                        </div>
                    </SwiperSlide>
                ))}

            </Swiper>
        </div>

    )
}
export default HeroBanner;