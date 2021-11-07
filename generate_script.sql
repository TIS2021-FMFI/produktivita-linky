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
      FROM generate_series(0,365*1000)as d
      WHERE (mod(d,7)!= 6 and mod(d,7)!=5)) as TsTable;

CREATE OR REPLACE FUNCTION insertRow(morning boolean)
RETURNS void
LANGUAGE sql AS
$$
INSERT INTO t_raw_data
SELECT  ts + TIME'08:00'* CASE WHEN morning = true then 1 else 0 end+ random() * (timestamp '2021-01-01 08:00:00' - timestamp '2021-01-01 00:00:00'),  --timestamp
        floor(random()*8+1), --series
       floor(random()*50+10), --pallets
       case when morning = true then smena(ts) else mod(smena(ts),2)+1 end, --shift
            floor(random()*8+1), --next_series
            floor(random()*50+50),--Perf_norm_per_h integer,
            floor(random()*50+50),--Perf_real_per_h integer,
            floor(random()*5+5),--Perf_norm_per_min integer,
            floor(random()*5+5)--Perf_real_per_min intege
        FROM timestamps as t
$$;

SELECT  insertRow(true)
FROM generate_series(20,Cast(floor(random()*50) as integer));

SELECT insertRow(false)
FROM generate_series(20,Cast(floor(random()*50) as integer));

