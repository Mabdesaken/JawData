package com.jawdata.hackjunction.jawdata;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jawdata.hackjunction.jawdata.Serializable.Fitbit;
import com.jawdata.hackjunction.jawdata.Serializable.JawData;
import com.jawdata.hackjunction.jawdata.Utility.Response;

import java.util.List;

public class OverviewActivity extends AppCompatActivity implements Response {
    public int avgHeartbeats = 0;
    public int avgSensorData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.themeBlue));
    }

    private void updateUI() {

    }

    private void updateFitbitData(List<Fitbit> result){
        int res = 0;
        for (Fitbit fit : result){
            res += fit.getHeart_rate();
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
        Intent intent = new Intent(this, ManageActivity.class);
        startActivity(intent);
    }

    public void onHealth(View view) {
        Intent intent = new Intent(this, HealthActivity.class);
        startActivity(intent);
    }
}
