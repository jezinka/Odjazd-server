package com.jezinka.odjazd.repository;

import com.jezinka.odjazd.model.StopTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StopTimeRepository extends CrudRepository<StopTime, Long> {

    @Query("select st.tripId from StopTime st")
    List<String> getAllTripIds();

}
