package com.E_Commerce.eCom.Configurations;

import java.util.function.Consumer;

public class ObjectUtils {

    public static <T> void setIfNotNull(T value , Consumer<T>setter){
        if(value!=null) setter.accept(value);
    }
}
