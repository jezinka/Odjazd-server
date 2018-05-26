package com.jezinka.odjazd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ServiceDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String serviceId;
    Integer dayOfWeek;

    public ServiceDay() {
    }

    public ServiceDay(String serviceId, Integer dayOfWeek) {
        this.serviceId = serviceId;
        this.dayOfWeek = dayOfWeek;
    }
}
