package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.logic.commands.FilterTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TimeslotRangePredicate;

/**
 * Parses input arguments and creates a new FilterTimeslotCommand object
 */
public class FilterTimeslotCommandParser implements Parser<FilterTimeslotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterTimeslotCommand
     * and returns a FilterTimeslotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTimeslotCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTimeslotCommand.MESSAGE_USAGE));
        }

        // We need to check if at least one prefix is present
        if (argMultimap.getValue(PREFIX_START_DATE).isEmpty()
                && argMultimap.getValue(PREFIX_END_DATE).isEmpty()
                && argMultimap.getValue(PREFIX_START_TIME).isEmpty()
                && argMultimap.getValue(PREFIX_END_TIME).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTimeslotCommand.MESSAGE_USAGE));
        }

        // Parse all the optional values
        Optional<LocalDate> startDate = Optional.empty();
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            startDate = Optional.of(ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get()));
        }

        Optional<LocalDate> endDate = Optional.empty();
        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            endDate = Optional.of(ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get()));
        }

        Optional<LocalTime> startTime = Optional.empty();
        if (argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
            startTime = Optional.of(ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get()));
        }

        Optional<LocalTime> endTime = Optional.empty();
        if (argMultimap.getValue(PREFIX_END_TIME).isPresent()) {
            endTime = Optional.of(ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get()));
        }

        // Add validation logic
        if (startDate.isPresent() && endDate.isPresent() && startDate.get().isAfter(endDate.get())) {
            throw new ParseException("Start date must be before or on end date.");
        }

        // Add validation logic
        if (startDate.isPresent() && endDate.isPresent() && startDate.get().isAfter(endDate.get())) {
            throw new ParseException("Start date must be before or on end date.");
        }

        if (startTime.isPresent() && endTime.isPresent() && startTime.get().isAfter(endTime.get())) {
            throw new ParseException("Start time must be before or on end time.");
        }

        TimeslotRangePredicate predicate = new TimeslotRangePredicate(
                startDate, endDate, startTime, endTime);

        return new FilterTimeslotCommand(predicate);
    }
}
