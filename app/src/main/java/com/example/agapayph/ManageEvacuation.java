package com.example.agapayph;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ManageEvacuation extends AppCompatActivity {

    ListView lv;
    EvacuationManagementAdapter adapter;
    List<ListEvacuationCenter> data;
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_evacuation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadListView();

    }

    @Override
    public void onResume() {

        super.onResume();
        loadListView();
    }

    public void loadListView() {

        lv = findViewById(R.id.lvEvacuation);
        dh = new DatabaseHelper(this);
        data = dh.listEvacuationCenter();
        adapter = new EvacuationManagementAdapter(this, data);
        lv.setAdapter(adapter);

    }

    public void back(View view) {

        finish();
        Intent i = new Intent(this, Admin.class);
        startActivity(i);

    }

}