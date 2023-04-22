package com.GingerHelen.utility;

import com.GingerHelen.data.*;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.exceptions.ScriptException;

import java.io.IOException;

/**
 * класс, запрашивающий у пользователя значения полей для объекта класса Flat до тех пор, пока
 * они не будут соответствовать требованиям из т/з (для консоли), или единожды (для скрипта)
 */
public class FlatFillerMain {
    private final FlatReader reader;
    private final CollectionManager collectionManager;
    private final FlatFiller flatFiller;

    public FlatFillerMain(FlatReader reader, InputManager inputManager,
                             OutputManager outputManager, CollectionManager collectionManager) {
        this.reader = reader;
        this.collectionManager = collectionManager;
        flatFiller = new FlatFiller(outputManager, inputManager);
    }

    public String fillName() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter name", reader::readNotEmptyString);
    }


    public int fillX() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter x coordinate", reader::readX);
    }


    public long fillY() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter y coordinate", reader::readY);
    }


    public Coordinates fillCoordinates() throws ScriptException, InvalidInputException, IOException {
        return new Coordinates(this.fillX(), this.fillY());
    }


    public Long fillArea() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter area", reader::readArea);
    }


    public long fillNumberOfRooms() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter number of rooms", reader::readGreaterNullLong);
    }


    public Furnish fillFurnish() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Chose the furnish type. Type NONE, FINE, LITTLE", reader::readFurnish);
    }


    public View fillView() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Chose the view. Type STREET, PARK, BAD, NORMAL, TERRIBLE", reader::readView);
    }


    public Transport fillTransport() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Chose the transport. Type FEW, NONE, LITTLE, NORMAL, ENOUGH", reader::readTransport);
    }


    public String fillHouseName() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter name of house", reader::readNotNullString);
    }

    public Long fillHouseYear() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter year, when house was build", reader::readHouseYear);
    }
    public Integer fillHouseNumberOfFloors() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter number of floors in house", reader::readGreaterNullInteger);
    }
    public long fillHouseNumberFlatsOnFloor() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter number flats on floor", reader::readGreaterNullLong);
    }
    public Long fillHouseNumberOfLifts() throws ScriptException, InvalidInputException, IOException {
        return flatFiller.fill("Enter number of lifts", reader::readGreaterNullLong);
    }
    public House fillHouse() throws ScriptException, InvalidInputException, IOException {
        return new House(this.fillHouseName(), this.fillHouseYear(), this.fillHouseNumberOfFloors(),
                this.fillHouseNumberFlatsOnFloor(), this.fillHouseNumberOfLifts());
    }


    public Flat fillFlat() throws ScriptException, InvalidInputException, IOException {
        return new Flat(this.fillName(), this.fillCoordinates(), this.fillArea(), this.fillNumberOfRooms(), this.fillFurnish(),
                this.fillView(), this.fillTransport(), this.fillHouse(), collectionManager);
    }
}
