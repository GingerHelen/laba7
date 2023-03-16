package com.GingerHelen.utility;


import com.GingerHelen.data.Transport;
import com.GingerHelen.data.View;
import com.GingerHelen.data.Furnish;
import com.GingerHelen.exceptions.IllegalValueException;
import com.GingerHelen.exceptions.InvalidInputException;

import java.io.IOException;
import java.util.Locale;

public class FlatReader {
    private static final long MIN_Y = -808;
    private final InputManager inputManager;

    public FlatReader(InputManager inputManager) {
        this.inputManager = inputManager;
    }
    public int readX() throws NumberFormatException, InvalidInputException, IOException {
        int x;
        String stringX;
        stringX = inputManager.read();
        x = Integer.parseInt(stringX);
        return x;
    }
    public long readY() throws IllegalValueException, NumberFormatException, InvalidInputException, IOException {
        long y;
        String stringY;
        stringY = inputManager.read();
        y = Long.parseLong(stringY);
        if (y <= MIN_Y) {
            throw new IllegalValueException("Value must be greater than " + MIN_Y);
        }
        return y;
    }

      public View readView() throws IllegalArgumentException, InvalidInputException, IOException {
        View view;
        view = View.valueOf(inputManager.read().toUpperCase(Locale.ROOT));
        return view;
    }

    public Transport readTransport() throws IllegalArgumentException, InvalidInputException, IOException {
        Transport transport = null;
        String stringTransport = inputManager.read();
        if (!stringTransport.isEmpty()) {
            transport = Transport.valueOf(stringTransport.toUpperCase(Locale.ROOT));
        }
        return transport;
    }


    public Furnish readFurnish() throws IllegalArgumentException, InvalidInputException, IOException {
        Furnish furnish = null;
        String stringFurnish = inputManager.read();
        if (!stringFurnish.isEmpty()) {
            furnish = Furnish.valueOf(stringFurnish.toUpperCase(Locale.ROOT));
        }
        return furnish;
    }

    public Long readArea() throws IllegalValueException, NumberFormatException, InvalidInputException, IOException {
        Long area = null;
        String stringArea = inputManager.read();
        if (!stringArea.isEmpty()) {
            long areaNotNull = Long.parseLong(stringArea);
            if (areaNotNull <= 0) {
                throw new IllegalValueException("Value must be greater than 0");
            }
            return areaNotNull;
        } else return area;
    }

    public Long readHouseYear() throws IllegalValueException, NumberFormatException, InvalidInputException, IOException {
        Long houseYear = null;
        String stringHouseYear = inputManager.read();
        if (!stringHouseYear.isEmpty()) {
            long houseYearNotNull = Long.parseLong(stringHouseYear);
            if (houseYearNotNull <= 0) {
                throw new IllegalValueException("Value must be greater than 0");
            }
            return houseYearNotNull;
        } else return houseYear;
    }

    public String readNotNullString() throws IllegalValueException, InvalidInputException, IOException {
        String string;
        string = inputManager.read();
        if (string.isEmpty()) {
            throw new IllegalValueException("This field cannot be null");
        }
        return string;
    }

    public String readNotEmptyString() throws IllegalValueException, InvalidInputException, IOException {
        String string;
        string = readNotNullString();
        if (string.trim().isEmpty()) {
            throw new IllegalValueException("This field cannot be empty");
        }
        return string;
    }
    public long readGreaterNullLong() throws IllegalValueException, NumberFormatException, InvalidInputException, IOException {
        String stringGreaterNull;
        stringGreaterNull = inputManager.read();
        long GreaterNull = Long.parseLong(stringGreaterNull);
        if (GreaterNull <= 0) {
            throw new IllegalValueException("Value must be greater than 0");
        }
        return GreaterNull;
    }
    public Integer readGreaterNullInteger() throws IllegalValueException, NumberFormatException, InvalidInputException, IOException {
        String stringGreaterNull;
        stringGreaterNull = inputManager.read();
        int GreaterNull = Integer.parseInt(stringGreaterNull);
        if (GreaterNull <= 0) {
            throw new IllegalValueException("Value must be greater than 0");
        }
        return GreaterNull;
    }
}