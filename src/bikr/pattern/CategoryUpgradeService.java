package bikr.pattern;

import bikr.model.Racer;
import bikr.model.ResultEntry;
import bikr.model.enums.CategoryLevel;
import bikr.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/*
  This is the Observer pattern subject.
  After results are posted, it increases podium .
  When a racer reaches 5 podiums, they get promoted to next category
  and all observers are notified.
 */
public class CategoryUpgradeService implements UpgradeSubject {

    private static final int PODIUM_THRESHOLD = 5;

    private final List<UpgradeObserver> observers = new ArrayList<>();
    private final UserRepository userRepository;

    public CategoryUpgradeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void attach(UpgradeObserver observer) { observers.add(observer); }

    @Override
    public void detach(UpgradeObserver observer) { observers.remove(observer); }

    @Override
    public void notifyObservers(Racer racer, CategoryLevel newCategory) {
        for (UpgradeObserver o : observers) o.update(racer, newCategory);
    }

    //This gets called after RaceResultRepository saved the entries.
    public void processResult(List<ResultEntry> entries) {
        for (ResultEntry entry : entries) {
            if (!entry.isPodiumCounted()) continue;

            Racer racer = userRepository.findRacerById(entry.getRacerId());
            if (racer == null) continue;

            int newPodiums = racer.getCurrentPodiums() + 1;

            if (newPodiums >= PODIUM_THRESHOLD) {
                CategoryLevel newCategory = nextCategory(racer.getCategory());

                if (newCategory == null) {
                    //If already in CAT_1, just reset podiums
                    userRepository.updateRacerCategory(racer.getUserId(), racer.getCategory(), 0);
                } else {
                    userRepository.updateRacerCategory(racer.getUserId(), newCategory, 0);
                    racer.setCategory(newCategory);
                    racer.setCurrentPodiums(0);
                    notifyObservers(racer, newCategory);
                }
            } else {
                userRepository.updateRacerCategory(racer.getUserId(), racer.getCategory(), newPodiums);
                racer.setCurrentPodiums(newPodiums);
            }
        }
    }

    //used for each case to upgrade to next level
    private CategoryLevel nextCategory(CategoryLevel current) {
        switch (current) {
            case CAT_5: return CategoryLevel.CAT_4;
            case CAT_4: return CategoryLevel.CAT_3;
            case CAT_3: return CategoryLevel.CAT_2;
            case CAT_2: return CategoryLevel.CAT_1;
            case CAT_1: return null;
            default:    return null;
        }
    }
}