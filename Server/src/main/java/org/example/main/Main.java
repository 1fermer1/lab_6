package org.example.main;

import org.example.collection.CollectionHandler;
import org.example.jsonLogic.Parser;

public class Main {
    public static void main(String[] args) {
        //TODO: как сделать докер файл
        // и как им пользоваться

        //TODO: большие месседжи

        Parser.readFile("config.env");

        LauncherService launcherService = new LauncherService();
        launcherService.init();
    }
}