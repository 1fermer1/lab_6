package org.example.data;

public class Location {
    private Double x; // Поле не может быть null
    private Double y; // Поле не может быть null
    private String name; // Строка не может быть путсой, Поле может быть null



    public Location(String name, Double x, Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
    public String getName() {
        return name;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Location location = (Location) obj;
        return location.x.equals(this.x) && location.y.equals(this.y) && location.name.equals(this.name);
    }
    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode() + name.hashCode();
    }
    @Override
    public String toString() {
        return name + " (" + x + "; " + y + ")";
    }
}
