package algonquin.cst2335.soma0036;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.soma0036.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.soma0036.databinding.ReceiveMessageBinding;
//import algonquin.cst2335.soma0036.databinding.SentMessage1Binding;
import algonquin.cst2335.soma0036.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatViewModel chatModel;
    ChatMessage chat = new ChatMessage("","",true);
//    ChatMessage chatMessage = messages.get(position);
    private RecyclerView.Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatModel = new ViewModelProvider(this).get(ChatViewModel.class);
        messages = chatModel.messages.getValue();
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
        String currentDateandTime = sdf.format(new Date());
        ChatViewModel cvm = new ViewModelProvider(this).get(ChatViewModel.class);

        if(messages == null)
        {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.send.setOnClickListener(click ->{
            chat = new ChatMessage(binding.edittxt.getText().toString(),currentDateandTime,true);
//            String txt = binding.edittxt.getText().toString();
            messages.add(chat);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.edittxt.setText("");
        });

        binding.receive.setOnClickListener(click ->{
            ChatMessage chatMessage = new ChatMessage(binding.edittxt.getText().toString(),currentDateandTime,false);
//            String txt = binding.edittxt.getText().toString();
            messages.add(chat);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.edittxt.setText("");
        });

        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.RecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
//                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    View root = binding.getRoot();
                    return new MyRowHolder(root);

                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
//                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    View root = binding.getRoot();
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
            public int getItemViewType(int position){
                chat.messages.get(position);
                if(chat.isSentButton){
                    return 0;
                }
                else {
                    return 1;
                }
            }
        });
    }
}

class MyRowHolder extends RecyclerView.ViewHolder {
    TextView messageText;
    TextView timeText;
    public MyRowHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.message);
        timeText = itemView.findViewById(R.id.time);
    }
}