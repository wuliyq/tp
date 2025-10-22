package seedu.address.model.person.exceptions;

public class TimeSlotConflictException extends RuntimeException {
  public TimeSlotConflictException(String message) {
    super(message);
  }
}
