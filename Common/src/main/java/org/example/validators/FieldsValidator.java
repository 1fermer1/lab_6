package org.example.validators;

import org.example.data.Coordinates;
import org.example.data.Location;

public class FieldsValidator {
    public static boolean checkName(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean checkCoordinates(Coordinates value) {
        return value != null && checkCoordinateX(value.getX()) && checkCoordinateY(value.getY());
    }

    public static boolean checkCoordinateX(String value) {
        try {
            long temp = Long.parseLong(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    } public static boolean checkCoordinateX(Long value) {
        return true;
    }

    public static boolean checkCoordinateY(String value) {
        try {
            int temp = Integer.parseInt(value);
            return temp > -807;
        } catch (Exception ex) {
            return false;
        }
    } public static boolean checkCoordinateY(Integer value) {
        return value > -807;
    }

    public static boolean checkLocation(Location value) {
        return (value == null) || (checkLocationX(value.getX()) && checkLocationY(value.getY()) && checkLocationName(value.getName()));
    }

    public static boolean checkLocationX(String value) {
        try {
            double temp = Double.parseDouble(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    } public static boolean checkLocationX(Double value) {
        return true;
    }

    public static boolean checkLocationY(String value) {
        try {
            double temp = Double.parseDouble(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    } public static boolean checkLocationY(Double value) {
        return true;
    }

    public static boolean checkLocationName(String value) {
        return !value.isEmpty();
    }

    public static boolean checkDistance(String value) {
        try {
            int temp = Integer.parseInt(value);
            return temp > 1;
        } catch (Exception ex) {
            return false;
        }
    } public static boolean checkDistance(Integer value) {
        return value != null && value > 1;
    }
}
