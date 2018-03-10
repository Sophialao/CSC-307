package util;

import model.DbWritable;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbUtils {

    private static final String USER = "root";
    private static final String PASSWORD = "ilovesam";

    public static DbWritable getObject(Class<? extends DbWritable> clazz, String id, String table) {
        try {
            String connectionUrl = Constants.DB_URL + "/payroll";

            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM " + table + " WHERE id = '" + id + "';");
            DbWritable o = clazz.getConstructor().newInstance();
            o.readFields(res);
            conn.close();

            return o;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, DbWritable> getAll(Class<? extends DbWritable> clazz, String table) {
        try {
            String connectionUrl = Constants.DB_URL + "/payroll";

            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM " + table + ";");

            Map<String, DbWritable> map = new HashMap<>();
            while (res.next()) {
                DbWritable o = clazz.getConstructor().newInstance();
                o.readFields(res);
                map.put(o.getId(), o);
            }

            conn.close();

            return map;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertOrDelete(String query) {
        try {
            String connectionUrl = Constants.DB_URL + "/payroll";

            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
