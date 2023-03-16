package com.GingerHelen.data;

public class House implements Comparable<House> {
    private String name; //Поле не может быть null
    private Long year; //Поле может быть null, Значение поля должно быть больше 0
    private Integer numberOfFloors; //Значение поля должно быть больше 0
    private long numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    private Long numberOfLifts; //Значение поля должно быть больше 0

    public House(String name, Long year, Integer numberOfFloors, long numberOfFlatsOnFloor, Long numberOfLifts) {
        this.name = name;
        this.year = year;
        this.numberOfFloors = numberOfFloors;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
        this.numberOfLifts = numberOfLifts;
    }

    public House() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public void setNumberOfFlatsOnFloor(long numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public Long getNumberOfLifts() {
        return numberOfLifts;
    }

    public void setNumberOfLifts(Long numberOfLifts) {
        this.numberOfLifts = numberOfLifts;
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