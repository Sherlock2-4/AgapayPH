package com.example.agapayph;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddEvaculation extends AppCompatActivity {

    String name, barangay, food, water, medicine;
    int capacity, occupancy;
    EditText etName, etBarangay, etCapacity, etOccupancy, etFood, etWater, etMedicine;
    DatabaseHelper dh;
    String placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_evaculation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        etBarangay = findViewById(R.id.etBarangay);
        etCapacity = findViewById(R.id.etCapacity);
        etOccupancy = findViewById(R.id.etOccupancy);
        etFood = findViewById(R.id.etFood);
        etWater = findViewById(R.id.etWater);
        etMedicine = findViewById(R.id.etMedicine);

        dh = new DatabaseHelper(this);

    }

    public void back (View view) {

        finish();
        Intent i = new Intent(this, Admin.class);
        startActivity(i);

    }

    public void addCenter(View view) {

        name = etName.getText().toString().trim();
        barangay = etBarangay.getText().toString().trim();

        placeholder = etCapacity.getText().toString().trim();
        if (!placeholder.isEmpty()) { capacity = Integer.parseInt(placeholder); }
        placeholder = etOccupancy.getText().toString().trim();
        if (!placeholder.isEmpty()) { occupancy  = Integer.parseInt(placeholder); }


        food = etFood.getText().toString().trim();
        water = etWater.getText().toString().trim();
        medicine = etMedicine.getText().toString().trim();

        boolean result = dh.addEvacuationCenter(name, capacity, occupancy, barangay, food, water, medicine);

        if (result) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Evacuation Center Added Successfully");
            builder.setMessage("Do you want to return to dashboard?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finish();
                    Intent j = new Intent(getApplicationContext(), Admin.class);
                    startActivity(j);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    etName.setText("");
                    etBarangay.setText("");
                    etCapacity.setText("");
                    etOccupancy.setText("");
                    etFood.setText("");
                    etWater.setText("");
                    etMedicine.setText("");

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }
}