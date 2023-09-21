package org.example.commandManager;

import org.example.data.Route;
import org.example.main.LauncherService;
import org.example.messages.MessageFromClient;

import java.io.*;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CommandFileManager {
    private BufferedReader reader;
    private static int recursionCounter = 0;
    private final LauncherService launcherService = new LauncherService();
    private ArrayList<String> script;
    private int index;

    public void launcher() {
        while (true) {
            if (script.size() == index) {
                return;
            }
            String[] splitCommand = script.get(index++).trim().split(" ");
            if (splitCommand.length > 1) {
                CommandHelper.setArgument(splitCommand[1]);
            }
            try {
                recursionCounter++;
                String answer = launcherService.mega(commandManager.get(splitCommand[0]).execute());
                if (recursionCounter > 400) {
                    return;
                }
                System.out.print(answer == null ? "" : answer + "\n");
            } catch (NullPointerException ex) {
                System.out.println("Incorrect input. write \"help\" to see all commands and their arguments");
            } catch (ConnectException ex) {
                System.out.println("Sorry, but server is off");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private final LinkedHashMap<String, IExecutable> commandManager = new LinkedHashMap<String, IExecutable>(){
            {
                    put("help", this::help);
                    put("info", this::info);
                    put("show", this::show);
                    put("add", this::add);
                    put("update", this::update);

                    put("remove_by_id", this::remove_by_id);
                    put("clear", this::clearCollection);
                    put("execute_script", this::execute_script);
                    put("exit", this::exit);
                    put("insert_at", this::insert_at);

                    put("reorder", this::reorder);
                    put("history", this::history);
                    put("count_less_than_distance", this::count_less_than_distance);
                    put("print_ascending", this::print_ascending);
                    put("print_descending", this::print_descending);
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
            Object[] array = CommandHelper.makeRoute(script, index);
            if (array == null) {
                recursionCounter = 2000;
                return new MessageFromClient(null, null, null);
            }
            index = (Integer) array[1];
            return new MessageFromClient("add", null, (Route) array[0]);
        }

        private MessageFromClient update() {
            Object[] array = CommandHelper.makeRoute(script, index);
            if (array == null) {
                recursionCounter = 2000;
                return new MessageFromClient(null, null, null);
            }
            index = (Integer) array[1];
            return new MessageFromClient("update", CommandHelper.getArgument(), (Route) array[0]);
        }

        private MessageFromClient remove_by_id() {
            return new MessageFromClient("remove_by_id", CommandHelper.getArgument(), null);
        }

        private MessageFromClient clearCollection() {
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
            return new MessageFromClient("execute_script", null, null);
        }

        private MessageFromClient exit() {
            System.exit(0);
            return new MessageFromClient("exit", null, null);
        }

        private MessageFromClient insert_at() {
            Object[] array = CommandHelper.makeRoute(script, index);
            if (array == null) {
                recursionCounter = 2000;
                return new MessageFromClient(null, null, null);
            }
            index = (Integer) array[1];
            return new MessageFromClient("insert_at", CommandHelper.getArgument(), (Route) array[0]);
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
    };

    public void setScript(ArrayList<String> script) {
        this.script = script;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
