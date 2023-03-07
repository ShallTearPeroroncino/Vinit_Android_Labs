package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import algonquin.cst2335.soma0036.databinding.ActivityMainBinding;
import data.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
//       TextView mytext = findViewById(R.id.textview);
//       Button btn = findViewById(R.id.mybutton);
//       EditText myedit = findViewById(R.id.myedittext);

//        TextView mytext = variableBinding.textview;
//        Button btn = variableBinding.mybutton;
//        EditText myedit = variableBinding.myedittext;

//        variableBinding.textview.setText((CharSequence) model.editString);
        variableBinding.mybutton.setOnClickListener(v -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
//            variableBinding.textview.setText("Your edit text has: " + model.editString);
//           String editString = myedit.getText().toString();
//           mytext.setText( "Your edit text has: " + editString);
           // Code here executes on main thread after user presses button
       });
        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has "+ s);
        });

        model.isSelected.observe(this, selected ->{
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);

            Context context = getApplicationContext();
            CharSequence text = "The value is now:" + selected;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        });

       variableBinding.checkBox.setOnCheckedChangeListener( (btn, isSelected) -> {
           model.isSelected.postValue(isSelected);
       });

        variableBinding.switch1.setOnCheckedChangeListener( (btn, isSelected) -> {
            model.isSelected.postValue(isSelected);
        });

        variableBinding.radioButton.setOnCheckedChangeListener( (btn, isSelected) -> {
            model.isSelected.postValue(isSelected);
        });

        variableBinding.myimagebutton.setOnClickListener(click->{
            Context context = getApplicationContext();
            int width= variableBinding.myimagebutton.getWidth();
            int height= variableBinding.myimagebutton.getHeight();
            CharSequence text = "The width = " + width + " and height = " + height;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

//        variableBinding.actionImage.setOnClickListener();
    }
}