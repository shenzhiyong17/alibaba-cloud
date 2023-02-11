package szy.cloud.common.serialize;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.util.CollectionUtils;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

public class GsonUtil{

    private static JsonUtil jsonUtil = JsonUtil.getDefaultInstance();

    public static JsonElement toJsonElement(Object o){
        JsonElement element;
        if (o instanceof String){
            element = new JsonPrimitive((String) o);
        }else if (o instanceof Number){
            element = new JsonPrimitive((Number) o);
        }else if (o instanceof Boolean){
            element = new JsonPrimitive((Boolean) o);
        }else if (o instanceof Character){
            element = new JsonPrimitive((Character) o);
        }else if (o instanceof JsonObject){
            element = (JsonObject) o;
        }else if (o instanceof JsonArray) {
            element = (JsonArray) o;
        }else if (o instanceof Collection){
            element = GsonUtil.toJsonArray((Collection) o);
        }else {
            String string = GsonUtil.toString(o);
            element = GsonUtil.toJsonObject(string);
        }
        return element;
    }

    public static JsonObject toJsonObject(String data) {
        return jsonUtil.fromJson(data, JsonObject.class);
    }

    public static JsonArray toJsonArray(Collection list) {
        JsonArray jsonArray = new JsonArray();
        if (CollectionUtils.isEmpty(list)) {
            return jsonArray;
        }
        for (Iterator iterator = list.iterator(); iterator.hasNext();){
            Object item = iterator.next();
            jsonArray.add(toJsonElement(item));
        }
        return jsonArray;
    }

    public static String toString(Object data) {
        return jsonUtil.toJson(data);
    }

    public static <T> T toBean(String data, Class<T> clazz) {
        return jsonUtil.fromJson(data, clazz);
    }

    public static <T> T toBean(Reader reader, Class<T> valueType) {
        return jsonUtil.fromJson(reader, valueType);
    }

    public static <T> T toBean(String content, Type valueTypeRef) {
        return jsonUtil.fromJson(content, valueTypeRef);
    }

    public static String jsonResponse(boolean success, Object data) {
        JsonObject json = new JsonObject();
        json.addProperty("success", success);
        if (!success) {
            json.addProperty("desc", "系统异常");
        }
        if (data == null) {
            return json.toString();
        }
        if (data instanceof JsonElement) {
            json.add("data", (JsonElement) data);
        } else {
            json.addProperty("data", data.toString());
        }
        return json.toString();
    }

    private static Comparator<String> getComparator() {
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
    }

    /**
     * JSON.toJSONString(message, SerializerFeature.MapSortField);
     */
    public static void sort(JsonElement e) {
        if (e.isJsonNull()) {
            return;
        }

        if (e.isJsonPrimitive()) {
            return;
        }

        if (e.isJsonArray()) {
            JsonArray a = e.getAsJsonArray();
            for (Iterator<JsonElement> it = a.iterator(); it.hasNext(); ) {
                sort(it.next());
            }
            return;
        }

        if (e.isJsonObject()) {
            Map<String, JsonElement> tm = new TreeMap<>(getComparator());
            for (Map.Entry<String, JsonElement> en : e.getAsJsonObject().entrySet()) {
                tm.put(en.getKey(), en.getValue());
            }

            for (Map.Entry<String, JsonElement> en : tm.entrySet()) {
                e.getAsJsonObject().remove(en.getKey());
                e.getAsJsonObject().add(en.getKey(), en.getValue());
                sort(en.getValue());
            }
            return;
        }
    }
}