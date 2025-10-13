package seedu.address.storage;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.TimeSlot;

/**
 * TImeSlotStorage
 */
public interface TImeSlotStorage {
    /**
     * Add a TimeSlot to the storage if it does not conflict with existing slots.
     * @param slot
     * @return
     */
    boolean addSlot(TimeSlot slot);

    void loadExistingSlots(ReadOnlyAddressBook addressBook);
}
