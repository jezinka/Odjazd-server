package com.jezinka.odjazd.service;

import java.time.LocalTime;

public class Const {
    public static final int OSIEDLE_SOBIESKIEGO_2 = 1946;
    public static final int OSIEDLE_SOBIESKIEGO_1 = 1945;
    public static final int PSIE_POLE_RONDO = 3415;
    public static final int MULICKA = 3195;

    public static final String STOP_TIMES_TXT = "stop_times.txt";
    public static final String TRIPS_TXT = "trips.txt";
    public static final String CALENDAR_TXT = "calendar.txt";
    public static final String STOPS_TXT = "stops.txt";
    public static final String STATIC_OTWARTY_WROCLAW_ROZKLAD_JAZDY_GTFS_ZIP = "/static/OtwartyWroclaw_rozklad_jazdy_GTFS.zip";

    public static final String SEPARATOR = ",";

    public static final LocalTime START_INTERVAL = LocalTime.parse("14:45:00");
    public static final LocalTime END_INTERVAL = LocalTime.parse("15:30:00");
}
