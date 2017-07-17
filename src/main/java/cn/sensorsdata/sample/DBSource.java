package cn.sensorsdata.sample;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by tianyi on 10/07/2017.
 */
public final class DBSource {
     private static  DruidDataSource db;
     public ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();
     private static final Logger logger = LoggerFactory.getLogger(DBSource.class);
     private static Connection conn = null;
     // 获取数据库的链接
     DBSource() {
          //System.out.println("attting");
          Properties prop = new Properties();
          db = new DruidDataSource();
          try {
               URL localPath = DBSource.class.getClassLoader().getResource("druid.properties");
               prop.load(new InputStreamReader(localPath.openStream()));
               for(Map.Entry entry: prop.entrySet()) {
                   BeanUtils.setProperty(db, (String)entry.getKey(), entry.getValue());
               }
               conn = this.getConnection();
          } catch (IOException ex) {
              logger.warn("mysql_ioe_exception {}", ex);
          } catch (InvocationTargetException ex) {
              logger.warn("mysql_invoca_target_exception {}", ex);
          } catch (IllegalAccessException ex)  {
              logger.warn("mysql_access_exception {}", ex);
          }
     }

     public Connection getConnection() {
         Connection connection = connectionHolder.get();
         if (connection == null) {
             try {
                 connection = db.getConnection();
             } catch (SQLException ex) {
                 logger.warn("get_mysql_connection exception {}", ex);
             }
         }
         return connection;
     }

     //执行sql
     public List<Map<String, Object>> executeQuery(String sql) throws SQLException {
         if (conn == null) {
             conn = this.getConnection();
         }
         Statement statement = conn.createStatement();
         ResultSet resultSet = statement.executeQuery(sql);
         int columnCount = resultSet.getMetaData().getColumnCount();
         List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
         while(resultSet.next()) {
             Map<String, Object> map = new HashMap<String, Object>();
             for (int i = 1; i <= columnCount; i++) {
                 map.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
             }
             list.add(map);
         }
         return list;
     }
     public void close() throws  SQLException {
         try {
             conn.close();
         } catch (SQLException ex) {
             logger.warn("close_connect failed");
         }

     }

}
