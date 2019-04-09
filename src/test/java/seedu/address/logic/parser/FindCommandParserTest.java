package seedu.address.logic.parser;

import static seedu.address.logic.commands.FindCommand.MESSAGE_NO_PARAMETER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", MESSAGE_NO_PARAMETER + '\n' + FindCommand.MESSAGE_USAGE);
    }

    /*@Test
    public void parse_validArgs_returnsFindCommand() {
        String Command = "n/Alice Bob";

        String[] splitedKeywords = Command.trim().split("\\s+");
        ArrayList<Predicate<Person>> predicates = new ArrayList<>();

        predicates.add(new NameContainsKeywordsPredicate(Arrays.asList(splitedKeywords)));


        Predicate<Person>[] predicatesList = predicates.toArray(new Predicate[predicates.size()]);
        Predicate<Person> PredicateResult = Stream.of(predicatesList).reduce(condition -> false, Predicate::or);
        no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(PredicateResult);
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommand);

        multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }
*/
}
