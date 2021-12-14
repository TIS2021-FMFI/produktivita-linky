INSERT INTO shift_names(name)VALUES ('Zmena 1'),
                                    ('Zmena 2');

INSERT INTO event_types(name) VALUES ('Porucha'),
                                     ('Údržba'),
                                     ('Míting'),
                                     ('Školenie'),
                                     ('Nedostatok boxov'),
                                     ('Chýba obsluha linky'),
                                     ('Veľa ručných prác');


INSERT INTO series(id,name,worth) VALUES
(1,'PLONG',800.0/352.0)
,(3,'PE40',800.0/597.0)
,(4,'PE60',1)
,(5,'PE10',800.0/267.0)
,(6,'PE20',800.0/560.0)
,(7,'PE05',800.0/267.0)
,(13,'PLA05',1)
,(14,'PLA10',1)
,(21,'PLA05',1)
,(22,'PLA10',1)
,(31,'PE05',800.0/267.0)
,(32,'PE10',800.0/267.0);

INSERT INTO events
SELECT 3*d,1,
       TIMESTAMP '2021-11-1 07:00:00'+ (INTERVAL '1 days'*d),
       TIMESTAMP '2021-11-1 07:10:00'+ (INTERVAL '1 days'*d),
       10,10*800.0/430.0

FROM generate_series(0,365*1)as d
WHERE (mod(d,7)!= 6 and mod(d,7)!=5) ;

INSERT INTO events
SELECT 3*d+1,2,
       TIMESTAMP '2021-11-1 08:00:00'+ (INTERVAL '1 days'*d),
       TIMESTAMP '2021-11-1 08:10:00'+ (INTERVAL '1 days'*d),
       10,10*800.0/430.0
FROM generate_series(0,365*1)as d
WHERE (mod(d,7)!= 6 and mod(d,7)!=5);

INSERT INTO events
SELECT 3*d+2,3,
       TIMESTAMP '2021-11-1 09:00:00'+ (INTERVAL '1 days'*d),
       TIMESTAMP '2021-11-1 09:10:00'+ (INTERVAL '1 days'*d),
       10,10*800.0/430.0
FROM generate_series(0,365*1)as d
WHERE (mod(d,7)!= 6 and mod(d,7)!=5);



