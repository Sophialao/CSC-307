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
        tc.setTimeIn(timeIn);
        tc.setTimeOut(timeOut);
        tc.write();
    }

<<<<<<< HEAD
    public void writeToFile(String filename) {
        TreeMap<Integer, String> tm = new TreeMap<Integer, String>();
        Set<Integer> keys = timecards.keySet();
        Iterator<Integer> keyIter = keys.iterator();

        while (keyIter.hasNext()) {
            int eid = keyIter.next();
            ArrayList<Timecard> employeeTc = getTimecardList(eid);

            for (Timecard tc : employeeTc) {
                tm.put(tc.getId(), tc.timecardToString());
            }
        }
        Utils.writeToFile(tm, filename);
    }

    public static void main(String args[]) {
        TimecardController test = new TimecardController();
        test.parseHashMap(Utils.parseFile("mock_db/Timecard.txt"));
        //test.printTimecards(1);
        //test.printTimecards(4);
        //test.printTimecards(22);

        // test.writeToFile("mock_db/Timecard_writeOutTest.txt");
    }
=======
>>>>>>> 65a0629428a1a1eb2f4d8a84c8fced83a314d984
}
