package com.example.agapayph;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ManageRelief extends AppCompatActivity {

    ListView lv;
    List<ListReliefRecord> data;
    ReliefAdapter adapter;
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_relief);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadListView();

    }

    public void loadListView() {

        lv = findViewById(R.id.lvRelief);
        dh = new DatabaseHelper(this);
        data = dh.listReliefRecord();
        adapter = new ReliefAdapter(this, data);
        lv.setAdapter(adapter);

    }

    public void back(View view) {
        finish();
    }

}