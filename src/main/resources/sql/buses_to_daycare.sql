create or replace view odjazd.buses_to_daycare as
  (select route_id,
          start_stop.id             as start_id,
          start_stop.name           as start_name,
          start_stop.departure_time as start_time,
          end_stop.id               as end_id,
          end_stop.name             as end_name,
          end_stop.departure_time   as end_time
   from (select s.id,
                name,
                departure_time,
                trip_id
         from stop_time st
           join stop s on s.id = st.stop_id
         where stop_sequence = 0) start_stop
     join (select s.id,
                  name,
                  departure_time,
                  trip_id
           from stop_time st
             join stop s on s.id = st.stop_id) end_stop on end_stop.trip_id = start_stop.trip_id
     join trip t on start_stop.trip_id = t.id
   where t.service_id = (select service_id from service_day where day_of_week = dayofweek(curdate()))
   order by end_stop.departure_time);