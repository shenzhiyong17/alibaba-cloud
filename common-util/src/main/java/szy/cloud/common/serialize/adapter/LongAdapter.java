package szy.cloud.common.serialize.adapter;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

@Slf4j
public class LongAdapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
    private final String nullString = "null";

    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String stringValue = json.getAsString();
            if (StringUtils.isBlank(stringValue) || stringValue.trim().equalsIgnoreCase(nullString)) {//定义为long类型,如果后台返回""或者null,则返回0
                return 0L;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsLong();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(String.valueOf(src));
    }
}
