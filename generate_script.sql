
INSERT INTO shift_names(name)VALUES ('jozkova'),('jankova');


INSERT INTO event_types(name) VALUES ('porucha'),('udrzba'),('ine');
INSERT INTO series(id,name,worth) VALUES
(1,'PLONG',800/352)
,(3,'PE40',800/597)
,(4,'PE60',1)
,(5,'PE10',800/267)
,(6,'PE20',800/560)
,(7,'PE05',800/267)
,(13,'PLA05',1)
,(14,'PLA10',1)
,(21,'PLA05',1)
,(22,'PLA10',1)
,(31,'PE05',800/267)
,(32,'PE10',800/267);

CREATE OR REPLACE FUNCTION random_pick()
    RETURNS int AS
$$
DECLARE
    field int[] := '{1,3,4,5,6,7,13,14,21,22,31,32}';
BEGIN
    RETURN field[floor((random()*12)+1)::int];
END
$$ LANGUAGE plpgsql VOLATILE;


CREATE OR REPLACE FUNCTION shift(ts timestamp)
    RETURNS integer
    LANGUAGE sql
AS
$$
SELECT CASE
           WHEN mod(CAST(floor((DATE_PART('day', ts - TIMESTAMP '2021-11-1 06:00:00'))/7) as integer),2) = 0 THEN 1
           else 2
           END
$$;

CREATE OR REPLACE VIEW timestamps as
SELECT TsTable.ts
FROM (SELECT TIMESTAMP '2021-11-1 06:00:00'+ (INTERVAL '1 days'*d) as ts
      FROM generate_series(0,365*10)as d
      WHERE (mod(d,7)!= 6 and mod(d,7)!=5)) as TsTable;

CREATE OR REPLACE FUNCTION insertRow()
RETURNS void
LANGUAGE sql AS
$$
INSERT INTO t_raw_data
SELECT  ts +(interval '8 hour') -- poobedna
            + random() * (timestamp '2021-01-01 08:00:00' - timestamp '2021-01-01 00:00:00'),  --timestamp
        random_pick(), --series
       floor(random()*30+10), --pallets
       smena(ts) , --shift
            random_pick(), --next_series
            floor(random()*50+50),--Perf_norm_per_h integer,
            floor(random()*50+50),--Perf_real_per_h integer,
            floor(random()*5+5),--Perf_norm_per_min integer,
            floor(random()*5+5)--Perf_real_per_min intege
        FROM timestamps as t;

INSERT INTO t_raw_data
SELECT  ts + random() * (timestamp '2021-01-01 08:00:00' - timestamp '2021-01-01 00:00:00'),  --timestamp
        random_pick(), --series
        floor(random()*30+10), --pallets
        mod(smena(ts),2)+1, --shift
        random_pick(), --next_series
        floor(random()*50+50),--Perf_norm_per_h integer,
        floor(random()*50+50),--Perf_real_per_h integer,
        floor(random()*5+5),--Perf_norm_per_min integer,
        floor(random()*5+5)--Perf_real_per_min intege
FROM timestamps as t;
$$;


CREATE OR REPLACE FUNCTION lost(dt date)
    RETURNS double precision
    LANGUAGE sql
AS
$$
SELECT (800-sum(t.paletts * s.worth))::double precision
FROM t_raw_data as t inner join series s on s.id = t.series
WHERE timestamp::date = dt and timestamp::time < TIME '14:00'
$$;

INSERT INTO shift_history(timestamp_begin,timestamp_end,goal,shift)
SELECT TIMESTAMP '2021-11-1 14:00:01'+ (INTERVAL '1 days'*d),
       TIMESTAMP '2021-11-1 22:00:00'+ (INTERVAL '1 days'*d),
       800,
       smena(TIMESTAMP '2021-11-1 14:00:01'+ (INTERVAL '1 days'*d))
FROM generate_series(0,365*10)as d
WHERE (mod(d,7)!= 6 and mod(d,7)!=5);

INSERT INTO shift_history(timestamp_begin,timestamp_end,goal,shift)
SELECT TIMESTAMP '2021-11-1 06:00:00'+ (INTERVAL '1 days'*d),
       TIMESTAMP '2021-11-1 14:00:00'+ (INTERVAL '1 days'*d),
       800,
       mod(smena(TIMESTAMP '2021-11-1 14:00:01'+ (INTERVAL '1 days'*d)),2)+1
FROM generate_series(0,365*10)as d
WHERE (mod(d,7)!= 6 and mod(d,7)!=5);

SELECT  insertRow()
FROM generate_series(0,Cast(floor(random()*10)+20 as integer));


INSERT INTO events
SELECT 3*d,1,
TIMESTAMP '2021-11-1 07:00:00'+ (INTERVAL '1 days'*d),
    TIMESTAMP '2021-11-1 07:10:00'+ (INTERVAL '1 days'*d),
       lost(Cast((TIMESTAMP '2021-11-1 07:00:00'+ (INTERVAL '1 days'*d)) as date))/3
FROM generate_series(0,365*10)as d
WHERE (mod(d,7)!= 6 and mod(d,7)!=5) and lost(Cast((TIMESTAMP '2021-11-1 07:00:00'+ (INTERVAL '1 days'*d)) as date)) >0 ;

INSERT INTO events
SELECT 3*d+1,2,
       TIMESTAMP '2021-11-1 08:00:00'+ (INTERVAL '1 days'*d),
       TIMESTAMP '2021-11-1 08:10:00'+ (INTERVAL '1 days'*d),
       lost((TIMESTAMP '2021-11-1 07:00:00'+ (INTERVAL '1 days'*d))::Date)/3
FROM generate_series(0,365*10)as d
WHERE (mod(d,7)!= 6 and mod(d,7)!=5) and lost(Cast((TIMESTAMP '2021-11-1 07:00:00'+ (INTERVAL '1 days'*d)) as date)) >0;
INSERT INTO events
SELECT 3*d+2,3,
       TIMESTAMP '2021-11-1 09:00:00'+ (INTERVAL '1 days'*d),
       TIMESTAMP '2021-11-1 09:10:00'+ (INTERVAL '1 days'*d),
       lost((TIMESTAMP '2021-11-1 07:00:00'+ (INTERVAL '1 days'*d))::Date)/3
FROM generate_series(0,365*10)as d
WHERE (mod(d,7)!= 6 and mod(d,7)!=5)
  and lost(Cast((TIMESTAMP '2021-11-1 07:00:00'+ (INTERVAL '1 days'*d)) as date)) >0;


