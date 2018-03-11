package util;

import model.DbWritable;
import model.Employee;
import model.HourlyEmployee;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbUtils {

    private static final String USER = "root";
    private static final String PASSWORD = "ilovesam";

    public static DbWritable getObject(Class<? extends DbWritable> clazz, String id, String table) {
        try {
            String connectionUrl = Constants.DB_URL + "/payroll";

            Connection conn = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM " + table + " WHERE id = '" + id + "';");
            DbWritable o = clazz.getConstructor().newInstance();
            if (res.next()) {
                o.readFields(res);
            } else {
                return null;
            }
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
            System.out.println(query);

            Connection conn = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static DbWritable queryAtt(Class<? extends DbWritable> clazz, String table, String attName, String attValue) {
        try {
            String connectionUrl = Constants.DB_URL + "/payroll";
            String query = "SELECT * FROM " + table + " WHERE " + attName + " = '" + attValue + "';";

            Connection conn = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery(query);
            DbWritable o = clazz.getConstructor().newInstance();
            if (res.next()) {
                o.readFields(res);
            }
            return o;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        //Employee hourly = new HourlyEmployee("Sam test", "13243 ave", 1323552345, 20.09, "M", 0);
        //Employee hourly = HourlyEmployee.getInstance("0ce03ef1-3c77-45ec-b53b-5cf8c85b0341");

        //hourly.write();

        //hourly.setAddress("now Im changed lane");
        //hourly.update();

//        Map<String, DbWritable> emps = HourlyEmployee.getAll();
//        Iterator<String> it = emps.keySet().iterator();
//        while (it.hasNext()) {
//            HourlyEmployee hours = (HourlyEmployee) emps.get(it.next());
//            System.out.println(hours.getName());
//            new Timecard(hours.getId(), new Date(), new Date()).write();
//        }

        Employee hourly = HourlyEmployee.getInstance("2b3abaa3-462c-474d-af85-924ccd7c3375");
        hourly.remove();



    }

}
