package org.example.messages;

public class MessageFromServer {
    private final String answer;

    public MessageFromServer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
