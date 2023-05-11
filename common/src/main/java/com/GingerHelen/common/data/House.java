package com.GingerHelen.common.data;

import java.io.Serializable;

public class House implements Comparable<House>, Serializable {
    private String name; //Поле не может быть null
    private final Long year; //Поле может быть null, Значение поля должно быть больше 0
    private final Integer numberOfFloors; //Значение поля должно быть больше 0
    private final long numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    private final Long numberOfLifts; //Значение поля должно быть больше 0

    public House(String name, Long year, Integer numberOfFloors, long numberOfFlatsOnFloor, Long numberOfLifts) {
        this.name = name;
        this.year = year;
        this.numberOfFloors = numberOfFloors;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
        this.numberOfLifts = numberOfLifts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(House o) {
        int value = this.numberOfLifts.compareTo(o.numberOfLifts);
        if (value == 0) {
            value = this.numberOfFloors.compareTo(o.numberOfFloors);
            if (value == 0) {
                value = this.year.compareTo(o.year);
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", numberOfFloors=" + numberOfFloors +
                ", numberOfFlatsOnFloor=" + numberOfFlatsOnFloor +
                ", numberOfLifts=" + numberOfLifts +
                '}';
    }
}