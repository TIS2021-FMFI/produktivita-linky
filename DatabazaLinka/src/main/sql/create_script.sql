
DROP TABLE IF EXISTS Shift_names CASCADE;
CREATE TABLE Shift_names
(
    Id serial PRIMARY KEY,
    Name varchar not null
);

DROP TABLE IF EXISTS Shift_history CASCADE;
CREATE TABLE Shift_history
(
    Id serial PRIMARY KEY,
    Timestamp_begin timestamp not null ,
    Timestamp_end timestamp not null,
    Goal integer not null,
    shift integer REFERENCES Shift_names
);

DROP TABLE IF EXISTS Series CASCADE;
CREATE TABLE Series
(
   Id serial PRIMARY KEY,
   Name varchar not null ,
   Worth double precision not null
);

DROP TABLE IF EXISTS t_raw_data CASCADE;
CREATE TABLE t_raw_data
(
    Timestamp timestamp PRIMARY KEY,
    Series integer REFERENCES Series,
    Paletts integer,
    Shift integer,
    Next_series integer REFERENCES Series,
    Perf_norm_per_h integer,
    Perf_real_per_h integer,
    Perf_norm_per_min integer,
    Perf_real_per_min integer
);

DROP TABLE IF EXISTS t_raw_data_history CASCADE;
CREATE TABLE t_raw_data_history
(
    Timestamp timestamp PRIMARY KEY,
    Series integer REFERENCES Series,
    Paletts integer,
    Shift integer,
    Next_series integer REFERENCES Series,
    Perf_norm_per_h integer,
    Perf_real_per_h integer,
    Perf_norm_per_min integer,
    Perf_real_per_min integer
);

DROP TABLE IF EXISTS Event_types CASCADE;
CREATE TABLE Event_types
(
    Id serial PRIMARY KEY,
    Name varchar not null
);

DROP TABLE IF EXISTS Events CASCADE;
CREATE TABLE Events
(
    Id serial PRIMARY KEY,
    Id_event integer REFERENCES Event,
    Timestamp_begin timestamp not null ,
    Timestamp_end timestamp not null,
    Potencionaly_washed_pallets double precision not null
);

