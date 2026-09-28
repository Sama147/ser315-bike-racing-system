package bikr.pattern;

import bikr.model.Racer;
import bikr.model.enums.CategoryLevel;

/*
 Observer observer design pattern
  Reacts to a category upgrade by printing a notification message.
  Doesn't touch the database but just produces the console output the
  user sees when their racer is promoted.
  it's a console print so the pattern is visible during the demo.
 */
public class RacerNotifyObserver implements UpgradeObserver {
    @Override
    public void update(Racer racer, CategoryLevel newCategory) {
        System.out.println("Notification: Racer " + racer.getFullName()
                + " has been promoted to " + newCategory + "!");
    }
}