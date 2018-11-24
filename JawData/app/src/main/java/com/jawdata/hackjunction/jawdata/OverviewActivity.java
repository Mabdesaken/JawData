package com.jawdata.hackjunction.jawdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jawdata.hackjunction.jawdata.Utility.FirebaseUtil;
import com.jawdata.hackjunction.jawdata.Utility.Response;

public class OverviewActivity extends AppCompatActivity implements Response {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        FirebaseUtil util = new FirebaseUtil(this);
        TextView userName = findViewById(R.id.user_name);
        util.fetchThreeLatestSensorData();
    }

    private <T> void updateUI(T result) {

    }

    @Override
    public <T> T getResponse(T result) {

    }
}
