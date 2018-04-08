package com.jezinka.odjazd.controller;

import com.jezinka.odjazd.model.Departure;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class IndexController {

    @RequestMapping("/")
    public List<Departure> index(@RequestParam(name = "from", required = false, defaultValue = "home") String from) {
        List<Departure> departures = new ArrayList<>();
        if (from.equals("work")) {

            departures.add(new Departure(from, "14:17", "14:20", "14:55", "14:57", Arrays.asList("33", "331"), "Bujwida"));
        } else {
            departures.add(new Departure(from, "14:21", "14:30", "14:38", "14:40", Collections.singletonList("150"), null));
            departures.add(new Departure(from, "14:33", "14:41", "14:47", "14:57", Collections.singletonList("D"), null));
        }
        return departures;
    }
}
