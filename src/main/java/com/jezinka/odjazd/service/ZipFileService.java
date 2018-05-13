package com.jezinka.odjazd.service;

import com.jezinka.odjazd.model.Stop;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
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

        ClassPathResource pathResource = new ClassPathResource("/static/OtwartyWroclaw_rozklad_jazdy_GTFS.zip");

        ZipFile zipFile = new ZipFile(pathResource.getFile());
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            // find nearest stops from home
            if (entry.getName().equals("stops.txt")) {
                InputStream is = zipFile.getInputStream(entry);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                br.readLine();

                return br.lines()
                        .map(s -> new Stop(s.split(",")))
                        .sorted(comparing)
                        .limit(10)
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}
