package seedu.address.model.person.timesheet;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/** Map Week Start time <-> bit slot[0, 672)，A week starts from Monday 0:00。*/
public final class WeekIndex {
    public static final int MINUTES_PER_BIN = 30;
    public static final int BINS_PER_DAY = (24 * 60) / MINUTES_PER_BIN; // 96
    public static final int DAYS_PER_WEEK = 7;
    public static final int BINS_PER_WEEK = BINS_PER_DAY * DAYS_PER_WEEK; // 336

    private final LocalDate weekStartMonday;

    /**
     * Returns a weekIndex given a localDate object
     * @param weekStartMonday
     */
    public WeekIndex(LocalDate weekStartMonday) {
        this.weekStartMonday = Objects.requireNonNull(weekStartMonday)
                .with(java.time.DayOfWeek.MONDAY);
    }

    public LocalDateTime startOfWeek() {
        return LocalDateTime.of(weekStartMonday, LocalTime.MIDNIGHT);
    }

    /** Time to the bit slot [0, 336). If time exceeds week limit of this WeekIndex, throw an error*/
    public int toIndex(LocalDateTime time) {
        long minutes = Duration.between(startOfWeek(), time).toMinutes();
        long span = (long) BINS_PER_WEEK * MINUTES_PER_BIN;
        if (minutes < 0 || minutes >= span) {
            throw new IllegalArgumentException("Time outside this week grid: " + time);
        }
        return (int) (minutes / MINUTES_PER_BIN);
    }
    /**
     * Returns a LocalDateTime object given an index
     * @param index
     * @return
     */
    public LocalDateTime toTime(int index) {
        if (index < 0 || index >= BINS_PER_WEEK) {
            throw new IllegalArgumentException("Index out of range: " + index);
        }
        return startOfWeek().plusMinutes((long) index * MINUTES_PER_BIN);
    }

    public LocalDate getWeekStartMonday() {
        return weekStartMonday;
    }
}

