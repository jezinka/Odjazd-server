package com.jezinka.odjazd.service;

import com.jezinka.odjazd.model.ServiceDay;
import com.jezinka.odjazd.model.Stop;
import com.jezinka.odjazd.model.StopTime;
import com.jezinka.odjazd.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

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

    private EntityManagerFactory emf;

    @Autowired
    public ZipFileService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void findRoute() throws IOException {

        List<Integer> homeStops = Arrays.asList(Const.OSIEDLE_SOBIESKIEGO_1, Const.OSIEDLE_SOBIESKIEGO_2);// findNearestStops(Comparator.comparing(Stop::getDistanceFromHome)).stream().map(Stop::getId).collect(Collectors.toList());
        List<Integer> daycareStops = Arrays.asList(Const.PSIE_POLE_RONDO, Const.MULICKA);

        BufferedReader br = getFromZip(Const.STOP_TIMES_TXT);
        br.readLine();

        Map<String, List<StopTime>> values = br.lines()
                .map(s -> new StopTime(s.split(Const.SEPARATOR)))
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

    public void saveData() throws IOException {
        saveStopFromFile();
        saveTripFromFile();
        saveStopTimeFromFile();
        saveCalendarsFromFile();
    }

    private void saveStopFromFile() throws IOException {
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
    }

    private void saveTripFromFile() throws IOException {
        String line;
        BufferedReader tripsBr = getFromZip(Const.TRIPS_TXT);
        tripsBr.readLine();

        int counter = 0;
        List<Trip> trips = new ArrayList<>();
        while ((line = tripsBr.readLine()) != null) {
            Trip trip = new Trip(line.split(Const.SEPARATOR));
            trips.add(trip);
            counter++;
        }
        bulkWithEntityManager(trips);
        System.out.println("saved lines = " + counter);
    }

    private void saveStopTimeFromFile() throws IOException {

        String line;
        BufferedReader stopTimeBr = getFromZip(Const.STOP_TIMES_TXT);
        stopTimeBr.readLine();

        int counter = 0;
        List<StopTime> stopTimes = new ArrayList<>();
        while ((line = stopTimeBr.readLine()) != null) {
            StopTime stopTime = new StopTime(line.split(Const.SEPARATOR));
            stopTimes.add(stopTime);
            counter++;
        }
        bulkWithEntityManager(stopTimes);
        System.out.println("saved lines = " + counter);
    }

    private void saveCalendarsFromFile() throws IOException {

        String line;
        BufferedReader calendarBr = getFromZip(Const.CALENDAR_TXT);
        calendarBr.readLine();

        List<ServiceDay> serviceDays = new ArrayList<>();
        while ((line = calendarBr.readLine()) != null) {
            serviceDays.addAll(transformIntoCalendars(line.split(Const.SEPARATOR)));
        }

        bulkWithEntityManager(serviceDays);
    }

    private List<ServiceDay> transformIntoCalendars(String[] splittedLine) {
        List<ServiceDay> serviceDays = new ArrayList<>();

        for (int x = 0; x < splittedLine.length; x++) {
            if (splittedLine[x].equals("1")) {
                ServiceDay serviceDay = new ServiceDay(splittedLine[0], getDayOfWeek(x));
                serviceDays.add(serviceDay);
            }
        }
        return serviceDays;
    }

    private int getDayOfWeek(int index) {
        return index <= 6 ? index + 1 : 1;
    }

    private void bulkWithEntityManager(List<?> items) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        items.forEach(entityManager::persist);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

