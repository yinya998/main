package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Event}'s {@code label} matches any of the keywords given.
 */
public class LabelContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public LabelContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getLabel().labelName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LabelContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((LabelContainsKeywordsPredicate) other).keywords)); // state check
    }

}
