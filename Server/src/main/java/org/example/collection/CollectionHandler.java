package org.example.collection;

import org.example.data.Route;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class CollectionHandler {
    private final ZonedDateTime collectionCreationDate = ZonedDateTime.now();
    private static ArrayList<Route> collection = new ArrayList<>();

    public ZonedDateTime getCollectionCreationDate() {
        return collectionCreationDate;
    }
    public ArrayList<Route> getCollection() {
        return collection;
    }
    public void setCollection(ArrayList<Route> collection) {
        CollectionHandler.collection = collection;
    }

    public void add(Route route) {
        collection.add(route);
    }
    public void update(int index, Route route) {
        collection.set(index, route);
    }
    public void insertAt(int index, Route route) {
        collection.add(index, route);
    }
    public void remove(Route route) {
        collection.remove(route);
    }

    public ArrayList<Integer> getIdsArray() {
        ArrayList<Integer> idsArray = new ArrayList<>();
        for (Route route : collection) {
            idsArray.add(route.getId());
        }
        return idsArray;
    }
}
