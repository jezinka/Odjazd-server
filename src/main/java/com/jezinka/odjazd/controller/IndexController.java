package com.jezinka.odjazd.controller;

import com.jezinka.odjazd.model.BusesToDaycare;
import com.jezinka.odjazd.repository.BusesToDaycareRepository;
import com.jezinka.odjazd.service.Const;
import com.jezinka.odjazd.service.ZipFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class IndexController {

    @Autowired
    ZipFileService zipFileService;

    @Autowired
    BusesToDaycareRepository busesToDaycareRepository;

    @RequestMapping("/")
    public List<BusesToDaycare> index() {

        return getBusesToDaycare();
    }

    private List<BusesToDaycare> getBusesToDaycare() {
        return busesToDaycareRepository.findBusesToDaycareByStartIdIsInAndEndIdIsInAndEndTimeBetween(
                Arrays.asList(Const.OSIEDLE_SOBIESKIEGO_1, Const.OSIEDLE_SOBIESKIEGO_2),
                Arrays.asList(Const.PSIE_POLE_RONDO, Const.MULICKA),
                Const.START_INTERVAL, Const.END_INTERVAL);
    }

    @RequestMapping("/fillDatabase")
    public String fillDatabase() {
        try {
            zipFileService.saveData();
        } catch (IOException e) {
            return "There was an error!";
        }
        return "Everything OK!";
    }
}
