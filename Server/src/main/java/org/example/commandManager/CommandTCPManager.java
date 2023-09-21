package org.example.commandManager;

import org.example.collection.CollectionHandler;
import org.example.comparators.AscendingComparator;
import org.example.comparators.DescendingComparator;
import org.example.data.Route;
import org.example.jsonLogic.Parser;
import org.example.messages.MessageFromServer;
import org.example.validators.RouteValidator;

import java.util.*;
import java.util.stream.Collectors;

public class CommandTCPManager {
    private LinkedHashMap<String, IExecutable> commandManager;

    public CommandTCPManager() {
        commandManager = new LinkedHashMap<>();

        commandManager.put("help", this::help);
        commandManager.put("info", this::info);
        commandManager.put("show", this::show);
        commandManager.put("add", this::add);

        commandManager.put("update", this::update);
        commandManager.put("remove_by_id", this::remove_by_id);
        commandManager.put("clear", this::clear);

        commandManager.put("execute_script", this::execute_script);
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

    private MessageFromServer help() {
        return new MessageFromServer("help: вывести справку по доступным командам\n" +
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
                "print_descending: вывести элементы коллекции в порядке убывания");
    }

    private MessageFromServer info() {
        return new MessageFromServer(CollectionHandler.getCollection().getClass() + "\n" +
                CollectionHandler.getCollectionCreationDate() + "\n" +
                CollectionHandler.getCollectionSize());
    }

    private MessageFromServer show() {

        String result = "";

        List<Route> routes = CollectionHandler.getCollection().stream()
                .sorted(Route::compareTo)
                .collect(Collectors.toList());
        for (Route route : routes) {
            result += route + "\n\n";
        }
        result = result.substring(0, result.length() - 2);
        return new MessageFromServer(result);
    }

    private MessageFromServer add() {
        CollectionHandler.add(CommandHelper.getRoute());
        return new MessageFromServer(null);
    }

    private MessageFromServer update() {
        try {
            int id = Integer.parseInt(CommandHelper.getArgument());
            Route route = CommandHelper.getRoute();
            route.setId(id);
            if (CollectionHandler.getIdsArray().contains(id)) {
                CollectionHandler.update(CollectionHandler.getIdsArray().indexOf(id), route);
                return new MessageFromServer(null);
            } else if (id < 1) {
                return new MessageFromServer("Incorrect id");
            }
            return new MessageFromServer("Collection don't contains object with this id");
        } catch (Exception ex) {
            return new MessageFromServer("Incorrect id");
        }
    }

    private MessageFromServer remove_by_id() {
        try {
            int id = Integer.parseInt(CommandHelper.getArgument());
            if (CollectionHandler.getIdsArray().contains(id)) {
                CollectionHandler.remove(CollectionHandler.getCollection().get(CollectionHandler.getIdsArray().indexOf(id)));
                return new MessageFromServer(null);
            } else if (id < 1) {
                return new MessageFromServer("Incorrect id");
            }
            return new MessageFromServer("Collection don't contains object with this id");
        } catch (Exception ex) {
            return new MessageFromServer("Incorrect id");
        }
    }

    private MessageFromServer clear() {
        CollectionHandler.setCollection(new ArrayList<Route>());
        return new MessageFromServer(null);
    }

    private MessageFromServer execute_script() {
        return new MessageFromServer(null);
    }

    private MessageFromServer insert_at() {
        try {
            int index = Integer.parseInt(CommandHelper.getArgument());
            if (index > -1 && index < CollectionHandler.getCollectionSize() + 1) {
                CollectionHandler.insertAt(index, CommandHelper.getRoute());
                return new MessageFromServer(null);
            }
            return new MessageFromServer("Incorrect index");
        } catch (Exception ex) {
            return new MessageFromServer("Incorrect argument");
        }
    }

    private MessageFromServer reorder() {
//        ArrayList<Route> reorderCollection = new ArrayList<Route>();
        List<Route> reorder = CollectionHandler.getCollection().stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
//        for (Route route : CollectionHandler.getCollection()) {
//            reorderCollection.add(0, route);
//        }
        ArrayList<Route> reorderCollection = new ArrayList<>(reorder);
        CollectionHandler.setCollection(reorderCollection);
        return new MessageFromServer(null);
    }

    private MessageFromServer history() {
        return new MessageFromServer(CommandHelper.getHistory());
    }

    private MessageFromServer count_less_than_distance() {
        try {
            int distance = Integer.parseInt(CommandHelper.getArgument());
            int counter = 0;
            for (Route route : CollectionHandler.getCollection()) {
                if (route.getDistance() < distance) {
                    counter++;
                }
            }
            return new MessageFromServer(Integer.toString(counter));
        } catch (Exception ex) {
            return new MessageFromServer("Incorrect argument");
        }
    }

    private MessageFromServer print_ascending() {
        if (CollectionHandler.getCollectionSize() == 0) {
            return new MessageFromServer(null);
        }
        TreeSet<Route> sortedCollection = new TreeSet<>(new AscendingComparator());
        sortedCollection.addAll(CollectionHandler.getCollection());
        String answer = "" + sortedCollection.pollFirst();
        Route route;
        while ((route = sortedCollection.pollFirst()) != null) {
            answer += "\n" + route;
        }
        return new MessageFromServer(answer);
    }

    private MessageFromServer print_descending() {
        if (CollectionHandler.getCollectionSize() == 0) {
            return new MessageFromServer(null);
        }
        TreeSet<Route> sortedCollection = new TreeSet<>(new DescendingComparator());
        sortedCollection.addAll(CollectionHandler.getCollection());
        String answer = "" + sortedCollection.pollFirst();
        Route route;
        while ((route = sortedCollection.pollFirst()) != null) {
            answer += "\n" + route;
        }
        return new MessageFromServer(answer);
    }
}
