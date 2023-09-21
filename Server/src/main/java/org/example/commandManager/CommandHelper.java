package org.example.commandManager;

import org.example.collection.CollectionHandler;
import org.example.data.Coordinates;
import org.example.data.Location;
import org.example.data.Route;
import org.example.validators.FieldsValidator;
import org.example.validators.RouteValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class CommandHelper {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String[] history = new String[5];
    private static Route route;
    private static String argument;

    public static void setHistory(String command){
        for (int i = history.length - 1; i > 0; i--) {
            history[i] = history[i - 1];
        }
        history[0] = command;
    }
    public static String getHistory() {
        String result = "" + history[0];
        for (int i = 1; i < history.length; i++) {
            if (history[i] == null) {
                return result;
            }
            result += "\n" + history[i];
        }
        return result;
    }

    public static void setArgument(String argument) {
        CommandHelper.argument = argument;
    }
    public static void setRoute(Route route) {
        CommandHelper.route = route;
    }

    public static String getArgument() {
        return argument;
    }
    public static Route getRoute() {
        if (CollectionHandler.getIdsArray().contains(route.getId())) {
            CommandHelper.route.setId(getNewId());
        }
        return CommandHelper.route;
    }
    public static int getNewId() {
        Random random = new Random();
        int temp = random.nextInt();
        temp = temp > 0 ? temp : -temp + 1;
        while (!RouteValidator.checkId(temp)) {
            temp = random.nextInt();
            temp = temp > 0 ? temp : -temp + 1;
        }
        return temp;
    }

    public static void makeRoute() {
        String name = doField("name", FieldsValidator::checkName);
        String coordinateX = doField("coordinate X", FieldsValidator::checkCoordinateX);
        String coordinateY = doField("coordinate Y", FieldsValidator::checkCoordinateY);
        String locationFromName = doField("location from name", FieldsValidator::checkLocationName);
        String locationFromX = doField("location from X", FieldsValidator::checkLocationX);
        String locationFromY = doField("location from Y", FieldsValidator::checkLocationY);
        String locationToName = doField("location to name", FieldsValidator::checkLocationName);
        String locationToX = doField("location to X", FieldsValidator::checkLocationX);
        String locationToY = doField("location to Y", FieldsValidator::checkLocationY);
        String distance = doField("distance", FieldsValidator::checkDistance);

        CommandHelper.route = new Route(name, new Coordinates(Long.parseLong(coordinateX), Integer.parseInt(coordinateY)),
                new Location(locationFromName, Double.parseDouble(locationFromX), Double.parseDouble(locationFromY)),
                new Location(locationToName, Double.parseDouble(locationToX), Double.parseDouble(locationToY)),
                Integer.parseInt(distance));
    }  public static Integer makeRoute(ArrayList<String> script, int index) {
        try {
            if (FieldsValidator.checkName(script.get(index)) &&
                    FieldsValidator.checkCoordinateX(script.get(index + 1)) &&
                    FieldsValidator.checkCoordinateY(script.get(index + 2))) {
                if (script.get(index + 3).isEmpty()) {
                    if (script.get(index + 4).isEmpty() && FieldsValidator.checkDistance(index + 5)) {
                        CommandHelper.route = new Route(script.get(index), new Coordinates(Long.parseLong(script.get(index + 1)),
                                Integer.parseInt(script.get(index + 2))), null, null,
                                Integer.parseInt(script.get(index + 5)));
                        return index + 6;
                    } else if (FieldsValidator.checkLocationName(script.get(index + 4)) &&
                            FieldsValidator.checkLocationX(script.get(index + 5)) &&
                            FieldsValidator.checkLocationY(script.get(index + 6)) &&
                            FieldsValidator.checkDistance(script.get(index + 7))) {
                        CommandHelper.route = new Route(script.get(index), new Coordinates(Long.parseLong(script.get(index + 1)),
                                Integer.parseInt(script.get(index + 2))), null, new Location(script.get(index + 4),
                                Double.parseDouble(script.get(index + 5)), Double.parseDouble(script.get(index + 6))),
                                Integer.parseInt(script.get(index + 7)));
                        return index + 8;
                    }
                } else if (FieldsValidator.checkLocationName(script.get(index + 3)) &&
                        FieldsValidator.checkLocationX(script.get(index + 4)) &&
                        FieldsValidator.checkLocationY(script.get(index + 5)) &&
                        FieldsValidator.checkLocationName(script.get(index + 6)) &&
                        FieldsValidator.checkLocationX(script.get(index + 7)) &&
                        FieldsValidator.checkLocationY(script.get(index + 8)) &&
                        FieldsValidator.checkDistance(script.get(index + 9))) {
                CommandHelper.route = new Route(script.get(index), new Coordinates(Long.parseLong(script.get(index + 1)),
                            Integer.parseInt(script.get(index + 2))), new Location(script.get(index + 3),
                            Double.parseDouble(script.get(index + 4)), Double.parseDouble(script.get(index + 5))),
                            new Location(script.get(index + 6), Double.parseDouble(script.get(index + 7)),
                                    Double.parseDouble(script.get(index + 8))), Integer.parseInt(script.get(index + 9)));
                return index + 10;
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    private static String doField(String fieldName, Validator validator) {
        System.out.print("Enter " + fieldName + " : ");
        try {
            String temp = reader.readLine();
            while (!validator.validate(temp)) {
                System.out.println("Incorrect input");
                System.out.print("Enter " + fieldName + " : ");
                temp = reader.readLine();
            }
            return temp;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private interface Validator {
        boolean validate(String value);
    }
}
