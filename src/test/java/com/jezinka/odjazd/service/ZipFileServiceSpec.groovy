package com.jezinka.odjazd.service

import com.jezinka.odjazd.model.Stop
import spock.lang.Specification

class ZipFileServiceSpec extends Specification {

    def "findNearestStops -> test"() {
        given:
        ZipFileService zipFileService = new ZipFileService()

        when:
        List<Stop> results = zipFileService.findNearestStops()

        then:
        noExceptionThrown()
        results.size() == 0
    }

    def "findNearestStops -> home test"() {
        given:
        ZipFileService zipFileService = new ZipFileService()

        when:
        List<Stop> results = zipFileService.findNearestStops { a, b -> a.distanceFromHome <=> b.distanceFromHome }

        then:
        noExceptionThrown()
        results.size() == 10
        results.first() == results.min { it.distanceFromHome }
        results.last() == results.max { it.distanceFromHome }
    }
}
