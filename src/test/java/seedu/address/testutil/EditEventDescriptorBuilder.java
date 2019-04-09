package seedu.address.testutil;

import seedu.address.logic.commands.EditECommand.EditEventDescriptor;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setName(event.getName());
        descriptor.setDescription(event.getDescription());
        descriptor.setVenue(event.getVenue());
        descriptor.setStartDateTime(event.getStartDateTime());
        descriptor.setEndDateTime(event.getEndDateTime());
        descriptor.setLabel(event.getLabel());
        descriptor.setReminders(event.getReminders());
        descriptor.setPersons(event.getPersons());
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new Venue(venue));
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withStartDateTime(String startDateTime) {
        descriptor.setStartDateTime(new DateTime(startDateTime));
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEndDateTime(String endDateTime) {
        descriptor.setEndDateTime(new DateTime(endDateTime));
        return this;
    }

    /**
     * Sets the {@code Label} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withLabel(String label) {
        descriptor.setLabel(new Label(label));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
