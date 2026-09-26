package bikr;

public class notes {

    /*
    ***************IN ENUMS
    - everything is capital
    - UI star ratings take int values (1,2,3,4,5),we need translate them to ONE,TWO,THREE,FOUR,FIVE respectively

    ***************IN USER CLASS
     - need to update MVC diagram and its applied 2 deisgn patterns version this field : String ssn and rename passwordHash field to password

     - need to update MVC diagram with DP to add: Date postedDate, and String LiscenseNumber, change Stirng id to String LiscenseId
     - need to update it in Organizer model , remove the int organizerId (its already in Users)
     - in Racer change totalPoduims to "currentPodiums"
     - in RaceResult add : int resultId
     - in Registration add : int registrationId
     - in ResultEntry add: int entryId
     - in Review add : int reviewId
     - in SystemSettings add : int settingId
     * *
     *
    *****************FOR REPOSITORY DIRECTORY AND WHAT IS INSIDE THEM
    basically im using sql lite for faster adoption of database and also it will run with the program when graders and peers run the program
    so that no one would need to connect mysql and take the schema and run it to run the program
    in sql lite we need a way to translate the java code pulls from the database and the database
    that is the job of repositories. think of it as our translator to contact the database

    ***************HERE ARE EXAMPLES OF HOW YOU PULL THINGS FROM THE DATABASE WHEN YOU MAKE THE CONTROLLERS

    int id = userRepository.insertRacer(someRacer);
    User u = userRepository.findByEmail("sally@test.com");
    int insertRacer(Racer r)              // for sign-up
    int insertOrganizer(Organizer o)      // for organizer sign-up
    User findByEmail(String email)        // for sign-in
    User findBySsn(String ssn)            // for admin sign-in
    Racer findRacerById(int id)           // for results + upgrade
    void updateRacerCategory(int userId, CategoryLevel newCat, int newPodiums)  // for observer

    int insert(Race race)                 // organizer creates race (stub path)
    List<Race> findAll()                  // for the race list
    Race findById(int id)
    int countRegistrations(int raceId)    // for (x / y) seat count

    int insert(Registration r)
    List<Registration> findByRace(int raceId)   // who's in the race
    boolean isFull(int raceId)                  // capacity check

    int insert(AccessRequest ar)
    List<AccessRequest> findPending()           // for admin queue
    void updateStatus(int requestId, RequestStatus s)

    int insertResult(RaceResult rr)
    int insertEntry(ResultEntry re)
    List<ResultEntry> findEntriesByResult(int resultId)

    int insert(License l)
    License findByUserId(int userId)
    void updateCategory(int userId, CategoryLevel newCat)   // for observer


    IN ADDITION, THE ONLY CODE WRITTEN IN REPOSITORIES DIR THAT WE ARE GONNA NEED ARE THESE FILES:
    UserRepository.java — the biggest one, gets you going
    RaceRepository.java — needed for the race list and capacity
    RegistrationRepository.java — for the sign-up scenario
    AccessRequestRepository.java — for admin approvals
    RaceResultRepository.java — for post-results
    LicenseRepository.java — for the observer update
    THESE AREN'T WRITTEN YET:
    PaymentProfileRepository
    ReviewRepository
    SystemSettingsRepository

    HERE ARE ASLO SCENARIO BASED CALLINGS:

    //For Racer sign-up (in AuthController):
    Racer r = new Racer(first, last, email, ssn, password);
    int newId = userRepository.insertRacer(r);
    Sign-in:

    //User u = userRepository.findByEmail(email);
    if (u == null) { //account does not exist // }
    else if (!u.getPassword().equals(password)) { // wrong credentials // }
            else { //welcome // }

    //Admin sign-in:
    User u = userRepository.findBySsn(ssn);

    //Observer upgrade:
    Racer r = userRepository.findRacerById(userId);
    userRepository.updateRacerCategory(userId, newCategory, 0);



    ********FOR userRepoTest.java in the bikr directory,
     */

}
