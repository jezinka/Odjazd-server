create table hibernate_sequence
(
  next_val bigint null
);

create table service_day
(
  id          int          not null    primary key,
  day_of_week int          null,
  service_id  varchar(255) null
);

create table stop
(
  id   int          not null
    primary key,
  code varchar(255) null,
  name varchar(255) null
);

create table stop_time
(
  id             int          not null
    primary key,
  departure_time time         null,
  stop_id        int          null,
  stop_sequence  int          not null,
  trip_id        varchar(255) null
);

create table trip
(
  id         varchar(255) not null
    primary key,
  route_id   varchar(255) null,
  service_id varchar(255) null
);

create or replace view buses_to_daycare as (select distinct `t`.`route_id`                                       AS `route_id`,
                                                            `start_stop`.`id`                                    AS `start_id`,
                                                            `start_stop`.`name`                                  AS `start_name`,
                                                            `start_stop`.`departure_time`                        AS `start_time`,
                                                            `end_stop`.`id`                                      AS `end_id`,
                                                            `end_stop`.`name`                                    AS `end_name`,
                                                            `end_stop`.`departure_time`                          AS `end_time`,
                                                            (`start_stop`.`departure_time` -
                                                             interval 10 minute)                                 AS `leave_time`,
                                                            (case
                                                               when (`end_stop`.`id` = 3195) then (
                                                                    `end_stop`.`departure_time` + interval 3 minute)
                                                               else (`end_stop`.`departure_time` +
                                                                     interval 6 minute) end)                     AS `on_the_spot`,
                                                            concat(`start_stop`.`departure_time`, '_',
                                                                   `end_stop`.`departure_time`)                  AS `id`
                                            from ((((select `s`.`id` AS `id`,`s`.`name` AS `name`,`st`.`departure_time` AS `departure_time`,`st`.`trip_id` AS `trip_id`
                                                     from (`odjazd`.`stop_time` `st`
                                                         join `odjazd`.`stop` `s` on ((`s`.`id` = `st`.`stop_id`)))
                                                     where (`st`.`stop_sequence` = 0)) `start_stop`
                                                join (select `s`.`id` AS `id`,`s`.`name` AS `name`,`st`.`departure_time` AS `departure_time`,`st`.`trip_id` AS `trip_id`
                                                      from (`odjazd`.`stop_time` `st`
                                                          join `odjazd`.`stop` `s` on ((`s`.`id` =
                                                                                        `st`.`stop_id`)))) `end_stop` on ((
                                              `end_stop`.`trip_id` =
                                              `start_stop`.`trip_id`))) join `odjazd`.`trip` `t` on ((
                                              `start_stop`.`trip_id` = `t`.`id`))) join `odjazd`.`service_day` on ((
                                              `t`.`service_id` = `odjazd`.`service_day`.`service_id`)))
                                            where ((`odjazd`.`service_day`.`day_of_week` = dayofweek(curdate())) and
                                                   (`start_stop`.`id` <> `end_stop`.`id`))
                                            order by `end_stop`.`departure_time`);

