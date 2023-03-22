package algonquin.cst2335.soma0036;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2335.soma0036.ChatMessage;
import algonquin.cst2335.soma0036.ChatMessageDAO;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {


    public static final String  DB_NAME = "Messages";

    public abstract ChatMessageDAO chatMessageDAO();

    private static MessageDatabase mInstance;
    public static synchronized MessageDatabase getInstance(Context ctx) {
        if(mInstance == null) {
            mInstance = Room.databaseBuilder(ctx.getApplicationContext(),
                            MessageDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }
}
