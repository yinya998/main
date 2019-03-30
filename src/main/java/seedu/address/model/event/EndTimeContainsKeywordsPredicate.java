package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Event}'s {@code end time} matches any of the keywords given.
 */
public class EndTimeContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EndTimeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getEndDateTime().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTimeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EndTimeContainsKeywordsPredicate) other).keywords)); // state check
    }

}
