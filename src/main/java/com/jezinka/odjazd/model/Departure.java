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

    private Departure() {
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

    public static class Builder {
        private String from;
        private String leave;
        private String departure;
        private String arrival;
        private String onTheSpot;
        private List<String> bus;
        private String transfer;

        public Builder() {

        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setLeave(String leave) {
            this.leave = leave;
            return this;
        }

        public Builder setDeparture(String departure) {
            this.departure = departure;
            return this;
        }

        public Builder setArrival(String arrival) {
            this.arrival = arrival;
            return this;
        }

        public Builder setOnTheSpot(String onTheSpot) {
            this.onTheSpot = onTheSpot;
            return this;
        }

        public Builder setBus(List<String> bus) {
            this.bus = bus;
            return this;
        }

        public Builder setTransfer(String transfer) {
            this.transfer = transfer;
            return this;
        }

        public Departure build() {
            Departure departure = new Departure();
            departure.from = this.from;
            departure.leave = this.leave;
            departure.departure = this.departure;
            departure.arrival = this.arrival;
            departure.bus = this.bus;
            departure.transfer = this.transfer;
            departure.onTheSpot = this.onTheSpot;
            return departure;
        }
    }
}
