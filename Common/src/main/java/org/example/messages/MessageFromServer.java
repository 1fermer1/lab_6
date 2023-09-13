package org.example.messages;

import org.example.data.Route;

public class MessageFromServer {
    private String result;
    private Integer[] idsCollection;

    public MessageFromServer(String result, Integer[] idsCollection) {
        this.result = result;
        this.idsCollection = idsCollection;
    }

    public String getResult() {
        return result;
    }
    public Integer[] getIdsCollection() {
        return idsCollection;
    }
}
