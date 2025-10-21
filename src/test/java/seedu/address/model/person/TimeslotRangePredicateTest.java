package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TimeslotRangePredicateTest {

    private static final LocalDate DATE_1 = LocalDate.of(2025, 10, 10);
    private static final LocalDate DATE_2 = LocalDate.of(2025, 10, 12);
    private static final LocalTime TIME_1 = LocalTime.of(9, 0);
    private static final LocalTime TIME_2 = LocalTime.of(12, 0);

    // Person with timeslot 2025-10-11 10:00-11:00
    private static final Person PERSON_IN_RANGE = new PersonBuilder()
            .withTimeSlot("2025-10-11 1000-1100").build();

    @Test
    public void equals() {
        TimeslotRangePredicate firstPredicate = new TimeslotRangePredicate(
                Optional.of(DATE_1), Optional.of(DATE_2),
                Optional.of(TIME_1), Optional.of(TIME_2));
        TimeslotRangePredicate secondPredicate = new TimeslotRangePredicate(
                Optional.of(DATE_1), Optional.empty(),
                Optional.of(TIME_1), Optional.empty());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TimeslotRangePredicate firstPredicateCopy = new TimeslotRangePredicate(
                Optional.of(DATE_1), Optional.of(DATE_2),
                Optional.of(TIME_1), Optional.of(TIME_2));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_timeslotOverlaps_returnsTrue() {
        // No filters
        TimeslotRangePredicate predicate = new TimeslotRangePredicate(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(predicate.test(PERSON_IN_RANGE));

        // Start Date filter (2025-10-10) -> Person is on 10-11
        predicate = new TimeslotRangePredicate(
                Optional.of(DATE_1), Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(predicate.test(PERSON_IN_RANGE));

        // End Date filter (2025-10-12) -> Person is on 10-11
        predicate = new TimeslotRangePredicate(
                Optional.empty(), Optional.of(DATE_2), Optional.empty(), Optional.empty());
        assertTrue(predicate.test(PERSON_IN_RANGE));

        // Start Time filter (09:00) -> Person is 10:00-11:00
        predicate = new TimeslotRangePredicate(
                Optional.empty(), Optional.empty(), Optional.of(TIME_1), Optional.empty());
        assertTrue(predicate.test(PERSON_IN_RANGE));

        // End Time filter (12:00) -> Person is 10:00-11:00
        predicate = new TimeslotRangePredicate(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(TIME_2));
        assertTrue(predicate.test(PERSON_IN_RANGE));

        // Fully enclosing filter
        predicate = new TimeslotRangePredicate(
                Optional.of(DATE_1), Optional.of(DATE_2), Optional.of(TIME_1), Optional.of(TIME_2));
        assertTrue(predicate.test(PERSON_IN_RANGE));

        // Overlap start time (filter 08:00-10:30)
        predicate = new TimeslotRangePredicate(
                Optional.empty(), Optional.empty(),
                Optional.of(LocalTime.of(8, 0)), Optional.of(LocalTime.of(10, 30)));
        assertTrue(predicate.test(PERSON_IN_RANGE));

        // Overlap end time (filter 10:30-12:00)
        predicate = new TimeslotRangePredicate(
                Optional.empty(), Optional.empty(),
                Optional.of(LocalTime.of(10, 30)), Optional.of(LocalTime.of(12, 0)));
        assertTrue(predicate.test(PERSON_IN_RANGE));
    }

}