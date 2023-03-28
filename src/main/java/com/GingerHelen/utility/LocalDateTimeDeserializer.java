package com.GingerHelen.utility;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * TypeAdapter для объектов типа LocalDateTime. Из json-строки следующего типа будет получен объект класса LocalDateTime
 *       { "year": 2023,
 *       "month": 3,
 *       "day": 16,
 *       "hour": 11,
 *       "minute": 30
 *       }
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson g = new Gson();
        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        Map<String, Integer> map = g.fromJson(jsonElement.toString(), type);
        return java.time.LocalDateTime.of(map.get("year"), map.get("month"), map.get("day"), map.get("hour"), map.get("minute"));
    }
}
