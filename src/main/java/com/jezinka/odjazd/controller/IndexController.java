package com.jezinka.odjazd.controller;

import com.jezinka.odjazd.model.Departure;
import com.jezinka.odjazd.service.ZipFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class IndexController {

    @Autowired
    ZipFileService zipFileService;

    @RequestMapping("/")
    public List<Departure> index(@RequestParam(name = "from", required = false, defaultValue = "home") String from) {
        List<Departure> departures = new ArrayList<>();

        if (from.equals("work")) {
            departures.add(new Departure.Builder()
                    .setFrom(from)
                    .setLeave("14:17")
                    .setDeparture("14:20")
                    .setArrival("14:55")
                    .setOnTheSpot("14:57")
                    .setBus(Arrays.asList("33", "331"))
                    .setTransfer("Bujwida")
                    .build());

        } else {
            departures.add(new Departure.Builder()
                    .setFrom("14:21").setLeave("14:30").setDeparture("14:38")
                    .setArrival("14:40").setBus(Collections.singletonList("150"))
                    .build());

            departures.add(new Departure.Builder()
                    .setFrom(from).setLeave("14:33").setDeparture("14:41")
                    .setArrival("14:47").setOnTheSpot("14:57").setBus(Collections.singletonList("D"))
                    .build());
        }
        return departures;
    }

    @RequestMapping("/fillDatabase")
    public String fillDatabase() {
        try {
            zipFileService.loadData();
        } catch (IOException e) {
            return "There was an error!";
        }
        return "Everything OK!";
    }
}
