package com.jawdata.hackjunction.jawdata.Serializable;

import com.google.firebase.database.PropertyName;

import java.util.List;
import java.util.Objects;

public class Fitbit implements Serialize{

    public static final String CMP = "FITBIT";

    @PropertyName("heart_rate")
    private long heart_rate;

    @PropertyName("timestamp")
    private long timestamp;

    public Fitbit() {
        // serialized ...
    }

    public Fitbit(int heartRate, int timestamp) {
        this.heart_rate = heartRate;
        this.timestamp = timestamp;
    }

    public static int duration(List<Fitbit> heartbeats) {
        Fitbit earliest = heartbeats.get(0);
        Fitbit latest = heartbeats.get(heartbeats.size() - 1);
        return (int) ((int)latest.timestamp-earliest.timestamp);
    }

    public long getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(long heart_rate) {
        this.heart_rate = heart_rate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fitbit fitbit = (Fitbit) o;
        return heart_rate == fitbit.heart_rate &&
                timestamp == fitbit.timestamp;
    }

    @Override
    public int hashCode() {

        return Objects.hash(heart_rate, timestamp);
    }

    @Override
    public String toString() {
        return "Fitbit{" +
                "heart_rate=" + heart_rate +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public <T> int calculateAverage(List<T> list) {
        if (list == null) return 0;
        long sum = 0;
        if(!list.isEmpty()) {
            for (Fitbit bit : (List<Fitbit>)list) {
                sum += bit.heart_rate;
            }
            return (int)sum / list.size();
        }
        return (int)sum;
    }
}
