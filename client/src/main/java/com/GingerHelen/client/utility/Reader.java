package com.GingerHelen.client.utility;

import com.GingerHelen.client.exceptions.IllegalValueException;
import com.GingerHelen.client.exceptions.InvalidInputException;

import java.io.IOException;

/**
 * функциональный интерфейс, представляющий метод считывания данных типа T
 * @param <T> тип считываемых данных
 */
@FunctionalInterface
public interface Reader<T> {
    T read() throws IllegalValueException, IllegalArgumentException, InvalidInputException, IOException;
}
