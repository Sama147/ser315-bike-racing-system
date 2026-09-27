package bikr.pattern;
import bikr.model.Racer;
import bikr.model.enums.CategoryLevel;

/*
  This is the Observer interface design pattern
  Contract for objects that want to be notified when a racer's
  category is upgraded. The Subject (CategoryUpgradeService) holds
  a list of these and calls update() on each when the event fires.
  Implementations: RacerNotifyObserver, LicenseCategoryObserver.
 */
public interface UpgradeObserver {
    void update(Racer racer, CategoryLevel newCategory);
}