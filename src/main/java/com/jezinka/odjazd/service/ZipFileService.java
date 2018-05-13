package com.jezinka.odjazd.service;

import com.jezinka.odjazd.model.Stop;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class ZipFileService {

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

