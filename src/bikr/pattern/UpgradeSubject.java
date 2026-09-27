package bikr.pattern;
import bikr.model.Racer;
import bikr.model.enums.CategoryLevel;

/*
   DESIGN PATTERN: Observer (concrete subject)
  
   Tracks racer podium counts and triggers category upgrades.
   Business rule: 5 podiums in a category => promote to the next category
   and reset podiums to 0.
  
   When a promotion happens, all registered observers are notified via
   notifyObservers(). Observers decide what to do (print, update DB, etc.).
  
   Flow:
     ResultController.postResults()
       -> save RaceResult + ResultEntry rows
       -> call processResult(entries)   [this class]
          -> for each podium-winning entry, increment the racer's count
          -> if count >= 5: promote + notifyObservers()
               -> RacerNotifyObserver.update()          (console)
               -> LicenseCategoryObserver.update()      (DB)
  
   Adding a new reaction = implement UpgradeObserver + attach() it in Main.
   No changes needed to this class.
  */
public interface UpgradeSubject {
    void attach(UpgradeObserver observer);
    void detach(UpgradeObserver observer);
    void notifyObservers(Racer racer, CategoryLevel newCategory);
}