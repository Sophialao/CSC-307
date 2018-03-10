package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbWritable {

    String getId();
    void write();
    void readFields(ResultSet res) throws SQLException;
    void update();
    void remove();
}
