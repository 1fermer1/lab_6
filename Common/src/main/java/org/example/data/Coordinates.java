package org.example.data;

public class Coordinates {
    private long x;
    private Integer y; // Значение поля должно быть больше -807, Поле не может быть null



    public Coordinates(long x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coordinates coordinates = (Coordinates) obj;
        return coordinates.x == this.x && coordinates.y.equals(this.y);
    }
    @Override
    public int hashCode() {
        return y.hashCode() + (int) x;
    }
    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}
