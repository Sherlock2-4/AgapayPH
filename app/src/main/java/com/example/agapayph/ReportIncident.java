package com.example.agapayph;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReportIncident extends AppCompatActivity {

    String category;
    String severity;
    String title;
    String barangay;
    int individual;
    String placeHolder;

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
    }


    public void submitClicked(View view) {

        title = ((TextView)findViewById(R.id.etTitle)).getText().toString().trim();
        barangay = ((TextView)findViewById(R.id.editTextText28)).getText().toString().trim();

        placeHolder = ((TextView)findViewById(R.id.editTextNumber)).getText().toString().trim();
        if (!placeHolder.isEmpty()) {
            individual = Integer.parseInt(placeHolder);
        }

        Toast.makeText(this, title + " " + barangay + " " + individual, Toast.LENGTH_SHORT).show();

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

        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();

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

        Toast.makeText(this, severity, Toast.LENGTH_SHORT).show();

    }

}