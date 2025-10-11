package seedu.address.model.person.timesheet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents one person's weekly availability timesheet.
 * The week is divided into fixed time slots (e.g. 15 minutes each),
 * and availability is stored using a compact {@link AvailabilityMask}.
 *
 * This class is immutable in identity (person + week) but mutable in content,
 * since availability can change using markAvailable()/markUnavailable().
 */
public final class PersonTimesheet {

    private final String personId;       // Reference to a Person. Later need to add in unique hashcode for each person
    private final WeekIndex weekIndex;   // Identifies which week this timesheet belongs to
    private final AvailabilityMask mask; // 1-bit availability grid for the week

    /**
     * Creates an empty timesheet for the given person and week.
     * All time slots are initially marked unavailable.
     */
    public PersonTimesheet(String personId, WeekIndex weekIndex) {
        this.personId = Objects.requireNonNull(personId);
        this.weekIndex = Objects.requireNonNull(weekIndex);
        this.mask = AvailabilityMask.empty(WeekIndex.BINS_PER_WEEK);
    }

    /**
     * Creates a timesheet with an existing mask (used by Storage during loading).
     */
    public PersonTimesheet(String personId, WeekIndex weekIndex, AvailabilityMask mask) {
        this.personId = Objects.requireNonNull(personId);
        this.weekIndex = Objects.requireNonNull(weekIndex);
        this.mask = Objects.requireNonNull(mask);

        if (mask.size() != WeekIndex.BINS_PER_WEEK) {
            throw new IllegalArgumentException("Mask size must be " + WeekIndex.BINS_PER_WEEK);
        }
    }

    /**
     * Factory method: restores a timesheet from binary availability string.
     * This assumes weekStartMonday + maskBinary is stored externally.
     */
    public static PersonTimesheet fromBinary(String personId, LocalDate weekStartMonday, String binary) {
        WeekIndex wi = new WeekIndex(weekStartMonday);
        AvailabilityMask m = AvailabilityMask.fromBinaryString(binary);
        return new PersonTimesheet(personId, wi, m);
    }

    /**
     * Serializes this timesheet to a binary string (672 chars) for Storage.
     */
    public String toBinary() {
        return mask.toBinaryString();
    }

    /**
     * Marks a time range [start, end) as available.
     * @param start inclusive
     * @param end exclusive
     */
    public void markAvailable(LocalDateTime start, LocalDateTime end) {
        int a = weekIndex.toIndex(start);
        int b = weekIndex.toIndex(end);
        mask.setRange(a, b, true);
    }

    /**
     * Marks a time range [start, end) as unavailable.
     * @param start inclusive
     * @param end exclusive
     */
    public void markUnavailable(LocalDateTime start, LocalDateTime end) {
        int a = weekIndex.toIndex(start);
        int b = weekIndex.toIndex(end);
        mask.setRange(a, b, false);
    }

    /**
     * Returns true if the entire time range [start, end) is available.
     */
    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        int a = weekIndex.toIndex(start);
        int b = weekIndex.toIndex(end);
        for (int i = a; i < b; i++) {
            if (!mask.get(i)) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------
    // Getters
    // ------------------------------------------------------------

    public String getPersonId() {
        return personId;
    }

    public WeekIndex getWeekIndex() {
        return weekIndex;
    }

    public AvailabilityMask getMask() {
        return mask;
    }

    public LocalDate getWeekStartMonday() {
        return weekIndex.getWeekStartMonday();
    }

    // ------------------------------------------------------------
    // Compact Encode/Decode (safe for JSON Storage)
    // ------------------------------------------------------------

    /**
     * Encodes this timesheet as a compact string "weekStart|binaryMask".
     * Example: "2025-10-13|000011110000..."
     */
    public String toEncodedString() {
        return weekIndex.getWeekStartMonday().toString() + "|" + mask.toBinaryString();
    }

    /**
     * Decodes a compact encoded timesheet string back into a PersonTimesheet.
     */
    public static PersonTimesheet fromEncodedString(String personId, String encoded) {
        String[] parts = encoded.split("\\|", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid encoded timesheet: " + encoded);
        }
        LocalDate weekStart = LocalDate.parse(parts[0]);
        String binary = parts[1];
        return fromBinary(personId, weekStart, binary);
    }
}



