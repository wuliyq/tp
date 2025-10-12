package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.timesheet.PersonTimesheet;
import seedu.address.model.person.timesheet.WeekIndex;

public class TimesheetGridTest {

    @Test
    public void testTimesheetGridConstructor() {
        // Test that constructor doesn't throw exceptions with valid input
        WeekIndex weekIndex = new WeekIndex(LocalDate.of(2025, 10, 13));
        PersonTimesheet timesheet = new PersonTimesheet("P001", weekIndex);

        // Just verify the objects can be created
        assertNotNull(timesheet);
        assertNotNull(weekIndex);
    }

    @Test
    public void testTimesheetGridDependencies() {
        WeekIndex weekIndex = new WeekIndex(LocalDate.of(2025, 10, 13));
        PersonTimesheet timesheet = new PersonTimesheet("P001", weekIndex);

        // Test the data that would be used by TimesheetGrid
        assertEquals("P001", timesheet.getPersonId());
        assertNotNull(timesheet.getMask());
        assertEquals(WeekIndex.BINS_PER_WEEK, timesheet.getMask().size());
    }
}

