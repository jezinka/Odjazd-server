package com.jezinka.odjazd.service

import com.jezinka.odjazd.model.ServiceDay
import spock.lang.Specification

class ZipFileServiceSpec extends Specification {

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
