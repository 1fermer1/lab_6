package org.example.commandManager;

import org.example.messages.MessageFromClient;

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
        String result = "help: вывести справку по доступным командам\n" +
                "info : вывести в стандартыный поток вывода информацию о коллекции (тип, дата инициализации, колличество элементов и т.д.)\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}: добавить новый жлемент в коллекцию\n" +
                "update id {element}: обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id: удалить элемент из коллекции по его id\n" +
                "clear: очистить коллекцию\n" +
                "execute_script file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме\n" +
                "exit: завершить программу (без сохранения в файл)\n" +
                "insert_at index {element}: добавить новый элемент в заданную позицию\n" +
                "reorder: отсортировать коллекцию в порядке, обратном нынешнему\n" +
                "history: вывести последние 5 команд (без их аргументов)\n" +
                "count_less_than_distance distance: вывести количество элементов, значение поля distance которых меньше заданного\n" +
                "print_ascending: вывести элементы коллекции в порядке возрвстания\n" +
                "print_descending: вывести элементы коллекции в порядке убывания";
        System.out.println(result);
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient info() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient show() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient add() {
        //TODO: add НЕ делает объект, а получает его из
        // переорпделенного на 2 случая жизни команд хелпера
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient update() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient remove_by_id() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient clear() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient execute_script() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient exit() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient insert_at() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient reorder() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient history() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient count_less_than_distance() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient print_ascending() {
        return new MessageFromClient(null, null, null);
    }

    private MessageFromClient print_descending() {
        return new MessageFromClient(null, null, null);
    }
}
