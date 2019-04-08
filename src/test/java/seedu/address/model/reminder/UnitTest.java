package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UnitTest {
    private static final Unit unit = new Unit("min");

    @Test
    public void constructor_test() {
        Unit unit2 = new Unit("min");
        assertEquals("min", unit2.getUnit());
    }

    @Test
    public void isValidTest() {
        assertTrue(Unit.isValidUnit("min"));
        assertTrue(Unit.isValidUnit("mIn"));
        assertTrue(Unit.isValidUnit("YEar"));
        assertTrue(Unit.isValidUnit("YEAR"));
        assertFalse(Unit.isValidUnit("random"));
    }


    @Test
    public void equalTest() {
        Unit unit2 = new Unit("min");
        Unit unit3 = new Unit("Min");
        assertEquals(unit2, unit);
        assertEquals(unit3, unit);
    }
}
