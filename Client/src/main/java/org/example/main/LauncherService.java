package org.example.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.commandManager.CommandManager;
import org.example.commandManager.IExecutable;
import org.example.jsonLogic.ZonedDateTimeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

public class LauncherService {
    private final static UserInputService userInputService = new UserInputService();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();
    private BufferedReader bufferedReader;
    private LinkedHashMap<String, IExecutable> commandManager = new CommandManager().getCommandManager();
    private int counter = 0;

    public void init() {
        userInputService.setMode(Mode.DEFAULT);
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello! And welcome to the Los Pollos Hermanos family! My name is Gustavo, but you can call me Gus...");
        launcher();
    }

    public void launcher() {
        while (true) {
            counter = 0;
            defaultCommand();
        }
    }

    public void defaultCommand() {
        String command;
        try {
            command = bufferedReader.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        try {
            String[] splitCommand = command.trim().split(" ");
            if (splitCommand.length == 1) {
                try {
                    commandManager.get(splitCommand[0]).execute();
                } catch (Exception ex) {
                    System.out.println("Введена некорректная команда, либо указаны неправильные аргументы. Вы можете ознакомиться со списком существующих команд, используя команду \"help\"");
                }
            } else {

            }
        } catch (NullPointerException ex) {
            System.out.println("Ctrl + D detected");
            System.exit(0);
        }
    }

    public void fileCommand() {
        //TODO: rekursia
    }
}
