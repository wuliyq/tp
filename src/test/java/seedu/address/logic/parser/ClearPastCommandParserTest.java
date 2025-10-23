package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearPastCommand;

public class ClearPastCommandParserTest {

    private ClearPastCommandParser parser = new ClearPastCommandParser();

    @Test
    public void parse_validArgs_returnsClearPastCommand() {
        // The command takes no arguments, so empty string is valid
        assertParseSuccess(parser, "", new ClearPastCommand());

        // Also valid with whitespace
        assertParseSuccess(parser, "   ", new ClearPastCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Any arguments should cause a failure
        assertParseFailure(parser, "some random args",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearPastCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearPastCommand.MESSAGE_USAGE));
    }
}
