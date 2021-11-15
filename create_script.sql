
DROP TABLE IF EXISTS Series CASCADE;
CREATE TABLE Series
(
   id serial PRIMARY KEY,
   name varchar not null ,
   worth double precision not null
);

DROP TABLE IF EXISTS t_raw_data CASCADE;
CREATE TABLE t_raw_data
(
    Timestamp timestamp PRIMARY KEY,
    Series integer REFERENCES Series,
    Paletts integer,
    Shift integer,
    Next_series integer,
    Perf_norm_per_h integer,
    Perf_real_per_h integer,
    Perf_norm_per_min integer,
    Perf_real_per_min integer
);

DROP TABLE IF EXISTS Event CASCADE;
CREATE TABLE Event
(
    id serial PRIMARY KEY,
    name varchar not null
);

DROP TABLE IF EXISTS Events CASCADE;
CREATE TABLE Events
(
    id serial PRIMARY KEY,
    id_event integer REFERENCES Event,
    timestamp_begin timestamp not null ,
    timestamp_end timestamp not null,
    potencionaly_washed_pallets double precision not null
);

