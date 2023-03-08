package algonquin.cst2335.soma0036;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.soma0036.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.soma0036.databinding.SentMessage1Binding;
import algonquin.cst2335.soma0036.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<String> messages;
    private RecyclerView.Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ChatViewModel cvm = new ViewModelProvider(this).get(ChatViewModel.class);
        messages = cvm.messages;

        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.send.setOnClickListener(click ->{
            String txt = binding.edittxt.getText().toString();
            messages.add(txt);
            myAdapter.notifyItemInserted(messages.size()-1);
//            myAdapter.notifyItemRemoved(messages.size()-1);
            binding.edittxt.setText("");
//            myAdapter.notifyDataSetChanged();
        });

        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.RecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    View root = binding.getRoot();
                    return new MyRowHolder(root);

                } else {
                    SentMessage1Binding binding = SentMessage1Binding.inflate(getLayoutInflater(), parent, false);
                    View root = binding.getRoot();
                    return new MyRowHolder(root);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                String messageOnthisRow = messages.get(position);
                holder.messageText.setText(messageOnthisRow);
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                String currentDateandTime = sdf.format(new Date());
                holder.timeText.setText(currentDateandTime);
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
                return position % 2;
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