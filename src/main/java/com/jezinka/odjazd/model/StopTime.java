package com.jezinka.odjazd.model;


import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class StopTime {

    @Id
    @GeneratedValue(generator = "sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence", allocationSize = 500)
    private Integer id;

    private Integer stopId;

    private String tripId;

    private LocalTime departureTime;

    private int stop_sequence;

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

    public StopTime(String[] s) {
        this.stopId = Integer.parseInt(s[3]);
        this.tripId = s[0];
        this.stop_sequence = Integer.parseInt(s[4]);

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