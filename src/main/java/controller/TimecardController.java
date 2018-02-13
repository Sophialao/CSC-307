package controller;
import model.Timecard;

import java.util.HashMap;
import java.util.ArrayList;

public class TimecardController {
    // This class has an ArrayLists of ArrayLists of Timecards
    // So each element represents an ArrayList of Timecards for each eid (employee)
    public HashMap<Integer, ArrayList<Timecard>> timecards = new HashMap<Integer, ArrayList<Timecard>>();
    public int idCount = 1;

    public TimecardController() {
    }

    public Timecard addTimecard(int eid, String timeIn, String timeOut) {
        int tcId = idCount++;
        Timecard tc = new Timecard(tcId, eid, timeIn, timeOut);

        if (timecards.get(eid) == null) { // new employee
            ArrayList<Timecard> newEmpl = new ArrayList<Timecard>();
            newEmpl.add(tc);
            timecards.put(eid, newEmpl);
            return tc;
        }
        timecards.get(eid).add(tc);
        return tc;
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
        ArrayList<Timecard> tc = timecards.get(eid);
        for (int i = 0; i < tc.size(); i++) {
            Timecard curr = tc.get(i);
            curr.printTimecard();
        }
    }

    public static void main(String args[]) {
        TimecardController test = new TimecardController();
        test.addTimecard(1, "1pm", "2pm");
        test.addTimecard(2, "3pm", "4pm");
        test.addTimecard(1, "5pm", "6pm");
        test.addTimecard(4, "7pm", "8pm");
        test.addTimecard(1, "9pm", "10pm");
        test.addTimecard(1, "11pm", "12am");
        test.addTimecard(7, "1am", "2am");
        test.addTimecard(1, "3am", "4am");
        test.addTimecard(9, "5am", "6am");
        test.addTimecard(1, "7am", "8am");
        //        //System.out.println("number of Timecards: " + test.getNumberOfTimecards());
        //        //test.print
        //ArrayList<Timecard> newList = new ArrayList<Timecard>(test.getMostRecentTimecards());
        //test.printTimecards(newList);
        test.printTimecards(1);

    }
}
