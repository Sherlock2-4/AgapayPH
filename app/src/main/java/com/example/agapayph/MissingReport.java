package com.example.agapayph;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MissingReport extends AppCompatActivity {

    EditText etName, etAge, etLocation, etDescription;
    String name, location, description;
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_missing_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = "";
        location = "";
        age = 0;
        description = "";

    }

    public void submitMissing(View view) {
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);

        name = etName.getText().toString().trim();

        if (!etAge.getText().toString().isEmpty()) {
            age = Integer.parseInt(etName.getText().toString().trim());
        }
        location = etLocation.getText().toString().trim();
        description = etDescription.getText().toString().trim();

        Boolean result = true;

        if (result) {

            etName.setText("");
            etAge.setText("");
            etLocation.setText("");
            etDescription.setText("");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Report Successful");
            builder.setMessage("Do you want to return to dashboard?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finish();
                    Intent j = new Intent(getApplicationContext(), Citizen.class);
                    startActivity(j);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {



                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public void back (View view) {

        finish();
        Intent i = new Intent(this, Citizen.class);
        startActivity(i);

    }
}