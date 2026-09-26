CREATE TABLE IF NOT EXISTS users (
                                     user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     role TEXT NOT NULL,
                                     first_name TEXT,
                                     last_name TEXT,
                                     email TEXT UNIQUE,
                                     ssn TEXT,
                                     password TEXT
);

CREATE TABLE IF NOT EXISTS racers (
                                      user_id INTEGER PRIMARY KEY,
                                      current_podiums INTEGER DEFAULT 0,
                                      category TEXT DEFAULT 'CAT_5',
                                      FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE IF NOT EXISTS organizers (
                                          user_id INTEGER PRIMARY KEY,
                                          FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE IF NOT EXISTS administrators (
                                              user_id INTEGER PRIMARY KEY,
                                              FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE IF NOT EXISTS payment_profiles (
                                                profile_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                                user_id INTEGER,
                                                card_number TEXT,
                                                card_expiration TEXT,
                                                FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE IF NOT EXISTS licenses (
                                        license_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        user_id INTEGER,
                                        expiration_date TEXT,
                                        category TEXT,
                                        FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE IF NOT EXISTS access_requests (
                                               request_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                               user_id INTEGER,
                                               status TEXT DEFAULT 'PENDING',
                                               FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE IF NOT EXISTS races (
                                     race_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     organizer_id INTEGER,
                                     race_name TEXT,
                                     race_officiality INTEGER,
                                     race_date TEXT,
                                     race_type TEXT,
                                     race_miles REAL,
                                     race_route TEXT,
                                     race_location TEXT,
                                     race_max_registrations INTEGER,
                                     race_last_day_registrations TEXT,
                                     FOREIGN KEY (organizer_id) REFERENCES users(user_id)
    );

CREATE TABLE IF NOT EXISTS registrations (
                                             registration_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                             racer_id INTEGER,
                                             race_id INTEGER,
                                             status TEXT,
                                             category TEXT,
                                             FOREIGN KEY (racer_id) REFERENCES racers(user_id),
    FOREIGN KEY (race_id) REFERENCES races(race_id)
    );

CREATE TABLE IF NOT EXISTS reviews (
                                       review_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       racer_id INTEGER,
                                       race_id INTEGER,
                                       rating TEXT,
                                       comment TEXT,
                                       FOREIGN KEY (racer_id) REFERENCES racers(user_id),
    FOREIGN KEY (race_id) REFERENCES races(race_id)
    );

CREATE TABLE IF NOT EXISTS race_results (
                                            result_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                            race_id INTEGER,
                                            category TEXT,
                                            posted_date TEXT,
                                            FOREIGN KEY (race_id) REFERENCES races(race_id)
    );

CREATE TABLE IF NOT EXISTS result_entries (
                                              entry_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                              result_id INTEGER,
                                              racer_id INTEGER,
                                              finishing_position INTEGER,
                                              podium_counted INTEGER,
                                              FOREIGN KEY (result_id) REFERENCES race_results(result_id),
    FOREIGN KEY (racer_id) REFERENCES racers(user_id)
    );

CREATE TABLE IF NOT EXISTS system_settings (
                                               setting_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                               announcement TEXT,
                                               maintenance_start TEXT,
                                               maintenance_end TEXT
);