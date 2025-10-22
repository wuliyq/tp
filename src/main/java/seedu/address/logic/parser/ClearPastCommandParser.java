package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClearPastCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearPastCommand object
 */
public class ClearPastCommandParser implements Parser<ClearPastCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearPastCommand
     * and returns a ClearPastCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearPastCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearPastCommand.MESSAGE_USAGE));
        }
        return new ClearPastCommand();
    }
}
