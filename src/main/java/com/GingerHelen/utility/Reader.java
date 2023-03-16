package com.GingerHelen.utility;


import com.GingerHelen.exceptions.IllegalValueException;
import com.GingerHelen.exceptions.InvalidInputException;

import java.io.IOException;

@FunctionalInterface
public interface Reader<T> {
    T read() throws IllegalValueException, IllegalArgumentException, InvalidInputException, IOException;
}
