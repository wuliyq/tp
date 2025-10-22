package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("friends"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("owesMoney"));

        FindTagCommand firstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand secondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // same values -> returns true
        FindTagCommand firstCommandCopy = new FindTagCommand(firstPredicate);
        assertEquals(firstCommand, firstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, firstCommand);

        // null -> returns false
        assertNotEquals(null, firstCommand);

        // different predicate -> returns false
        assertNotEquals(firstCommand, secondCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // No persons should match an empty keyword list
        assertEquals(0, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_multipleKeywords_filtersListSuccessfully() {
        // This test checks that the predicate filters successfully without depending on specific persons
        var keywords = Arrays.asList("friends", "owesMoney");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Ensure filtered list matches expected count
        assertEquals(expectedModel.getFilteredPersonList().size(), model.getFilteredPersonList().size());
    }
}
