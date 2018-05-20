package com.jezinka.odjazd.model;

public class Trip {

    String id;
    String routeId;

    public String getId() {
        return id;
    }

    public String getRouteId() {
        return routeId;
    }

    public Trip(String[] splittedLine) {
        this.id = splittedLine[2];
        this.routeId = splittedLine[0];
    }
}
