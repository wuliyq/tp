package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in a timeslot conflict.
 */
public class TimeSlotConflictException extends RuntimeException {
    public TimeSlotConflictException() {
        super("Operation would result in a timeslot conflict");
    }

    public TimeSlotConflictException(String message) {
        super(message);
    }
}
