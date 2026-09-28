Bikr: Bike Racing Registration System

A Java console application built for SER315. Demonstrates MVC architecture,
the Builder pattern, the Observer pattern, and a JDBC repository layer over
an embedded SQLite database.

## What the program does

The demo runs a scripted end-to-end flow (no user input required):

1. A racer is created in the database with 4 podium finishes
2. The racer registers for a race
3. An organizer posts results — the racer finishes 2nd (a podium)
4. The podium count reaches 5, triggering an automatic category upgrade:
   - `RacerNotifyObserver` prints a notification
   - `LicenseCategoryObserver` updates the license row in the database
5. The final state is printed: racer category upgraded, podiums reset,
   license category matches

## Design patterns demonstrated


**Builder**  `src/bikr/pattern/RaceBuilder.java` and constructs `Race` objects step-by-step 
 **Observer**  `src/bikr/pattern/` — `CategoryUpgradeService` fires upgrade events to `RacerNotifyObserver` and `LicenseCategoryObserver` 
 **MVC**  `model/` (data), `view/` (terminal I/O), `controller/` (logic) |
 **Repository / DAO**  `src/bikr/repository/` — all SQL lives here, nothing else in the app touches the DB 

## Requirements

- JDK 17 or newer
- IntelliJ IDEA (or any Java IDE)
- No database installation needed — SQLite is embedded

## How to run

1. Clone the repo
2.  Open the folder in IntelliJ:
- **File → Open** → select the project folder
3. Add the SQLite JDBC driver to the project:
- **File → Project Structure → Libraries → `+` → Java**
- Select `lib/sqlite-jdbc-3.53.4.0.jar`
- Apply → OK
4. Run the demo:
- Open `src/bikr/Main.java`
- Right-click inside the file → **Run 'Main.main()'**
5. The database (`bikr.db`) is created automatically on first run with
schema and seed data. Delete the file to reset.
