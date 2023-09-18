package org.example.commandManager;

import org.example.data.Route;

public class CommandHelper {
    private static String[] history = new String[5];
    private static Route route;
    private static String argument;

    public void setHistory(String command){
        for (int i = history.length - 1; i > 0; i--) {
            history[i] = history[i - 1];
        }
        history[0] = command;
    }

    public String getHistory() {
        String result = "> " + history[0];
        for (int i = 1; i < history.length; i++) {
            if (history[i] == null) {
                return result;
            }
            result += "\n> " + history[i];
        }
        return result;
    }

    public void setArguments(Route route, String argument) {
        CommandHelper.route = route;
        CommandHelper.argument = argument;
    }


    //TODO: add НЕ делает объект, а получает его из
    // переорпделенного на 3 случая жизни команд хелпера
    // И ТАМ ЖЕ ПОЛУЧАЕТ ID
    public Route makeRoute(){
        return null;
    }
}
