package com.jezinka.odjazd.service

import com.jezinka.odjazd.model.Stop
import org.joda.time.LocalTime
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

    def "findRoute -> test"() {
        given:
        ZipFileService zipFileService = new ZipFileService()

        when:
        zipFileService.findRoute()

        then:
        noExceptionThrown()
    }

    def "isInInterval"() {
        given:
        ZipFileService zipFileService = new ZipFileService()

        when:
        boolean result = zipFileService.isInInterval(departureTime)

        then:
        println departureTime
        result == expectedResult

        where:
        departureTime         | expectedResult
        new LocalTime(13, 00) | false
        new LocalTime(14, 45) | true
        new LocalTime(15, 10) | true
        new LocalTime(16, 00) | false

    }
}
