package cn.waggag.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description: jackson-databind的Json工具类
 * @author: waggag
 * @time: 2019/8/30 0:55
 * @Company http://www.waggag.cn
 */
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     *  将对象序列化为字符串
     * @param object  需要序列化的对象
     * @return 对象序列化后的字符串
     */
    @Nullable
    public static String serialize(Object object) {
        if (object == null) {
            return null;
        }
        if (object.getClass() == String.class) {
            return (String) object;
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            logger.error("json序列化出错：" + object, exception);
            return null;
        }
    }

    /**
     * 将JSON字符串解析为指定的类
     * @param json JSON字符串
     * @param tClass 要解析的类
     * @param <T> 指定需要的类型
     * @return 返回解析出的具体的类
     */
    @Nullable
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException exception) {
            logger.error("json解析出错：" + json, exception);
            return null;
        }
    }

    /**
     * 将JSON字符串解析为指定的类的List
     * @param json JSON字符串
     * @param eClass 要解析的类型
     * @param <E> 指定需要的类型
     * @return
     */
    @Nullable
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException exception) {
            logger.error("json解析出错：" + json, exception);
            return null;
        }
    }

    /**
     * 将JSON字符串解析为指定的类的Map
     * @param json JSON字符串
     * @param kClass 指定Map键的类型
     * @param vClass  指定Map值的类型
     * @param <K>  键的泛型
     * @param <V>  值的泛型
     * @return 返回解析出的Map集合
     */
    @Nullable
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException exception) {
            logger.error("json解析出错：" + json, exception);
            return null;
        }
    }

    /**
     * 解析复杂的对象
     * @param json 要解析的字符串
     * @param type 生成的结果类型
     * @param <T> 指定解析的类型
     * @return 返回需要解析的类型
     */
    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException exception) {
            logger.error("json解析出错：" + json, exception);
            return null;
        }
    }

}
