package com.jezinka.odjazd.service;

import com.jezinka.odjazd.model.Stop;
import com.jezinka.odjazd.model.StopTime;
import com.jezinka.odjazd.model.Trip;
import org.joda.time.LocalTime;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class ZipFileService {

    public void findRoute() throws IOException {

        List<Integer> homeStops = Arrays.asList(Stops.OSIEDLE_SOBIESKIEGO_1, Stops.OSIEDLE_SOBIESKIEGO_2);// findNearestStops(Comparator.comparing(Stop::getDistanceFromHome)).stream().map(Stop::getId).collect(Collectors.toList());
        List<Integer> daycareStops = Arrays.asList(Stops.PSIE_POLE_RONDO, Stops.MULICKA);

        BufferedReader br = getFromZip("stop_times.txt");
        br.readLine();

        Map<String, List<StopTime>> values = br.lines()
                .map(s -> new StopTime(s.split(",")))
                .collect(Collectors.groupingBy(StopTime::getTripId));

        List<String> routes = new ArrayList<>();
        for (String key : values.keySet()) {
            for (Integer homeStop : homeStops) {
                if (containsStop(values, key, homeStop)) {
                    for (Integer daycareStop : daycareStops) {
                        if (containsStop(values, key, daycareStop)) {
                            routes.add(key);
                        }
                    }
                }
            }
        }

        BufferedReader br_route = getFromZip("trips.txt");
        br_route.readLine();

        List<String> trips = br_route.lines()
                .map(r -> new Trip(r.split(",")))
                .filter(r -> routes.contains(r.getId()))
                .map(Trip::getRouteId)
                .distinct()
                .collect(Collectors.toList());


        System.out.println(trips);
    }

    private boolean containsStop(Map<String, List<StopTime>> values, String key, Integer stop) {
        return values.get(key)
                .stream()
                .filter(rs -> isInInterval(rs.getDepartureTime()))
                .map(StopTime::getStopId)
                .collect(Collectors.toList())
                .contains(stop);
    }

    boolean isInInterval(LocalTime departureTime) {
        LocalTime startTime = new LocalTime(14, 45);
        LocalTime endTime = new LocalTime(15, 30);

        return (departureTime.equals(startTime) || departureTime.isAfter(startTime)) &&
                (departureTime.equals(endTime) || departureTime.isBefore(endTime));
    }


    public List<Stop> findNearestStops(Comparator<Stop> comparing) throws IOException {

        if (comparing == null) {
            return new ArrayList<>();
        }

        BufferedReader br = getFromZip("stops.txt");
        br.readLine();

        return br.lines()
                .map(s -> new Stop(s.split(",")))
                .sorted(comparing)
                .limit(10)
                .collect(Collectors.toList());
    }

    private BufferedReader getFromZip(String fileName) throws IOException {
        ClassPathResource pathResource = new ClassPathResource("/static/OtwartyWroclaw_rozklad_jazdy_GTFS.zip");

        ZipFile zipFile = new ZipFile(pathResource.getFile());
        ZipEntry entry = zipFile.getEntry(fileName);

        return new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
    }
}

