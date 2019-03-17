package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.event.Event;

public class Reminder {
    private Event event;
    private String message;
    private String name;

    public Reminder(Event event, String message){
        requireAllNonNull(event,message);
        this.event=event;
        this.message=message;
    }

    public Reminder(String name, String message){
        requireAllNonNull(name,message);
        this. name =name;
        this.message=message;
    }

    public Reminder (Reminder source){
        this(source.getEvent(),source.getMessage());
    }

    public void setMessage(String message){
        requireAllNonNull(message);
        this.message=message;
    }

    public String getMessage(){
        return message;
    }

    public String getName(){
        return name;
    }
    //TODO: implement getEvent, which needs a get() in event class, event file
    public Event getEvent(){
        return event;
    }
    @Override
    public boolean equals(Object other){
        if(other==this){
            return true;
        }

        if (other instanceof Reminder && this.equals((Reminder)other) ){
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(event, message);
    }


}
