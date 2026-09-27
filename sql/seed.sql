-- Admin: Ardass Sardas
INSERT INTO users (role, first_name, last_name, email, ssn, password)
VALUES ('ADMIN', 'Ardass', 'Sardas', 'ardass@bikr.com', '999999999', 'admin123');

INSERT INTO administrators (user_id) VALUES (last_insert_rowid());

-- Seed organizer (already approved, for demo)
INSERT INTO users (role, first_name, last_name, email, ssn, password)
VALUES ('ORGANIZER', 'Christian', 'Robins', 'chris@test.com', '111111111', 'pass');

INSERT INTO organizers (user_id) VALUES (last_insert_rowid());

INSERT INTO access_requests (user_id, status)
VALUES (last_insert_rowid(), 'APPROVED');

-- Seed 3 races (organizer_id = 2)
INSERT INTO races (organizer_id, race_name, race_officiality, race_date, race_type, race_miles, race_route, race_location, race_max_registrations, race_last_day_registrations)
VALUES
    (2, 'Grand Canyonic heated wheels', 1, '2026-01-15', 'ROAD',     50.0, 'Canyon Loop',   'Arizona', 7, '2026-01-01'),
    (2, 'Seattle Crystal Twilight',     1, '2026-02-20', 'ROAD',     40.0, 'City Circuit',  'Seattle', 8, '2026-02-05'),
    (2, 'Maunakai volcanic boom',       1, '2026-03-10', 'MOUNTAIN', 30.0, 'Volcano Trail', 'Hawaii',  8, '2026-02-25');