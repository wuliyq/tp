package seedu.address.model.person.timesheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class WeekIndexTest {

    @Test
    public void constructor_setsWeekStartMonday_correctly() {
        LocalDate wednesday = LocalDate.of(2025, 10, 15); // a Wednesday
        WeekIndex weekIndex = new WeekIndex(wednesday);
        assertEquals(DayOfWeek.MONDAY, weekIndex.getWeekStartMonday().getDayOfWeek());
        assertEquals(LocalDate.of(2025, 10, 13), weekIndex.getWeekStartMonday());
    }

    @Test
    public void startOfWeek_returnsMondayMidnight() {
        LocalDate monday = LocalDate.of(2025, 10, 13);
        WeekIndex weekIndex = new WeekIndex(monday);
        LocalDateTime start = weekIndex.startOfWeek();
        assertEquals(LocalDateTime.of(2025, 10, 13, 0, 0), start);
    }

    @Test
    public void toIndex_returnsCorrectSlot() {
        LocalDate monday = LocalDate.of(2025, 10, 13);
        WeekIndex weekIndex = new WeekIndex(monday);

        LocalDateTime mondayMidnight = LocalDateTime.of(monday, LocalTime.MIDNIGHT);
        LocalDateTime monday0630 = LocalDateTime.of(monday, LocalTime.of(0, 30));
        LocalDateTime tuesday0100 = LocalDateTime.of(monday.plusDays(1), LocalTime.of(1, 0));

        assertEquals(0, weekIndex.toIndex(mondayMidnight));
        assertEquals(1, weekIndex.toIndex(monday0630));
        assertEquals(50, weekIndex.toIndex(tuesday0100));
    }

    @Test
    public void toIndex_throwsForOutOfWeekTime() {
        LocalDate monday = LocalDate.of(2025, 10, 13);
        WeekIndex weekIndex = new WeekIndex(monday);

        LocalDateTime beforeWeek = monday.minusDays(1).atStartOfDay();
        LocalDateTime afterWeek = monday.plusDays(7).atStartOfDay();

        assertThrows(IllegalArgumentException.class, () -> weekIndex.toIndex(beforeWeek));
        assertThrows(IllegalArgumentException.class, () -> weekIndex.toIndex(afterWeek));
    }

    @Test
    public void toTime_returnsCorrectDateTime() {
        LocalDate monday = LocalDate.of(2025, 10, 13);
        WeekIndex weekIndex = new WeekIndex(monday);

        LocalDateTime time0 = weekIndex.toTime(0);
        LocalDateTime time1 = weekIndex.toTime(1);
        LocalDateTime timeLast = weekIndex.toTime(WeekIndex.BINS_PER_WEEK - 1);

        assertEquals(LocalDateTime.of(monday, LocalTime.MIDNIGHT), time0);
        assertEquals(LocalDateTime.of(monday, LocalTime.of(0, 30)), time1);
        assertEquals(LocalDateTime.of(monday.plusDays(6), LocalTime.of(23, 30)), timeLast);
    }

    @Test
    public void toTime_throwsForInvalidIndex() {
        LocalDate monday = LocalDate.of(2025, 10, 13);
        WeekIndex weekIndex = new WeekIndex(monday);

        assertThrows(IllegalArgumentException.class, () -> weekIndex.toTime(-1));
        assertThrows(IllegalArgumentException.class, () -> weekIndex.toTime(WeekIndex.BINS_PER_WEEK));
    }

    @Test
    public void startAndToIndex_areInverseOfToTime() {
        LocalDate monday = LocalDate.of(2025, 10, 13);
        WeekIndex weekIndex = new WeekIndex(monday);

        for (int i = 0; i < WeekIndex.BINS_PER_WEEK; i += 50) { // sample every 50 slots
            LocalDateTime dt = weekIndex.toTime(i);
            int idx = weekIndex.toIndex(dt);
            assertEquals(i, idx);
        }
    }
}

