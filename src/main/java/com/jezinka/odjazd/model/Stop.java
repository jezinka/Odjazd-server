package com.jezinka.odjazd.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stop {

    @Id
    private Integer id;
    private String code;
    private String name;

    public Stop() {
    }

    public Stop(String[] splittedLine) {
        this.id = Integer.parseInt(splittedLine[0]);
        this.code = splittedLine[1];
        this.name = splittedLine[2].replaceAll("\"", "");
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}