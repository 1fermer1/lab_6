package org.example.jsonLogic;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.collection.CollectionHandler;
import org.example.data.Route;
import org.example.validators.FullRouteValidator;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class Parser {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();
    private final FullRouteValidator fullRouteValidator = new FullRouteValidator();
    private final CollectionHandler collectionHandler = new CollectionHandler();

    public ArrayList<Route> readFile(String configName) {
        ArrayList<Route> routesList = new ArrayList<>();

        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(configName)) {
            properties.load(inputStream);
        } catch (Exception ex) {
            System.out.println("Config file read error");
            throw new RuntimeException(ex);
        }

        String fileName = properties.getProperty("FILE_NAME");
        try (JsonReader reader = new JsonReader(new FileReader(fileName))) {
            reader.beginArray();
            while (reader.hasNext()) {
                JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
                Route route = gson.fromJson(obj, Route.class);
                if (fullRouteValidator.checkRoute(route)) {
                    collectionHandler.add(route);
                }
            }
            reader.endArray();
        } catch (IOException ex) {
            System.out.println("Import error");
            throw new RuntimeException(ex);
        }

        System.out.println("Import successful");
        return routesList;
    }

    public void writeFile(ArrayList<Route> routesList, String configName) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(configName)) {
            properties.load(inputStream);
        } catch (IOException ex) {
            System.out.println("Config file read error");
            throw new RuntimeException(ex);
        }

        String fileName = properties.getProperty("FILE_NAME");
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            JsonWriter writer = gson.newJsonWriter(out);
            writer.setIndent("\t");
            writer.setSerializeNulls(true);
            writer.beginArray();
            for (Route route : routesList) {
                gson.toJson(route, Route.class, writer);
            }
        } catch (IOException ex) {
            System.out.println("Export error");
            throw new RuntimeException(ex);
        }

        System.out.println("Export successful");
    }
}
