package org.example.collection;

import org.example.data.Route;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class CollectionHandler {
    private final static ZonedDateTime collectionCreationDate = ZonedDateTime.now();
    private static ArrayList<Route> collection = new ArrayList<Route>();

    public static ZonedDateTime getCollectionCreationDate() {
        return CollectionHandler.collectionCreationDate;
    }
    public static ArrayList<Route> getCollection() {
        return CollectionHandler.collection;
    }
    public static void setCollection(ArrayList<Route> collection) {
        CollectionHandler.collection = collection;
    }
    public static int getCollectionSize() {
        return CollectionHandler.collection.size();
    }

    public static void add(Route route) {
        CollectionHandler.collection.add(route);
    }
    public static void update(int index, Route route) {
        CollectionHandler.collection.set(index, route);
    }
    public static void insertAt(int index, Route route) {
        CollectionHandler.collection.add(index, route);
    }
    public static void remove(Route route) {
        CollectionHandler.collection.remove(route);
    }

    public static ArrayList<Integer> getIdsArray() {
        ArrayList<Integer> idsArray = new ArrayList<>();
        for (Route route : CollectionHandler.collection) {
            idsArray.add(route.getId());
        }
        return idsArray;
    }
}
