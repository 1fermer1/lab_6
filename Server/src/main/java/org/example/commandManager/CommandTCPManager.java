package org.example.commandManager;

import org.example.collection.CollectionHandler;
import org.example.data.Route;
import org.example.jsonLogic.Parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class CommandTCPManager {
    private LinkedHashMap<String, IExecutable> commandManager;
    private CommandHelper commandHelper = new CommandHelper();
    private CollectionHandler collectionHandler = new CollectionHandler();
    private Parser parser = new Parser();
    private UserInputService userInputService = new UserInputService();

    public CommandTCPManager() {
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

    public LinkedHashMap<String, IExecutable> getCommandManager() {
        return commandManager;
    }

    private String help() {
        String result = "help: вывести справку по доступным командам\n" +
                "info : вывести в стандартыный поток вывода информацию о коллекции (тип, дата инициализации, колличество элементов и т.п.)\n" +
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
        return result;
    }

    private String info() {
        String result = "";
        ArrayList<Route> r = collectionHandler.getCollection();
        result += collectionHandler.getCollection().getClass() + "\n" +
                collectionHandler.getCollectionCreationDate() + "\n" +
                collectionHandler.getCollectionSize();
        return result;
    }

    private String show() {
        String result = "";
        for (Route route : collectionHandler.getCollection()) {
            result += route + "\n";
        }
        return result;
    }

    private String add() {

        // TODO: incorrect!!! where is ID?!


        return null;
    } private String add(ArrayList<String> script, int index) {
        // TODO: if-else on null and this method replace to file commandFileMeneger
        //CollectionHandler.add((Route) (CommandHelper.makeRoute(script, index))[0]);
        return null;
    }


    private String update() {
        // TODO: execute
        return null;
    }

    private String remove_by_id() {
        // TODO: execute
        return null;
    }

    private String clear() {
        collectionHandler.setCollection(new ArrayList<Route>());
        return null;
    }

    private String save() {
        parser.writeFile(collectionHandler.getCollection(), "config.env");
        return null;
    }

    private String execute_script() {
        // TODO: execute
        return null;
    }

    private String exit() {
        save();
        System.exit(0);
        return null;
    }

    private String insert_at() {
        // TODO: execute
        return null;
    }

    private String reorder() {
        // TODO: execute
        return null;
    }

    private String history() {
        return commandHelper.getHistory();
    }

    private String count_less_than_distance() {
        // TODO: execute
        return "";
    }

    private String print_ascending() {
        // TODO: execute
        return "";
    }

    private String print_descending() {
        // TODO: execute
        return "";
    }
}
