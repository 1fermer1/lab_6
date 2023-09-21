package org.example.jsonLogic;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.collection.CollectionHandler;
import org.example.data.Route;
import org.example.validators.RouteValidator;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class Parser {
    private final static Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();

    public static void readFile(String configName) {
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
                if (RouteValidator.checkRoute(route)) {
                    CollectionHandler.add(route);
                }
            }
            reader.endArray();
        } catch (IOException ex) {
            System.out.println("Import error");
            throw new RuntimeException(ex);
        }

        System.out.println("Import successful");
    }

    public static void writeFile(ArrayList<Route> routesList, String configName) {
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
            writer.endArray();
        } catch (IOException ex) {
            System.out.println("Export error");
            throw new RuntimeException(ex);
        }

        System.out.println("Export successful");
    }
}
