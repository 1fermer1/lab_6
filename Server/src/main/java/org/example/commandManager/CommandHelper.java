package org.example.commandManager;

import org.example.data.Route;

public class CommandHelper {
    private String[] history = new String[5];

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



    public Route makeRoute(){
        return new Route();
    }
}
