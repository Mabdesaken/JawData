package com.jawdata.hackjunction.jawdata.Serializable;

import com.google.firebase.database.PropertyName;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class JawData {

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
}
