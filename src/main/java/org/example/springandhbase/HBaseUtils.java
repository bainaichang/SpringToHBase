package org.example.springandhbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class HBaseUtils {
    
    private static String quorums;
    private static Connection conn = null;
    
    @Value("${hbase.config.zookeeper.quorum}")
    private String quorum;
    
    @PostConstruct
    public void getQuorum() {
        quorums = this.quorum;
    }
    
    /**
     * 获取hbase连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getHBaseConnection() {
        if (conn == null) {
            //hbase配置对象
            Configuration configuration = HBaseConfiguration.create();
            //设置zookeeper地址
            configuration.set("hbase.zookeeper.quorum", quorums);
            try {
                conn = ConnectionFactory.createConnection(configuration);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }
}

