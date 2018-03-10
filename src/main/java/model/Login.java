package model;

import util.Constants;
import util.Utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Login implements DbWritable {
    private String id;
    private String loginID;
    private String username;
    private String password;

    public Login() {
        this.id = UUID.randomUUID().toString();
    }

    public Login(String loginID, String username, String password) {
        this.loginID = loginID;
        this.username = username;
        this.password = password;
        this.id = UUID.randomUUID().toString();
    }

    public static Login getInstance(String id) {
        if (id == null)
            return new Login();
        else {
            String[] db = Utils.readLine(Constants.LOGIN_DB, id);
            if (db != null) {
                Login time = new Login();
                time.readFields(db);
                time.setId(id);
                return time;
            }
            return new Login();
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = Utils.parseFile(Constants.LOGIN_DB, Timecard.class);

        return db;
    }

    public String getId() {
        return this.id;
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



    public void readFields(String[] args) {
        if (args.length == 1){
            return;
        }
        this.id = args[0];
        this.username = args[1];
        this.password = args[2];
    }

    public void write() {
        Utils.removeLine(Constants.LOGIN_DB, this.id);
        System.out.println("loggin in" + this.id + "," + this.username + "," + this.password);
        String toWrite = this.id + "," + this.username + "," + this.password;
        Utils.appendLine(Constants.LOGIN_DB, toWrite);
    }


}
