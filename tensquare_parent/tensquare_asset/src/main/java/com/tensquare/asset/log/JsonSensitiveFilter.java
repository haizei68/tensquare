package com.tensquare.asset.log;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.tensquare.asset.validation.Sensitive;

import java.lang.reflect.Field;

public class JsonSensitiveFilter implements PropertyFilter {
    public JsonSensitiveFilter() {
    }

    public boolean apply(Object object, String name, Object value) {
        Class bizclz = object.getClass();
        Field[] bizfields = bizclz.getDeclaredFields();
        if (bizfields != null) {
            Field[] var9 = bizfields;
            int var8 = bizfields.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                Field field = var9[var7];
                if (name.equals(field.getName()) && field.isAnnotationPresent(Sensitive.class)) {
                    return false;
                }
            }
        }

        return true;
    }
}

