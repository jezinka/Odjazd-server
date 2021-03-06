package com.jezinka.odjazd.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Trip {

    @Id
    private String id;
    private String routeId;
    private String serviceId;

    public String getId() {
        return id;
    }

    public String getRouteId() {
        return routeId;
    }

    public Trip() {
    }

    public Trip(String[] splittedLine) {
        this.id = splittedLine[2];
        this.serviceId = splittedLine[1];
        this.routeId = splittedLine[0];
    }
}
