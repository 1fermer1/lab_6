package org.example.commandManager;

import org.example.messages.MessageFromClient;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CommandConsoleManager {
    private final LinkedHashMap<String, IExecutable> commandManager;
    private BufferedReader reader;

    public CommandConsoleManager() {
        commandManager = new LinkedHashMap<>();

        commandManager.put("help", this::help);
        commandManager.put("info", this::info);
        commandManager.put("show", this::show);
        commandManager.put("add", this::add);
        commandManager.put("update", this::update);

        commandManager.put("remove_by_id", this::remove_by_id);
        commandManager.put("clear", this::clear);
        commandManager.put("execute_script", this::execute_script);
        commandManager.put("exit", this::exit);
        commandManager.put("insert_at", this::insert_at);

        commandManager.put("reorder", this::reorder);
        commandManager.put("history", this::history);
        commandManager.put("count_less_than_distance", this::count_less_than_distance);
        commandManager.put("print_ascending", this::print_ascending);
        commandManager.put("print_descending", this::print_descending);
    }

    public LinkedHashMap<String, IExecutable> getCommandManager() {
        return commandManager;
    }

    private MessageFromClient help() {
        return new MessageFromClient("help", null, null);
    }

    private MessageFromClient info() {
        return new MessageFromClient("info", null, null);
    }

    private MessageFromClient show() {
        return new MessageFromClient("show", null, null);
    }

    private MessageFromClient add() {
        return new MessageFromClient("add", null, CommandHelper.makeRoute());
    }

    private MessageFromClient update() {
        return new MessageFromClient("update", CommandHelper.getArgument(), CommandHelper.makeRoute());
    }

    private MessageFromClient remove_by_id() {
        return new MessageFromClient("remove_by_id", CommandHelper.getArgument(), null);
    }

    private MessageFromClient clear() {
        return new MessageFromClient("clear", null, null);
    }

    private MessageFromClient execute_script() {
        ArrayList<String> script = new ArrayList<String>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(CommandHelper.getArgument()))));
            String commandLine;
            while ((commandLine = reader.readLine()) != null) {
                script.add(commandLine);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        CommandFileManager commandFileManager = new CommandFileManager();
        commandFileManager.setScript(script);
        commandFileManager.setIndex(0);
        commandFileManager.launcher();
        commandFileManager.reloadRecursionCounter();
        return new MessageFromClient("execute_script", null, null);
    }

    private MessageFromClient exit() {
        System.exit(0);
        return new MessageFromClient("exit", null, null);
    }

    private MessageFromClient insert_at() {
        return new MessageFromClient("insert_at", CommandHelper.getArgument(), CommandHelper.makeRoute());
    }

    private MessageFromClient reorder() {
        return new MessageFromClient("reorder", null, null);
    }

    private MessageFromClient history() {
        return new MessageFromClient("history", null, null);
    }

    private MessageFromClient count_less_than_distance() {
        return new MessageFromClient("count_less_than_distance", CommandHelper.getArgument(), null);
    }

    private MessageFromClient print_ascending() {
        return new MessageFromClient("print_ascending", null, null);
    }

    private MessageFromClient print_descending() {
        return new MessageFromClient("print_descending", null, null);
    }
}
