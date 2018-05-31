package com.jezinka.odjazd.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "BUSES_TO_DAYCARE")
public class BusesToDaycare {
    @Id
    String id;
    String routeId;
    Integer startId;
    String startName;
    LocalTime startTime;
    Integer endId;
    String endName;
    LocalTime endTime;
    LocalTime onTheSpot;
    LocalTime leaveTime;

    public String getRouteId() {
        return routeId;
    }

    public String getStartName() {
        return startName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getEndName() {
        return endName;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getOnTheSpot() {
        return onTheSpot;
    }

    public LocalTime getLeaveTime() {
        return leaveTime;
    }
}
