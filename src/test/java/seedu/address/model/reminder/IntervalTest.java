package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IntervalTest {
    private static final Interval interval = new Interval("2", "min");

    @Test
    public void constructor_test() {
        Interval interval1 = new Interval("2", "min");
        assertEquals("2", interval1.getIntervalInt());
        assertEquals("min", interval1.getUnit());
    }

    @Test
    public void isValidInterval_test() {
        assertTrue(Interval.isValidInterval("2", "min"));
        assertTrue(Interval.isValidInterval("2", "MIN"));
        assertTrue(Interval.isValidInterval("3", "Hour"));
        assertFalse(Interval.isValidInterval("2", "random"));
    }

    @Test
    public void toString_test() {
        Interval interval1 = new Interval("2", "min");
        assertEquals("2min", interval1.toString());
    }
}
