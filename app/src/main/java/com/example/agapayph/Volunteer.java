package com.example.agapayph;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Volunteer extends AppCompatActivity {

    TextView tvName;
    ListView lvMain;
    List<ListMissingPerson> dataMissing;
    List<ListReliefRecord> dataRelief;
    List<ListReliefRecord> dataReliefFiltered;
    ReliefVolunteerAdapter adapterRelief;
    MissingPersonAdapter adapterMissing;
    DatabaseHelper dh;
    TabLayout tabVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volunteer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvName = findViewById(R.id.tvName);
        tvName.setText(DataHolder.username);

        dh = new DatabaseHelper(this);
        lvMain = findViewById(R.id.lvMain);

        tabTask(new View(this));

        tabVolunteer = findViewById(R.id.tabVolunteer);
        tabVolunteer.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int postion = tab.getPosition();
                switch (postion) {
                    case 0:
                        tabTask(new View(tab.view.getContext()));
                        break;
                    case 1:
                        tabRelief(new View(tab.view.getContext()));
                        break;
                    case 2:
                        tabMissing(new View(tab.view.getContext()));
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void logoutClicked(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Volunteer.this);
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
                Intent j = new Intent(Volunteer.this, MainActivity.class);
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

    public void tabTask(View view) {



    }

    public void tabRelief(View view) {

        dataRelief = dh.listReliefRecord();
        dataReliefFiltered = new ArrayList<>();

        for (ListReliefRecord l: dataRelief) {

            if (l.volunteer_id == DataHolder.volunteerId) {
                dataReliefFiltered.add(l);
            }

        }

        adapterRelief = new ReliefVolunteerAdapter(this, dataReliefFiltered);
        lvMain.setAdapter(adapterRelief);

    }

    public void tabMissing(View view) {

        //dataMissing = dh.mi

    }
}