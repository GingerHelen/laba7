package com.GingerHelen.client.utility;

import com.GingerHelen.client.exceptions.IllegalValueException;
import com.GingerHelen.client.exceptions.InvalidInputException;
import com.GingerHelen.common.data.Furnish;
import com.GingerHelen.common.data.Transport;
import com.GingerHelen.common.data.View;

import java.io.IOException;

/**
 * класс с методами для чтения полей объекта типа Flat
 */
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
        switch (inputManager.read().trim()) {
            case "1":
               return View.STREET;
            case "2":
                return View.PARK;
            case "3":
                return View.BAD;
            case "4":
                return View.NORMAL;
            case "5":
                return View.TERRIBLE;
            default:
                throw new IllegalArgumentException("Enter a number from list");
        }
    }

    public Transport readTransport() throws IllegalArgumentException, InvalidInputException, IOException {
        switch (inputManager.read().trim()) {
            case "1":
                return Transport.FEW;
            case "2":
                return Transport.NONE;
            case "3":
                return Transport.LITTLE;
            case "4":
                return Transport.NORMAL;
            case "5":
                return Transport.ENOUGH;
            default:
                return null;
        }

    }


    public Furnish readFurnish() throws IllegalArgumentException, InvalidInputException, IOException {

        switch (inputManager.read().trim()) {
            case "1":
                return Furnish.NONE;
            case "2":
                return Furnish.FINE;
            case "3":
                return Furnish.LITTLE;
            default:
                return null;
        }

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