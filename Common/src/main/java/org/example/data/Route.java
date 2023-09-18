package org.example.data;

import java.time.ZonedDateTime;
import java.util.Random;

public class Route implements Comparable<Route> {
    private int id; // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
                    // Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; // Поле может быть null
    private Location to; // Поле может быть null
    private int distance; // Значение поля дожно быть больше 1



    public Route(String name, Coordinates coordinates, Location from, Location to, int distance) {
        id = new Random().nextInt();
        id = id < 0 ? -id : id + 1;
        this.name = name;
        this.coordinates = coordinates;
        creationDate = ZonedDateTime.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public Location getFrom() {
        return from;
    }
    public Location getTo() {
        return to;
    }
    public int getDistance() {
        return distance;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Route route = (Route) obj;
        return route.name.equals(this.name) &&
                route.coordinates.equals(this.coordinates) &&
                route.creationDate.equals(this.creationDate) &&
                route.from.equals(this.from) &&
                route.to.equals(this.to) &&
                route.distance == this.distance;
    }
    @Override
    public int hashCode() {
        return id + name.hashCode() + coordinates.hashCode() +
                creationDate.hashCode() + from.hashCode() +
                to.hashCode() + distance;
    }
    @Override
    public String toString() {
        return id + ": " + name + "\n" +
                "coordinates: " + coordinates + "\n" +
                "creationDate: " + creationDate + "\n" +
                "location from: " + from + "\n" +
                "location to: " + to + "\n" +
                "distance: " + distance;

    }
    @Override
    public int compareTo(Route route) {
        return this.name.compareTo(route.name);
    }
}
