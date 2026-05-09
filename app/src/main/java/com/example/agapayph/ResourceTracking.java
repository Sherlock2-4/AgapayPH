package com.example.agapayph;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ResourceTracking extends AppCompatActivity {

    String beneficiary_name;
    String barangay;
    String relief_type;
    int quantity;
    String distribution_date;
    int volunteer_id;
    String placeholder;

    EditText etBeneficiary, etBarangay, etRelief, etQuantity, etVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resource_tracking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etBeneficiary = findViewById(R.id.etBeneficiary);
        etBarangay = findViewById(R.id.etBarangay);
        etRelief = findViewById(R.id.etRelief);
        etQuantity = findViewById(R.id.etQuantity);
        etVolunteer = findViewById(R.id.etVolunteer);


    }

    public void logClicked(View view) {

        beneficiary_name = "";
        barangay = "";
        relief_type = "";
        placeholder = "";
        quantity = 0;
        distribution_date = "";
        volunteer_id = 0;

        beneficiary_name = etBeneficiary.getText().toString().trim();
        barangay = etBarangay.getText().toString().trim();
        relief_type = etRelief.getText().toString().trim();

        placeholder = etQuantity.getText().toString().trim();
        if (!placeholder.isEmpty()) { quantity = Integer.parseInt(placeholder); }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm a", Locale.getDefault());

        DatabaseHelper dh = new DatabaseHelper(this);
        volunteer_id = Integer.parseInt(dh.getVolunteerId(etVolunteer.getText().toString().trim()));

        boolean result = dh.addReliefRecords(beneficiary_name, barangay, relief_type, quantity, sdf.format(c.getTime()), volunteer_id);

        if (result) {

            etBeneficiary.setText("");
            etBarangay.setText("");
            etRelief.setText("");
            etQuantity.setText("");
            etVolunteer.setText("");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logging Successful");
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



                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

    public void back (View view) {

        finish();
        Intent i = new Intent(this, Admin.class);
        startActivity(i);

    }
}