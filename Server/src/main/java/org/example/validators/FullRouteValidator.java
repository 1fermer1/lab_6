package org.example.validators;

import org.example.collection.CollectionHandler;
import org.example.data.Route;

import java.util.ArrayList;

public class FullRouteValidator {
    private final CollectionHandler collectionHandler = new CollectionHandler();
    private final RouteFieldsValidator routeFieldsValidator = new RouteFieldsValidator();

    public boolean checkRoute(Route route) {
        return checkId(route.getId()) &&
                routeFieldsValidator.name(route.getName()) &&
                routeFieldsValidator.coordinates(route.getCoordinates()) &&
                routeFieldsValidator.creationDate(route.getCreationDate()) &&
                routeFieldsValidator.location(route.getFrom()) &&
                routeFieldsValidator.location(route.getTo()) &&
                routeFieldsValidator.distance(route.getDistance());
    }

    public boolean checkId(String value) {
        try {
            int temp = Integer.parseInt(value);
            if (temp < 1) {
                return false;
            }
            ArrayList<Integer> idsCollection = collectionHandler.getIdsArray();
            return !idsCollection.contains(temp);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean checkId(Integer value) {
        if (value < 1) {
            return false;
        }
        ArrayList<Integer> idsCollection = collectionHandler.getIdsArray();
        return !idsCollection.contains(value);
    }
}
