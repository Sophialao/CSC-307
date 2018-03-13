package model;

import util.Constants;
import util.DbUtils;
import util.DbSqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class Login implements DbWritable {
    private String id;
    private String username;
    private String password;

    public Login() {
        this.id = UUID.randomUUID().toString();
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Login getInstance(String id) {
        if (id == null)
            return new Login();
        else {
            return (Login) DbSqlite.getObject(Login.class, id, Constants.LOGIN_DB);
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = DbSqlite.getAll(Login.class, Constants.LOGIN_DB);
        return db;
    }

    public String getId() {
        return this.username;
    }

    public String getUsername(){
        return this.username;
    }


    public String getPassword() {
        return this.password;
    }


    private void setId(String id) { this.id = id;}

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) {
        this.password = password;
    }



    public void readFields(ResultSet res) throws SQLException {
        this.setUsername(res.getString("username"));
        this.setPassword(res.getString("password"));
    }

    public void update() {
        String stmt = "UPDATE " + Constants.LOGIN_DB + " SET ";
        stmt += "password = '" + this.getPassword() + "', ";
        stmt += " WHERE username = '" + this.getUsername() +"';";
        DbSqlite.insertOrDelete(stmt);
    }

    public void write() {
        String stmt = "INSERT INTO " + Constants.LOGIN_DB + "(id, password) VALUES (";
        stmt += "'" + this.getUsername() + "', '" + this.getPassword() +  "');";
        DbSqlite.insertOrDelete(stmt);
    }

    public void remove() {
        String stmt = "DELETE FROM " + Constants.LOGIN_DB + " WHERE id = '" + this.getUsername() + "';";
        DbSqlite.insertOrDelete(stmt);
    }


}
