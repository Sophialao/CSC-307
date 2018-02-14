package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Utils;

import static org.junit.jupiter.api.Assertions.*;

class TimecardTest2 {

    @BeforeEach
    void setUp() {
        TimecardController t = new TimecardController();
        t.parseHashMap(Utils.parseFile("mock_db/Timecard.txt"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
    }

    @Test
    void getTimeIn() {
    }

    @Test
    void getTimeOut() {
    }

    @Test
    void setTimeIn() {
    }

    @Test
    void setTimeOut() {
    }

    @Test
    void timecardToString() {
    }

    @Test
    void printTimecard() {
    }
}