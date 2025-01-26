package org.example.springandhbase.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.example.springandhbase.HBaseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class TestController {
    @GetMapping("/createTable")
    @ResponseBody
    public String createDemo01(@RequestParam("tableName") String tableName,
                               @RequestParam("family") String family) {
        Connection hBaseConnection = HBaseUtils.getHBaseConnection();
        Admin admin = HBaseUtils.getAdmin();
        TableName tbname = TableName.valueOf(tableName);
        try {
            if (admin.tableExists(tbname)) {
                System.out.println("表已存在!");
                JSONObject put = JSONUtil.createObj()
                                         .set("status", "400")
                                         .set("msg", "table is exist!");
                return put.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 表描述器建造对象
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tbname);
        // 列族描述器建造对象
        ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(family));
        // 真正的表描述器
        ColumnFamilyDescriptor columnFamilyDescriptor = columnFamilyDescriptorBuilder.build();
        // 将列族和表联系起来
        tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptor);
        // 真正的表描述器
        TableDescriptor tableDescriptor = tableDescriptorBuilder.build();
        // 开始创建表
        try {
            admin.createTable(tableDescriptor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        JSONObject put = JSONUtil.createObj()
                                 .set("status", "200")
                                 .set("msg", "ok");
        System.out.println("tableName = " + tbname);
        System.out.println("family = " + family);
        return put.toString();
    }
    
    @GetMapping("/put")
    @ResponseBody
    public String putDemo01(@RequestParam("tableName") String tableName,
                            @RequestParam("rowKey") String rowKey,
                            @RequestParam("family") String family,
                            @RequestParam("column") String column,
                            @RequestParam("value") String value) {
        Connection conn = HBaseUtils.getHBaseConnection();
        JSONObject ret = JSONUtil.createObj();
        Table table;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 根据rowkey添加
        Put put = new Put(Bytes.toBytes(rowKey));
        // 列族,列名,值
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
        try {
            table.put(put);
        } catch (IOException e) {
            System.out.println("data put failed!");
            ret.set("status", "400")
               .set("msg", "data put failed!");
            return ret.toString();
        }
        try {
            table.close();
        } catch (IOException e) {
            System.out.println("table close failed!");
            throw new RuntimeException(e);
        }
        
        ret.set("status", "200")
           .set("msg", "ok");
        return ret.toString();
    }
    
    @GetMapping("/get")
    @ResponseBody
    public String getDemo01(@RequestParam("tableName") String tableName,
                            @RequestParam("rowKey") String rowKey) {
        
        Table table;
        JSONObject ret = JSONUtil.createObj();
        try {
            table = HBaseUtils.getHBaseConnection()
                              .getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = null;
        try {
            result = table.get(get);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
            // 获得列族的名字
            String f = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            // 获得列名
            String c = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            // 获得值
            String v = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            ret.set(f+":"+c,v);
        }
        ret.set("status", "200")
           .set("msg", "ok");
        return ret.toString();
    }
}
