package seedu.address.logic;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderList;

/**
 *Represent the thread that reminder checking use
 */
public class ReminderCheck implements Runnable {
    private Thread t;
    private Model model;
    private List<Reminder> lastShownReminder;
    ReminderCheck(Model model) {
        this.model = model;
        //lastShownReminder = model.getFilteredReminderList();
    }

    /**
     * The inner logic of reminder check. Did a while loop to check which reminders should be shown, which reminders
     * should be deleted.
     */
    public void run() {
        System.out.println("reminder testing thread is running now");
        try {
            while (true) {
                System.out.println("one model passing");
                lastShownReminder = this.model.getFilteredReminderList();
                for (int i = 0; i < lastShownReminder.size(); i++) {
                    Reminder r = lastShownReminder.get(i);
                    //System.out.println("compare result"+r.compareWithCurrentTime());
                    if ( r.compareWithCurrentTime()) {
                        r.setShow(true);
                    }else if( !r.compareWithCurrentTime() && r.deleteReminder()) {
                        //the reminder should end.
                        r.setNotShow(true);
                    }
                    if (r.getNotShow() ) {model.deleteReminder(r);}
                }
                //model.commitAddressBook();
                for (int i = 0; i < model.getAddressBook().getReminderList().size(); i++){
                    ReminderList temp = model.getAddressBook().getReminderListTest();
                    System.out.println("name is" + temp.get(i).getName());
                    System.out.println("message is" + temp.get(i).getMessage());
                    System.out.println("should the reminder be shown now?" + temp.get(i).getShow());
                    System.out.println("should the reminder be deleted?" + temp.get(i).getNotShow());
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
