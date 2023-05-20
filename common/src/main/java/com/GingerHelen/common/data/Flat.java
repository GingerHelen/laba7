package com.GingerHelen.common.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class Flat implements Comparable<Flat>, Serializable {
    private Long id;

    private String name;

    private final Coordinates coordinates;

    private final LocalDateTime creationDate;

    private final Long area;

    private final long numberOfRooms;

    private final Furnish furnish;

    private final View view;

    private final Transport transport;

    private final House house;
    private final int hashcode;
    private final String owner;

    public Flat(String name, Coordinates coordinates, Long area, long numberOfRooms,
                Furnish furnish, View view, Transport transport, House house, String owner) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
        this.creationDate = LocalDateTime.now();

        hashcode = Objects.hash(name, coordinates, house, transport, furnish);
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", name=" + name +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", numberOfRooms=" + numberOfRooms +
                ", furnish=" + furnish +
                ", view=" + view +
                ", transport=" + transport +
                ", house=" + house + "}";
    }

    @Override
    public int hashCode() {
        return hashcode;
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
