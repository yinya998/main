package seedu.address.logic.parser;

import static seedu.address.logic.commands.FindCommand.MESSAGE_NO_PARAMETER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordPredicate;
import seedu.address.model.person.EmailContainsKeywordPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordPredicate;
import seedu.address.model.person.TagsContainsKeywordPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * check if there's a prefix in the command
     */
    private boolean hasPrefix(String command) {
        String[] commands = command.split("\\s+");

        return (commands[0].contains(PREFIX_NAME.toString()) || commands[0].contains(PREFIX_EMAIL.toString())
                || commands[0].contains(PREFIX_ADDRESS.toString())
                || commands[0].contains(PREFIX_PHONE.toString())
                || commands[0].contains(PREFIX_TAG.toString()));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_NO_PARAMETER, FindCommand.MESSAGE_USAGE));
        }


        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        ArrayList<Predicate<Person>> predicates = new ArrayList<>();
        Predicate<Person> predicateResult;

        // if there's no prefix, find in all fields
        if (!hasPrefix(trimmedArgs)) {
            String[] splitedKeywords = trimmedArgs.split("\\s+");

            predicates.add(new NameContainsKeywordsPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new PhoneContainsKeywordPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new EmailContainsKeywordPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new AddressContainsKeywordPredicate(Arrays.asList(splitedKeywords)));
            predicates.add(new TagsContainsKeywordPredicate(Arrays.asList(splitedKeywords)));

            Predicate<Person>[] predicatesList = predicates.toArray(new Predicate[predicates.size()]);
            predicateResult = Stream.of(predicatesList).reduce(condition -> false, Predicate::or);

            return new FindCommand(predicateResult);
        }

        // create find Command according to the specific prefix
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameList = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            predicates.add(new NameContainsKeywordsPredicate(Arrays.asList(nameList)));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailList = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            predicates.add(new EmailContainsKeywordPredicate(Arrays.asList(emailList)));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] phoneList = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");
            predicates.add(new PhoneContainsKeywordPredicate(Arrays.asList(phoneList)));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String[] addressList = argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+");
            predicates.add(new AddressContainsKeywordPredicate(Arrays.asList(addressList)));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String[] tagList = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            predicates.add(new TagsContainsKeywordPredicate(Arrays.asList(tagList)));
        }


        Predicate<Person>[] predicatesList = predicates.toArray(new Predicate[predicates.size()]);
        predicateResult = Stream.of(predicatesList).reduce(condition -> true, Predicate::and);

        return new FindCommand(predicateResult);

    }
}
