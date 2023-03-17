package algonquin.cst2335.soma0036;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class ChatMessage {
//    protected ArrayList<ChatMessage> messages;
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name="message")
    protected String message;
    @ColumnInfo(name="timeSent")
    protected String timeSent;
    @ColumnInfo(name="isSentButton")
    protected boolean isSentButton;

    public ChatMessage() {}
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

