package com.jezinka.odjazd.model;

import java.util.List;

public class Departure {
    private String from;
    private String leave;
    private String departure;
    private String arrival;
    private String onTheSpot;
    private List<String> bus;
    private String transfer;

    public Departure() {
    }

    public Departure(String from, String leave, String departure, String arrival, String onTheSpot, List<String> bus, String transfer) {
        this.from = from;
        this.leave = leave;
        this.departure = departure;
        this.arrival = arrival;
        this.onTheSpot = onTheSpot;
        this.bus = bus;
        this.transfer = transfer;
    }

    public String getFrom() {

        return from;
    }

    public String getLeave() {
        return leave;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getOnTheSpot() {
        return onTheSpot;
    }

    public List<String> getBus() {
        return bus;
    }

    public String getTransfer() {
        return transfer;
    }
}
