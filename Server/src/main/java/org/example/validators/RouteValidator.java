package org.example.validators;

import org.example.collection.CollectionHandler;
import org.example.data.Route;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class RouteValidator {

    public static boolean checkRoute(Route route) {
        return checkId(route.getId()) &&
                FieldsValidator.checkName(route.getName()) &&
                FieldsValidator.checkCoordinates(route.getCoordinates()) &&
                checkCreationDate(route.getCreationDate()) &&
                FieldsValidator.checkLocation(route.getFrom()) &&
                FieldsValidator.checkLocation(route.getTo()) &&
                FieldsValidator.checkDistance(route.getDistance());
    }

    public static boolean checkId(Integer value) {
        if (value < 1) {
            return false;
        }
        ArrayList<Integer> idsCollection = CollectionHandler.getIdsArray();
        return !idsCollection.contains(value);
    }

    public static boolean checkCreationDate(ZonedDateTime value) {
        return value != null && value.compareTo(ZonedDateTime.now()) < 1;
    }
}
