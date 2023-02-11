package szy.cloud.common.serialize.adapter;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Type;

@Slf4j
public class DoubleAdapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
    private final String nullString = "null";

    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String stringValue = json.getAsString();
            if (StringUtils.isBlank(stringValue) || stringValue.trim().equalsIgnoreCase(nullString)) {//定义为double类型,如果后台返回""或者null,则返回0
                return 0.00;
            }
        } catch (Exception e) {
            log.error("check illegal double value failed {}", ExceptionUtils.getStackTrace(e));
        }
        try {
            return json.getAsDouble();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
