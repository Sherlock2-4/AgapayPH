package com.example.agapayph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Boolean status;
    String inputUsername, inputPassword;

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



        sp = getSharedPreferences("MyPref", MODE_PRIVATE);

        DataHolder.username = sp.getString("username", "None");
        DataHolder.role = sp.getString("role", "None");
        status = sp.getBoolean("isLoggedIn", false);

    }

    public void logInClicked(View view) {

        EditText etUsername = findViewById(R.id.editTextText2);
        EditText etPassword = findViewById(R.id.editTextTextPassword);

        inputUsername = etUsername.getText().toString();
        inputPassword = etPassword.getText().toString();

        // LOGIC FOR CHECKING USERNAME AND PASSWORD

        Boolean result = false;

        // LOGIC FOR CHECKING USERNAME AND PASSWORD
        // Pag nakapaglogin si user, ilagay sa DataHolder.role yung role nya, diko alam kung pano HAHAHAAHAHHAHA

        if (result) {


            //Kapag Successful log in
            editor = sp.edit();
            editor.putString("username", inputUsername);
            editor.putString("role", DataHolder.role);
            editor.putBoolean("isLoggedIn", true);


        } else {

            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();

        }
    }


    public void registerClicked(View view) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }
}