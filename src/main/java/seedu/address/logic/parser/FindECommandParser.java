package seedu.address.logic.parser;

import static seedu.address.logic.commands.FindECommand.MESSAGE_NO_PARAMETER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindECommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DescriptionContainsKeywordsPredicate;
import seedu.address.model.event.EndTimeContainsKeywordsPredicate;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.event.LabelContainsKeywordsPredicate;
import seedu.address.model.event.StartTimeContainsKeywordsPredicate;
import seedu.address.model.event.VenueContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindECommand object
 */
public class FindECommandParser implements Parser<FindECommand> {

    /**
     * check if there's a prefix in the command
     */
    private boolean hasPrefix(String command) {
        String[] commands = command.split("\\s+");

        return (commands[0].contains(PREFIX_NAME.toString())
                || commands[0].contains(PREFIX_DESCRIPTION.toString())
                || commands[0].contains(PREFIX_VENUE.toString())
                || commands[0].contains(PREFIX_START_TIME.toString())
                || commands[0].contains(PREFIX_END_TIME.toString()))
                || commands[0].contains(PREFIX_LABEL.toString());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindECommand
     * and returns an FindECommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindECommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_NO_PARAMETER, FindECommand.MESSAGE_USAGE));
        }

        //String[] nameKeywords = trimmedArgs.split("\\s+");
        //return new FindECommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_LABEL);
        ArrayList<Predicate<Event>> predicates = new ArrayList<>();
        Predicate<Event> predicateResult;

        // if there's no prefix, find in all fields
        if (!hasPrefix(trimmedArgs)) {
            String[] splitedKeywords = trimmedArgs.split("\\s+");

            predicates.add(new EventNameContainsKeywordsPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new DescriptionContainsKeywordsPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new VenueContainsKeywordsPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new StartTimeContainsKeywordsPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new EndTimeContainsKeywordsPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new LabelContainsKeywordsPredicate(Arrays.asList(splitedKeywords)));


            Predicate<Event>[] predicatesList = predicates.toArray(new Predicate[predicates.size()]);
            predicateResult = Stream.of(predicatesList).reduce(condition -> false, Predicate::or);

            return new FindECommand(predicateResult);
        }

        // create findE Command according to the specific prefix
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] eventNameList = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            predicates.add(new EventNameContainsKeywordsPredicate(Arrays.asList(eventNameList)));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            String[] descriptionList = argMultimap.getValue(PREFIX_DESCRIPTION).get().split("\\s+");
            predicates.add(new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionList)));
        }

        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            String[] venueList = argMultimap.getValue(PREFIX_VENUE).get().split("\\s+");
            predicates.add(new VenueContainsKeywordsPredicate(Arrays.asList(venueList)));
        }

        if (argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
            String[] startTimeList = argMultimap.getValue(PREFIX_START_TIME).get().split("\\s+");
            predicates.add(new StartTimeContainsKeywordsPredicate(Arrays.asList(startTimeList)));
        }

        if (argMultimap.getValue(PREFIX_END_TIME).isPresent()) {
            String[] endTimeList = argMultimap.getValue(PREFIX_END_TIME).get().split("\\s+");
            predicates.add(new EndTimeContainsKeywordsPredicate(Arrays.asList(endTimeList)));
        }

        if (argMultimap.getValue(PREFIX_LABEL).isPresent()) {
            String[] labelList = argMultimap.getValue(PREFIX_LABEL).get().split("\\s+");
            predicates.add(new LabelContainsKeywordsPredicate(Arrays.asList(labelList)));
        }

        Predicate<Event>[] predicatesList = predicates.toArray(new Predicate[predicates.size()]);
        predicateResult = Stream.of(predicatesList).reduce(condition -> true, Predicate::and);

        return new FindECommand(predicateResult);

    }
}
