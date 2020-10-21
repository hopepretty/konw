package com.pc.bean;

import com.google.protobuf.Descriptors;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Protobuf工具类
 * @author pc
 * @Date 2020/10/12
 **/
public class ProtobufUtil {

//    public <T> T parseBytes(byte[] data, Class<T> clazz) {
//    }

    /**
     * 转化对象
     * @param fields
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyProp(Map<Descriptors.FieldDescriptor, Object> fields, Class<T> clazz) {
        try {
            final T t = clazz.newInstance();
            fields.forEach((k, v) -> {
                setFieldValue(t, k.getName(), v);
            });
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    private static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     *
     * 如向上转型到Object仍无法找到, 返回null.
     */
    private static Field getAccessibleField(final Object obj, final String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class;
             superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

}
