package com.jawdata.hackjunction.jawdata;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jawdata.hackjunction.jawdata.Serializable.Fitbit;
import com.jawdata.hackjunction.jawdata.Serializable.JawData;
import com.jawdata.hackjunction.jawdata.Serializable.Recipe;
import com.jawdata.hackjunction.jawdata.Serializable.Serialize;
import com.jawdata.hackjunction.jawdata.Utility.FirebaseUtil;
import com.jawdata.hackjunction.jawdata.Utility.NotificationUtility;
import com.jawdata.hackjunction.jawdata.Utility.Response;
import com.jawdata.hackjunction.jawdata.Utility.ScoreUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class HealthActivity extends AppCompatActivity implements Response {

    private List<Fitbit> heartbeats = null;
    private List<JawData> jawData = null;
    private List<Recipe> recipes = null;
    private int score = 0;
    private NotificationUtility mNotifUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        mNotifUtil = new NotificationUtility(this);
        FirebaseUtil util = new FirebaseUtil(this);
        TextView userName = findViewById(R.id.user_name);



        LineChart chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(1,2));
        entries.add(new Entry(5,3));
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh

        /*util.fetchThreeLatestSensorData();
        util.fetchFitbitData();*/
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void updateFitbitData(List<Fitbit> result){
        int res = 0;
        List<Entry> graphEntries = new ArrayList<>();
        for (Fitbit fit : result){
            res += fit.getHeartRate();
            graphEntries.add(new Entry(fit.getTimestamp(),fit.getHeartRate()));
        }
        updateGraph(graphEntries,"Heartbeats");
    }

    private void updateGraph(List<Entry> graphEntries, String label) {
        LineChart chart = (LineChart) findViewById(R.id.chart);
        LineDataSet set = new LineDataSet(graphEntries,label);
        LineData lineData = chart.getLineData();
        if (lineData == null){
            lineData = new LineData(set);
        } else if(datasetExists(lineData.getDataSetLabels(),label) != -1){
            lineData.addDataSet(set);
        } else {
            ILineDataSet dataSet = lineData.getDataSetByIndex(datasetExists(lineData.getDataSetLabels(), label));
            for (int i = 0; i<set.getEntryCount(); i++) {
                dataSet.addEntry(set.getEntryForIndex(i));
            }
        }
        chart.setData(lineData);
        chart.invalidate();
    }

    private int datasetExists(String[] dataSetLabels, String label) {
        for (int i = 0; i<dataSetLabels.length; i++){
            if (dataSetLabels[i].equals(label)) return i;
        }
        return -1;
    }

    private void updateJawData(List<JawData> result) {
        List<Entry> graphEntries = new ArrayList<>();
        for (JawData jaw : result){
            graphEntries.add(new Entry(jaw.getTimestamp().getTime(),jaw.getValue()));
        }
        updateGraph(graphEntries,"Chewing pattern");
        updateScore();
    }

    private void updateScore() {
        int avgHeart = new Fitbit().calculateAverage(heartbeats);
        int avgJaw = new JawData().calculateAverage(jawData);

        score = ScoreUtil.calculateScore(avgHeart,avgJaw,200); //TODO: hardcoded deficit
    }

    @Override
    public void getResponse(String cmpmsg, Object result) {
        if (cmpmsg.equals(Fitbit.CMP)){
            updateFitbitData((List<Fitbit>)result);
        } else if (cmpmsg.equals(JawData.CMP)) {
            updateJawData((List<JawData>) result);
        }

    }

    public void invalidateAndNotify(View view) {
        updateFitbitData(heartbeats);
        updateJawData(jawData);
        if(!recipes.isEmpty()) {
            Notification.Builder nb = mNotifUtil.
                    getAndroidChannelNotification("What about trying " + recipes.get(0).getTitle() + " tonight?",  );

            mNotifUtil.getManager().notify(101, nb.build());
        }
    }
}
