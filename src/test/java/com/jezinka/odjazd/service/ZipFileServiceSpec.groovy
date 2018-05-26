package com.jezinka.odjazd.service

import com.jezinka.odjazd.model.ServiceDay
import spock.lang.Specification

import java.time.LocalTime

class ZipFileServiceSpec extends Specification {

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
        departureTime            | expectedResult
        LocalTime.parse("13:00") | false
        LocalTime.parse("14:45") | true
        LocalTime.parse("15:10") | true
        LocalTime.parse("16:00") | false
    }

    def "getDayOfTheWeek"() {
        given:
        ZipFileService zipFileService = new ZipFileService()

        when:
        int result = zipFileService.getDayOfWeek(index)

        then:
        result == expectedResult

        where:
        index | expectedResult
        1     | 2
        2     | 3
        3     | 4
        4     | 5
        5     | 6
        6     | 7
        7     | 1
    }

    def "transformIntoCalendars"() {
        given:
        String[] splittedLine = ["4", "0", "1", "0", "1", "0", "0", "1"]
        ZipFileService zipFileService = new ZipFileService()

        when:
        List<ServiceDay> result = zipFileService.transformIntoCalendars(splittedLine)

        then:
        result.size() == 3
        result.any { it.serviceId == "4" && it.dayOfWeek == 3 }
        result.any { it.serviceId == "4" && it.dayOfWeek == 5 }
        result.any { it.serviceId == "4" && it.dayOfWeek == 1 }

    }
}
