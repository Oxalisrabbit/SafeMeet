package com.example.originalnavigationdrawer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBUtils {
    private static String driver = "com.mysql.jdbc.Driver";

//    private static String url = "jdbc:mysql://localhost:3306/safemeet";

    private static String user = "root";

    private static String password = "";

    private static Connection getConn(String dbName){

        Connection connection = null;
        try{
            Class.forName(driver);
            String ip = "192.168.100.18";// 写成本机地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName,
                    user, password);

        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public static HashMap<String, Object> getInfoByUsername(String name){

        HashMap<String, Object> map = new HashMap<>();

        Connection connection = getConn("safemeet");

        try {
            String sql = "select * from users where username = ?";

            if (connection != null){
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null){
                    // 设置上面的sql语句中的？的值为name
                    ps.setString(1, name);
                    // 执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if (rs != null){
                        int count = rs.getMetaData().getColumnCount();
                        Log.e("DBUtils","cols：" + count);
                        while (rs.next()){
                            // 注意：下标是从1开始的
                            for (int i = 1;i <= count;i++){
                                String field = rs.getMetaData().getColumnName(i);
                                map.put(field, rs.getString(field));
                            }
                        }
                        connection.close();
                        ps.close();
                        return  map;
                    }else {
                        return null;
                    }
                }else {
                    return  null;
                }
            }else {
                return  null;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","error：" + e.getMessage());
            return null;
        }
    }

    public static List<HashMap<String, Object>> getAllType(){

        List<HashMap<String, Object>> res = new ArrayList<>();

        Connection connection = getConn("safemeet");

        try{
            String sql = "SELECT * FROM types";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ResultSet rs = ps.executeQuery();
                if (rs != null){
                    int count = rs.getMetaData().getColumnCount();
                    Log.e("DBUtils","cols：" + count);
                    while (rs.next()){
                        // 注意：下标是从1开始的
                        HashMap<String, Object> map = new HashMap<>();
                        for (int i = 1;i <= count;i++){
                            String field = rs.getMetaData().getColumnName(i);
                            map.put(field, rs.getString(field));
                        }
                        res.add(map);
                    }
                    connection.close();
                    ps.close();
                    return  res;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","error：" + e.getMessage());
            return null;
        }
    }

    public static List<HashMap<String, Object>> getActivitiesByType(int type){
        List<HashMap<String, Object>> res = new ArrayList<>();

        Connection connection = getConn("safemeet");

        try{
            String sql = "SELECT * FROM activities WHERE state = 1 AND type = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setInt(1, type);
                ResultSet rs = ps.executeQuery();
                if (rs != null){
                    int count = rs.getMetaData().getColumnCount();
                    Log.e("DBUtils","cols：" + count);
                    while (rs.next()){
                        // 注意：下标是从1开始的
                        HashMap<String, Object> map = new HashMap<>();
                        for (int i = 1;i <= count;i++){
                            String field = rs.getMetaData().getColumnName(i);
                            map.put(field, rs.getString(field));
                        }
                        res.add(map);
                    }
                    connection.close();
                    ps.close();
                    return  res;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","error：" + e.getMessage());
            return null;
        }
    }

    public static boolean updateStateByEnd(){
        Connection connection = getConn("safemeet");

        try {
            String sql = "UPDATE activities SET state = -1 WHERE end < CURRENT_TIME";
            PreparedStatement ps = connection.prepareStatement(sql);
            if(ps != null){
                ps.executeUpdate();
                connection.close();
                ps.close();
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","error：" + e.getMessage());
            return false;
        }
    }

}
