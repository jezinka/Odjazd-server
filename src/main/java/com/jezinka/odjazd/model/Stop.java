package com.jezinka.odjazd.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Stop {

    private Integer id;
    private String code;
    private String name;
    private LatLng latLng;

    public Stop(String[] splittedLine) {
        this.id = Integer.parseInt(splittedLine[0]);
        this.code = splittedLine[1];
        this.name = splittedLine[2];
        double latitude = Double.parseDouble(splittedLine[3]);
        double longitude = Double.parseDouble(splittedLine[4]);
        this.latLng = new LatLng(latitude, longitude);
    }

    public int getDistanceFromHome() {
        LatLng home = new LatLng(51.1590400, 17.1414480);
        return new Double(LatLngTool.distance(this.latLng, home, LengthUnit.METER)).intValue();
    }

    public int getDistanceFromDaycare() {
        LatLng daycare = new LatLng(51.145902, 17.1159413);
        return new Double(LatLngTool.distance(this.latLng, daycare, LengthUnit.METER)).intValue();
    }

    @Override
    public String toString() {
        return "Stop{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", distanceFromHome=" + getDistanceFromHome() +
                ", distanceFromDaycare=" + getDistanceFromDaycare() +
                '}';
    }

}