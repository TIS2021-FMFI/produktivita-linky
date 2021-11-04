DROP TABLE IF EXISTS DOVODY CASCADE;
CREATE TABLE t_raw_data
(
    Series integer,
    Paletts integer,
    Shift integer,
    Next_series integer,
    Perf_norm_per_h integer,
    Perf_real_per_h integer,
    Perf_norm_per_min integer,
    Perf_real_per_min integer
);