package com.jezinka.odjazd.service;

import com.jezinka.odjazd.model.Stop;
import com.jezinka.odjazd.model.StopTime;
import com.jezinka.odjazd.model.Trip;
import com.jezinka.odjazd.model.repository.StopRepository;
import com.jezinka.odjazd.model.repository.StopTimeRepository;
import com.jezinka.odjazd.model.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class ZipFileService {

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private StopTimeRepository stopTimeRepository;

    private EntityManagerFactory emf;

    @Autowired
    public ZipFileService(EntityManagerFactory emf) {
        Assert.notNull(emf, "EntityManagerFactory must not be null");
        this.emf = emf;
    }

    public void findRoute() throws IOException {

        List<Integer> homeStops = Arrays.asList(Const.OSIEDLE_SOBIESKIEGO_1, Const.OSIEDLE_SOBIESKIEGO_2);// findNearestStops(Comparator.comparing(Stop::getDistanceFromHome)).stream().map(Stop::getId).collect(Collectors.toList());
        List<Integer> daycareStops = Arrays.asList(Const.PSIE_POLE_RONDO, Const.MULICKA);

        BufferedReader br = getFromZip(Const.STOP_TIMES_TXT);
        br.readLine();

        Map<String, List<StopTime>> values = br.lines()
                .map(s -> new StopTime(1, s.split(Const.SEPARATOR)))
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

        BufferedReader br_route = getFromZip(Const.TRIPS_TXT);
        br_route.readLine();

        List<String> trips = br_route.lines()
                .map(r -> new Trip(r.split(Const.SEPARATOR)))
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
        LocalTime startTime = LocalTime.parse("14:45");
        LocalTime endTime = LocalTime.parse("15:30");

        return (departureTime.equals(startTime) || departureTime.isAfter(startTime)) &&
                (departureTime.equals(endTime) || departureTime.isBefore(endTime));
    }

    private BufferedReader getFromZip(String fileName) throws IOException {
        ClassPathResource pathResource = new ClassPathResource(Const.STATIC_OTWARTY_WROCLAW_ROZKLAD_JAZDY_GTFS_ZIP);

        ZipFile zipFile = new ZipFile(pathResource.getFile());
        ZipEntry entry = zipFile.getEntry(fileName);

        return new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
    }

    public void loadData() throws IOException {
        String line;

        BufferedReader stopBr = getFromZip(Const.STOPS_TXT);
        stopBr.readLine();

        int counter = 0;
        List<Stop> stops = new ArrayList<>();
        while ((line = stopBr.readLine()) != null) {
            Stop stop = new Stop(line.split(Const.SEPARATOR));
            stops.add(stop);
            counter++;
        }
        bulkWithEntityManager(stops);
        System.out.println("saved lines = " + counter);

        BufferedReader tripsBr = getFromZip(Const.TRIPS_TXT);
        tripsBr.readLine();

        counter = 0;
        List<Trip> trips = new ArrayList<>();
        while ((line = tripsBr.readLine()) != null) {
            Trip trip = new Trip(line.split(Const.SEPARATOR));
            trips.add(trip);
            counter++;
        }
        bulkWithEntityManager(trips);
        System.out.println("saved lines = " + counter);

        BufferedReader stopTimeBr = getFromZip(Const.STOP_TIMES_TXT);
        stopTimeBr.readLine();

        counter = 0;
        List<StopTime> stopTimes = new ArrayList<>();
        while ((line = stopTimeBr.readLine()) != null) {
            StopTime stopTime = new StopTime(counter, line.split(Const.SEPARATOR));
            stopTimes.add(stopTime);
            counter++;
        }
        bulkWithEntityManager(stopTimes);
        System.out.println("saved lines = " + counter);
    }

    private void bulkWithEntityManager(List<?> items) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        items.forEach(entityManager::persist);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

