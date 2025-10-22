package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TimeslotStartTimeContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTimeslotCommand object.
 */
public class FindTimeslotCommandParser implements Parser<FindTimeslotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTimeslotCommand
     * and returns a FindTimeslotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTimeslotCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTimeslotCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTimeslotCommand(new TimeslotStartTimeContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
