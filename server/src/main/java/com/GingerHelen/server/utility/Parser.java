package com.GingerHelen.server.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.GingerHelen.common.data.Flat;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.TreeMap;

public final class Parser {
    /**
     * перевод из строки json в java-объект
     * @param strData преобразование данных в формат String
     * @return переданная коллекция в формате java-объекта (TreeMap)
     * @throws JsonSyntaxException в случае ошибок синтаксиса json строки
     */
    public static TreeMap<Long, Flat> deSerialize(String strData) throws JsonSyntaxException {
        if (strData.trim().isEmpty()) {
            return new TreeMap<>();
        }
        Gson g = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
        Type type = new TypeToken<TreeMap<Long, Flat>>() {
        }.getType();
        return g.fromJson(strData, type);
    }

    /**
     * перевод из java-объекта в строку json
     * @param collectionData коллекция, которая преобразовывается в json формат
     * @return переданная коллекция в формате String в виде json
     */
    public static String serialize(TreeMap<Long, Flat> collectionData) {
        Gson g = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).
                setPrettyPrinting().create();
        return g.toJson(collectionData);
    }
}
