package com.example.agapayph;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;


public class ReportIncident extends AppCompatActivity {



    String category;
    String severity;
    String title;
    String barangay;
    int individual;
    String placeHolder;
    String description;
    String priority;
    int priorityScore;

    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_incident);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        category = "";
        severity = "";
        title = "";
        barangay = "";
        individual = 0;
        placeHolder = "";
        description = "";
        priority = "";
        priorityScore = 0;

        dh = new DatabaseHelper(this);
    }


    public void submitClicked(View view) {

        title = ((TextView)findViewById(R.id.etTitle)).getText().toString().trim();
        barangay = ((TextView)findViewById(R.id.editTextText28)).getText().toString().trim();
        description = ((TextView)findViewById(R.id.editTextText260)).getText().toString().trim();

        placeHolder = ((TextView)findViewById(R.id.editTextNumber)).getText().toString().trim();
        if (!placeHolder.isEmpty()) {
            individual = Integer.parseInt(placeHolder);
        }

        calculatePriority();

        Calendar c = Calendar.getInstance(Locale.getDefault());


        Boolean result = dh.addIncident(title, category, description, individual, barangay, c.getTime()+"", severity, priority, 0, 0);

        if (result) {

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

                    resetAll();

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

    public void calculatePriority() {

        priorityScore = 0;

        switch (severity) {
            case "Low":
                priorityScore += 10;
                break;
            case "Moderate":
                priorityScore += 20;
                break;
            case "High":
                priorityScore += 30;
                break;
            case "Critical":
                priorityScore += 50;
                break;
        }

        switch (category) {
            case "Flood":
                priorityScore += 15;
                break;
            case "Medical":
                priorityScore += 30;
                break;
            case "Missing":
                priorityScore += 20;
                break;
            case "Earthquake":
                priorityScore += 25;
                break;
            case "Fire":
                priorityScore += 30;
                break;
            case "Infrastructure":
                priorityScore += 20;
                break;
        }

        if (individual >= 20) {
            priorityScore += 20;
        }

        if (priorityScore >= 80) {
            priority = "Critical";
        } else if (priorityScore >= 60) {
            priority = "High";
        } else if (priorityScore >= 40) {
            priority = "Medium";
        } else {
            priority = "Low";
        }

    }

    public void categorySelect(View view) {

        CardView cvFlood = findViewById(R.id.cvFlood);
        CardView cvFire = findViewById(R.id.cvFire);
        CardView cvEarthquake = findViewById(R.id.cvEarthquake);
        CardView cvMedical = findViewById(R.id.cvMedical);
        CardView cvMissing = findViewById(R.id.cvMissing);
        CardView cvInfrastructure = findViewById(R.id.cvInfrastructure);

        cvFlood.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvFire.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvEarthquake.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvMedical.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvMissing.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvInfrastructure.setCardBackgroundColor(getResources().getColor(R.color.cardBG));

        CardView selectedCard = (CardView)view;
        selectedCard.setCardBackgroundColor(getResources().getColor(R.color.green));

        if (cvFlood == selectedCard) { category = "Flood"; }
        if (cvFire == selectedCard) { category = "Fire"; }
        if (cvEarthquake == selectedCard) { category = "Earthquake"; }
        if (cvMedical == selectedCard) { category = "Medical"; }
        if (cvMissing == selectedCard) { category = "Missing"; }
        if (cvInfrastructure == selectedCard) { category = "Infrastructure"; }


    }

    public void severitySelect(View view) {

        Button btnLow = findViewById(R.id.button3);
        Button btnModerate = findViewById(R.id.button4);
        Button btnHigh = findViewById(R.id.button5);
        Button btnCritical = findViewById(R.id.button6);

        btnLow.setBackground(getResources().getDrawable(R.drawable.moderate_bg));
        btnModerate.setBackground(getResources().getDrawable(R.drawable.moderate_bg));
        btnHigh.setBackground(getResources().getDrawable(R.drawable.moderate_bg));
        btnCritical.setBackground(getResources().getDrawable(R.drawable.moderate_bg));

        Button selectedButton = (Button)view;
        selectedButton.setBackground(getResources().getDrawable(R.drawable.green_bg));

        if (btnLow == selectedButton) { severity = "Low"; }
        if (btnModerate == selectedButton) { severity = "Moderate"; }
        if (btnHigh == selectedButton) { severity = "High"; }
        if (btnCritical == selectedButton) { severity = "Critical"; }


    }

    public void back (View view) {

        finish();
        Intent i = new Intent(this, Citizen.class);
        startActivity(i);

    }

    public void resetAll() {

        category = "";
        severity = "";
        title = "";
        barangay = "";
        individual = 0;
        placeHolder = "";
        description = "";
        priority = "";
        priorityScore = 0;

        Button btnLow = findViewById(R.id.button3);
        Button btnModerate = findViewById(R.id.button4);
        Button btnHigh = findViewById(R.id.button5);
        Button btnCritical = findViewById(R.id.button6);

        btnLow.setBackground(getResources().getDrawable(R.drawable.moderate_bg));
        btnModerate.setBackground(getResources().getDrawable(R.drawable.moderate_bg));
        btnHigh.setBackground(getResources().getDrawable(R.drawable.moderate_bg));
        btnCritical.setBackground(getResources().getDrawable(R.drawable.moderate_bg));

        CardView cvFlood = findViewById(R.id.cvFlood);
        CardView cvFire = findViewById(R.id.cvFire);
        CardView cvEarthquake = findViewById(R.id.cvEarthquake);
        CardView cvMedical = findViewById(R.id.cvMedical);
        CardView cvMissing = findViewById(R.id.cvMissing);
        CardView cvInfrastructure = findViewById(R.id.cvInfrastructure);

        cvFlood.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvFire.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvEarthquake.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvMedical.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvMissing.setCardBackgroundColor(getResources().getColor(R.color.cardBG));
        cvInfrastructure.setCardBackgroundColor(getResources().getColor(R.color.cardBG));

        ((TextView)findViewById(R.id.etTitle)).setText("");
        ((TextView)findViewById(R.id.editTextText28)).setText("");
        ((TextView)findViewById(R.id.editTextText260)).setText("");
        ((TextView)findViewById(R.id.editTextNumber)).setText("");

    }

}