package com.GingerHelen.utility;


import com.GingerHelen.exceptions.IllegalValueException;
import com.GingerHelen.exceptions.InvalidInputException;

import java.io.IOException;

/**
 * функциональный интерфейс, представляющий метод считывания данных типа T
 * @param <T> тип считываемых данных
 */
@FunctionalInterface
public interface Reader<T> {
    T read() throws IllegalValueException, IllegalArgumentException, InvalidInputException, IOException;
}
