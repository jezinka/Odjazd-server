package com.jezinka.odjazd.repository;

import com.jezinka.odjazd.model.BusesToDaycare;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalTime;
import java.util.List;

public interface BusesToDaycareRepository extends CrudRepository<BusesToDaycare, Long> {

    List<BusesToDaycare> findBusesToDaycareByStartIdIsInAndEndIdIsInAndEndTimeBetween(List<Integer> startEnds, List<Integer> endsEnds, LocalTime startInterval, LocalTime endInterval);
}
