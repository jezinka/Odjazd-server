package com.jezinka.odjazd.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
public class StopTime {

    @Id
    private Integer id;

    private Integer stopId;

    private String tripId;

    private LocalTime departureTime;

    public String getTripId() {
        return this.tripId;
    }

    public Integer getStopId() {
        return this.stopId;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public StopTime() {
    }

    public StopTime(int i, String[] s) {
        this.id = i;
        this.stopId = Integer.parseInt(s[3]);
        this.tripId = s[0];

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
}