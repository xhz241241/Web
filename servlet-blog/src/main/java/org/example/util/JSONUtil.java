package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JSONUtil {
    public static ObjectMapper mapper = new ObjectMapper();
    //序列化java对象为接送字符串
    public static String serialize(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }
    //反序列化接送字符串为java对象
    public static <T>  T deserialize(InputStream is, Class<T> c) throws IOException {
        return mapper.readValue(is, c);
    }
}
