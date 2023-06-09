package com.GingerHelen.server.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * TypeAdapter для объектов класса LocalDateTime. Данные будут представлены в следующем виде:
 *
 *       { "year": 2023,
 *       "month": 3,
 *       "day": 16,
 *       "hour": 11,
 *       "minute": 30
 *       }
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject o = new JsonObject();

        o.addProperty("year", localDateTime.getYear());
        o.addProperty("month", localDateTime.getMonthValue());
        o.addProperty("day", localDateTime.getDayOfMonth());
        o.addProperty("hour", localDateTime.getHour());
        o.addProperty("minute", localDateTime.getMinute());
        return o;
    }
}
