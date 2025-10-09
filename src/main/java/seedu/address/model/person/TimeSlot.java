package seedu.address.model.person;


public class TimeSlot {

    public static final String MESSAGE_CONSTRAINTS = "Timeslots should not be blank";

    public final String value;

    public TimeSlot(String timeSlot) {
        this.value = timeSlot;
    }

    public static boolean isValidTimeSlot(String test) {
        return true;
    }
}
