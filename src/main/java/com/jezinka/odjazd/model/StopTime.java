package com.jezinka.odjazd.model;

import org.joda.time.LocalTime;

public class StopTime {

    Integer stopId;
    String tripId;
    LocalTime departureTime;

    public String getTripId() {
        return tripId;
    }

    public Integer getStopId() {
        return stopId;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public StopTime(String[] s) {
        this.stopId = Integer.parseInt(s[3]);
        this.tripId = s[0];

        try {
            this.departureTime = LocalTime.parse(s[2]);
        } catch (Exception e) {
            this.departureTime = new LocalTime(0, Integer.parseInt(s[2].split(":")[1]));
        }
    }

    @Override
    public String toString() {
        return "StopTime{" +
                "stopId=" + stopId +
                ", tripId='" + tripId + '\'' +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }
}