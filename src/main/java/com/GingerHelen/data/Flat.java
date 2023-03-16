package com.GingerHelen.data;

import java.time.LocalDateTime;
import java.util.Objects;
import com.GingerHelen.utility.CollectionManager;


public class Flat implements Comparable<Flat> {
    private Integer id;

    private String name;

    private Coordinates coordinates;

    private LocalDateTime creationDate;

    private Long area;

    private long numberOfRooms;

    private Furnish furnish;

    private View view;

    private Transport transport;

    private House house;

    public Flat(String name, Coordinates coordinates, Long area, long numberOfRooms,
                Furnish furnish, View view, Transport transport, House house, CollectionManager collectionManager) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
        id = collectionManager.getMaxId() + 1;
        this.creationDate = LocalDateTime.now();
    }

    public Flat() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", name=" + name +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", numberOfRooms" + numberOfRooms +
                ", furnish=" + furnish +
                ", view=" + view +
                ", transport=" + transport +
                ", house=" + house + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, house, transport, furnish); //TODO
    }

    @Override
    public int compareTo(Flat o) {
        int value = Long.compare(this.numberOfRooms, o.numberOfRooms);
        if (value == 0) {
            value = this.area.compareTo(o.area);
            if (value == 0) {
                value = this.id.compareTo(o.id);
            }
        }
        return value;
    }
}
