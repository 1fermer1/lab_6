package org.example.data;

public class Location {
    // TODO: name condition
    private String name; // Строка не может быть путсой, Поле может быть null
    private Double x; // Поле не может быть null
    private Double y; // Поле не может быть null



    public Location(String name, Double x, Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }
    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Location location = (Location) obj;
        return location.name.equals(this.name) && location.x.equals(this.x) && location.y.equals(this.y);
    }
    @Override
    public int hashCode() {
        return name.hashCode() + x.hashCode() + y.hashCode();
    }
    @Override
    public String toString() {
        return name + " (" + x + "; " + y + ")";
    }
}
