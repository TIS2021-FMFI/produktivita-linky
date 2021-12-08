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

DROP TABLE IF EXISTS t_raw_data_history CASCADE;
CREATE TABLE t_raw_data_history
(
    Series integer REFERENCES Series not null ,
    Paletts double precision not null,
    Shift integer not null ,
    Date date not null ,
    PRIMARY KEY (Date,Shift,Series)
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
    Id_event integer REFERENCES Event_types,
    Timestamp_begin timestamp not null ,
    Timestamp_end timestamp not null,
    Potencionaly_washed_pallets double precision not null
);

