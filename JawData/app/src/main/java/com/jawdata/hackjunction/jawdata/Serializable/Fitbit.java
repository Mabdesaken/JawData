package com.jawdata.hackjunction.jawdata.Serializable;

import com.google.firebase.database.PropertyName;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class Fitbit implements Serialize {

    public static final String CMP = "FITBIT";

    @PropertyName("heart_rate")
    private int heartRate;

    @PropertyName("timestamp")
    private int timestamp;

    public Fitbit() {
        // serialized ...
    }

    public Fitbit(int heartRate, int timestamp) {
        this.heartRate = heartRate;
        this.timestamp = timestamp;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fitbit fitbit = (Fitbit) o;
        return heartRate == fitbit.heartRate &&
                timestamp == fitbit.timestamp;
    }

    @Override
    public int hashCode() {

        return Objects.hash(heartRate, timestamp);
    }

    @Override
    public String toString() {
        return "Fitbit{" +
                "heartRate=" + heartRate +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public <T> int calculateAverage(List<T> list) {
        List<Fitbit> newList = (List<Fitbit>)list;
        Integer sum = 0;
        if(!list.isEmpty()) {
            for (Fitbit bit : newList) {
                sum += bit.heartRate;
            }
            return sum / list.size();
        }
        return sum;
    }
}
