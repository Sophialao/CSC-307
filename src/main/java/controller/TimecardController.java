package controller;
import model.Timecard;
import util.Utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimecardController {

    public TimecardController() {
    }

    public void addTimecard(String eid, Date timeIn, Date timeOut) {
        Timecard tc = Timecard.getInstance(null);
        tc.setEId(eid);
        System.out.println(timeIn);
        tc.setTimeIn(timeIn);
        tc.setTimeOut(timeOut);
        tc.write();
    }


}
