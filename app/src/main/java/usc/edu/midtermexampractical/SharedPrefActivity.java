package usc.edu.midtermexampractical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SharedPrefActivity extends AppCompatActivity {
    EditText password, email, emaillogin, passwordlogin;
    Button savebtn, readbtn, clearbtn, backbtn, recycbtn;
    TextView readname, reademail;

    private static final String MyPref = "mypref";
    private static final String Email = "emailKey";
    private static final String Password = "passwordKey";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shared_pref);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        emaillogin = findViewById(R.id.emaillogin);
        passwordlogin = findViewById(R.id.passwordlogin);
        savebtn = findViewById(R.id.savebtn);
        readbtn = findViewById(R.id.readbtn);
        clearbtn = findViewById(R.id.clearbtn);
        backbtn = findViewById(R.id.backbtn);
        recycbtn = findViewById(R.id.recyclergo);
        readname = findViewById(R.id.displayname);
        reademail = findViewById(R.id.displayemail);


        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stremail = email.getText().toString();
                String strepassword = password.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Email, stremail);
                editor.putString(Password, strepassword);
//                editor.commit();
                editor.apply();

                Toast.makeText(getApplicationContext(), "Data has been saved", Toast.LENGTH_SHORT).show();
            }
        });

        readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                Toast.makeText(getApplicationContext(), "Data retrieved", Toast.LENGTH_SHORT).show();
            }
        });

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Toast.makeText(getApplicationContext(), "Data has been cleared", Toast.LENGTH_SHORT).show();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaillogin.getText().toString();
                String password = passwordlogin.getText().toString();
                String registeredEmail = sharedPreferences.getString(Email, "");
                String registeredPassword = sharedPreferences.getString(Password, "");

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SharedPrefActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else if (email.equals(registeredEmail) && password.equals(registeredPassword)) {
                    Intent intent = new Intent(SharedPrefActivity.this, Home_Screen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SharedPrefActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


        recycbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SharedPrefActivity.this, JSONParserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        String storedEmail = sharedPreferences.getString(Email, "No email found");
        String storedPassword = sharedPreferences.getString(Password, "No password found");

        reademail.setText("Email: " + storedEmail);
        readname.setText("Password: " + storedPassword);
    }
}