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

    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JawData() {
        // serialize yo...
    }

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
        if (list == null) return 0;
        Integer sum = 0;
        if(!list.isEmpty()) {
            for (JawData jaw : (List<JawData>)list) {
                sum += jaw.value;
            }
            return sum / list.size();
        }
        return sum;
    }
}
