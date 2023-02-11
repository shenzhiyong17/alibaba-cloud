package szy.cloud.common.serialize;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import org.apache.commons.lang3.StringUtils;
import szy.cloud.common.serialize.adapter.DoubleAdapter;
import szy.cloud.common.serialize.adapter.IntegerAdapter;
import szy.cloud.common.serialize.adapter.LongAdapter;

import java.io.Reader;
import java.lang.reflect.Type;

public class JsonUtil {
    private static volatile JsonUtil instance = null;
    private Gson gson;

    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gsonBuilder.enableComplexMapKeySerialization();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        gsonBuilder.serializeNulls();
        gsonBuilder.registerTypeAdapter(Long.class, new LongAdapter());
        gsonBuilder.registerTypeAdapter(long.class, new LongAdapter());
        gsonBuilder.registerTypeAdapter(Double.class, new DoubleAdapter());
        gsonBuilder.registerTypeAdapter(double.class, new DoubleAdapter());
        gsonBuilder.registerTypeAdapter(Integer.class, new IntegerAdapter());
        gsonBuilder.registerTypeAdapter(int.class, new IntegerAdapter());//不添加会出现 = 序列化为 \u003d 的问题
        gson = gsonBuilder
                .create();
    }

    private JsonUtil() {
    }

    public static JsonUtil getDefaultInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (JsonUtil.class) {
            if (instance != null) {
                return instance;
            }
            return new JsonUtil();
        }
    }

    public static JsonUtil withBuilder(GsonBuilder gsonBuilder) {
        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.gson = gsonBuilder.create();
        return jsonUtil;
    }

    public <T> T fromJson(String content, Class<T> valueType) {
        try {
            if (StringUtils.isNotEmpty(content)) {
                return gson.fromJson(content, valueType);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(Reader reader, Class<T> valueType) {
        try {
            if (reader != null) {
                return gson.fromJson(reader, valueType);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String content, Type valueTypeRef) {
        try {
            if (content != null) {
                return gson.fromJson(content, valueTypeRef);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(Reader reader, Type valueTypeRef) {
        try {
            if (reader != null) {
                return gson.fromJson(reader, valueTypeRef);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(Object value) {
        try {
            if (value != null) {
                return gson.toJson(value);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
