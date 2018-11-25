package com.jawdata.hackjunction.jawdata;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jawdata.hackjunction.jawdata.Serializable.Fitbit;
import com.jawdata.hackjunction.jawdata.Serializable.JawData;
import com.jawdata.hackjunction.jawdata.Serializable.Recipe;
import com.jawdata.hackjunction.jawdata.Utility.FirebaseUtil;
import com.jawdata.hackjunction.jawdata.Utility.NotificationUtility;
import com.jawdata.hackjunction.jawdata.Utility.Response;
import com.jawdata.hackjunction.jawdata.Utility.ScoreUtil;


import java.util.ArrayList;
import java.util.List;

public class HealthActivity extends AppCompatActivity implements Response {

    private List<Fitbit> heartbeats = null;
    private List<JawData> jawData = null;
    private Recipe recipe = null;
    private double score = 0;
    private NotificationUtility mNotifUtil;
    private FirebaseUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        mNotifUtil = new NotificationUtility(this);
        util = new FirebaseUtil(this);
        TextView userName = findViewById(R.id.user_name);

        Chart chart = findViewById(R.id.chart);
        chart.setDescription(null);    // Hide the description
        chart.getXAxis().setDrawLabels(false);
    }

    @Override
    protected void onResume(){
        super.onResume();
        util.fetchLatestSensorData(5);
        util.fetchLatestFitbitData(5);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void updateFitbitData(List<Fitbit> result){
        heartbeats = result;
        List<Entry> graphEntries = new ArrayList<>();
        for (Fitbit fit : result){
            graphEntries.add(new Entry(fit.getTimestamp(),fit.getHeart_rate()));
        }
        LineDataSet set = new LineDataSet(graphEntries,"Heartbeats");
        set.setColors(ColorTemplate.MATERIAL_COLORS);

        //updateGraph(set,"Heartbeats");
        updateScore();
        if(score > 0) {
            util.fetchRelevantRecipe(score);
        }
    }

    private void updateGraph(LineDataSet set, String label) {
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineChart chart = (LineChart) findViewById(R.id.chart);
        LineData lineData = chart.getLineData();
        if (lineData == null){
            lineData = new LineData(set);
        } else if(datasetExists(lineData.getDataSetLabels(),label) == -1){
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
        jawData = result;
        List<Entry> graphEntries = new ArrayList<>();
        for (JawData jaw : result){
            graphEntries.add(new Entry(jaw.getTimestamp().getTime(),jaw.getValue()));
        }
        LineDataSet set = new LineDataSet(graphEntries,"Chewing pattern");
        set.setColors(ColorTemplate.JOYFUL_COLORS);
        updateGraph(set,"Chewing pattern");
        updateScore();
    }

    private void updateScore() {
        int avgHeart = new Fitbit().calculateAverage(heartbeats);
        int duration = Fitbit.duration(heartbeats);

        score = ScoreUtil.calculateScore(avgHeart,80,22,duration,1500,3000); //TODO: hardcoded deficit
    }

    @Override
    public void getResponse(String cmpmsg, Object result) {
        if (cmpmsg.equals(Fitbit.CMP)){
            updateFitbitData((List<Fitbit>)result);
        } else if (cmpmsg.equals(JawData.CMP)) {
            updateJawData((List<JawData>) result);
        } else if (cmpmsg.equals(Recipe.CMP)){
            recipe = (Recipe) result;
            notifyOnRecipe();
        }

    }

    public void invalidateAndNotify(View view) {
        updateFitbitData(heartbeats);
        updateJawData(jawData);
        if(recipe != null) {
            notifyOnRecipe();
        }
    }

    public void notifyOnRecipe(){
        if (recipe != null) {
            Notification.Builder nb = mNotifUtil.
                    getAndroidChannelNotification("What about trying " + recipe.getTitle() + " tonight?",  ScoreUtil.getPersonalizedMsg(score));

            mNotifUtil.getManager().notify(101, nb.build());
        }
    }
}
