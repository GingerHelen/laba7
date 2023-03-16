package com.GingerHelen.utility;

import com.google.gson.Gson;
import com.GingerHelen.data.*;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.TreeMap;

public final class Parser {
    public static TreeMap<Long, Flat> deSerialize(String strData) throws JsonSyntaxException, IllegalArgumentException {
        if (strData.trim().isEmpty()) {
            return new TreeMap<>();
        }
        Gson g = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
        Type type = new TypeToken<TreeMap<Long, Flat>>() {
        }.getType();
        return g.fromJson(strData, type);
    }
    public static String serialize(TreeMap<Long, Flat> collectionData) {
        Gson g = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).
                setPrettyPrinting().create();
        return g.toJson(collectionData);
    }
}
