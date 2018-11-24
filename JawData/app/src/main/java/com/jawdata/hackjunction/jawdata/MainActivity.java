package com.jawdata.hackjunction.jawdata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void typeAndData(String type, String data) {
        String category = type;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference catRef = database.getReference(category);
        catRef.setValue(data);
    }

    public void onGotItClick(View view) {
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }
}
