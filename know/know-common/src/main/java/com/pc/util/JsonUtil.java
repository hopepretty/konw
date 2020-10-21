package com.pc.util;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * json工具类
 *
 * @author pc
 *
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = null;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * obj转String
     *
     * @param object
     *            object
     * @return String
     */
    public static String objectToString(Object object) {
        if (object == null) {
            return null;
        }
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * String转obj
     *
     * @param <T>
     *            obj类型
     * @param json
     *            jsonstring
     * @param clazz
     *            obj的class
     * @return
     */
    public static <T> T stringToObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * String转obj
     *
     * @param json
     * @param reference
     *            返回值类型 ，例如 new TypeReference&lt;Map&lt;String, User&gt;&gt;(){}
     * @return
     */
    @Deprecated
    public static <T> T stringToObject(String json, TypeReference<T> reference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        T t = null;
        try {
            t = objectMapper.readValue(json, reference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * String转List<T>
     *
     * @param <T>
     *            obj类型
     * @param json
     *            jsonstring
     * @param clazz
     *            需要映射的class
     * @return
     */
    public static <T> List<T> stringToList(String json, Class<T>... clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        List<T> list = null;
        try {
            list = objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获得json中一个key的值
     *
     * @param json
     * @param key
     * @return
     */
    public static String getValue(String json, String key) {
        if (StringUtils.isEmpty(json) && StringUtils.isNotEmpty(key)) {
            return null;
        }
        String value = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            jsonNode = jsonNode.findPath(key);
            if (jsonNode != null) {
                value = jsonNode.asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
