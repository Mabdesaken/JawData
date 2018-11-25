package com.jawdata.hackjunction.jawdata.Serializable;

import com.google.firebase.database.PropertyName;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class JawData implements Serialize {

    public static final String CMP = "JAWDATA";
    private Date timestamp;
    private Integer value;

    public static SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mmm:ss");

    public JawData(Date timestamp, Integer value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public <T> int calculateAverage(List<T> list) {
        List<JawData> newList = (List<JawData>)list;
        Integer sum = 0;
        if(!list.isEmpty()) {
            for (JawData jaw : newList) {
                sum += jaw.value;
            }
            return sum / list.size();
        }
        return sum;
    }
}
