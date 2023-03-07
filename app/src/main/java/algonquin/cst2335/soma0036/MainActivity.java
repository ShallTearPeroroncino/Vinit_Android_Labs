package algonquin.cst2335.soma0036;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.soma0036.databinding.ActivityMainBinding;

/**
 * @author somai
 * @version 11.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * This holds the value of editText
     */
   private EditText passwordtext;
    /**
     * This holds the value of button
     */

    private Button login;

    /**
     * This holds the value of TextView
     */
    private TextView textView;

    private boolean checkPasswordComplexity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textView = findViewById(R.id.passText);
        passwordtext = findViewById(R.id.editPass);
        login = findViewById(R.id.login);

        login.setOnClickListener(click -> {
            String password = passwordtext.getText().toString();
           checkPasswordComplexity(password);

           if(checkPasswordComplexity(password)){
               textView.setText("Your password meets the requirements");
           }
           else
               textView.setText("You shall not pass");
        });
    }

    /**
     * This function check the complexity of the password
     * @param password The String object that we are checking
     * @return Returns true if password if complex enough
     */
    boolean checkPasswordComplexity (String password) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (isUpperCase(c)) {
                foundUpperCase = true;
            } else if (isLowerCase(c)) {
                foundLowerCase = true;
            } else if (isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase) {
            Toast.makeText(this, "You shall not pass", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "You shall not pass", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "You shall not pass", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "You shall not pass", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**This functions checks special characters
     * @param c
     * @return false If there's no special characters
     * @return true If there's special characters
     */
    boolean isSpecialCharacter(char c)
    {
        switch (c)
        {
            case '#' :
            case '?' :
            case '*' :
            case '$' :
            case '%' :
            case '^' :
            case '&' :
            case '!' :
            case '@' :
                return true;
            default:
                return false;
        }
    }
}
