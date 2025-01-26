package org.example.springandhbase.pojo;

import org.springframework.stereotype.Component;

@Component
public class HBaseData {
    // 表名
    private String tableName;
    // rowKey
    private String rowKey;
    // 列族名
    private String family;
    // 列名
    private String column;
    // 值
    private String value;
    
    public HBaseData(String tableName, String rowKey, String family, String column, String value) {
        this.tableName = tableName;
        this.rowKey = rowKey;
        this.family = family;
        this.column = column;
        this.value = value;
    }
    
    public HBaseData() {
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getRowKey() {
        return rowKey;
    }
    
    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }
    
    public String getFamily() {
        return family;
    }
    
    public void setFamily(String family) {
        this.family = family;
    }
    
    public String getColumn() {
        return column;
    }
    
    public void setColumn(String column) {
        this.column = column;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
