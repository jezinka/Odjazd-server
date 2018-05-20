package com.jezinka.odjazd.model;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
public class StopTime {

    @EmbeddedId
    private StopTimePK stopTimeId;

    private LocalTime departureTime;

    public String getTripId() {
        return stopTimeId.tripId;
    }

    public Integer getStopId() {
        return stopTimeId.stopId;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public StopTime(String[] s) {
        this.stopTimeId = new StopTimePK(Integer.parseInt(s[3]), s[0]);

        try {
            this.departureTime = LocalTime.parse(s[2]);
        } catch (Exception e) {
            this.departureTime = LocalTime.parse("00:" + s[2].split(":")[1]);
        }
    }

    @Override
    public String toString() {
        return "StopTime{" +
                "stopId=" + getStopId() +
                ", tripId='" + getTripId() + '\'' +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }

    private static class StopTimePK implements Serializable {
        private Integer stopId;
        private String tripId;

        public StopTimePK() {
        }

        public StopTimePK(Integer stopId, String tripId) {
            this.stopId = stopId;
            this.tripId = tripId;
        }

        public Integer getStopId() {
            return stopId;
        }

        public String getTripId() {
            return tripId;
        }
    }
}