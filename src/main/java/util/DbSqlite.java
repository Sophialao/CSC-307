package util;


import model.DbWritable;
import model.Employee;
import model.HourlyEmployee;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import model.DbWritable;
import model.Employee;
import model.HourlyEmployee;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbSqlite {


        private static final String USER = "root";
        private static final String PASSWORD = "ilovesam";


    public static DbWritable getObject(Class<? extends DbWritable> clazz, String id, String table) {

                String url = "jdbc:sqlite:test.db";

                try (Connection conn = DriverManager.getConnection(url)) {
                    if (conn != null) {
                        DatabaseMetaData meta = conn.getMetaData();
                        System.out.println("The driver name is " + meta.getDriverName());
                        System.out.println("A new database has been created.");
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



                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            return null;
        }

        public static Map<String, DbWritable> getAll(Class<? extends DbWritable> clazz, String table) {
            try {
                String url = "jdbc:sqlite:test.db";

                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet res;
                if (clazz.getName() == "model.HourlyEmployee"){
                    res = stmt.executeQuery("SELECT * FROM " + table + " WHERE type = 'Hourly'"  + ";");

                }
                else if (clazz.getName() == "model.SalaryEmployee"){
                    res = stmt.executeQuery("SELECT * FROM " + table + " WHERE type = 'Salary'"  + ";");

                }
                else {
                    res = stmt.executeQuery("SELECT * FROM " + table + ";");
                }

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
                System.out.println(query);

                String url = "jdbc:sqlite:test.db";

                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();

                stmt.executeUpdate(query);
                conn.close();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }

    public static boolean idExists(String query) {
        try {
            System.out.println(query);

            String url = "jdbc:sqlite:test.db";

            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(query);
            ResultSet res = stmt.executeQuery(query);
//            res.next();
            String name = res.getString("id");
            conn.close();
            return name != null && !name.equals("");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

        public static DbWritable queryAtt(Class<? extends DbWritable> clazz, String table, String attName, String attValue) {
            try {
                String query = "SELECT * FROM " + table + "  WHERE " + attName + " = '" + attValue + "';";

                String url = "jdbc:sqlite:test.db";

                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();

                ResultSet res = stmt.executeQuery(query);
                DbWritable o = clazz.getConstructor().newInstance();
                if (res.next()) {
                    o.readFields(res);
                }
                conn.close();

                return o;

            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }


}
