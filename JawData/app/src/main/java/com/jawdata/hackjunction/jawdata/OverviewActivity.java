package com.jawdata.hackjunction.jawdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jawdata.hackjunction.jawdata.Serializable.Fitbit;
import com.jawdata.hackjunction.jawdata.Serializable.JawData;
import com.jawdata.hackjunction.jawdata.Serializable.Recipe;
import com.jawdata.hackjunction.jawdata.Utility.FirebaseUtil;
import com.jawdata.hackjunction.jawdata.Utility.Response;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity implements Response {
    public int avgHeartbeats = 0;
    public int avgSensorData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        FirebaseUtil util = new FirebaseUtil(this);
        TextView userName = findViewById(R.id.user_name);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.avg_heartbeats))) {
            avgHeartbeats = savedInstanceState.getInt(getString(R.string.avg_heartbeats));
        } else if (getIntent().getStringExtra(getString(R.string.avg_heartbeats)) != null) {
            avgHeartbeats = Integer.valueOf(getIntent().getStringExtra(getString(R.string.avg_heartbeats)));
        }
        util.fetchThreeLatestSensorData();
        util.fetchFitbitData();
    }

    private void updateUI() {

    }

    private void updateFitbitData(List<Fitbit> result){
        int res = 0;
        for (Fitbit fit : result){
            res += fit.getHeartRate();
        }
        avgHeartbeats = res/result.size();
    }

    private void updateJawData(List<JawData> result) {
        int res = 0;
        for (JawData jaw : result){
            res += jaw.getValue();
        }
        avgSensorData = res/result.size();
    }

    @Override
    public void getResponse(String cmpmsg,Object result) {
        if (cmpmsg.equals(Fitbit.CMP)){
            updateFitbitData((List<Fitbit>)result);
        } else if (cmpmsg.equals(JawData.CMP)) {
            updateJawData((List<JawData>)result);
        } else {
            updateUI();
        }
    }

    public void onManage(View view) {
    }
}
