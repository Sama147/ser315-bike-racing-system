package bikr;
/*
its for testing the database connections with the model by making a small that that does:
 Creates a UserRepository instance

        1- Inserts a hardcoded racer ("Sally Anderson") via insertRacer() and prints the generated user_id
        2- Calls findByEmail("sally@test.com") and prints the loaded racer's full name, category, and podium count
        and verifies the SELECT and the role-based object reconstruction
        3- Calls findRacerById(id) and prints the result — verifies lookup by primary key
        4- Calls updateRacerCategory(id, CAT_4, 0) then findRacerById(id) again — verifies the UPDATE and that the change persisted
        ONLY RUN IT ONCE btw if you want to test it yourself.
        the test passed for me but I decided to keep the file for you as reference into how the progam works and as a guide to helping you
        write your code part
 */

import bikr.model.Racer;
import bikr.repository.UserRepository;

public class UserRepoTest {
    public static void main(String[] args) {
        UserRepository repo = new UserRepository();

        // Insert a racer
        Racer r = new Racer("Sally", "Anderson", "sally@test.com", "123456789", "pass123");
        int id = repo.insertRacer(r);
        System.out.println("Inserted racer with id = " + id);

        // Find by email
        Racer loaded = (Racer) repo.findByEmail("sally@test.com");
        System.out.println("Found: " + loaded.getFullName()
                + " | category: " + loaded.getCategory()
                + " | podiums: " + loaded.getCurrentPodiums());

        // Find by id
        Racer byId = repo.findRacerById(id);
        System.out.println("By id: " + byId.getFullName());

        // Update category
        repo.updateRacerCategory(id, bikr.model.enums.CategoryLevel.CAT_4, 0);
        Racer updated = repo.findRacerById(id);
        System.out.println("Updated category: " + updated.getCategory());
    }
}