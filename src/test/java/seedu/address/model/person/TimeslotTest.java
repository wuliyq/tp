package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Test
    public void isPast_test() {
        // Mock 'now' to be a fixed point in time for predictable tests
        LocalDateTime now = LocalDateTime.of(2025, 10, 22, 16, 0); // 4:00 PM on Oct 22

        // Case 1: Timeslot is fully in the past (yesterday)
        TimeSlot pastSlot = new TimeSlot("2025-10-21 1400-1500");
        assertTrue(pastSlot.isPast(now));

        // Case 2: Timeslot is fully in the past (today)
        TimeSlot pastTodaySlot = new TimeSlot("2025-10-22 1000-1100");
        assertTrue(pastTodaySlot.isPast(now));

        // Case 3: Timeslot is in progress (end time is in future)
        TimeSlot inProgressSlot = new TimeSlot("2025-10-22 1500-1700");
        assertFalse(inProgressSlot.isPast(now));

        // Case 4: Timeslot is fully in the future (today)
        TimeSlot futureTodaySlot = new TimeSlot("2025-10-22 1800-1900");
        assertFalse(futureTodaySlot.isPast(now));

        // Case 5: Timeslot is fully in the future (tomorrow)
        TimeSlot futureSlot = new TimeSlot("2025-10-23 1000-1100");
        assertFalse(futureSlot.isPast(now));

        // Case 6: Timeslot ends exactly 'now'
        TimeSlot edgeSlot = new TimeSlot("2025-10-22 1500-1600");
        assertFalse(edgeSlot.isPast(now)); // .isBefore() is strict, so this is not "past"
    }

    @Test
    public void getNextOccurrence_test() {
        LocalDateTime now = LocalDateTime.of(2025, 10, 22, 16, 0); // 4:00 PM on Wed, Oct 22

        // Case 1: Past slot (yesterday, Tue Oct 21)
        TimeSlot pastSlot = new TimeSlot("2025-10-21 1000-1100");
        TimeSlot expectedNext = new TimeSlot("2025-10-28 1000-1100"); // Next Tuesday
        assertEquals(expectedNext, pastSlot.getNextOccurrence(now));

        // Case 2: Past slot (today, Wed Oct 22 @ 3PM)
        TimeSlot pastTodaySlot = new TimeSlot("2025-10-22 1500-1530");
        TimeSlot expectedNextToday = new TimeSlot("2025-10-29 1500-1530"); // Next Wednesday
        assertEquals(expectedNextToday, pastTodaySlot.getNextOccurrence(now));

        // Case 3: Future slot (today, Wed Oct 22 @ 5PM)
        TimeSlot futureTodaySlot = new TimeSlot("2025-10-22 1700-1800");
        // Slot is in the future, so the "next" occurrence is itself
        assertEquals(futureTodaySlot, futureTodaySlot.getNextOccurrence(now));

        // Case 4: Future slot (tomorrow, Thu Oct 23)
        TimeSlot futureSlot = new TimeSlot("2025-10-23 1000-1100");
        // Slot is in the future, so the "next" occurrence is itself
        assertEquals(futureSlot, futureSlot.getNextOccurrence(now));
    }
}
