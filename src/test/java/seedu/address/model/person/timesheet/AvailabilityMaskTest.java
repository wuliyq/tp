package seedu.address.model.person.timesheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AvailabilityMask}.
 */
public class AvailabilityMaskTest {

    private AvailabilityMask mask;

    @BeforeEach
    void setUp() {
        mask = new AvailabilityMask(10); // 10 time slots
    }

    @Test
    void constructor_initializesEmptyMask() {
        assertEquals(10, mask.size());
        for (int i = 0; i < mask.size(); i++) {
            assertFalse(mask.get(i));
        }
        assertEquals(0, mask.cardinality());
    }

    @Test
    void set_singleIndex_setsCorrectly() {
        mask.set(3, true);
        assertTrue(mask.get(3));
        mask.set(3, false);
        assertFalse(mask.get(3));
    }

    @Test
    void set_outOfRange_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> mask.set(-1, true));
        assertThrows(IllegalArgumentException.class, () -> mask.set(10, true));
    }

    @Test
    void setRange_validRange_setsCorrectly() {
        mask.setRange(2, 5, true);
        for (int i = 2; i < 5; i++) {
            assertTrue(mask.get(i));
        }
        assertEquals(3, mask.cardinality());

        mask.setRange(3, 5, false);
        assertFalse(mask.get(3));
        assertFalse(mask.get(4));
        assertTrue(mask.get(2));
    }

    @Test
    void setRange_invalidRange_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> mask.setRange(-1, 3, true));
        assertThrows(IllegalArgumentException.class, () -> mask.setRange(0, 11, true));
        assertThrows(IllegalArgumentException.class, () -> mask.setRange(5, 3, true));
    }

    @Test
    void cardinality_countsCorrectly() {
        mask.set(0, true);
        mask.set(1, true);
        mask.set(2, false);
        assertEquals(2, mask.cardinality());
    }

    @Test
    void toBinaryString_andFromBinaryString_roundTripWorks() {
        mask.set(0, true);
        mask.set(3, true);
        mask.set(9, true);

        String binary = mask.toBinaryString();
        assertEquals("1001000001", binary);

        AvailabilityMask restored = AvailabilityMask.fromBinaryString(binary);
        assertEquals(mask.size(), restored.size());
        for (int i = 0; i < mask.size(); i++) {
            assertEquals(mask.get(i), restored.get(i));
        }
    }

    @Test
    void fromBinaryString_parsesCorrectly() {
        String s = "101010";
        AvailabilityMask m = AvailabilityMask.fromBinaryString(s);
        assertEquals(6, m.size());
        assertTrue(m.get(0));
        assertFalse(m.get(1));
        assertTrue(m.get(2));
    }

    @Test
    void toBinaryString_returnsExpectedLength() {
        String binary = mask.toBinaryString();
        assertEquals(10, binary.length());
        assertTrue(binary.chars().allMatch(c -> c == '0' || c == '1'));
    }

    @Test
    void check_invalidIndex_throws() {
        // indirectly test private check() via get()
        assertThrows(IllegalArgumentException.class, () -> mask.get(-1));
        assertThrows(IllegalArgumentException.class, () -> mask.get(10));
    }

    @Test
    void emptyFactory_createsMaskOfCorrectSize() {
        AvailabilityMask emptyMask = AvailabilityMask.empty(5);
        assertEquals(5, emptyMask.size());
        assertEquals(0, emptyMask.cardinality());
    }
}

