package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimeslotTest {

    @Test
    public void constructor_validInput_success() {
        TimeSlot slot = new TimeSlot("2025-10-12 0800-0900");
        assertEquals(LocalDate.of(2025, 10, 12), slot.getDate());
        assertEquals(LocalTime.of(8, 0), slot.getStartTime());
        assertEquals(LocalTime.of(9, 0), slot.getEndTime());
        assertEquals("2025-10-12 0800-0900", slot.toString());
    }

    @Test
    public void constructor_invalidFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot("2025/10/12 0800-0900"));
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot("2025-10-12"));
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot("0800-0900"));
    }

    @Test
    public void constructor_invalidTimeOrder_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot("2025-10-12 1000-0900"));
    }

    @Test
    public void constructor_shortDuration_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot("2025-10-12 0800-0810"));
    }

    @Test
    public void isValidTimeSlot_validInputs_returnTrue() {
        assertTrue(TimeSlot.isValidTimeSlot("2025-10-12 0800-0900"));
        assertTrue(TimeSlot.isValidTimeSlot("2024-01-01 1300-1400"));
    }

    @Test
    public void isValidTimeSlot_invalidInputs_returnFalse() {
        assertFalse(TimeSlot.isValidTimeSlot(null));
        assertFalse(TimeSlot.isValidTimeSlot("")); // empty
        assertFalse(TimeSlot.isValidTimeSlot("not-a-date 0800-0900"));
        assertFalse(TimeSlot.isValidTimeSlot("2025-10-12 9999-1200"));
        assertFalse(TimeSlot.isValidTimeSlot("2025-10-12 0900-0900"));
        assertFalse(TimeSlot.isValidTimeSlot("2025-10-12 0850-0900")); // less than 30 min
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        TimeSlot slot1 = new TimeSlot("2025-10-12 0800-0900");
        TimeSlot slot2 = new TimeSlot("2025-10-12 0800-0900");
        assertEquals(slot1, slot2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        TimeSlot slot1 = new TimeSlot("2025-10-12 0800-0900");
        TimeSlot slot2 = new TimeSlot("2025-10-12 0900-1000");
        assertNotEquals(slot1, slot2);
    }

    @Test
    public void toString_validSlot_matchesExpectedFormat() {
        TimeSlot slot = new TimeSlot("2025-10-12 0800-0900");
        assertEquals("2025-10-12 0800-0900", slot.toString());
    }
}
