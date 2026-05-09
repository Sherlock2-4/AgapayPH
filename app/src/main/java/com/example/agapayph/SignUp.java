package com.example.agapayph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dh = new DatabaseHelper(this);
    }

    public void roleSelect(View view) {

        CardView cvAdmin = findViewById(R.id.cvAdmin);
        CardView cvResponder = findViewById(R.id.cvResponder);
        CardView cvVolunteer = findViewById(R.id.cvVolunteer);
        CardView cvCitizen = findViewById(R.id.cvCitizen);

        cvAdmin.setCardBackgroundColor(Color.parseColor("#FFCC9C"));
        cvResponder.setCardBackgroundColor(Color.parseColor("#FF7878"));
        cvVolunteer.setCardBackgroundColor(Color.parseColor("#FFF297"));
        cvCitizen.setCardBackgroundColor(Color.parseColor("#B9E6FF"));

        CardView selectedCard = (CardView)view;
        selectedCard.setCardBackgroundColor(getResources().getColor(R.color.green));

        if (cvAdmin == selectedCard) { DataHolder.role = "Administrator"; }
        if (cvResponder == selectedCard) { DataHolder.role = "Responder"; }
        if (cvVolunteer == selectedCard) { DataHolder.role = "Volunteer"; }
        if (cvCitizen == selectedCard) { DataHolder.role = "Citizen"; }

    }

    public void createAccount(View view) {

        String name = ((EditText)findViewById(R.id.etName)).getText().toString().trim();

        long phone = 0;
        String number = ((EditText)findViewById(R.id.editTextPhone)).getText().toString().trim();
        if (!number.isEmpty()) {phone = Long.parseLong(number);}

        String address = ((EditText)findViewById(R.id.editTextText3)).getText().toString().trim();
        String username = ((EditText)findViewById(R.id.editTextText2)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.editTextTextPassword)).getText().toString().trim();

        if (name.isEmpty() || phone == 0 || address.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
        } else if (DataHolder.role.isEmpty()) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
        } else {


            Boolean result = dh.addUser(username, password, name, phone+"", address, DataHolder.role);

            if (result) {

                SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("realname", name);
                editor.putString("username", username);
                editor.putString("role", DataHolder.role);
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                DataHolder.username = username;

                if (DataHolder.role.equals("Citizen")) {

                    finish();
                    Intent i = new Intent(this, Citizen.class);
                    startActivity(i);

                } else if (DataHolder.role.equals("Administrator")) {

                    finish();
                    Intent i = new Intent(this, Admin.class);
                    startActivity(i);

                } else if (DataHolder.role.equals("Volunteer")) {

                    dh.addVolunteers(username);
                    DataHolder.volunteerId = Integer.parseInt(dh.getVolunteerId(username));

                    finish();
                    Intent i = new Intent(this, Volunteer.class);
                    startActivity(i);

                }

            } else {

            }
        }
    }


    public void back(View view) {

        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

}