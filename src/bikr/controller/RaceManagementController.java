package bikr.controller;

import bikr.view.OrganizerMainView;
import bikr.view.OrganizerRaceManagementView;

import bikr.model.Race;
import bikr.model.enums.RaceType;
import bikr.pattern.RaceBuilder;
import bikr.repository.RaceRepository;

import java.time.LocalDate;
import java.util.Scanner;

public class RaceManagementController {

    private final OrganizerMainView organizerMainView;
    private final OrganizerRaceManagementView raceManagementView;

    private final RaceRepository raceRepository;

    public RaceManagementController(RaceRepository raceRepository) {
        this.organizerMainView = new OrganizerMainView();
        this.raceManagementView = new OrganizerRaceManagementView();

        this.raceRepository = raceRepository;
    }

    public boolean createRace(int organizerId, Scanner scanner) {
        System.out.println("\n--- Create New Race ---");
        organizerMainView.clickCreateRace();
        raceManagementView.displayRaceForm();

        System.out.print("Enter Race Name: ");
        String raceName = scanner.nextLine().trim();

        System.out.print("Enter Race Date (YYYY-MM-DD): ");
        String raceDateStr = scanner.nextLine().trim();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine().trim();

        if (raceName.isEmpty() || raceDateStr.isEmpty()) {
            System.out.println("\n[ERROR] Race creation failed: Name and Date are required.");
            return false;
        }

        LocalDate raceDate = LocalDate.parse(raceDateStr);

        System.out.println("Select Race Type: 1) ROAD_RACE  2) CRITERIUM  3) TIME_TRIAL  4) GRAVEL");
        System.out.print("Choice: ");
        RaceType raceType = parseRaceType(scanner.nextLine().trim());

        System.out.print("Is this an official race? (y/n): ");
        boolean officiality = scanner.nextLine().trim().equalsIgnoreCase("y");

        System.out.print("Enter Race Miles: ");
        double miles = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Enter Route Description: ");
        String route = scanner.nextLine().trim();

        System.out.print("Enter Max Registrations: ");
        int maxRegistrations = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter Last Day to Register (YYYY-MM-DD): ");
        LocalDate lastDayToRegister = LocalDate.parse(scanner.nextLine().trim());

        Race race = new RaceBuilder(raceName, raceDate)
                .setType(raceType)
                .setOfficiality(officiality)
                .setMiles(miles)
                .setRoute(route)
                .setLocation(location)
                .setMaxRegistrations(maxRegistrations)
                .setLastDayRegistrations(lastDayToRegister)
                .build();

        race.setOrganizerId(organizerId);
        int raceId = raceRepository.insert(race);

        System.out.println("\n[SUCCESS] Race '" + raceName + "' successfully created (race_id=" + raceId
                + ") on " + raceDate + " at " + location + "!");
        return true;
    }

    private RaceType parseRaceType(String choice) {
        switch (choice) {
            case "1": return RaceType.ROAD;
            case "2": return RaceType.CRITERIUM;
            case "3": return RaceType.TIME_TRIAL;
            case "4": return RaceType.GRAVEL;
            default:
                System.out.println("Unrecognized choice, defaulting to ROAD_RACE.");
                return RaceType.ROAD;
        }
    }

    public void editRace() {
        // Stubbed feature.
        System.out.println("Editing race via RaceManagementController...");
    }

    public void cancelRace() {
        // Stubbed feature.
        System.out.println("Cancelling race via RaceManagementController...");
    }

    public void getOrganizedRaces() {
        // Stubbed feature.
        System.out.println("Retrieving organized races via RaceManagementController...");
    }
}
