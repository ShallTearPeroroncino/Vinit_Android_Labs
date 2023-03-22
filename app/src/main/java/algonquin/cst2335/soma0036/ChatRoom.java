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
    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatViewModel chatModel;
    ChatMessage chat = new ChatMessage("", "", false);
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
    String currentDateandTime = sdf.format(new Date());
    private RecyclerView.Adapter myAdapter;

    public void addContent(Boolean isTrue ){
        currentDateandTime = sdf.format(new Date());
        chat = new ChatMessage(binding.edittxt.getText().toString(), currentDateandTime, isTrue);
        messages.add(chat);
        myAdapter.notifyItemInserted(messages.size() - 1);
        binding.edittxt.setText("");
    };

    private void insertMessage(ChatMessage newMessage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDao.insertMessage(newMessage);
            }
        }).start();
    }

    private void loadMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ChatMessage> loadedMessages = (ArrayList<ChatMessage>) mDao.getAllMessages();
                messages.addAll( mDao.getAllMessages() );
                chatModel.messages.postValue(loadedMessages);
                myAdapter.notifyDataSetChanged();
            }
        }).start();
    }

    private void deleteMessages(ChatMessage messageToDelete){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDao.deleteMessage(messageToDelete);
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MessageDatabase db = MessageDatabase.getInstance(this);
        mDao = db.chatMessageDAO();
        super.onCreate(savedInstanceState);

        chatModel = new ViewModelProvider(this).get(ChatViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null){
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.send.setOnClickListener(click -> {
         addContent(true);
        });

        binding.receive.setOnClickListener(click -> {
           addContent(false);
        });
        loadMessages();
//        chatModel = new ViewModelProvider(this).get(ChatViewModel.class);
//        messages = chatModel.messages.getValue();
//        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
//        mDao = db.cmDAO();
//
//        setContentView(binding.getRoot());
//        if (messages == null) {
//            chatModel.messages.setValue(messages = new ArrayList<ChatMessage>());
//            Executor thread = Executors.newSingleThreadExecutor();
//            thread.execute(() ->
//            {
//                mDao.insertMessage(chat);
//            });
//        }

//        ChatViewModel cvm = new ViewModelProvider(this).get(ChatViewModel.class);
//
//        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));



//        binding.receive.setOnClickListener(click -> {
//            chat = new ChatMessage(binding.edittxt.getText().toString(), currentDateandTime, false);
//            messages.add(chat);
//            myAdapter.notifyItemInserted(messages.size() - 1);
//            binding.edittxt.setText("");
//            Executor thread = Executors.newSingleThreadExecutor();
//            thread.execute(() ->
//            {
//                mDao.insertMessage(chat);
//            });
//        });

        binding.RecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
//                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    SentMessageBinding sendBinding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(sendBinding.getRoot());

                } else {
//                    ReceiveMessageBinding receive_binding = ReceiveMessageBinding.inflate(getLayoutInflater(),parent,true);
//                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    ReceiveMessageBinding receiveBinding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(receiveBinding.getRoot());
//                    View root = receive_binding.getRoot();
//                    return new MyRowHolder(root);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText("");
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(currentDateandTime);
//                ChatMessage chatmessge = messages.get(position);
//                holder.messageText.setText(chatmessge.getMessage());
//                holder.timeText.setText(currentDateandTime);
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
//                chat.messages.get(position);
                ChatMessage chatmessage = messages.get(position);
                if (chatmessage.isSentButton) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        binding.RecyclerView.setAdapter(myAdapter);
        loadMessages();
        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

   class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
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


