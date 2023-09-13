package org.example.commandManager;

import org.example.messages.MessageFromServer;

import java.util.LinkedHashMap;

public class CommandManager {
    private LinkedHashMap<String, IExecutable> commandManager;

    public CommandManager() {
        commandManager = new LinkedHashMap<>();

        commandManager.put("help", this::help);
        commandManager.put("info", this::info);
        commandManager.put("show", this::show);
        commandManager.put("add", this::add);

        commandManager.put("update", this::update);
        commandManager.put("remove_by_id", this::remove_by_id);
        commandManager.put("clear", this::clear);
        commandManager.put("save", this::save);

        commandManager.put("execute_script", this::execute_script);
        commandManager.put("exit", this::exit);
        commandManager.put("insert_at", this::insert_at);
        commandManager.put("reorder", this::reorder);

        commandManager.put("history", this::history);
        commandManager.put("count_less_than_distance", this::count_less_than_distance);
        commandManager.put("print_ascending", this::print_ascending);
        commandManager.put("print_descending", this::print_descending);
    }

    private MessageFromServer help() {
        String result = "help: вывести справку по доступным командам\n" +
                        "info : вывести в стандартыный поток вывода информацию о коллекции (тип, дата инициализации, колличество элементов и т.д.)\n" +
                        "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                        "add {element}: добавить новый жлемент в коллекцию\n" +
                        "update id {element}: обновить значение элемента коллекции, id которого равен заданному\n" +
                        "remove_by_id id: удалить элемент из коллекции по его id\n" +
                        "clear: очистить коллекцию\n" +
                        "save: сохранить коллекцию в файл\n" +
                        "execute_script file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме\n" +
                        "exit: завершить программу (без сохранения в файл)\n" +
                        "insert_at index {element}: добавить новый элемент в заданную позицию\n" +
                        "reorder: отсортировать коллекцию в порядке, обратном нынешнему\n" +
                        "history: вывести последние 5 команд (без их аргументов)\n" +
                        "count_less_than_distance distance: вывести количество элементов, значение поля distance которых меньше заданного\n" +
                        "print_ascending: вывести элементы коллекции в порядке возрвстания\n" +
                        "print_descending: вывести элементы коллекции в порядке убывания";
        System.out.println(result);
        return new MessageFromServer(null, null);
    }

    private MessageFromServer info() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer show() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer add() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer update() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer remove_by_id() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer clear() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer save() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer execute_script() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer exit() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer insert_at() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer reorder() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer history() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer count_less_than_distance() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer print_ascending() {
        return new MessageFromServer(null, null);
    }

    private MessageFromServer print_descending() {
        return new MessageFromServer(null, null);
    }
}
