package bikr.pattern;

import bikr.model.Racer;
import bikr.model.enums.CategoryLevel;
import bikr.repository.LicenseRepository;

/*
 Observer design pattern
 Reacts to a category upgrade by updating the racer's license row
 in the database. Handles the persistence side effect of a promotion.
 Kept separate from RacerNotifyObserver so the two responsibilities
 (notify user vs. persist change) don't mix. Adding a new reaction
 means adding a new observer, not editing an existing one.
 Receives LicenseRepository via constructor injection.
 */
public class LicenseCategoryObserver implements UpgradeObserver {

    private final LicenseRepository licenseRepository;

    public LicenseCategoryObserver(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Override
    public void update(Racer racer, CategoryLevel newCategory) {
        licenseRepository.updateCategory(racer.getUserId(), newCategory);
        System.out.println(">>> License updated for " + racer.getFullName()
                + " to " + newCategory);
    }
}