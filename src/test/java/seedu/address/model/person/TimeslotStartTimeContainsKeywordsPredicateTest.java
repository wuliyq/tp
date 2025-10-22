package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TimeslotStartTimeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        var firstPredicate = new TimeslotStartTimeContainsKeywordsPredicate(Collections.singletonList("2025-10-12"));
        var secondPredicate = new TimeslotStartTimeContainsKeywordsPredicate(Collections.singletonList("08:00"));

        assertTrue(firstPredicate.equals(firstPredicate));

        var firstPredicateCopy = new TimeslotStartTimeContainsKeywordsPredicate(
                Collections.singletonList("2025-10-12"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasMatchingDate_returnsTrue() {
        var predicate = new TimeslotStartTimeContainsKeywordsPredicate(Collections.singletonList("2025-10-12"));
        Person person = new PersonBuilder()
                .withName("Alice")
                .withTimeSlot("2025-10-12 0800-0900")
                .build();

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personHasMatchingStartTime_returnsTrue() {
        var predicate = new TimeslotStartTimeContainsKeywordsPredicate(Collections.singletonList("0800"));
        Person person = new PersonBuilder()
                .withTimeSlot("2025-10-13 0800-0900")
                .build();

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personHasMatchingDateAndTime_returnsTrue() {
        var predicate = new TimeslotStartTimeContainsKeywordsPredicate(Arrays.asList("2025-10-12", "0800"));
        Person person = new PersonBuilder()
                .withTimeSlot("2025-10-12 0800-0900")
                .build();

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personHasDateButDifferentTime_returnsFalse() {
        var predicate = new TimeslotStartTimeContainsKeywordsPredicate(Arrays.asList("2025-10-12", "1000"));
        Person person = new PersonBuilder()
                .withTimeSlot("2025-10-12 0800-0900")
                .build();

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_noKeywords_returnsFalse() {
        var predicate = new TimeslotStartTimeContainsKeywordsPredicate(Collections.emptyList());
        Person person = new PersonBuilder()
                .withTimeSlot("2025-10-12 0800-0900")
                .build();

        assertFalse(predicate.test(person));
    }
}


