package seedu.address.model.person.timesheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTimesheetTest {

    private PersonTimesheet timesheet;
    private WeekIndex weekIndex;

    @BeforeEach
    void setUp() {
        weekIndex = new WeekIndex(LocalDate.of(2025, 10, 13)); // Monday
        timesheet = new PersonTimesheet("person1", weekIndex);
    }

    @Test
    void constructor_createsEmptyMaskInitially() {
        assertNotNull(timesheet.getMask());
        assertEquals(WeekIndex.BINS_PER_WEEK, timesheet.getMask().size());
        assertFalse(timesheet.getMask().get(0)); // initially all false
    }

    @Test
    void markAvailableAndUnavailable_worksCorrectly() {
        LocalDateTime start = weekIndex.getWeekStartMonday().atTime(9, 0);
        LocalDateTime end = weekIndex.getWeekStartMonday().atTime(10, 0);

        // mark available
        timesheet.markAvailable(start, end);
        assertTrue(timesheet.isAvailable(start, end));

        // mark unavailable again
        timesheet.markUnavailable(start, end);
        assertFalse(timesheet.isAvailable(start, end));
    }

    @Test
    void isAvailable_partialRange_returnsFalseIfAnyUnavailable() {
        LocalDateTime start = weekIndex.getWeekStartMonday().atTime(9, 0);
        LocalDateTime mid = weekIndex.getWeekStartMonday().atTime(9, 30);
        LocalDateTime end = weekIndex.getWeekStartMonday().atTime(10, 0);

        timesheet.markAvailable(start, mid); // only half hour available
        assertFalse(timesheet.isAvailable(start, end)); // full hour should fail
    }

    @Test
    void toBinaryAndFromBinary_restoreEquivalentTimesheet() {
        timesheet.markAvailable(
                weekIndex.getWeekStartMonday().atTime(8, 0),
                weekIndex.getWeekStartMonday().atTime(9, 0)
        );

        String binary = timesheet.toBinary();
        PersonTimesheet restored = PersonTimesheet.fromBinary("person1",
                weekIndex.getWeekStartMonday(), binary);

        assertEquals(binary, restored.toBinary());
        assertEquals(timesheet.isAvailable(
                weekIndex.getWeekStartMonday().atTime(8, 0),
                weekIndex.getWeekStartMonday().atTime(9, 0)), true);
    }

    @Test
    void toEncodedStringAndFromEncodedString_restoreEquivalent() {
        timesheet.markAvailable(
                weekIndex.getWeekStartMonday().atTime(13, 0),
                weekIndex.getWeekStartMonday().atTime(14, 0)
        );

        String encoded = timesheet.toEncodedString();
        PersonTimesheet restored = PersonTimesheet.fromEncodedString("person1", encoded);

        assertEquals(timesheet.toBinary(), restored.toBinary());
        assertEquals(timesheet.getWeekStartMonday(), restored.getWeekStartMonday());
    }

    @Test
    void fromEncodedString_invalidFormat_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                PersonTimesheet.fromEncodedString("person1", "invalidEncodedString"));
    }

    @Test
    void getters_returnExpectedValues() {
        assertEquals("person1", timesheet.getPersonId());
        assertEquals(weekIndex, timesheet.getWeekIndex());
        assertEquals(weekIndex.getWeekStartMonday(), timesheet.getWeekStartMonday());
    }
}
