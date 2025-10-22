package seedu.address.model.person;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code TimeSlot} overlaps with the given time range.
 */
public class TimeslotRangePredicate implements Predicate<Person> {
    private final Optional<LocalDate> startDate;
    private final Optional<LocalDate> endDate;
    private final Optional<LocalTime> startTime;
    private final Optional<LocalTime> endTime;

    /**
     * Constructs a predicate with the given date and time boundaries.
     * All parameters are optional.
     */
    public TimeslotRangePredicate(Optional<LocalDate> startDate, Optional<LocalDate> endDate,
                                  Optional<LocalTime> startTime, Optional<LocalTime> endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean test(Person person) {
        if (person.getTimeSlot() == null) {
            return false;
        }

        TimeSlot personSlot = person.getTimeSlot();
        LocalDate personDate = personSlot.getDate();
        LocalTime personStart = personSlot.getStartTime();
        LocalTime personEnd = personSlot.getEndTime();

        // 1. Check Date Range
        // Must be on or after startDate (if it exists)
        boolean afterStartDate = startDate.map(start -> !personDate.isBefore(start)).orElse(true);
        // Must be on or before endDate (if it exists)
        boolean beforeEndDate = endDate.map(end -> !personDate.isAfter(end)).orElse(true);

        // 2. Check Time Range
        // The person's slot must NOT end before the filter's start time
        boolean afterStartTime = startTime.map(start -> !personEnd.isBefore(start)).orElse(true);
        // The person's slot must NOT start after the filter's end time
        boolean beforeEndTime = endTime.map(end -> !personStart.isAfter(end)).orElse(true);

        return afterStartDate && beforeEndDate && afterStartTime && beforeEndTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimeslotRangePredicate)) {
            return false;
        }

        TimeslotRangePredicate otherPredicate = (TimeslotRangePredicate) other;
        return startDate.equals(otherPredicate.startDate)
                && endDate.equals(otherPredicate.endDate)
                && startTime.equals(otherPredicate.startTime)
                && endTime.equals(otherPredicate.endTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("startDate", startDate.map(LocalDate::toString).orElse("any"))
                .add("endDate", endDate.map(LocalDate::toString).orElse("any"))
                .add("startTime", startTime.map(LocalTime::toString).orElse("any"))
                .add("endTime", endTime.map(LocalTime::toString).orElse("any"))
                .toString();
    }
}
