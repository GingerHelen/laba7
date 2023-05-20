package com.GingerHelen.common.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Setter
@Getter
public class Coordinates implements Serializable {
    private final int x;
    private final long y;
    public Coordinates(int x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x
                + ", y=" + y + "}";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates that = (Coordinates) o;
        return Double.compare(that.x, x) == 0 && (y == that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
