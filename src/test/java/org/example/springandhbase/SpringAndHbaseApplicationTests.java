package org.example.springandhbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class SpringAndHbaseApplicationTests {
    
    @Test
    void contextLoads() {
    }
    
    @Test
    void hbaseConnTest() {
        TableName tableName = TableName.valueOf("student_info");
        try {
            System.out.println(HBaseUtils.getHBaseConnection()
                                         .getAdmin()
                                         .tableExists(tableName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void putTest() throws Exception {
        Table table = HBaseUtils.getHBaseConnection()
                                .getTable(TableName.valueOf("student_info"));
        String dfName = "stu";
        for (int i = 1; i <= 100; i++) {
            String rowKey = String.format("%04d", i - 1);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("c1"), Bytes.toBytes("name"), Bytes.toBytes(dfName + i));
            put.addColumn(Bytes.toBytes("c1"), Bytes.toBytes("test"), Bytes.toBytes(i + ""));
            table.put(put);
        }
        table.close();
    }
}
