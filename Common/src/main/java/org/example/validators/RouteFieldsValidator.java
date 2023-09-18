package org.example.validators;

import org.example.data.Coordinates;
import org.example.data.Location;

import java.time.ZonedDateTime;

public class RouteFieldsValidator {
    public boolean name(String value) {
        return value != null && !"".equals(value);
    }

    public boolean coordinates(Coordinates value) {
        return value != null && coordinateX(value.getX()) && coordinateY(value.getY());
    }

    public boolean coordinateX(String value) {
        try {
            long temp = Long.parseLong(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    } public boolean coordinateX(Long value) {
        return true;
    }

    public boolean coordinateY(String value) {
        try {
            int temp = Integer.parseInt(value);
            return temp > -807;
        } catch (Exception ex) {
            return false;
        }
    } public boolean coordinateY(Integer value) {
        return value > -807;
    }

    public boolean creationDate(String value) {
        try {
            ZonedDateTime temp = ZonedDateTime.parse(value);
            return temp.compareTo(ZonedDateTime.now()) < 1;
        } catch (Exception ex) {
            return false;
        }
    } public boolean creationDate(ZonedDateTime value) {
        return value != null && value.compareTo(ZonedDateTime.now()) < 1;
    }

    public boolean location(Location value) {
        return (value == null) || (locationX(value.getX()) && locationY(value.getY()) && locationName(value.getName()));
    }

    public boolean locationX(String value) {
        try {
            double temp = Double.parseDouble(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    } public boolean locationX(Double value) {
        return true;
    }

    public boolean locationY(String value) {
        try {
            double temp = Double.parseDouble(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    } public boolean locationY(Double value) {
        return true;
    }

    public boolean locationName(String value) {
        return !"".equals(value);
    }

    public boolean distance(String value) {
        try {
            int temp = Integer.parseInt(value);
            return temp > 1;
        } catch (Exception ex) {
            return false;
        }
    } public boolean distance(Integer value) {
        return value != null && value > 1;
    }
}
