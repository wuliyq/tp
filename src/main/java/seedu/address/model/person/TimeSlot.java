package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's lesson time slot in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeSlot(String)}
 */
public class TimeSlot {

    public static final String MESSAGE_CONSTRAINTS =
            "TimeSlot should be in the format YYYY-MM-DD HHMM-HHMM, "
                    + "where start time is before end time and the duration is at least 30 minutes.\n"
                    + "Example: 2025-10-12 1600-1800";

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public final LocalDate date;
    public final LocalTime startTime;
    public final LocalTime endTime;

    /**
     * Constructs a {@code TimeSlot}.
     *
     * @param timeSlotString A valid time slot string in the format "YYYY-MM-DD HHMM-HHMM".
     */
    public TimeSlot(String timeSlotString) {
        requireNonNull(timeSlotString);
        checkArgument(isValidTimeSlot(timeSlotString), MESSAGE_CONSTRAINTS);

        String[] parts = timeSlotString.trim().split(" ");
        this.date = LocalDate.parse(parts[0], DATE_FORMATTER);

        String[] times = parts[1].split("-");
        this.startTime = LocalTime.parse(times[0], TIME_FORMATTER);
        this.endTime = LocalTime.parse(times[1], TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid time slot.
     */
    public static boolean isValidTimeSlot(String test) {
        if (test == null) {
            return false;
        }

        String[] parts = test.trim().split(" ");
        if (parts.length != 2) {
            return false;
        }

        // Validate date
        try {
            LocalDate.parse(parts[0], DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        // Validate times
        String[] times = parts[1].split("-");
        if (times.length != 2) {
            return false;
        }

        try {
            LocalTime start = LocalTime.parse(times[0], TIME_FORMATTER);
            LocalTime end = LocalTime.parse(times[1], TIME_FORMATTER);

            if (!start.isBefore(end)) {
                return false;
            }

            Duration duration = Duration.between(start, end);
            if (duration.toMinutes() < 30) {
                return false;
            }

        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return date.format(DATE_FORMATTER) + " " + startTime.format(TIME_FORMATTER) + "-" + endTime.format(TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimeSlot)) {
            return false;
        }

        TimeSlot otherSlot = (TimeSlot) other;
        return date.equals(otherSlot.date)
                && startTime.equals(otherSlot.startTime)
                && endTime.equals(otherSlot.endTime);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + endTime.hashCode();
        return result;
    }
}

