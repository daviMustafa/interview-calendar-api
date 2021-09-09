CREATE SEQUENCE IF NOT EXISTS candidate_id_seq;

CREATE TABLE IF NOT EXISTS candidate
(
    id        BIGINT DEFAULT nextval('candidate_id_seq') NOT NULL,
    firstName varchar(20)     NOT NULL,
    lastName  varchar(20)     NOT NULL,
    PRIMARY KEY (id)
);

insert into candidate(id, firstName, lastName)
values (1,'Davi', 'Mustafa'),
       (2, 'Carl', 'Silva');

ALTER SEQUENCE candidate_id_seq RESTART WITH 3;

--------------------------------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS interviewer_id_seq;

CREATE TABLE IF NOT EXISTS interviewer
(
    id        BIGINT DEFAULT nextval('interviewer_id_seq') NOT NULL,
    firstName varchar(20)     NOT NULL,
    lastName  varchar(20)     NOT NULL,
    PRIMARY KEY (id)
    );

insert into interviewer(id, firstName, lastName)
values (1,'Debora', 'Silva');

insert into interviewer(id, firstName, lastName)
values (2,'Ingrid', 'Martins');

ALTER SEQUENCE interviewer_id_seq RESTART WITH 3;

--------------------------------------------------------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS interviewer_time_slot_id_seq;

CREATE TABLE IF NOT EXISTS interviewer_time_slot
(
    id                  BIGINT DEFAULT nextval('interviewer_time_slot_id_seq') NOT NULL,
    interviewer_id      BIGINT NOT NULL,
    dateFrom            date     NOT NULL,
    dateTo              date     NOT NULL,
    timeFrom            time     NOT NULL,
    timeTo              time     NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (interviewer_id, dateFrom, dateTo, timeFrom, timeTo),
    CONSTRAINT fk_interviewer FOREIGN KEY(interviewer_id) REFERENCES interviewer(id)
    );

insert into interviewer_time_slot(id, interviewer_id, dateFrom, dateTo, timeFrom, timeTo)
values (1, 1, '2021-09-06', '2021-09-10', '09:00:00', '16:00:00');

insert into interviewer_time_slot(id, interviewer_id, dateFrom, dateTo, timeFrom, timeTo)
values (2, 2, '2021-09-06', '2021-09-06', '12:00:00', '18:00:00');

insert into interviewer_time_slot(id, interviewer_id, dateFrom, dateTo, timeFrom, timeTo)
values (3, 2, '2021-09-07', '2021-09-07', '09:00:00', '12:00:00');

insert into interviewer_time_slot(id, interviewer_id, dateFrom, dateTo, timeFrom, timeTo)
values (4, 2, '2021-09-08', '2021-09-08', '12:00:00', '18:00:00');

insert into interviewer_time_slot(id, interviewer_id, dateFrom, dateTo, timeFrom, timeTo)
values (5, 2, '2021-09-09', '2021-09-09', '09:00:00', '12:00:00');


ALTER SEQUENCE interviewer_time_slot_id_seq RESTART WITH 6;

-----------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS candidate_time_slot_id_seq;

CREATE TABLE IF NOT EXISTS candidate_time_slot
(
    id                  BIGINT DEFAULT nextval('candidate_time_slot_id_seq') NOT NULL,
    candidate_id        BIGINT   NOT NULL,
    dateFrom            date     NOT NULL,
    dateTo              date     NOT NULL,
    timeFrom            time     NOT NULL,
    timeTo              time     NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (candidate_id, dateFrom, dateTo, timeFrom, timeTo),
    CONSTRAINT fk_candidate FOREIGN KEY(candidate_id) REFERENCES candidate(id)
    );

insert into candidate_time_slot(id, candidate_id, dateFrom, dateTo, timeFrom, timeTo)
values (1, 1, '2021-09-06', '2021-09-10', '09:00:00', '10:00:00');

insert into candidate_time_slot(id, candidate_id, dateFrom, dateTo, timeFrom, timeTo)
values (2, 1, '2021-09-08', '2021-09-08', '10:00:00', '12:00:00');

ALTER SEQUENCE candidate_time_slot_id_seq RESTART WITH 3;

-----------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS interview_id_seq;

CREATE TABLE IF NOT EXISTS interview
(
    id              BIGINT DEFAULT nextval('interviewer_id_seq') NOT NULL,
    candidate_id    BIGINT NOT NULL,
    interviewer_id  BIGINT NOT NULL,
    startDateTime   timestamp     NOT NULL,
    endDateTime     timestamp     NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (candidate_id, interviewer_id, startDateTime, endDateTime),
    CONSTRAINT fk_candidate FOREIGN KEY(candidate_id) REFERENCES candidate(id),
    CONSTRAINT fk_interviewer FOREIGN KEY(interviewer_id) REFERENCES interviewer(id)
    );

insert into interview(id, candidate_id, interviewer_id, startDateTime, endDateTime)
values (1,1, 1, '2021-08-25 17:00:00-00', '2021-08-25 18:00:00-00');

insert into interview(id, candidate_id, interviewer_id, startDateTime, endDateTime)
values (2,2, 1, '2021-08-26 17:00:00-00', '2021-08-26 18:00:00-00');

ALTER SEQUENCE interview_id_seq RESTART WITH 3;

----------------------------------------------------------------------------------------