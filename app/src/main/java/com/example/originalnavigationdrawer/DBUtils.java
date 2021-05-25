package com.example.originalnavigationdrawer;

import android.content.Context;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    public static boolean insertActivity(long uid, int type, Timestamp start, Timestamp end, String location, String content){
        Connection connection = getConn("safemeet");

        try {
            String sql = "INSERT INTO activities (uid, type, state, start, end, location, content) VALUES (?, ?, 1, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setLong(1, uid);
                ps.setInt(2, type);
                ps.setTimestamp(3, start);
                ps.setTimestamp(4, end);
                ps.setString(5, location);
                ps.setString(6, content);
                ps.execute();
                connection.close();
                ps.close();
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils", "error:" + e.getMessage());
            return false;
        }
    }

    public static boolean insertUserInfo(String username, String password, int age, String gender, String phoneNum, String emergency_call, String tags){
        Connection connection = getConn("safemeet");

        try {
            String sql = "INSERT INTO users (username, password, age, gender, phone_num, emergency_call, tags) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setInt(3, age);
                ps.setString(4, gender);
                ps.setString(5, phoneNum);
                ps.setString(6, emergency_call);
                ps.setString(7, tags);
                ps.execute();
                connection.close();
                ps.close();
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils", "error:" + e.getMessage());
            return false;
        }
    }

    public static boolean insertComment(long activityId, long uid, int rating, String content){
        Connection connection = getConn("safemeet");
        try {
            String sql = "INSERT INTO comments (act_id, uid, rating, content) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setLong(1, activityId);
                ps.setLong(2, uid);
                ps.setInt(3, rating);
                ps.setString(4, content);
                ps.execute();
                connection.close();
                ps.close();
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils", "error:" + e.getMessage());
            return false;
        }
    }

    public static boolean selectLoginInfo(String username, String password){
        Connection connection = getConn("safemeet");
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs != null){
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils", "error:" + e.getMessage());
            return false;
        }
    }

    public static boolean addEmergencyMessage(long uid, long actId, String content){
        Connection connection = getConn("safemeet");
        String sql = "SELECT id FROM emergency WHERE uid = ? AND act_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setLong(1, uid);
                ps.setLong(2, actId);
                ResultSet rs = ps.executeQuery();
                if (rs != null){
                    if (rs.next()){
                        sql = "UPDATE emergency SET content=CONCAT(content, ?) WHERE uid = ? AND act_id = ?";
                        ps.close();
                        ps = connection.prepareStatement(sql);
                        if (ps != null){
                            ps.setString(1, content);
                            ps.setLong(2, uid);
                            ps.setLong(3, actId);
                            ps.execute();
                            connection.close();
                            ps.close();
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        sql = "INSERT INTO emergency (uid, act_id, time, content) VALUES (?, ?, ?, ?)";
                        ps.close();
                        ps = connection.prepareStatement(sql);
                        if (ps != null){
                            ps.setLong(1, uid);
                            ps.setLong(2, actId);
                            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                            ps.setString(4, content);
                            ps.execute();
                            connection.close();
                            ps.close();
                            return true;
                        }
                        else {
                            return false;
                        }
                    }

                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils", "error: " + e.getMessage());
            return false;
        }
    }

    public static List<HashMap<String, Object>> getUserInfoById(long id){
        List<HashMap<String, Object>> res = new ArrayList<>();
        Connection connection = getConn("safemeet");
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setLong(1, id);
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
            Log.e("DBUtils", "error: " + e.getMessage());
            return null;
        }
    }

    public static boolean addComment(long actId, long creatorId, long uid, float rating, String content){
        Connection connection = getConn("safemeet");
        String sql = "INSERT INTO comments (act_id, creator_id, uid, rating, content) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setLong(1, actId);
                ps.setLong(2, creatorId);
                ps.setLong(3, uid);
                ps.setFloat(4, rating);
                ps.setString(5, content);
                ps.execute();
                connection.close();
                ps.close();
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean changeActivityState(long id){
        Connection connection = getConn("safemeet");
        String sql = "UPDATE activities SET state = 0 WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setLong(1, id);
                ps.execute();
                connection.close();
                ps.close();
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean updateRatingByCreatorId(long creatorId){
        Connection connection = getConn("safemeet");
        String sql = "SELECT rating FROM comments WHERE creator_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setLong(1, creatorId);
                ResultSet rs = ps.executeQuery();
                if (rs != null){
                    int count = rs.getMetaData().getColumnCount();
                    Log.e("DBUtils","cols：" + count);
                    float res = 0;
                    long loop = 0;
                    while (rs.next()){
                        for (int i = 1;i <= count;i++){
                            String field = rs.getMetaData().getColumnName(i);
                            res += rs.getFloat(field);
                        }
                        ++loop;
                    }
                    ps.close();
                    sql = "UPDATE users SET rating = ? WHERE id = ?";
                    ps = connection.prepareStatement(sql);
                    if (ps != null){
                        ps.setFloat(1, (res + 5) / (loop + 1));
                        ps.setLong(2, creatorId);
                        ps.execute();
                        connection.close();
                        ps.close();
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils", "error:" + e.getMessage());
            return false;
        }
    }

    public static List<HashMap<String, Object>> getAllEmergency(){
        List<HashMap<String, Object>> res = new ArrayList<>();
        Connection connection = getConn("safemeet");
        String sql = "SELECT * FROM emergency";
        try {
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
            return null;
        }
    }

    public static boolean addNewMember(String formedUserId, long actId){
        Connection connection = getConn("safemeet");
        String sql = "SELECT id FROM activities WHERE id = ? AND members_id_group is NULL";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps != null){
                ps.setLong(1, actId);
                ResultSet rs = ps.executeQuery();
                if (rs != null){
                    if (rs.next()){
                        Log.d("","1");
                        sql = "UPDATE activities SET members_id_group = ? WHERE id = ?";
                        ps.close();
                        ps = connection.prepareStatement(sql);
                        if (ps != null){
                            ps.setString(1, formedUserId);
                            ps.setLong(2, actId);
                            ps.execute();
                            connection.close();
                            ps.close();
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        sql = "UPDATE activities SET members_id_group=CONCAT(members_id_group, ?) WHERE id = ?";
                        ps.close();
                        ps = connection.prepareStatement(sql);
                        if (ps != null){
                            ps.setString(1, formedUserId);
                            ps.setLong(2, actId);
                            ps.execute();
                            connection.close();
                            ps.close();
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

}
