package com.example.agapayph;

import android.app.ComponentCaller;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    String imagePath;
    int priorityScore;
    ImageView ivImage;

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



//        ivImage = findViewById(R.id.ivImage);
//        getImageFromPath("/data/user/0/com.example.agapayph/app_imgDir/IMG_1778339320921.jpg", ivImage);
    }


//    public void getImageFromPath(String path, ImageView image){
//        File file = new File(path);
//        if(file.exists()){
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//            image.setImageBitmap(bitmap);
//        }
//    }

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


        Boolean result = dh.addIncident(title, category, description, individual, barangay, c.getTime()+"", severity, imagePath, priority, 0, 0);

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

    public void uploadImage(View view) {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i, 1);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            ivImage.setImageURI(uri);

            imagePath = saveImageGetPath(uri);

//            SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.put

        }


    }

    public String saveImageGetPath(Uri uri) {

        String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";

        File directory = getDir("imgDir", MODE_PRIVATE);
        File imagePath = new File(directory, fileName);

        try(InputStream i = getContentResolver().openInputStream(uri);
            FileOutputStream fos = new FileOutputStream(imagePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = i.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            return imagePath.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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


        cvFlood.setCardBackgroundColor(Color.parseColor("#C7EEFF"));
        cvFire.setCardBackgroundColor(Color.parseColor("#FFC7C7"));
        cvEarthquake.setCardBackgroundColor(Color.parseColor("#FFE7A4"));
        cvMedical.setCardBackgroundColor(Color.parseColor("#FFC3D8"));
        cvMissing.setCardBackgroundColor(Color.parseColor("#FFCE97"));
        cvInfrastructure.setCardBackgroundColor(Color.parseColor("#F8FFC0"));

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

        btnLow.setBackground(getResources().getDrawable(R.drawable.low_bg));
        btnModerate.setBackground(getResources().getDrawable(R.drawable.moderate_bg));
        btnHigh.setBackground(getResources().getDrawable(R.drawable.high_bg));
        btnCritical.setBackground(getResources().getDrawable(R.drawable.critical_bg));

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

        btnLow.setBackground(getResources().getDrawable(R.drawable.low_bg));
        btnModerate.setBackground(getResources().getDrawable(R.drawable.moderate_bg));
        btnHigh.setBackground(getResources().getDrawable(R.drawable.high_bg));
        btnCritical.setBackground(getResources().getDrawable(R.drawable.critical_bg));

        CardView cvFlood = findViewById(R.id.cvFlood);
        CardView cvFire = findViewById(R.id.cvFire);
        CardView cvEarthquake = findViewById(R.id.cvEarthquake);
        CardView cvMedical = findViewById(R.id.cvMedical);
        CardView cvMissing = findViewById(R.id.cvMissing);
        CardView cvInfrastructure = findViewById(R.id.cvInfrastructure);

        cvFlood.setCardBackgroundColor(Color.parseColor("#C7EEFF"));
        cvFire.setCardBackgroundColor(Color.parseColor("#FFC7C7"));
        cvEarthquake.setCardBackgroundColor(Color.parseColor("#FFE7A4"));
        cvMedical.setCardBackgroundColor(Color.parseColor("#FFC3D8"));
        cvMissing.setCardBackgroundColor(Color.parseColor("#FFCE97"));
        cvInfrastructure.setCardBackgroundColor(Color.parseColor("#F8FFC0"));

        ((TextView)findViewById(R.id.etTitle)).setText("");
        ((TextView)findViewById(R.id.editTextText28)).setText("");
        ((TextView)findViewById(R.id.editTextText260)).setText("");
        ((TextView)findViewById(R.id.editTextNumber)).setText("");

    }

}