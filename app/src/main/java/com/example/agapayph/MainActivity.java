package com.example.agapayph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        status = sp.getBoolean("isLoggedIn", false);

        if (status) {
            Toast.makeText(this, "user is logged in", Toast.LENGTH_SHORT).show();
        }


//        JUST TO CLEAR IT UP
        editor = sp.edit();
        editor.clear();
        editor.apply();

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
            editor.putString("username", inputUsername);
            editor.putString("role", DataHolder.role);
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Success");
            builder.setMessage("Welcome " + DataHolder.username + " Your role is " + DataHolder.role);
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {

            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();

        }
    }


    public void registerClicked(View view) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }
}