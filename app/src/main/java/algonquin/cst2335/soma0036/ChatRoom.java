package algonquin.cst2335.soma0036;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.soma0036.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.soma0036.databinding.ReceiveMessageBinding;
//import algonquin.cst2335.soma0036.databinding.SentMessage1Binding;
import algonquin.cst2335.soma0036.databinding.SentMessageBinding;
public class ChatRoom extends AppCompatActivity {
    ChatMessageDAO mDao;
//    List<ChatMessage> allMessages = mDao.getAllMessages();
    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages;
    ChatViewModel chatModel;
    ChatMessage chat = new ChatMessage("", "", false);
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
    String currentDateandTime = sdf.format(new Date());
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        chatModel = new ViewModelProvider(this).get(ChatViewModel.class);
        messages = chatModel.messages.getValue();
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDao = db.cmDAO();

        setContentView(binding.getRoot());
        if (messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<ChatMessage>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDao.insertMessage(chat);
            });
        }

//        ChatViewModel cvm = new ViewModelProvider(this).get(ChatViewModel.class);
//
//        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.send.setOnClickListener(click -> {
            chat = new ChatMessage(binding.edittxt.getText().toString(), currentDateandTime, true);
            messages.add(chat);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.edittxt.setText("");
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDao.insertMessage(chat);
            });
        });

        binding.receive.setOnClickListener(click -> {
            chat = new ChatMessage(binding.edittxt.getText().toString(), currentDateandTime, false);
            messages.add(chat);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.edittxt.setText("");
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDao.insertMessage(chat);
            });
        });

        binding.RecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
//                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    SentMessageBinding sent_binding = SentMessageBinding.inflate(getLayoutInflater(),parent,false);
                    View root = sent_binding.getRoot();
                    return new MyRowHolder(root);

                } else {
                    ReceiveMessageBinding receive_binding = ReceiveMessageBinding.inflate(getLayoutInflater(),parent,true);
//                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    View root = receive_binding.getRoot();
                    return new MyRowHolder(root);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatmessge = messages.get(position);
                holder.messageText.setText(chatmessge.getMessage());
                holder.timeText.setText(currentDateandTime);
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
//                chat.messages.get(position);
                ChatMessage chat = messages.get(position);
                if (chat.isSentButton) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

   class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk ->  {

                int position = getAbsoluteAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setTitle("Question:")
                        .setMessage("Do you want to delete the message: " + messageText.getText())
                        .setNegativeButton("No", (dialog, cl)-> {})
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            Executor thread = Executors.newSingleThreadExecutor();
                            ChatMessage m = messages.get(position);
                            thread.execute(() -> {
                                mDao.deleteMessage(m);
                            });

                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);
                            Snackbar.make(messageText,"You deleted message #"+ position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo",click ->{
                                        messages.add(position, m);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        })
                        .create().show();
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
    }


