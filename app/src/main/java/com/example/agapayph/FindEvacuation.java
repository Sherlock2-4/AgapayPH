package com.example.agapayph;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class FindEvacuation extends AppCompatActivity {

    ListView lvEvacuation;
    EvacuationAdapter adapter;
    List<ListEvacuationCenter> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_evacuation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DatabaseHelper dh = new DatabaseHelper(this);

        data = dh.listEvacuationCenter();


        lvEvacuation = findViewById(R.id.lvEvacuation);
        adapter = new EvacuationAdapter(this, data);


        lvEvacuation.setAdapter(adapter);

    }



    public void back (View view) {
        finish();
        Intent i = new Intent(this, Citizen.class);
        startActivity(i);
    }
}