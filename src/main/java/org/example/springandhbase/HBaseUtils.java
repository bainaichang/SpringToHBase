package org.example.springandhbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class HBaseUtils {
    
    private static Connection conn = null;
    private static Admin admin = null;
    
    @Value("${hbase.config.zookeeper.quorum}")
    private String quorum;
    
    @PostConstruct
    private void init() {
        if (conn == null) {
            //hbase配置对象
            Configuration configuration = HBaseConfiguration.create();
            //设置zookeeper地址
            configuration.set("hbase.zookeeper.quorum", quorum);
            try {
                conn = ConnectionFactory.createConnection(configuration);
                admin = conn.getAdmin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * 获取hbase连接
     *
     * @return
     * @throws Exception
     */
    
    public static Connection getHBaseConnection() {
        return conn;
    }
    
    public static Admin getAdmin() {
        return admin;
    }
}

