package com.sera.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 基于Jackson的Json操作工具类
 *
 * @author wangqing
 * @since 14-4-16 下午1:42
 */
public class JacksonUtils {
    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);
    private static ObjectMapper mapper;

    public JacksonUtils() {
        this(null);
    }

    public JacksonUtils(Include include) {
        mapper = new ObjectMapper();
        if (include != null) {
            //设置输出时包含属性的风格
            mapper.setSerializationInclusion(include);
        }
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //设置默认DateFormat yyyy-MM-dd HH:mm:ss
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用。
     *
     * @return 只输出非Null且非Empty的属性到Json字符串的Mapper
     */
    public static JacksonUtils nonEmptyMapper() {
        return new JacksonUtils(Include.NON_EMPTY);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     *
     * @return 只输出初始值被改变的属性到Json字符串的Mapper
     */
    public static JacksonUtils nonDefaultMapper() {
        return new JacksonUtils(Include.NON_DEFAULT);
    }

    /**
     * 创建默认的Mapper，所有属性都会被输出。
     *
     * @return 输出所有属性到Json字符串的Mapper
     */
    public static JacksonUtils defaultMapper() {
        return new JacksonUtils();
    }


    /**
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     *
     * @param object
     */
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p/>
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * <p/>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
     *
     * @see #fromJson(String, com.fasterxml.jackson.databind.JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函数createCollectionType构造类型,然后调用本函数.
     *
     * @see #createCollectionType(Class, Class...)
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 构造泛型的Collection Type如:
     * ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     */
    public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 当JSON里只有Bean的部分属性时，更新一个已存在的Bean，只覆盖该部分的值。
     */
    public <T> T update(String jsonString, T object) {
        try {
            return (T) mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            log.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            log.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
        return null;
    }

    /**
     * 输出JSONP格式Text，格式为functionName(json)。
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 设定是否使用Enum的toString函数来读写Enum,
     * 为False时，使用Enum的name()函数来读写Enum, 默认为False.
     * 注意本函数一定要在Mapper创建后, 所有的读写动作之前调用.
     */
    public void enableEnumUseToString() {
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    /**
     * 设定格式化Date类型数据格式
     * 在所有读写动作之前调用
     */
    public void setDateFormat(String dateFormat) {
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 判断是否是Json字符串
     *
     * @param jsonStr
     * @return
     */
    public boolean parseJson(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return false;
        }
        try {
            mapper.readValue(jsonStr, Object.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
