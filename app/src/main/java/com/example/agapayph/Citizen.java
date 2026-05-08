package com.example.agapayph;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Citizen extends AppCompatActivity {

    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_citizen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvName = findViewById(R.id.textView17);
        tvName.setText("Citizen: " + DataHolder.username);

    }

    public void incidentClicked(View view) {

        Intent i = new Intent(this, ReportIncident.class);
        startActivity(i);

    }

    public void toMissingPerson(View view) {

        Intent i = new Intent(this, MissingReport.class);
        startActivity(i);

    }

    public void toEvacuation(View view) {

        Intent i = new Intent(this, FindEvacuation.class);
        startActivity(i);

    }

    public void logoutClicked(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Citizen.this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", "");
                editor.putString("role", "");
                editor.putBoolean("isLoggenIn", false);
                editor.apply();

                finish();
                Intent j = new Intent(Citizen.this, MainActivity.class);
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