package org.example.springandhbase.pojo;

import java.util.Arrays;

//timestamp device_id values
public class HbaseTestData {
    private long timestamp;
    private String device_id;
    private float[] values;
    
    @Override
    public String toString() {
        return "HbaseTestData{" +
                "timestamp=" + timestamp +
                ", device_id='" + device_id + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getDevice_id() {
        return device_id;
    }
    
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
    
    public float[] getValues() {
        return values;
    }
    
    public void setValues(float[] values) {
        this.values = values;
    }
}
