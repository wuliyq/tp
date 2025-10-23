package seedu.address.storage;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.TimeSlot;

/**
 * TimeSlotStorage
 */
public interface TimeSlotStorage {
    /**
     * Add a TimeSlot to the storage if it does not conflict with existing slots.
     * @param slot
     * @return
     */
    boolean addSlot(TimeSlot slot);

    /**
     * Removes a TimeSlot from the storage.
     * Assumes the slot exists.
     * @param slot
     */
    void removeSlot(TimeSlot slot);

    void loadExistingSlots(ReadOnlyAddressBook addressBook);
}
