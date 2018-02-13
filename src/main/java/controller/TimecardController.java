package controller;
import model.Timecard;
import util.Utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimecardController {
    // This class has an ArrayLists of ArrayLists of Timecards
    // So each element represents an ArrayList of Timecards for each eid (employee)
    public HashMap<Integer, ArrayList<Timecard>> timecards = new HashMap<Integer, ArrayList<Timecard>>();

    public TimecardController() {
    }

    public void addTimecard(int tcId, int eid, Date timeIn, Date timeOut) {
        Timecard tc = new Timecard(tcId, eid, timeIn, timeOut);

        if (timecards.get(eid) == null) { // new employee
            ArrayList<Timecard> newEmpl = new ArrayList<Timecard>();
            newEmpl.add(tc);
            timecards.put(eid, newEmpl);
        }
        timecards.get(eid).add(tc);
    }

    public ArrayList<Timecard> getMostRecentTimecards(int eid) {
        int from = timecards.get(eid).size() - 7;
        int to = timecards.get(eid).size();
        ArrayList<Timecard> result = new ArrayList<Timecard>(timecards.get(eid).subList(from, to));
        return result;
    }

    public int getNumberOfTimecards(int eid) {
        return timecards.get(eid).size();
    }

    public ArrayList<Timecard> getTimecardList(int eid) {
        return timecards.get(eid);
    }

    public void printTimecards(int eid) {
        if (timecards.get(eid) == null) {
            System.err.println("Employee id " + Integer.toString(eid) + " does not exist.");
            return;
        }

        ArrayList<Timecard> tc = timecards.get(eid);
        Collections.sort(tc, new Comparator<Timecard>() {
            public int compare(Timecard o1, Timecard o2) {
                return o1.getTimeIn().compareTo(o2.getTimeIn());
            }
        });
        System.out.println("For employee id: " + Integer.toString(eid));
        for (int i = 0; i < tc.size(); i++) {
            Timecard curr = tc.get(i);
            curr.printTimecard();
        }
    }

    public void parseHashMap(Map<Integer, String> m) {
        Set<Integer> idSet = m.keySet();
        Iterator<Integer> idIter = idSet.iterator();

        while (idIter.hasNext()) {
            int id = idIter.next();
            String valueAsString = m.get(id).trim();
            String[] tokens = valueAsString.split(" ");

            if (tokens.length != 5) {
                System.err.println("Timecard Controller: Incorrect number of tokens.");
            }

            int eid = Integer.parseInt(tokens[0]);
            String timeInConcat = tokens[1] + " " + tokens[2];
            String timeOutConcat = tokens[3] + " " + tokens[4];
            Date timeIn = parseDateTime(timeInConcat);
            Date timeOut = parseDateTime(timeOutConcat);

            addTimecard(id, eid, timeIn, timeOut);
        }
    }

    public Date parseDateTime(String time) {
        Date d = null;
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd kk:mm");
        try {
            d = df.parse(time);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

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
        test.printTimecards(1);
        test.printTimecards(4);
        test.printTimecards(22);

        // test.writeToFile("mock_db/Timecard_writeOutTest.txt");
    }
}
