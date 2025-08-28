package com.E_Commerce.eCom.Utils;

import java.util.function.Consumer;

public class ObjectUtils {

    public static <T> void setIfNotNull(T value , Consumer<T>setter){
        if(value!=null) setter.accept(value);
    }
}
