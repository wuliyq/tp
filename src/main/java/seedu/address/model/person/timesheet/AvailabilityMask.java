package seedu.address.model.person.timesheet;

import java.util.BitSet;

/**
 * Represents a compact weekly availability grid using bits.
 * Each bit represents a fixed time interval (e.g. 15 minutes).
 * 1 = available, 0 = unavailable.
 *
 * This mask is lightweight, memory efficient, and optimized for fast
 * availability operations (bit operation to find common availability
 * It is serialisable to/from a binary string, which makes it easy to store.
 */

public final class AvailabilityMask {

    private final BitSet bits; // bit 1 = available
    private final int size;    // total number of time slots (e.g. 672 for 15-minute bins)

    /**
     * Creates an empty availability mask (all values default to false/unavailable).
     *
     * @param size number of time slots in the mask
     */
    public AvailabilityMask(int size) {
        this.size = size;
        this.bits = new BitSet(size);
    }

    /**
     * Creates an empty mask of the given size.
     */
    public static AvailabilityMask empty(int size) {
        return new AvailabilityMask(size);
    }

    /**
     * @return total number of slots in this mask
     */
    public int size() {
        return size;
    }

    /**
     * Sets a single timeslot as available/unavailable.
     *
     * @param index index of the timeslot
     * @param available true = available, false = unavailable
     */
    public void set(int index, boolean available) {
        check(index);
        if (available) {
            bits.set(index);
        } else {
            bits.clear(index);
        }
    }

    /**
     * Sets a range of slots [fromInclusive, toExclusive) as available/unavailable.
     *
     * @param fromInclusive starting index (inclusive)
     * @param toExclusive ending index (exclusive)
     * @param available true = available, false = unavailable
     */
    public void setRange(int fromInclusive, int toExclusive, boolean available) {
        if (fromInclusive < 0 || toExclusive > size || fromInclusive > toExclusive) {
            throw new IllegalArgumentException("Invalid range");
        }
        if (available) {
            bits.set(fromInclusive, toExclusive);
        } else {
            bits.clear(fromInclusive, toExclusive);
        }
    }

    /**
     * Returns whether a specific slot is available.
     */
    public boolean get(int index) {
        check(index);
        return bits.get(index);
    }

    /**
     * @return number of available slots (bit count)
     */
    public int cardinality() {
        return bits.cardinality();
    }

    /**
     * Serializes the mask into a fixed-length binary string.
     * Example: "001101001..." (length = size)
     */
    public String toBinaryString() {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(bits.get(i) ? '1' : '0');
        }
        return sb.toString();
    }

    /**
     * Restores a mask from a binary string.
     * The length of the string determines the mask size.
     */
    public static AvailabilityMask fromBinaryString(String s) {
        AvailabilityMask m = new AvailabilityMask(s.length());
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                m.bits.set(i);
            }
        }
        return m;
    }

    /**
     * Ensures index is within bounds.
     */
    private void check(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of range: " + index);
        }
    }
}



