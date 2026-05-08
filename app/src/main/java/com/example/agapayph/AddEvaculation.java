package com.example.agapayph;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddEvaculation extends AppCompatActivity {

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
    }

    public void back (View view) {

        finish();
        Intent i = new Intent(this, Admin.class);
        startActivity(i);

    }

    public void addCenter(View view) {



//        if (result) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Report Successful");
//            builder.setMessage("Do you want to return to dashboard?");
//            builder.setCancelable(false);
//            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    finish();
//                    Intent j = new Intent(getApplicationContext(), Citizen.class);
//                    startActivity(j);
//                }
//            });
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    resetAll();
//
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
//
//        }

    }
}