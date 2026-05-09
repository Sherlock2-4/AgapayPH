package com.example.agapayph;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
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

        performTaskWithLoading();

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

    public void call911(View view) { //NOT WORKING

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

         if (tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
             Intent i = new Intent(Intent.ACTION_CALL).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             i.putExtra("simSlot", 0);
             i.setData(Uri.parse("tel:" + 911));
             startActivity(i);
         }

    }

    public void performTaskWithLoading() {  //NOT WORKING
        View loader = findViewById(R.id.loadingOverlay);

        // 1. Start Loading
        loader.bringToFront();
        loader.setVisibility(View.VISIBLE);

        // 2. Delay for 2 seconds
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // 3. Stop Loading
                loader.setVisibility(View.GONE);

                // 4. Do your actual work (e.g., refresh list, save to DB)
                Toast.makeText(getApplicationContext(), "Data Updated!", Toast.LENGTH_SHORT).show();
            }
        }, 10000);
    }
}