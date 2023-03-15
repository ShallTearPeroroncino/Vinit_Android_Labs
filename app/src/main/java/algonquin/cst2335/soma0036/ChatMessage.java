package algonquin.cst2335.soma0036;

import java.util.ArrayList;

public class ChatMessage {
    ArrayList<ChatMessage> messages;
    public String message;
    String timeSent;
    boolean isSentButton;

    public ChatMessage(String m, String t, boolean sent) {
        this.message = m;
        this.timeSent = t;
        this.isSentButton = sent;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }
}

