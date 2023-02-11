package szy.cloud.common.serialize.adapter;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Type;

@Slf4j
public class IntegerAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {

    private final String nullString = "null";

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String stringValue = json.getAsString();
            if (StringUtils.isBlank(stringValue) || stringValue.trim().equalsIgnoreCase(nullString)) {//定义为int类型,如果后台返回""或者null,则返回0
                return 0;
            }
        } catch (Exception e) {
            log.error("check illegal int value failed {}", ExceptionUtils.getStackTrace(e));
        }
        try {
            return json.getAsInt();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
