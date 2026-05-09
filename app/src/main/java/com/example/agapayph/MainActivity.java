package com.example.agapayph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Boolean status;
    String inputUsername, inputPassword;
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            dh = new DatabaseHelper(this);
            dh.getWritableDatabase();//checking if db is created

            sp = getSharedPreferences("MyPref", MODE_PRIVATE);

            DataHolder.username = sp.getString("username", "");
            DataHolder.role = sp.getString("role", "");
            DataHolder.realName = sp.getString("realname", "");
            status = sp.getBoolean("isLoggedIn", false);

            if (status) {
                if (DataHolder.role.equals("Citizen")) {


                    Intent i = new Intent(this, Citizen.class);
                    startActivity(i);

                } else if (DataHolder.role.equals("Administrator")) {

                    finish();
                    Intent i = new Intent(this, Admin.class);
                    startActivity(i);

                } else if (DataHolder.role.equals("Volunteer")) {


                    DataHolder.volunteerId = Integer.parseInt(dh.getVolunteerId(sp.getString("username", "")));
                    finish();
                    Intent i = new Intent(this, Volunteer.class);
                    startActivity(i);

                }
            }


//        JUST TO CLEAR IT UP
//        editor = sp.edit();
//        editor.clear();
//        editor.apply();
        } catch (Exception e) {
            Log.e("CRITICAL_ERROR", "App failed during onCreate", e);
        }
    }

    public void logInClicked(View view) {

        EditText etUsername = findViewById(R.id.editTextText2);
        EditText etPassword = findViewById(R.id.editTextTextPassword);

        inputUsername = etUsername.getText().toString().trim();
        inputPassword = etPassword.getText().toString().trim();


        // LOGIC FOR CHECKING USERNAME AND PASSWORD
        Boolean result = dh.checkUserLogin(inputUsername, inputPassword); //method here?
        // LOGIC FOR CHECKING USERNAME AND PASSWORD


        if (result) {

            editor = sp.edit();
            editor.putString("realname", DataHolder.realName);
            editor.putString("username", inputUsername);
            editor.putString("role", DataHolder.role);
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            DataHolder.username = inputUsername;

            if (DataHolder.role.equals("Citizen")) {


                Intent i = new Intent(this, Citizen.class);
                startActivity(i);

            } else if (DataHolder.role.equals("Administrator")) {

                finish();
                Intent i = new Intent(this, Admin.class);
                startActivity(i);

            } else if (DataHolder.role.equals("Volunteer")) {

                DataHolder.volunteerId = Integer.parseInt(dh.getVolunteerId(sp.getString("username", "")));
                finish();
                Intent i = new Intent(this, Volunteer.class);
                startActivity(i);

            }

        } else {

            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();

        }
    }


    public void registerClicked(View view) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }
}